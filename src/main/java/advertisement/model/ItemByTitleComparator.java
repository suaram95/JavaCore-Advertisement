package advertisement.model;

import java.util.Comparator;


public class ItemByTitleComparator implements Comparator<Item> {

    @Override
    public int compare(Item adv1, Item adv2) {
        return adv1.getTitle().compareTo(adv2.getTitle());
    }
}
