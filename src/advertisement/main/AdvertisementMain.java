package advertisement.main;

import java.util.Scanner;

public class AdvertisementMain implements Commands {

    private static final Scanner scanner=new Scanner(System.in);

    public static void main(String[] args) {
        boolean isRun=true;
        while (isRun){
            Commands.printCommandsForLanguage();
            int command;
            try {
                command=Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e){
                command=-1;
            }
            switch (command){
                case EXIT:
                    isRun=false;
                    break;
                case ARMENIAN:
                   chooseArmenian();
                    break;
                case ENGLISH:
                    chooseEnglish();
                    break;
                case RUSSIAN:
                    chooseRussian();
                    break;
                default:
                    System.out.println("Wrong Command!\n");
            }
        }
    }

    private static void chooseArmenian() {
        ItemForArmenian.mainPart();
    }

    private static void chooseEnglish() {
        ItemForEnglish.mainPart();
    }

    private static void chooseRussian() {
        ItemForRussian.mainPart();
    }
}
