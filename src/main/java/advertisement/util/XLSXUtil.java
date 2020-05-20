package advertisement.util;

import advertisement.model.Category;
import advertisement.model.Gender;
import advertisement.model.Item;
import advertisement.model.User;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class XLSXUtil {

    //Methods for importing users from file

    //English
    public static List<User> readUser(String path) {
        List<User> result = new LinkedList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(path);
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                result.add(getUserFromRow(row));
            }

        } catch (IOException e) {
            System.out.println("Error while importing users");
        }
        return result;
    }
    //Armenian
    public static List<User> readUserArm(String path) {
        List<User> result = new LinkedList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(path);
            Sheet sheet = workbook.getSheetAt(1);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                result.add(getUserFromRow(row));
            }

        } catch (IOException e) {
            System.out.println("Սխալ է տեղի ունեցել օգտատերերի տվյալները ներբեռնելու ժամանակ");
        }
        return result;
    }

    //Russian
    public static List<User> readUserRus(String path) {
        List<User> result = new LinkedList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(path);
            Sheet sheet = workbook.getSheetAt(2);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                result.add(getUserFromRow(row));
            }

        } catch (IOException e) {
            System.out.println("Ошибка во время импорта данных");
        }
        return result;
    }

    private static User getUserFromRow(Row row) {

        return User.builder()
                .name(row.getCell(0).getStringCellValue())
                .surname(row.getCell(1).getStringCellValue())
                .gender(Gender.valueOf(row.getCell(2).getStringCellValue().toUpperCase()))
                .age(Double.valueOf(row.getCell(3).getNumericCellValue()).intValue())
                .phoneNumber(row.getCell(4).getStringCellValue())
                .password(row.getCell(5).getStringCellValue())
                .build();
    }


    //----- End of methods for importing users


    //Methods for importing items from file
    public static List<Item> readItems(String path) {
        List<Item> result = new LinkedList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(path);
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                result.add(getItemFromRow(row));
            }

        } catch (IOException e) {
            System.out.println("Error while importing items");
        }
        return result;
    }

    public static List<Item> readItemsArm(String path) {
        List<Item> result = new LinkedList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(path);
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                result.add(getItemFromRow(row));
            }

        } catch (IOException e) {
            System.out.println("Սխալ է տեղի ունեցել հայտարարությունների տվյալները ներբեռնելու ժամանակ");
        }
        return result;
    }

    public static List<Item> readItemsRus(String path) {
        List<Item> result = new LinkedList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(path);
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                result.add(getItemFromRow(row));
            }

        } catch (IOException e) {
            System.out.println("Ошибка во время импорта данных обьявлений");
        }
        return result;
    }

    private static Item getItemFromRow(Row row) {
        return Item.builder()
                .id(Double.valueOf(row.getCell(0).getNumericCellValue()).intValue())
                .author(User.builder().phoneNumber(row.getCell(1).getStringCellValue()).build())
                .category(Category.valueOf(row.getCell(2).getStringCellValue()))
                .title(row.getCell(3).getStringCellValue())
                .text(row.getCell(4).getStringCellValue())
                .price(Double.valueOf(row.getCell(5).getNumericCellValue()).intValue())
                .createdDate(row.getCell(6 ).getDateCellValue())
                .build();
    }



    //Methods for exporting items from file
    //English
    public static void writeItems(String path, List<Item> items) {
        String fileName = "Items export ENG_" + System.nanoTime() + ".xlsx";
        File file = new File(path, fileName);
        try {
            file.createNewFile();
            XSSFWorkbook workbook=new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Հայտարարություններ");
            writeItemsHeader(sheet.createRow(0));
            int rowIndex=1;
            CellStyle dateCellStyle = workbook.createCellStyle();
            short df = workbook.createDataFormat().getFormat("dd-MM-yyyy hh:mm:ss");
            dateCellStyle.setDataFormat(df);
            for (Item item : items) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(item.getId());
                row.createCell(1).setCellValue(item.getAuthor().getPhoneNumber());
                row.createCell(2).setCellValue(item.getCategory().name());
                row.createCell(3).setCellValue(item.getTitle());
                row.createCell(4).setCellValue(item.getText());
                row.createCell(5).setCellValue(item.getPrice());
                Cell dateCell = row.createCell(6);
                dateCell.setCellValue(item.getCreatedDate());
                dateCell.setCellStyle(dateCellStyle);

            }
            workbook.write(new FileOutputStream(file));

        } catch (IOException e) {
            System.out.println("Error while exporting Items");
        }
    }

    private static void writeItemsHeader(XSSFRow row) {
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Phone Number");
        row.createCell(2).setCellValue("Category");
        row.createCell(3).setCellValue("Title");
        row.createCell(4).setCellValue("Text");
        row.createCell(5).setCellValue("Price");
        row.createCell(6).setCellValue("Created Date");

    }

    //Armenian
    public static void writeItemsArm(String path, List<Item> items) {
        String fileName = "Items export ARM_" + System.nanoTime() + ".xlsx";
        File file = new File(path, fileName);
        try {
            file.createNewFile();
            XSSFWorkbook workbook=new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Items");
            writeItemsHeaderArm(sheet.createRow(0));
            int rowIndex=1;
            CellStyle dateCellStyle = workbook.createCellStyle();
            short df = workbook.createDataFormat().getFormat("dd-MM-yyyy hh:mm:ss");
            dateCellStyle.setDataFormat(df);
            for (Item item : items) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(item.getId());
                row.createCell(1).setCellValue(item.getAuthor().getPhoneNumber());
                row.createCell(2).setCellValue(item.getCategory().name());
                row.createCell(3).setCellValue(item.getTitle());
                row.createCell(4).setCellValue(item.getText());
                row.createCell(5).setCellValue(item.getPrice());
                Cell dateCell = row.createCell(6);
                dateCell.setCellValue(item.getCreatedDate());
                dateCell.setCellStyle(dateCellStyle);

            }
            workbook.write(new FileOutputStream(file));

        } catch (IOException e) {
            System.out.println("Սխալ է տեղի ունեցել հայտարարությունները վերբեռնելու ժամանակ");
        }
    }

    private static void writeItemsHeaderArm(XSSFRow row) {
        row.createCell(0).setCellValue("ԻԴ");
        row.createCell(1).setCellValue("Հեռ. Համար");
        row.createCell(2).setCellValue("Կատեգորիա");
        row.createCell(3).setCellValue("Վերնագիր");
        row.createCell(4).setCellValue("Տեքստ");
        row.createCell(5).setCellValue("Գին");
        row.createCell(6).setCellValue("Ստեղծման օրը");
    }

    //Russian

    public static void writeItemsRus(String path, List<Item> items) {
        String fileName = "Items export RUS_" + System.nanoTime() + ".xlsx";
        File file = new File(path, fileName);
        try {
            file.createNewFile();
            XSSFWorkbook workbook=new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Обьявления");
            writeItemsHeaderRus(sheet.createRow(0));
            int rowIndex=1;
            CellStyle dateCellStyle = workbook.createCellStyle();
            short df = workbook.createDataFormat().getFormat("dd-MM-yyyy hh:mm:ss");
            dateCellStyle.setDataFormat(df);
            for (Item item : items) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(item.getId());
                row.createCell(1).setCellValue(item.getAuthor().getPhoneNumber());
                row.createCell(2).setCellValue(item.getCategory().name());
                row.createCell(3).setCellValue(item.getTitle());
                row.createCell(4).setCellValue(item.getText());
                row.createCell(5).setCellValue(item.getPrice());
                Cell dateCell = row.createCell(6);
                dateCell.setCellValue(item.getCreatedDate());
                dateCell.setCellStyle(dateCellStyle);

            }
            workbook.write(new FileOutputStream(file));

        } catch (IOException e) {
            System.out.println("Ошибка во время импорта обьявлений");
        }
    }

    private static void writeItemsHeaderRus(XSSFRow row) {
        row.createCell(0).setCellValue("ИД");
        row.createCell(1).setCellValue("Тел. Номер");
        row.createCell(2).setCellValue("Категория");
        row.createCell(3).setCellValue("Заголовок");
        row.createCell(4).setCellValue("Текст");
        row.createCell(5).setCellValue("Цена");
        row.createCell(6).setCellValue("Дата создания");
    }
}


