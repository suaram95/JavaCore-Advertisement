package advertisement.main;

public interface Commands {

    //Commands part
    int EXIT = 0;
    int ARMENIAN = 1;
    int ENGLISH = 2;
    int RUSSIAN = 3;

    int LOGIN = 1;
    int REGISTER = 2;
    int IMPORT_USERS = 3;

    int LOGOUT = 0;
    int ADD_NEW_ITEM = 1;
    int IMPORT_ITEMS = 2;
    int EXPORT_ITEMS = 3;
    int PRINT_MY_ALL_ITEMS = 4;
    int PRINT_ALL_ITEMS = 5;
    int PRINT_ITEMS_BY_CATEGORY = 6;
    int PRINT_ALL_ITEMS_BY_TITLE_SORT = 7;
    int PRINT_ALL_ITEMS_BY_DATE_SORT = 8;
    int DELETE_MY_ALL_ITEMS = 9;
    int DELETE_ITEM_BY_TITLE = 10;


    //Language choose part

    static void printCommandsForLanguage() {
        System.out.println("Հայերենի համար սեղմեք: " + ARMENIAN);
        System.out.println("For English input: " + ENGLISH);
        System.out.println("Для русского языка нажмите: " + RUSSIAN);
        System.out.println("----------------------------------------");
        System.out.println("Դուրս գալու համար սեղմեք: " + EXIT);
        System.out.println("For Exit input: " + EXIT);
        System.out.println("Для выхода нажмите: " + EXIT);
    }


    //Armenian part

    static void printMainCommandsArm() {
        System.out.println("Սեղմեք " + EXIT + " Ելքի համար");
        System.out.println("Սեղմեք " + LOGIN + " Մուտք գործելու համար");
        System.out.println("Սեղմեք " + REGISTER + " Գրանցման համար");
        System.out.println("Սեղմեք " + IMPORT_USERS + " Օգտագործողների տվյալները ներբեռնելու համար");
    }

    static void printUserCommandsArm() {
        System.out.println("Սեղմեք " + LOGOUT + " Ելքի համար");
        System.out.println("Սեղմեք " + ADD_NEW_ITEM + " նոր հայտարարություն ավելացնելու համար");
        System.out.println("Սեղմեք " + IMPORT_ITEMS + " հայտարարությունները ֆայլից ներբեռնելու համար");
        System.out.println("Սեղմեք " + EXPORT_ITEMS + " հայտարարությունները ֆայլ վերբեռնելու համար");
        System.out.println("Սեղմեք " + PRINT_MY_ALL_ITEMS + " ձեր բոլոր հայտարարությունները տեսնելու համար");
        System.out.println("Սեղմեք " + PRINT_ALL_ITEMS + " բոլոր հայտարարությունները տեսնելու համար");
        System.out.println("Սեղմեք " + PRINT_ITEMS_BY_CATEGORY + " հայտարարությունները ըստ կատեգորիաների տեսնելու համար");
        System.out.println("Սեղմեք " + PRINT_ALL_ITEMS_BY_TITLE_SORT + " հայտարարությունները ըստ վերնագրերի տեսակավորելու համար");
        System.out.println("Սեղմեք " + PRINT_ALL_ITEMS_BY_DATE_SORT + " հայտարարությունները ըստ ավելացման օրվա տեսակավորելու համար");
        System.out.println("Սեղմեք " + DELETE_MY_ALL_ITEMS + " ձեր բոլոր հայտարարությունները ջնջելու համար");
        System.out.println("Սեղմեք " + DELETE_ITEM_BY_TITLE + " հայտարարաությունը ըստ տրված ID-ի");
    }


    //English part

    static void printMainCommandsEng() {
        System.out.println("Input " + EXIT + " to Exit");
        System.out.println("Input " + LOGIN + " to Login");
        System.out.println("Input " + REGISTER + " to Register");
        System.out.println("Input " + IMPORT_USERS + " to Import Users");

    }

    static void printUserCommandsEng() {
        System.out.println("Input " + LOGOUT + " to Logout");
        System.out.println("Input " + ADD_NEW_ITEM + " to add new Item");
        System.out.println("Input " + IMPORT_ITEMS + " to import Items from file");
        System.out.println("Input " + EXPORT_ITEMS + " to export Items to file");
        System.out.println("Input " + PRINT_MY_ALL_ITEMS + " to see your all Items");
        System.out.println("Input " + PRINT_ALL_ITEMS + " to see all Items");
        System.out.println("Input " + PRINT_ITEMS_BY_CATEGORY + " to see Item by choosed category");
        System.out.println("Input " + PRINT_ALL_ITEMS_BY_TITLE_SORT + " to sort all Items by title");
        System.out.println("Input " + PRINT_ALL_ITEMS_BY_DATE_SORT + " to sort all Items by date");
        System.out.println("Input " + DELETE_MY_ALL_ITEMS + " to delete your all Items");
        System.out.println("Input " + DELETE_ITEM_BY_TITLE + " to delete Item by given ID");
    }

    //Russian part

    static void printMainCommandsRus() {
        System.out.println("Нажмите " + EXIT + " для Выхода");
        System.out.println("Нажмите " + LOGIN + " чтобы Войти");
        System.out.println("Нажмите " + REGISTER + " для Регистрации");
        System.out.println("Нажмите " + IMPORT_USERS + " чтобы импортировать данные пользователей");

    }

    static void printUserCommandsRus() {
        System.out.println("Нажмите " + LOGOUT + " чтобы Выйти");
        System.out.println("Нажмите " + ADD_NEW_ITEM + " для добавления нового обьявления");
        System.out.println("Нажмите " + IMPORT_ITEMS + " для импорта обьявлений");
        System.out.println("Нажмите " + EXPORT_ITEMS + " для экспорта обьявлений");
        System.out.println("Нажмите " + PRINT_MY_ALL_ITEMS + " для просмотра всех ваших обьявлений");
        System.out.println("Нажмите " + PRINT_ALL_ITEMS + " для просмотра всех обьявлений");
        System.out.println("Нажмите " + PRINT_ITEMS_BY_CATEGORY + " для просмотра обьявлений по выбранному категорию");
        System.out.println("Нажмите " + PRINT_ALL_ITEMS_BY_TITLE_SORT + " для сортировки обьявлений по заголовкам");
        System.out.println("Нажмите " + PRINT_ALL_ITEMS_BY_DATE_SORT + " для сортировки обьявлений по числу добавления");
        System.out.println("Нажмите " + DELETE_MY_ALL_ITEMS + " для удаления всех ваших обьявлений");
        System.out.println("Нажмите " + DELETE_ITEM_BY_TITLE + " для удаления обьявлений по заданному ID");
    }

}
