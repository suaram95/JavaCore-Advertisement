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

public class ItemForRussian implements Commands {

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
            if (userStorage.getUser(user.getPhoneNumber())!=null){
                System.out.println("Пользователь с Тел.Номером: "+user.getPhoneNumber()+" уже существует");
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
        try {
            itemStorage.initData();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
                    System.out.println("Все обьявления удалены!");
                    break;
                case DELETE_AD_BY_TITLE:
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






