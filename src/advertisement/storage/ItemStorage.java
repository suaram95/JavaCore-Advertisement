package advertisement.storage;

import advertisement.exceptions.ModelNotFoundException;
import advertisement.model.*;
import advertisement.util.ItemFileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ItemStorage {

    private static long itemId = 1;

    private List<Item> items = new ArrayList<>();


    public void add(Item item) {
        item.setId(itemId++);
        items.add(item);
        try {
            ItemFileUtil.seializeItemList(items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Item getItemById(long id) {
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public void initData() throws IOException, ClassNotFoundException {
        File file = new File("D:\\Aram\\IT Space LLC\\My Projects\\JavaCore-Advertisement\\src\\advertisement\\util\\dataFiles\\ItemData.txt");
        if (!file.exists()) {
            boolean newFile = file.createNewFile();
            if (newFile) {
                System.out.println("In package <dataFiles> was created file <ItemData.txt>");
            } else {
                System.err.println("Something went wrong! File was not created");
            }
        } else {
            items = ItemFileUtil.deserializeItemList();
        }
    }


    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void print() {
        for (Item ad : this.items) {
            System.out.println(ad);
        }
    }

    public void getByCategory(Category category) throws ModelNotFoundException {
        for (Item item : this.items) {
            if (item.getCategory() == category) {
                System.out.println(item);
            }
        }
        throw new ModelNotFoundException(String.format("Advertisement with category %s dows not exist", category));
    }


    public void sortAdsByTitle() {
        ItemByTitleComparator titleComparator = new ItemByTitleComparator();
        this.items.sort(titleComparator);
        print();
    }

    public void sortAdsByDate() {
        ItemByDateComparator dateComparator = new ItemByDateComparator();
        this.items.sort(dateComparator);
        print();
    }


    public void printAdsByUser(User user) {
        for (Item item : this.items) {
            if (item.getAuthor().equals(user)) {
                System.out.println(item);
            }
        }
    }

    public void deleteAdsByAuthor(User user) {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item next = iterator.next();
            if (next.getAuthor().equals(user)) {
                iterator.remove();
            }
        }
        try {
            ItemFileUtil.seializeItemList(items);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        items.removeIf(item -> item.getUser().equals(user));
    }

    public void deleteItemsById(long id) {
        items.remove(getItemById(id));
        try {
            ItemFileUtil.seializeItemList(items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
