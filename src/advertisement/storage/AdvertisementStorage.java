package advertisement.storage;

import advertisement.exceptions.ModelNotFoundException;
import advertisement.model.AdsByDateComparator;
import advertisement.model.AdsByTitleComparator;
import advertisement.model.Advertisement;
import advertisement.model.Category;
import java.util.ArrayList;
import java.util.List;


public class AdvertisementStorage {

    private List<Advertisement> adList=new ArrayList<>();

    public void add(Advertisement ad){
        adList.add(ad);
    }

    public boolean isEmpty(){
        return adList.isEmpty();
    }

    public void print(){
        for (Advertisement ad:adList) {
            System.out.println(ad);
        }
    }

    public void getByCategory(Category category) throws ModelNotFoundException {
        for (Advertisement advertisement:adList) {
            if (advertisement.getCategory().equals(category)){
                System.out.println(advertisement);
            }
        }
        throw new ModelNotFoundException(String.format("Advertisement with category %s dows not exist",category));
    }

    public void deleteAllAds(){
        adList.clear();
    }


    public void deleteAdByTitle(String adTitle) throws ModelNotFoundException {
        for (Advertisement advertisement : adList) {
            if (advertisement.getTitle().equals(adTitle)){
                adList.clear();
            }
        }
        throw new ModelNotFoundException(String.format("Advertisement with title $s does not exist",adTitle));
    }

    public void sortAdsByTitle() {
        AdsByTitleComparator titleComparator=new AdsByTitleComparator();
        adList.sort(titleComparator);
        print();
    }

    public void sortAdsByDate() {
        AdsByDateComparator dateComparator=new AdsByDateComparator();
        adList.sort(dateComparator);
        print();
    }
}
