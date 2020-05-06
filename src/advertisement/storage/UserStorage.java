package advertisement.storage;

import advertisement.exceptions.ModelNotFoundException;
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

    public void initData() {
        try {
            userMap = UserFileUtil.deserializeUserMap();
        } catch (Exception e) {
            e.printStackTrace();
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
