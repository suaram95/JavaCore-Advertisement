package advertisement.main;

import advertisement.exceptions.ModelNotFoundException;
import advertisement.model.Category;
import advertisement.model.Gender;
import advertisement.model.Item;
import advertisement.model.User;
import advertisement.storage.ItemStorage;
import advertisement.storage.UserStorage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ItemForArmenian implements Commands {

    private static Scanner scanner = new Scanner(System.in);
    private static ItemStorage itemStorage = new ItemStorage();
    private static UserStorage userStorage = new UserStorage();
    private static User currentUser;

    public static void mainPart() {
        try {
            userStorage.initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isRun = true;
        while (isRun) {
            Commands.printMainCommandsArm();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case EXIT:
                    isRun = false;
                    System.out.println("Ծրագիրը փակված է");
                    break;
                case LOGIN:
                    loginUser();
                    break;
                case REGISTER:
                    registerUser();
                    break;
                case IMPORT_USERS:
                    importUsersFromFile();
                    break;
                default:
                    System.err.println("Սխալ հրաման!!");
            }
        }
    }

    //Main part

    private static void registerUser() {
        System.out.println("Գրանցման համար լրացրեք տվյալները");
        try {
            User user = new User();
            System.out.print("Անուն: ");
            user.setName(scanner.nextLine());
            System.out.print("Ազգանուն: ");
            user.setSurname(scanner.nextLine());
            System.out.print("Սեռ (Արական կամ Իգական): ");
            user.setGender(Gender.valueOf(scanner.nextLine().toUpperCase()));
            System.out.print("Տարիք: ");
            user.setAge(Integer.parseInt(scanner.nextLine()));
            System.out.print("Հեռախոսահամար: ");
            user.setPhoneNumber(scanner.nextLine());
            System.out.print("Գաղտնաբառ: ");
            user.setPassword(scanner.nextLine());
            if (userStorage.getUser(user.getPhoneNumber()) != null) {
                System.out.println(user.getPhoneNumber() + "հեռախոսահամարով օգտատեր արդեն գոյութոյւն ունի");
                registerUser();
            } else {
                userStorage.add(user);
                System.out.println("\nՇնորհակալություն դուք գրանցված եք!\n");
                userStorage.printUsers();
            }
        } catch (Exception e) {
            System.out.println("Սխալ տվյալ!");
            registerUser();
        }
    }


    private static void importUsersFromFile() {
        System.out.println("Մուտքագրեք ֆայլի հասցեն");
        String filePath = scanner.nextLine();
        try {
            XSSFWorkbook workbook=new XSSFWorkbook(filePath);
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 0; i <=lastRowNum; i++) {
                Row row =sheet.getRow(i);
                String name = row.getCell(0).getStringCellValue();
                String surname = row.getCell(1).getStringCellValue();
                Gender gender=Gender.valueOf(row.getCell(2).getStringCellValue().toUpperCase());
                double age = row.getCell(3).getNumericCellValue();

                Cell phoneNumber=row.getCell(4);
                String phoneNumberStr = phoneNumber.getCellType()== CellType.NUMERIC ?
                        String.valueOf(row.getCell(4).getNumericCellValue()): row.getCell(4).getStringCellValue();

                String password = row.getCell(5).getStringCellValue();
                User user=new User();
                user.setName(name);
                user.setSurname(surname);
                user.setGender(gender);
                user.setAge(Double.valueOf(age).intValue());
                user.setPhoneNumber(phoneNumberStr);
                user.setPassword(password);
                System.out.println(user);
                userStorage.add(user);
            }
            System.out.println("Ներբեռնումը հաջողվել է");

        } catch (IOException e) {
            System.out.println("Ներբեռնման ժամանակ տեղի է ունեցել սխալ");
        }

    }

    private static void loginUser() {
        if (userStorage.isEmty()) {
            System.out.println("Խնդրում ենք սկզբից գրանցվեք!");
            registerUser();
        }
        System.out.println("Խնդրում ենք մուտքագրել Հեռախոսահամարը և Գաղտնաբառը");
        System.out.print("Հեռախոսահամար: ");
        String userPhone = scanner.nextLine();
        System.out.print("Գաղտանաբառ: ");
        String userPassword = scanner.nextLine();
        User user = userStorage.getUser(userPhone);
        if (user != null && user.getPassword().equals(userPassword)) {
            currentUser = user;
            System.out.println("\nԲարի Գալուստ ! " + user.getName() + " " + user.getSurname() + "\n");
            loginedUser();
        } else {
            System.out.println("Մուտքագրված Հեռախոսահամարը կամ գաղտնաբառը սխալ է, կրկին փորձեք");
            loginUser();
        }


    }

    private static void loginedUser() {

        itemStorage.initData();

        boolean isRun = true;
        while (isRun) {
            Commands.printUserCommandsArm();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case LOGOUT:
                    isRun = false;
                    break;
                case ADD_NEW_AD:
                    add();
                    break;
                case PRINT_MY_ALL_ADS:
                    itemStorage.printAdsByUser(currentUser);
                    break;
                case PRINT_ALL_ADS:
                    itemStorage.print();
                    break;
                case PRINT_AD_BY_CATEGORY:
                    printByCategory();
                    break;
                case PRINT_ALL_ADS_BY_TITLE_SORT:
                    sortAdsByTitle();
                    break;
                case PRINT_ALL_ADS_BY_DATE_SORT:
                    sortAdsByDate();
                    break;
                case DELETE_MY_ALL_ADS:
                    itemStorage.deleteAdsByAuthor(currentUser);
                    System.out.println("Բոլոր հայտարարությունները հեռացված են!");
                    break;
                case DELETE_AD_BY_TITLE:
                    deleteById();
                    break;
                default:
                    System.err.println("Սխալ հրաման!!");
            }
        }
    }

    //User part

    private static void add() {
        System.out.println("Հայտարարությունը ավելացնելու համար լրացրեք հետևյալ դաշտերը");
        System.out.println("Խնդրում ենք ընտրել կատեգորիան հետևյալ ցուցակից: " + Arrays.toString(Category.values()));
        try {
            Item item = new Item();
            item.setAuthor(currentUser);
            System.out.print("Կատեգորիա: ");
            try {
                item.setCategory(Category.valueOf(scanner.nextLine().toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Սխալ կատեգորիա!");
                add();
            }
            System.out.print("Վերնագիր: ");
            item.setTitle(scanner.nextLine());
            System.out.print("Տեքստ: ");
            item.setText(scanner.nextLine());
            System.out.print("Գին: ");
            item.setPrice(Double.parseDouble(scanner.nextLine()));
            item.setCreatedDate(new Date());
            itemStorage.add(item);
            itemStorage.print();
        } catch (Exception e) {
            System.out.println("Սխալ տվյալ!");
            add();
        }
    }

    private static void printByCategory() {
        System.out.println("Խնդրում ենք ընտրել կատեգորիան հետևյալ ցուցակից: " + Arrays.toString(Category.values()));
        try {
            Category category = Category.valueOf(scanner.nextLine().toUpperCase());
            itemStorage.getByCategory(category);
        } catch (ModelNotFoundException e) {
            e.getMessage();
        } catch (IllegalArgumentException e) {
            System.out.println("Մուտքագրված կատեգորիան գոյություն չունի!!");
            printByCategory();
        }
    }

    private static void sortAdsByDate() {
        List<Item> sortedListByDate = itemStorage.getItems();
        if (sortedListByDate.size() >= 2) {
            itemStorage.sortAdsByDate();
        } else {
            System.out.println("Դուք ավելացրել եք միայն մեկ հայտարարություն, խնդրում ենք ավելացնել մեկ ուրիշը, տեսակավորելու համար");
            add();
        }
    }

    private static void sortAdsByTitle() {
        List<Item> sortedListByTitle = itemStorage.getItems();
        if (sortedListByTitle.size() >= 2) {
            itemStorage.sortAdsByTitle();
        } else {
            System.out.println("Դուք ավելացրել եք միայն մեկ հայտարարություն, խնդրում ենք ավելացնել մեկ ուրիշը, տեսակավորելու համար");
            add();
        }
    }


    private static void deleteById() {
        System.out.println("Մուտքագրեք հայտարարության ID-ն, այն ջնջելու համար");
        itemStorage.printAdsByUser(currentUser);
        long id = Long.parseLong(scanner.nextLine());
        Item itemById = itemStorage.getItemById(id);
        if (itemById != null && itemById.getAuthor().equals(currentUser)) {
            itemStorage.deleteItemsById(id);
        } else {
            System.out.println("Սխալ ID!");
        }
    }

}






