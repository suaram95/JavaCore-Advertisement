package advertisement.main;

import advertisement.exceptions.ModelNotFoundException;
import advertisement.model.Item;
import advertisement.model.Category;
import advertisement.model.Gender;
import advertisement.model.User;
import advertisement.storage.ItemStorage;
import advertisement.storage.UserStorage;

import java.io.IOException;
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
            try {
                userStorage.initData();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            userStorage.add(user);
            System.out.println("\nThanks you are Registered!\n");
        } catch (Exception e) {
            System.out.println("Invalid data!");
            registerUser();
        }
    }

    private static void loginUser() {
        if (userStorage.isEmty()) {
            System.out.println("Please Register first!");
            registerUser();
        }
        System.out.println("Please input Phone Number & Password to Login");
        System.out.print("Phone Number: ");
        String userPhone = scanner.nextLine();
        System.out.print("Password: ");
        String userPassword = scanner.nextLine();
        User user = userStorage.getUser(userPhone);
        if (user != null && user.getPassword().equals(userPassword)) {
            currentUser = user;
            System.out.println("\nWelcome ! " + user.getName() + " " + user.getSurname()+"\n");
            loginedUser();
        } else {
            System.out.println("You entered wrong Phone Number or password, try again");
            loginUser();
        }

    }

    private static void loginedUser() {
        try {
            itemStorage.initData();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
                    System.out.println("All advertisements are deleted!");
                    break;
                case DELETE_AD_BY_TITLE:
                    deleteById();
                    break;
                default:
                    System.err.println("Wrong Command!!");
            }
        }
    }

    //User part

    private static void add() {
        System.out.println("To add an advertisement fill in the fields below.");
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
            System.out.println("You added only one advertisement, please add another for sorting");
            add();
        }
    }

    private static void sortAdsByTitle() {
        List<Item> sortedListByTitle = itemStorage.getItems();
        if (sortedListByTitle.size() >= 2) {
            itemStorage.sortAdsByTitle();
        } else {
            System.out.println("You added only one advertisement, please add another for sorting");
            add();
        }
    }


    private static void deleteById() {
        System.out.println("Input Advertisement ID to delete");
        itemStorage.printAdsByUser(currentUser);
        long id = Long.parseLong(scanner.nextLine());
        Item itemById = itemStorage.getItemById(id);
        if (itemById!=null && itemById.getAuthor().equals(currentUser)){
            itemStorage.deleteItemsById(id);
        } else {
            System.out.println("Wrong Id");
        }
    }

}






