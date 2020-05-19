package advertisement.main;

import advertisement.exceptions.ModelNotFoundException;
import advertisement.model.Item;
import advertisement.model.Category;
import advertisement.model.Gender;
import advertisement.model.User;
import advertisement.storage.ItemStorage;
import advertisement.storage.UserStorage;
import advertisement.util.XLSXUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ItemForRussian implements Commands {

    private static Scanner scanner = new Scanner(System.in);
    private static ItemStorage itemStorage = new ItemStorage();
    private static UserStorage userStorage = new UserStorage();
    private static User currentUser;

    public static void mainPart() {
        userStorage.initData();

        boolean isRun = true;
        while (isRun) {
            Commands.printMainCommandsRus();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case EXIT:
                    isRun = false;
                    System.out.println("Программа закрыта");
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
                    System.err.println("Неверная команда!!");
            }
        }
    }


    //Main part

    private static void registerUser() {
        System.out.println("Вводите данные для регистрации");
        try {
            User user = new User();
            System.out.print("Имя: ");
            user.setName(scanner.nextLine());
            System.out.print("Фамилия: ");
            user.setSurname(scanner.nextLine());
            System.out.print("Пол (Мужской или Женский): ");
            user.setGender(Gender.valueOf(scanner.nextLine().toUpperCase()));
            System.out.print("Возраст: ");
            user.setAge(Integer.parseInt(scanner.nextLine()));
            System.out.print("Тел.Номер: ");
            user.setPhoneNumber(scanner.nextLine());
            System.out.print("Пароль: ");
            user.setPassword(scanner.nextLine());
            if (userStorage.getUser(user.getPhoneNumber()) != null) {
                System.out.println("Пользователь с Тел.Номером: " + user.getPhoneNumber() + " уже существует");
                registerUser();
            } else {
                userStorage.add(user);
                System.out.println("\nСпасибо вы зарегистрированы!\n");
                userStorage.printUsers();
            }
        } catch (Exception e) {
            System.out.println("Недействительные данные!");
            registerUser();
        }
    }

    private static void importUsersFromFile() {
        System.out.println("Введите адрес файла");
        String filePath = scanner.nextLine();
        try {
            XSSFWorkbook workbook=new XSSFWorkbook(filePath);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 0; i <=lastRowNum ; i++) {
                Row row= sheet.getRow(i);
                String name = row.getCell(0).getStringCellValue();
                String surname = row.getCell(1).getStringCellValue();
                Gender gender=Gender.valueOf(row.getCell(2).getStringCellValue().toUpperCase());
                double age=row.getCell(3).getNumericCellValue();

                Cell phoneNumber=row.getCell(4);
                String phoneNumberStr=phoneNumber.getCellType()== CellType.NUMERIC?
                        String.valueOf(row.getCell(4).getNumericCellValue()): row.getCell(4).getStringCellValue();
                String password = row.getCell(5).getStringCellValue();

                User user=new User();
                user.setName(name);
                user.setSurname(surname);
                user.setGender(gender);
                user.setAge(Double.valueOf(age).intValue());
                user.setPhoneNumber(phoneNumberStr);
                user.setPassword(password);
                userStorage.add(user);
                System.out.println("Импорт удался!");
            }
        } catch (IOException e) {
            System.out.println("Импорт файла не удался");
        }
    }

    private static void loginUser() {
        if (userStorage.isEmty()) {
            System.out.println("Пожалуйста сперва регистрируйтесь!");
            registerUser();
        }
        System.out.println("Пожалуйста введите Тел.Номер и Пароль для входа");
        System.out.print("Тел.Номер: ");
        String userPhone = scanner.nextLine();
        System.out.print("Пароль: ");
        String userPassword = scanner.nextLine();
        User user = userStorage.getUser(userPhone);
        if (user != null && user.getPassword().equals(userPassword)) {
            currentUser = user;
            System.out.println("\nДобро Пажаловать ! " + user.getName() + " " + user.getSurname() + "\n");
            loginedUser();
        } else {
            System.out.println("Вы ввели неправилний Тел.Номер или Пароль, попробуйте снова");
            loginUser();
        }
    }

    private static void loginedUser() {

        itemStorage.initData();

        boolean isRun = true;
        while (isRun) {
            Commands.printUserCommandsRus();
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
                case ADD_NEW_ITEM:
                    add();
                    break;
                case IMPORT_ITEMS:
                    importItemsFromFile();
                    break;
                case EXPORT_ITEMS:
                    exportItemstoFile();
                    break;
                case PRINT_MY_ALL_ITEMS:
                    itemStorage.printAdsByUser(currentUser);
                    break;
                case PRINT_ALL_ITEMS:
                    itemStorage.print();
                    break;
                case PRINT_ITEMS_BY_CATEGORY:
                    printByCategory();
                    break;
                case PRINT_ALL_ITEMS_BY_TITLE_SORT:
                    sortAdsByTitle();
                    break;
                case PRINT_ALL_ITEMS_BY_DATE_SORT:
                    sortAdsByDate();
                    break;
                case DELETE_MY_ALL_ITEMS:
                    itemStorage.deleteAdsByAuthor(currentUser);
                    System.out.println("Все обьявления удалены!");
                    break;
                case DELETE_ITEM_BY_TITLE:
                    deleteById();
                    break;
                default:
                    System.err.println("Неверная команда!!");
            }
        }
    }



    //User part

    private static void add() {
        System.out.println("Для добавления обьявления заполните следуюшие поля");
        System.out.println("Пожалуйста выберите категорию из этого листа: " + Arrays.toString(Category.values()));
        try {
            Item item = new Item();
            item.setAuthor(currentUser);
            System.out.print("Категория: ");
            try {
                item.setCategory(Category.valueOf(scanner.nextLine().toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Неверная категория!");
                add();
            }
            System.out.print("Заголовка: ");
            item.setTitle(scanner.nextLine());
            System.out.print("Текст: ");
            item.setText(scanner.nextLine());
            System.out.print("Цена: ");
            item.setPrice(Double.parseDouble(scanner.nextLine()));
            item.setCreatedDate(new Date());
            itemStorage.add(item);
            itemStorage.print();
        } catch (Exception e) {
            System.out.println("Неверная команда!!");
            add();
        }
    }

    private static void importItemsFromFile() {
        System.out.println("Пожалуйста введите адрес файла");
        String path  = scanner.nextLine();
        File file = new File(path);
        if (file.exists()&& file.isFile()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Item> items = XLSXUtil.readItems(path);
                    for (Item item : items) {
                        item.setAuthor(userStorage.getUser(item.getAuthor().getPhoneNumber()));
                        itemStorage.add(item);
                    }
                }
            }).start();
            System.out.println("Импорт удался!");
        } else {
            System.out.println("Пожалуйста введите правильный адрес");
        }
    }

    private static void exportItemstoFile() {
        System.out.println("Пожалуйста введите адрес папки");
        String path  = scanner.nextLine();
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(path);

                if (file.exists()&& file.isDirectory()){
                    XLSXUtil.writeItems(path,itemStorage.getItems());
                } else {
                    System.out.println("Пожалуйста введите правильный адрес");
                }
            }
        }).start();
        System.out.println("Экспорт удался!");
    }

    private static void printByCategory() {
        System.out.println("Пожалуйста выберите категорию из этого листа: " + Arrays.toString(Category.values()));
        try {
            Category category = Category.valueOf(scanner.nextLine().toUpperCase());
            itemStorage.getByCategory(category);
        } catch (ModelNotFoundException e) {
            e.getMessage();
        } catch (IllegalArgumentException e) {
            System.out.println("Введенная категория не сушествует!!");
            printByCategory();
        }
    }

    private static void sortAdsByDate() {
        List<Item> sortedListByDate = itemStorage.getItems();
        if (sortedListByDate.size() >= 2) {
            itemStorage.sortAdsByDate();
        } else {
            System.out.println("Вы добавили только одно обьявление, пожалуйста добавьте еще одну для сортировки");
            add();
        }
    }

    private static void sortAdsByTitle() {
        List<Item> sortedListByTitle = itemStorage.getItems();
        if (sortedListByTitle.size() >= 2) {
            itemStorage.sortAdsByTitle();
        } else {
            System.out.println("Вы добавили только одно обьявление, пожалуйста добавьте еще одну для сортировки");
            add();
        }
    }


    private static void deleteById() {

        System.out.println("Вводите ID обьявления для удаления");
        itemStorage.printAdsByUser(currentUser);
        long id = Long.parseLong(scanner.nextLine());
        Item itemById = itemStorage.getItemById(id);
        if (itemById != null && itemById.getAuthor().equals(currentUser)) {
            itemStorage.deleteItemsById(id);
        } else {
            System.out.println("Неверний ID!");
        }
    }

}






