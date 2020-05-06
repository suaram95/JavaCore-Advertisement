package advertisement.util;

import advertisement.model.Item;

import java.io.*;
import java.util.List;

public class ItemFileUtil {

    private static final String FILE_PATH = "D:\\Aram\\IT Space LLC\\My Projects\\JavaCore-Advertisement\\src\\advertisement\\util\\dataFiles\\ItemData.txt";

    public static void seializeItemList(List<Item> itemList) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
        objectOutputStream.writeObject(itemList);
        objectOutputStream.close();
    }

    public static List<Item> deserializeItemList() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_PATH));

        List<Item> advInput = (List<Item>) objectInputStream.readObject();
        objectInputStream.close();
        return advInput;

    }

}
