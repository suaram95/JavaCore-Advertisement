package advertisement;

public interface UserCommands {

    int LOGOUT = 0;
    int ADD_NEW_AD = 1;
    int PRINT_MY_ALL_ADS = 2;
    int PRINT_ALL_ADS = 3;
    int PRINT_AD_BY_CATEGORY = 4;
    int PRINT_ALL_ADS_BY_TITLE_SORT = 5;
    int PRINT_ALL_ADS_BY_DATE_SORT = 6;
    int DELETE_MY_ALL_ADS = 7;
    int DELETE_AD_BY_TITLE = 8;

    static void printUserCommands() {
        System.out.println("Input " + LOGOUT + " to EXIT");
        System.out.println("Input " + ADD_NEW_AD + " to ADD NEW ADD");
        System.out.println("Input " + PRINT_MY_ALL_ADS + " to PRINT MY ALL ADS");
        System.out.println("Input " + PRINT_ALL_ADS + " to PRINT ALL ADS");
        System.out.println("Input " + PRINT_AD_BY_CATEGORY + " to PRINT AD BY CATEGORY");
        System.out.println("Input " + PRINT_ALL_ADS_BY_TITLE_SORT + " to PRINT ALL ADS BY TITLE SORT");
        System.out.println("Input " + PRINT_ALL_ADS_BY_DATE_SORT + " to PRINT ALL ADS BY DATE SORT");
        System.out.println("Input " + DELETE_MY_ALL_ADS + " to DELETE MY ALL ADS");
        System.out.println("Input " + DELETE_AD_BY_TITLE + " to DELETE AD BY TITLE");
    }


}
