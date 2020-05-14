package advertisement.storage;

import advertisement.exceptions.ModelNotFoundException;
import advertisement.model.*;
import advertisement.util.FileUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ItemStorage {

    private static long itemId = 1;

    private List<Item> items = new ArrayList<>();


    public void add(Item item) {
        item.setId(itemId++);
        items.add(item);
        FileUtil.serializeItemList(items);
    }

    public Item getItemById(long id) {
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public void initData(){
        items = FileUtil.deserializeItemList();
        if (this.items != null && !this.items.isEmpty()) {
            Item item = this.items.get(this.items.size() - 1);
            itemId = item.getId() + 1;
        }
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
        FileUtil.serializeItemList(items);
//        items.removeIf(item -> item.getUser().equals(user));
    }

    public void deleteItemsById(long id) {
        items.remove(getItemById(id));
        FileUtil.serializeItemList(items);
    }


}
