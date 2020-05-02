package advertisement.main;

public interface Commands {

    //Commands part
    int EXIT = 0;
    int ARMENIAN = 1;
    int ENGLISH = 2;
    int RUSSIAN = 3;

    int LOGIN = 1;
    int REGISTER = 2;

    int LOGOUT = 0;
    int ADD_NEW_AD = 1;
    int PRINT_MY_ALL_ADS = 2;
    int PRINT_ALL_ADS = 3;
    int PRINT_AD_BY_CATEGORY = 4;
    int PRINT_ALL_ADS_BY_TITLE_SORT = 5;
    int PRINT_ALL_ADS_BY_DATE_SORT = 6;
    int DELETE_MY_ALL_ADS = 7;
    int DELETE_AD_BY_TITLE = 8;


    //Language choose part

    static void printCommandsForLanguage() {
        System.out.println("Հայերենի համար սեղմեք: " + ARMENIAN);
        System.out.println("For English input: " + ENGLISH);
        System.out.println("Для русского языка нажмите: " + RUSSIAN);
    }


    //Armenian part

    static void printMainCommandsArm() {
        System.out.println("Սեղմեք " + EXIT + " Ելքի համար");
        System.out.println("Սեղմեք " + LOGIN + " Մուտք գործելու համար");
        System.out.println("Սեղմեք " + REGISTER + " Գրանցման համար");
    }

    static void printUserCommandsArm() {
        System.out.println("Սեղմեք " + LOGOUT + " Ելքի համար");
        System.out.println("Սեղմեք " + ADD_NEW_AD + " նոր հայտարարություն ավելացնելու համար");
        System.out.println("Սեղմեք " + PRINT_MY_ALL_ADS + " ձեր բոլոր հայտարարությունները տեսնելու համար");
        System.out.println("Սեղմեք " + PRINT_ALL_ADS + " բոլոր հայտարարությունները տեսնելու համար");
        System.out.println("Սեղմեք " + PRINT_AD_BY_CATEGORY + " հայտարարությունները ըստ կատեգորիաների տեսնելու համար");
        System.out.println("Սեղմեք " + PRINT_ALL_ADS_BY_TITLE_SORT + " հայտարարությունները ըստ վերնագրերի տեսակավորելու համար");
        System.out.println("Սեղմեք " + PRINT_ALL_ADS_BY_DATE_SORT + " հայտարարությունները ըստ ավելացման օրվա տեսակավորելու համար");
        System.out.println("Սեղմեք " + DELETE_MY_ALL_ADS + " ձեր բոլոր հայտարարությունները ջնջելու համար");
        System.out.println("Սեղմեք " + DELETE_AD_BY_TITLE + " հայտարարաությունը ըստ տրված վերնագրի ջնջելու համար");
    }


    //English part

    static void printMainCommandsEng(){
        System.out.println("Input "+EXIT+" to Exit");
        System.out.println("Input "+LOGIN+" to Login");
        System.out.println("Input "+REGISTER+" to Register");
    }

    static void printUserCommandsEng() {
        System.out.println("Input " + LOGOUT + " to Logout");
        System.out.println("Input " + ADD_NEW_AD + " to add new Advertisement");
        System.out.println("Input " + PRINT_MY_ALL_ADS + " to see your all Advertisements");
        System.out.println("Input " + PRINT_ALL_ADS + " to see all Advertisements");
        System.out.println("Input " + PRINT_AD_BY_CATEGORY + " to see Advertisement by choosed category");
        System.out.println("Input " + PRINT_ALL_ADS_BY_TITLE_SORT + " to sort all Advetisement by title");
        System.out.println("Input " + PRINT_ALL_ADS_BY_DATE_SORT + " to sort all Advetisement by date");
        System.out.println("Input " + DELETE_MY_ALL_ADS + " to delete your all Advertisements");
        System.out.println("Input " + DELETE_AD_BY_TITLE + " to delete Advertisement by given title");
    }

    //Russian part

    static void printMainCommandsRus(){
        System.out.println("Нажмите "+EXIT+" для Выхода");
        System.out.println("Нажмите "+LOGIN+" чтобы Войти");
        System.out.println("Нажмите "+REGISTER+" для Регистрации");
    }

    static void printUserCommandsRus() {
        System.out.println("Нажмите " + LOGOUT + " чтобы Выйти");
        System.out.println("Нажмите " + ADD_NEW_AD + " для добавления нового обьявления");
        System.out.println("Нажмите " + PRINT_MY_ALL_ADS + " для просмотра всех ваших обьявлений");
        System.out.println("Нажмите " + PRINT_ALL_ADS + " для просмотра всех обьявлений");
        System.out.println("Нажмите " + PRINT_AD_BY_CATEGORY + " для просмотра обьявлений по выбранному категорию");
        System.out.println("Нажмите " + PRINT_ALL_ADS_BY_TITLE_SORT + " для сортировки обьявлений по заголовкам");
        System.out.println("Нажмите " + PRINT_ALL_ADS_BY_DATE_SORT + " для сортировки обьявлений по числу добавления");
        System.out.println("Нажмите " + DELETE_MY_ALL_ADS + " для удаления всех ваших обьявлений");
        System.out.println("Нажмите " + DELETE_AD_BY_TITLE + " для удаления обьявлений по заданному заголовку");
    }

}
