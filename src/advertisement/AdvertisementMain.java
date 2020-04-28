package advertisement;

import advertisement.exceptions.ModelNotFoundException;
import advertisement.model.Advertisement;
import advertisement.model.Category;
import advertisement.model.Gender;
import advertisement.model.User;
import advertisement.storage.AdvertisementStorage;
import advertisement.storage.UserStorage;

import java.util.Date;
import java.util.Scanner;

public class AdvertisementMain implements MainCommands, UserCommands {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final AdvertisementStorage AD_STORAGE = new AdvertisementStorage();
    private static final UserStorage USER_STORAGE = new UserStorage();
    private static User logineduser;

    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            MainCommands.printMainCommands();
            int command;
            try {
                command = Integer.parseInt(SCANNER.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case EXIT:
                    isRun = false;
                    System.out.println("Program was closed");
                    break;
                case LOGIN:
                    login();
                    break;
                case REGISTER:
                    register();
                    break;
                default:
                    System.err.println("Wrong Command!!");
            }
        }
    }

    //Main part

    private static void register() {
        System.out.println("Input data for Registration");
        try {
            User user = new User();
            System.out.print("Name: ");
            user.setName(SCANNER.nextLine());
            System.out.print("Surname: ");
            user.setSurname(SCANNER.nextLine());
            System.out.print("Gender: ");
            user.setGender(Gender.valueOf(SCANNER.nextLine().toUpperCase()));
            System.out.print("Age: ");
            user.setAge(Integer.parseInt(SCANNER.nextLine()));
            System.out.print("Phone Number: ");
            user.setPhoneNumber(SCANNER.nextLine());
            System.out.print("Password: ");
            user.setPassword(SCANNER.nextLine());
            USER_STORAGE.add(user);
            System.out.println("Thanks you are Registered!");
        } catch (Exception e) {
            System.out.println("Invalid data!");
            register();
        }
    }

    private static void login() {
        if (USER_STORAGE.isEmty()) {
            System.out.println("Please Register first!");
            return;
        }
        System.out.println("Please input Phone Number & Password to Login");
        System.out.print("Phone Number: ");
        String userPhone = SCANNER.nextLine();
        System.out.print("Password: ");
        String userPassword = SCANNER.nextLine();

//      logineduser = USER_STORAGE.getUserbyPhoneAndPassword(userPhone, userPassword);
        System.out.println("You succesfully entered your profile\n");
        loginedUserCommands();

    }

    private static void loginedUserCommands() {
        boolean isRun = true;
        while (isRun) {
            UserCommands.printUserCommands();
            int command;
            command = Integer.parseInt(SCANNER.nextLine());
            switch (command) {
                case LOGOUT:
                    isRun = false;
                    break;
                case ADD_NEW_AD:
                    add();
                    break;
                case PRINT_MY_ALL_ADS:
                    AD_STORAGE.print();
                    break;
                case PRINT_ALL_ADS:
                    AD_STORAGE.print();
                    break;
                case PRINT_AD_BY_CATEGORY:
                    printByCategory();
                    break;
                case PRINT_ALL_ADS_BY_TITLE_SORT:
                    //todo
                    break;
                case PRINT_ALL_ADS_BY_DATE_SORT:
                    //todo
                    break;
                case DELETE_MY_ALL_ADS:
                    AD_STORAGE.deleteAllAds();
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
        Advertisement advertisement = new Advertisement();
        advertisement.setAuthor(logineduser);
        System.out.print("Category: ");
        advertisement.setCategory(Category.valueOf(SCANNER.nextLine().toUpperCase()));
        System.out.print("Title: ");
        advertisement.setTitle(SCANNER.nextLine());
        System.out.print("Text: ");
        advertisement.setText(SCANNER.nextLine());
        System.out.print("Price: ");
        advertisement.setPrice(Double.parseDouble(SCANNER.nextLine()));
        advertisement.setCreatedDate(new Date());
        AD_STORAGE.add(advertisement);
        AD_STORAGE.print();
    }

    private static void printByCategory() {
        System.out.println("Input name of Category to see advertisements");
        try {
            Category category = Category.valueOf(SCANNER.nextLine().toUpperCase());
            AD_STORAGE.getByCategory(category);
        } catch (ModelNotFoundException e) {
            e.getMessage();
            printByCategory();
        } catch (IllegalArgumentException e){
            System.out.println("Entered category does not exist!!");
            printByCategory();
        }
    }

    private static void deleteAdByTitle() {
        System.out.println("Input Advertisement title to delete");
        String adTitle = SCANNER.nextLine();
        try {
            AD_STORAGE.deleteAdByTitle(adTitle);
        } catch (ModelNotFoundException e) {
            e.getMessage();
        }
    }


}






