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

public class AdvertisementForRussian implements Commands {

    private static Scanner scanner = new Scanner(System.in);
    private static AdvertisementStorage advertisementStorage = new AdvertisementStorage();
    private static UserStorage userStorage = new UserStorage();
    private static User currentUser;

    public static void mainPart() {
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
            userStorage.add(user);
            System.out.println("\nСпасибо вы зарегистрированы!\n");
        } catch (Exception e) {
            System.out.println("Недействительные данные!");
            registerUser();
        }
    }

    private static void loginUser() {
        if (userStorage.isEmty()) {
            System.out.println("Пожалуйста сперва регистрируйтесь!");
            return;
        }
        System.out.println("Пожалуйста введите Тел.Номер и Пароль для входа");
        System.out.print("Тел.Номер: ");
        String userPhone = scanner.nextLine();
        System.out.print("Пароль: ");
        String userPassword = scanner.nextLine();
        User user = userStorage.getUser(userPhone);
        if (user != null && user.getPassword().equals(userPassword)) {
            currentUser = user;
            System.out.println("\nДобро Пажаловать ! " + user.getName() + " " + user.getSurname()+"\n");
            loginedUser();
        } else {
            System.out.println("Вы ввели неправилний Тел.Номер или Пароль");
        }
    }

    private static void loginedUser() {
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
                    System.out.println("Все обьявления удалены!");
                    break;
                case DELETE_AD_BY_TITLE:
                    deleteAdByTitle();
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
            Advertisement advertisement = new Advertisement();
            advertisement.setAuthor(currentUser);
            System.out.print("Категория: ");
            try {
                advertisement.setCategory(Category.valueOf(scanner.nextLine().toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Неверная категория!");
                add();
            }
            System.out.print("Заголовка: ");
            advertisement.setTitle(scanner.nextLine());
            System.out.print("Текст: ");
            advertisement.setText(scanner.nextLine());
            System.out.print("Цена: ");
            advertisement.setPrice(Double.parseDouble(scanner.nextLine()));
            advertisement.setCreatedDate(new Date());
            advertisementStorage.add(advertisement);
            advertisementStorage.print();
        } catch (Exception e) {
            System.out.println("Неверная команда!!");
            add();
        }
    }

    private static void printByCategory() {
        System.out.println("Пожалуйста выберите категорию из этого листа: " + Arrays.toString(Category.values()));
        try {
            Category category = Category.valueOf(scanner.nextLine().toUpperCase());
            advertisementStorage.getByCategory(category);
        } catch (ModelNotFoundException e) {
            e.getMessage();
        } catch (IllegalArgumentException e) {
            System.out.println("Введенная категория не сушествует!!");
            printByCategory();
        }
    }

    private static void sortAdsByDate() {
        List<Advertisement> sortedListByDate = advertisementStorage.getAdList();
        if (sortedListByDate.size() >= 2) {
            advertisementStorage.sortAdsByDate();
        } else {
            System.out.println("Вы добавили только одно обьявление, пожалуйста добавьте еще одну для сортировки");
            add();
        }
    }

    private static void sortAdsByTitle() {
        List<Advertisement> sortedListByTitle = advertisementStorage.getAdList();
        if (sortedListByTitle.size() >= 2) {
            advertisementStorage.sortAdsByTitle();
        } else {
            System.out.println("Вы добавили только одно обьявление, пожалуйста добавьте еще одну для сортировки");
            add();
        }
    }


    private static void deleteAdByTitle() {
        System.out.println("Вводите заголовок категории для удаления");
        advertisementStorage.printAdsByUser(currentUser);
        String advTitle = scanner.nextLine();
        Advertisement advbyTitle = advertisementStorage.getByTitle(advTitle);
        if (advbyTitle != null && advbyTitle.getAuthor().equals(currentUser)) {
            advertisementStorage.deleteAdByTitle(advbyTitle);
        } else {
            System.out.println("Неверний заголовок!");
        }
    }

}






