package advertisement;

public interface MainCommands {

    int EXIT=0;
    int LOGIN=1;
    int REGISTER=2;

    static void printMainCommands(){
        System.out.println("Input "+EXIT+" to EXIT");
        System.out.println("Input "+LOGIN+" to LOGIN");
        System.out.println("Input "+REGISTER+" to REGISTER");
    }

}
