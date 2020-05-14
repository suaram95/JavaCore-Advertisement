package advertisement.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Item implements Serializable {

    private long id;
    private User author;
    private Category category;
    private String title;
    private String text;
    private double price;
    private Date createdDate;


}
