package advertisement;

import advertisement.exceptions.ModelNotFoundException;
import advertisement.model.*;
import advertisement.storage.AdvertisementStorage;
import advertisement.storage.UserStorage;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AdvertisementMain implements MainCommands, UserCommands {

    private static Scanner scanner = new Scanner(System.in);
    private static AdvertisementStorage advertisementStorage = new AdvertisementStorage();
    private static UserStorage userStorage = new UserStorage();
    private static User currentUser;

    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            MainCommands.printMainCommands();
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
            System.out.print("Gender: ");
            user.setGender(Gender.valueOf(scanner.nextLine().toUpperCase()));
            System.out.print("Age: ");
            user.setAge(Integer.parseInt(scanner.nextLine()));
            System.out.print("Phone Number: ");
            user.setPhoneNumber(scanner.nextLine());
            System.out.print("Password: ");
            user.setPassword(scanner.nextLine());
            userStorage.add(user);
            System.out.println("Thanks you are Registered!");
        } catch (Exception e) {
            System.out.println("Invalid data!");
            registerUser();
        }
    }

    private static void loginUser() {
        if (userStorage.isEmty()) {
            System.out.println("Please Register first!");
            return;
        }
        System.out.println("Please input Phone Number & Password to Login");
        System.out.print("Phone Number: ");
        String userPhone = scanner.nextLine();
        System.out.print("Password: ");
        String userPassword = scanner.nextLine();
        User user = userStorage.getUser(userPhone);
        if (user != null && user.getPassword().equals(userPassword)) {
            currentUser = user;
            System.out.println("Welcome ! " + user.getName() + " " + user.getSurname());
            loginedUser();
        } else {
            System.out.println("Ypu entered wrong Phone Number or password");
        }


    }

    private static void loginedUser() {
        boolean isRun = true;
        while (isRun) {
            UserCommands.printUserCommands();
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
                    advertisementStorage.printAdsByUser(currentUser);
                    break;
                case PRINT_ALL_ADS:
                    advertisementStorage.print();
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
                    advertisementStorage.deleteAdsByAuthor(currentUser);
                    System.out.println("All advertisements are deleted!");
                    break;
                case DELETE_AD_BY_TITLE:
                    deleteAdByTitle();
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
            Advertisement advertisement = new Advertisement();
            advertisement.setAuthor(currentUser);
            System.out.print("Category: ");
            try {
                advertisement.setCategory(Category.valueOf(scanner.nextLine().toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid category!");
                add();
            }
            System.out.print("Title: ");
            advertisement.setTitle(scanner.nextLine());
            System.out.print("Text: ");
            advertisement.setText(scanner.nextLine());
            System.out.print("Price: ");
            advertisement.setPrice(Double.parseDouble(scanner.nextLine()));
            advertisement.setCreatedDate(new Date());
            advertisementStorage.add(advertisement);
            advertisementStorage.print();
        } catch (Exception e) {
            System.out.println("Invalid data!");
            add();
        }
    }

    private static void printByCategory() {
        System.out.println("Please choose category name from list: " + Arrays.toString(Category.values()));
        try {
            Category category = Category.valueOf(scanner.nextLine().toUpperCase());
            advertisementStorage.getByCategory(category);
        } catch (ModelNotFoundException e) {
            e.getMessage();
        } catch (IllegalArgumentException e) {
            System.out.println("Entered category does not exist!!");
            printByCategory();
        }
    }

    private static void sortAdsByDate() {
        List<Advertisement> sortedListByDate = advertisementStorage.getAdList();
        if (sortedListByDate.size() >= 2) {
            advertisementStorage.sortAdsByDate();
        } else {
            System.out.println("You added only one advertisement, please add another for sorting");
            add();
        }
    }

    private static void sortAdsByTitle() {
        List<Advertisement> sortedListByTitle = advertisementStorage.getAdList();
        if (sortedListByTitle.size() >= 2) {
            advertisementStorage.sortAdsByTitle();
        } else {
            System.out.println("You added only one advertisement, please add another for sorting");
            add();
        }
    }


    private static void deleteAdByTitle() {
        System.out.println("Input Advertisement title to delete");
        advertisementStorage.printAdsByUser(currentUser);
        String advTitle = scanner.nextLine();
        Advertisement advbyTitle = advertisementStorage.getByTitle(advTitle);
        if (advbyTitle != null && advbyTitle.getAuthor().equals(currentUser)) {
            advertisementStorage.deleteAdByTitle(advbyTitle);
        } else {
            System.out.println("Wrong title!");
        }
    }

}






