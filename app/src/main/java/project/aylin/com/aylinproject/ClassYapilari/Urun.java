package project.aylin.com.aylinproject.ClassYapilari;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aylin on 18/02/2018.
 */

public class Urun implements Serializable {

    String productId;//"573"
    String productName;//"Çay"
    String description;//"çay"
    String price;//"2.00"
    ArrayList<String> images;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

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

    public Urun(String productId, String productName, String description, String price, ArrayList<String> images) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.images = images;
    }
}
