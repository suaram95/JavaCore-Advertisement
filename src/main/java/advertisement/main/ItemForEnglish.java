package advertisement.main;

import advertisement.exceptions.ModelNotFoundException;
import advertisement.model.Category;
import advertisement.model.Gender;
import advertisement.model.Item;
import advertisement.model.User;
import advertisement.storage.ItemStorage;
import advertisement.storage.UserStorage;
import advertisement.util.XLSXUtil;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ItemForEnglish implements Commands {

    private static Scanner scanner = new Scanner(System.in);
    private static ItemStorage itemStorage = new ItemStorage();
    private static UserStorage userStorage = new UserStorage();
    private static User currentUser;

    public static void mainPart() {
        boolean isRun = true;
        while (isRun) {

            userStorage.initData();

            Commands.printMainCommandsEng();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case EXIT:
                    isRun = false;
                    System.out.println("Program was closed");
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
                    System.err.println("Wrong Command!!");
            }
        }
    }

    //Main part

    private static void registerUser() {
        System.out.println("Input data for Registration");
        try {
            User user = new User();
            System.out.print("Name: ");
            user.setName(scanner.nextLine());
            System.out.print("Surname: ");
            user.setSurname(scanner.nextLine());
            System.out.print("Gender (Male or Female): ");
            user.setGender(Gender.valueOf(scanner.nextLine().toUpperCase()));
            System.out.print("Age: ");
            user.setAge(Integer.parseInt(scanner.nextLine()));
            System.out.print("Phone Number: ");
            user.setPhoneNumber(scanner.nextLine());
            System.out.print("Password: ");
            user.setPassword(scanner.nextLine());
            if (userStorage.getUser(user.getPhoneNumber()) != null) {
                System.out.println("User with Phone Number: " + user.getPhoneNumber() + " already exists");
                registerUser();
            } else {
                userStorage.add(user);
                System.out.println("\nThanks you are Registered!\n");
                userStorage.printUsers();
            }
        } catch (Exception e) {
            System.out.println("Invalid data!");
            registerUser();
        }

    }

    private static void importUsersFromFile() {
        System.out.println("Please select file path to import users data");
        String filePath  = scanner.nextLine();
        List<User> users = XLSXUtil.readUser(filePath);
        for (User user : users) {
            userStorage.add(user);
        }
    }

    private static void loginUser() {
        if (userStorage.isEmty()) {
            System.out.println("Please Register first!");
            registerUser();
        }
        System.out.println("\nPlease input Phone Number & Password to Login");
        System.out.print("Phone Number: ");
        String userPhone = scanner.nextLine();
        System.out.print("Password: ");
        String userPassword = scanner.nextLine();
        User user = userStorage.getUser(userPhone);
        if (user != null && user.getPassword().equals(userPassword)) {
            currentUser = user;
            System.out.println("\nWelcome ! " + user.getName() + " " + user.getSurname() + "\n");
            loginedUser();
        } else {

            System.out.println("You entered wrong Phone Number or password, try again");
            loginUser();
        }

    }



    private static void loginedUser() {

        itemStorage.initData();

        boolean isRun = true;
        while (isRun) {
            Commands.printUserCommandsEng();
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
                    System.out.println("All items are deleted!");
                    break;
                case DELETE_ITEM_BY_TITLE:
                    deleteById();
                    break;
                default:
                    System.err.println("Wrong Command!!");
            }
        }
    }



    //User part

    private static void add() {
        System.out.println("To add an item fill in the fields below.");
        System.out.println("Please choose category name from list: " + Arrays.toString(Category.values()));
        try {
            Item item = new Item();
            item.setAuthor(currentUser);
            System.out.print("Category: ");
            try {
                item.setCategory(Category.valueOf(scanner.nextLine().toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid category!");
                add();
            }
            System.out.print("Title: ");
            item.setTitle(scanner.nextLine());
            System.out.print("Text: ");
            item.setText(scanner.nextLine());
            System.out.print("Price: ");
            item.setPrice(Double.parseDouble(scanner.nextLine()));
            item.setCreatedDate(new Date());
            itemStorage.add(item);
            itemStorage.print();
        } catch (Exception e) {
            System.out.println("Invalid data!");
            add();
        }
    }


    private static void importItemsFromFile() {
        System.out.println("Please input excel file path");
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
            System.out.println("Import has success!");
        } else {
            System.out.println("Please input valid path");
        }
    }

    private static void exportItemstoFile() {
        System.out.println("Please input folder path");
        String path  = scanner.nextLine();
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(path);

                if (file.exists()&& file.isDirectory()){
                    XLSXUtil.writeItems(path,itemStorage.getItems());
                } else {
                    System.out.println("Please input valid path");
                }
            }
        }).start();
        System.out.println("Export has success!");
    }


    private static void printByCategory() {
        System.out.println("Please choose category name from list: " + Arrays.toString(Category.values()));
        try {
            Category category = Category.valueOf(scanner.nextLine().toUpperCase());
            itemStorage.getByCategory(category);
        } catch (ModelNotFoundException e) {
            e.getMessage();
        } catch (IllegalArgumentException e) {
            System.out.println("Entered category does not exist!!");
            printByCategory();
        }
    }

    private static void sortAdsByDate() {
        List<Item> sortedListByDate = itemStorage.getItems();
        if (sortedListByDate.size() >= 2) {
            itemStorage.sortAdsByDate();
        } else {
            System.out.println("You added only one item, please add another for sorting");
            add();
        }
    }

    private static void sortAdsByTitle() {
        List<Item> sortedListByTitle = itemStorage.getItems();
        if (sortedListByTitle.size() >= 2) {
            itemStorage.sortAdsByTitle();
        } else {
            System.out.println("You added only one item, please add another for sorting");
            add();
        }
    }


    private static void deleteById() {
        System.out.println("Input item ID to delete");
        itemStorage.printAdsByUser(currentUser);
        long id = Long.parseLong(scanner.nextLine());
        Item itemById = itemStorage.getItemById(id);
        if (itemById != null && itemById.getAuthor().equals(currentUser)) {
            itemStorage.deleteItemsById(id);
        } else {
            System.out.println("Wrong Id");
        }
    }

}






