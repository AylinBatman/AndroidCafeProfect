package project.aylin.com.aylinproject.ClassYapilari;

import java.util.ArrayList;

/**
 * Created by aylin on 18/02/2018.
 */

public class Siparis {
    String date;//"573"~~

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String productName;//"Çay"
    String description;//"çay"
    String price;//"2.00"
    ArrayList<String> images;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public Siparis(String date, String productName, String description, String price, ArrayList<String> images) {
        this.date = date;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.images = images;
    }
}
