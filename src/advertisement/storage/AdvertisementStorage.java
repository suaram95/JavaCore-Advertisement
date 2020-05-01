package advertisement.storage;

import advertisement.exceptions.ModelNotFoundException;
import advertisement.model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class AdvertisementStorage {

    private List<Advertisement> adList = new ArrayList<>();

    public void add(Advertisement ad) {
        adList.add(ad);
    }

    public boolean isEmpty() {
        return adList.isEmpty();
    }

    public List<Advertisement> getAdList() {
        return adList;
    }

    public void print() {
        for (Advertisement ad : adList) {
            System.out.println(ad);
        }
    }

    public void getByCategory(Category category) throws ModelNotFoundException {
        for (Advertisement advertisement : adList) {
            if (advertisement.getCategory()==category) {
                System.out.println(advertisement);
            }
        }
        throw new ModelNotFoundException(String.format("Advertisement with category %s dows not exist", category));
    }

    public void deleteAdByTitle(Advertisement adTitle)  {
        adList.removeIf(advertisement -> advertisement.getTitle().equals(adTitle));
    }

    public void sortAdsByTitle() {
        AdsByTitleComparator titleComparator = new AdsByTitleComparator();
        adList.sort(titleComparator);
        print();
    }

    public void sortAdsByDate() {
        AdsByDateComparator dateComparator = new AdsByDateComparator();
        adList.sort(dateComparator);
        print();
    }

    public void deleteAdsByAuthor(User user) {
        adList.removeIf(next -> next.getAuthor().equals(user));
    }

    public void printAdsByUser(User user) {
        for (Advertisement advertisement : adList) {
            if (advertisement.getAuthor().equals(user)){
                System.out.println(advertisement);
            }
        }
    }

    public Advertisement getByTitle(String advTitle) {
        for (Advertisement advertisement : adList) {
            if (advertisement.getTitle().equals(advTitle)){
                return advertisement;
            }
        }
        return null;
    }
}
