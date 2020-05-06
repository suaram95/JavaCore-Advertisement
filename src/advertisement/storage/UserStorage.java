package advertisement.storage;

import advertisement.model.User;
import advertisement.util.UserFileUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserStorage {

    Map<String, User> userMap = new HashMap<>();

    public void add(User user) {
        userMap.put(user.getPhoneNumber(), user);
        try {
            UserFileUtil.seializeUserMap(userMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initData() throws IOException, ClassNotFoundException {
        File file=new File("D:\\Aram\\IT Space LLC\\My Projects\\JavaCore-Advertisement\\src\\advertisement\\util\\dataFiles\\UserData.txt");
        if (!file.exists()){
            boolean newFile = file.createNewFile();
            if (newFile){
                System.out.println("In package <dataFiles> was created file <UserData.txt>");
            } else{
                System.err.println("Something went wrong! File was not created");
            }
        } else  {
            userMap = UserFileUtil.deserializeUserMap();
        }

    }

    public User getUser(String phoneNumber) {
        return userMap.get(phoneNumber);
    }


    public void printUsers() {
        for (User user : userMap.values()) {
            System.out.println(user);
        }
    }

    public boolean isEmty() {
        return userMap.isEmpty();
    }


}
