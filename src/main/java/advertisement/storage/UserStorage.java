package advertisement.storage;

import advertisement.model.User;
import advertisement.util.FileUtil;

import java.util.HashMap;
import java.util.Map;

public class UserStorage {

    Map<String, User> userMap = new HashMap<>();

    public void add(User user) {
        userMap.put(user.getPhoneNumber(), user);
        FileUtil.serializeUserMap(userMap);
    }


    public void initData(){
        userMap = FileUtil.deserializeUserMap();
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
