package advertisement.storage;

import advertisement.exceptions.ModelNotFoundException;
import advertisement.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserStorage {

    Map<String, User> userMap = new HashMap<>();

    public void add(User user) {
        userMap.put(user.getPhoneNumber(), user);
    }

    public User getUserbyPhoneAndPassword(String phone, String password) throws ModelNotFoundException {
//        User user=new User();
//        User user1 = userMap.get(user.getPhoneNumber());
//        if (user1.getPhoneNumber().equals(phone)&& user1.getPassword().equals(password)){
//            return user1;
//        }
        System.out.println("Hello");
        throw new ModelNotFoundException(String.format("User account with phone %s does not exist", phone));
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
