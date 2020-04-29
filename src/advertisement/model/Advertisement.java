package advertisement.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Advertisement {

    private User author;
    private Category category;
    private String title;
    private String text;
    private double price;
    private Date createdDate;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss");

    public Advertisement(User author, Category category, String title, String text, double price, Date createdDate, SimpleDateFormat sdf) {
        this.author = author;
        this.category = category;
        this.title = title;
        this.text = text;
        this.price = price;
        this.createdDate = createdDate;
        this.sdf = sdf;
    }

    public Advertisement() {
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Advertisement that = (Advertisement) o;

        if (Double.compare(that.price, price) != 0) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (category != that.category) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        return sdf != null ? sdf.equals(that.sdf) : that.sdf == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = author != null ? author.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (sdf != null ? sdf.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "author=" + author +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", price=" + price +
                ", Date=" + sdf.format(createdDate) +
                '}';
    }
}
