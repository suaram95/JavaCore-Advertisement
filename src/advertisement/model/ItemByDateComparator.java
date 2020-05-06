package advertisement.model;

import java.util.Comparator;

public class ItemByDateComparator implements Comparator<Item> {

    @Override
    public int compare(Item adv1, Item adv2) {
        return adv2.getCreatedDate().compareTo(adv1.getCreatedDate());
    }
}
