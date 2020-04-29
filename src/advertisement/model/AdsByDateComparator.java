package advertisement.model;

import java.util.Comparator;

public class AdsByDateComparator implements Comparator<Advertisement> {

    @Override
    public int compare(Advertisement adv1, Advertisement adv2) {
        return adv2.getCreatedDate().compareTo(adv1.getCreatedDate());
    }
}
