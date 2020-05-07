package advertisement.util;

import advertisement.model.Item;
import advertisement.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {

    private static final String USER_MAP_PATH = "src\\resources\\userMap.data";
    private static final String ITEM_LIST_PATH = "src\\resources\\itemList.data";

    //User serialization
    public static void serializeUserMap(Map<String, User> userMap) {
        File userMapFile = new File(USER_MAP_PATH);
        try {
            if (!userMapFile.exists()) {
                userMapFile.createNewFile();
                System.out.println("Your data will be saved in storage");
            }
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(USER_MAP_PATH))) {
                objectOutputStream.writeObject(userMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Map<String, User> deserializeUserMap() {
        Map<String, User> result = new HashMap<>();
        File userMapFile = new File(USER_MAP_PATH);
        if (userMapFile.exists()) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(USER_MAP_PATH));
                Object object = objectInputStream.readObject();
                return (Map<String, User>) object;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    //Item serialization
    public static void serializeItemList(List<Item> items) {
        File itemListFile = new File(ITEM_LIST_PATH);
        try {
            if (!itemListFile.exists()) {
                itemListFile.createNewFile();
                System.out.println("Item data will be saved in storage");
            }
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(ITEM_LIST_PATH))) {
                objectOutputStream.writeObject(items);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Item> deserializeItemList() {
        List<Item> result =new ArrayList<>();
        File itemListFile=new File(ITEM_LIST_PATH);
        if (itemListFile.exists()){
            try {
                ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(ITEM_LIST_PATH));
                Object object = objectInputStream.readObject();
                return (List<Item>) object;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


}
