package advertisement.model;

import java.util.Comparator;


public class AdsByTitleComparator implements Comparator<Advertisement> {

    @Override
    public int compare(Advertisement adv1, Advertisement adv2) {
        return adv1.getTitle().compareTo(adv2.getTitle());
    }
}
