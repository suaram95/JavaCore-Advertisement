package advertisement.util;

import advertisement.model.User;

import java.io.*;
import java.util.Map;

public class UserFileUtil {

    private static final String FILE_PATH="D:\\Aram\\IT Space LLC\\My Projects\\JavaCore-Advertisement\\src\\advertisement\\util\\dataFiles\\UserData.txt";

    public static void seializeUserMap(Map<String, User> userMap) throws IOException {
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(FILE_PATH));
        objectOutputStream.writeObject(userMap);
        objectOutputStream.close();

    }

    public static Map<String, User> deserializeUserMap() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(FILE_PATH));
        Map<String, User> userInput = (Map<String, User>) objectInputStream.readObject();
        return userInput;
    }

}
