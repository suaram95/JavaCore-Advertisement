package advertisement.main;

import advertisement.exceptions.ModelNotFoundException;
import advertisement.model.Advertisement;
import advertisement.model.Category;
import advertisement.model.Gender;
import advertisement.model.User;
import advertisement.storage.AdvertisementStorage;
import advertisement.storage.UserStorage;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AdvertisementForArmenian implements Commands {

    private static Scanner scanner = new Scanner(System.in);
    private static AdvertisementStorage advertisementStorage = new AdvertisementStorage();
    private static UserStorage userStorage = new UserStorage();
    private static User currentUser;

    public static void mainPart() {
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
            userStorage.add(user);
            System.out.println("\nՇնորհակալություն, դուք գրանցված եք!\n");
        } catch (Exception e) {
            System.out.println("Սխալ տվյալ!");
            registerUser();
        }
    }

    private static void loginUser() {
        if (userStorage.isEmty()) {
            System.out.println("Խնդրում ենք սկզբից գրանցվեք!");
            return;
        }
        System.out.println("Խնդրում ենք մուտքագրել Հեռախոսահամարը և Գաղտանաբառը");
        System.out.print("Հեռախոսահամար: ");
        String userPhone = scanner.nextLine();
        System.out.print("Գաղտանաբառ: ");
        String userPassword = scanner.nextLine();
        User user = userStorage.getUser(userPhone);
        if (user != null && user.getPassword().equals(userPassword)) {
            currentUser = user;
            System.out.println("\nԲարի Գալուստ ! " + user.getName() + " " + user.getSurname()+"\n");
            loginedUser();
        } else {
            System.out.println("Մուտքագրված Հեռախոսահամարը կամ գաղտնաբառը սխալ է");
        }


    }

    private static void loginedUser() {
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
                    System.out.println("Բոլոր հայտարարությունները հեռացված են!");
                    break;
                case DELETE_AD_BY_TITLE:
                    deleteAdByTitle();
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
            Advertisement advertisement = new Advertisement();
            advertisement.setAuthor(currentUser);
            System.out.print("Կատեգորիա: ");
            try {
                advertisement.setCategory(Category.valueOf(scanner.nextLine().toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Սխալ կատեգորիա!");
                add();
            }
            System.out.print("Վերանգիր: ");
            advertisement.setTitle(scanner.nextLine());
            System.out.print("Տեքստ: ");
            advertisement.setText(scanner.nextLine());
            System.out.print("Գին: ");
            advertisement.setPrice(Double.parseDouble(scanner.nextLine()));
            advertisement.setCreatedDate(new Date());
            advertisementStorage.add(advertisement);
            advertisementStorage.print();
        } catch (Exception e) {
            System.out.println("Սխալ տվյալ!");
            add();
        }
    }

    private static void printByCategory() {
        System.out.println("Խնդրում ենք ընտրել կատեգորիան հետևյալ ցուցակից: " + Arrays.toString(Category.values()));
        try {
            Category category = Category.valueOf(scanner.nextLine().toUpperCase());
            advertisementStorage.getByCategory(category);
        } catch (ModelNotFoundException e) {
            e.getMessage();
        } catch (IllegalArgumentException e) {
            System.out.println("Մուտքագրված կատեգորիան գոյություն չունի!!");
            printByCategory();
        }
    }

    private static void sortAdsByDate() {
        List<Advertisement> sortedListByDate = advertisementStorage.getAdList();
        if (sortedListByDate.size() >= 2) {
            advertisementStorage.sortAdsByDate();
        } else {
            System.out.println("Դուք ավելացրել եք միայն մեկ հայտարարություն, խնդրում ենք ավելացնել մեկ ուրիշը, տեսակավորելու համար");
            add();
        }
    }

    private static void sortAdsByTitle() {
        List<Advertisement> sortedListByTitle = advertisementStorage.getAdList();
        if (sortedListByTitle.size() >= 2) {
            advertisementStorage.sortAdsByTitle();
        } else {
            System.out.println("Դուք ավելացրել եք միայն մեկ հայտարարություն, խնդրում ենք ավելացնել մեկ ուրիշը, տեսակավորելու համար");
            add();
        }
    }


    private static void deleteAdByTitle() {
        System.out.println("Մուտքագրեք հայտարարության վերանգիրը ջնջելու համար");
        advertisementStorage.printAdsByUser(currentUser);
        String advTitle = scanner.nextLine();
        Advertisement advbyTitle = advertisementStorage.getByTitle(advTitle);
        if (advbyTitle != null && advbyTitle.getAuthor().equals(currentUser)) {
            advertisementStorage.deleteAdByTitle(advbyTitle);
        } else {
            System.out.println("Մուտքագրված վերանգիրը սխալ է!");
        }
    }

}






