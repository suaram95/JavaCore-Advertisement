package advertisement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {

    private long id;
    private User author;
    private Category category;
    private String title;
    private String text;
    private double price;
    private Date createdDate;


}
