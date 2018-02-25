package project.aylin.com.aylinproject.ClassYapilari;

/**
 * Created by aylin on 18/02/2018.
 */

public class Kategori {

    String CatogryId;//"1651"
    String TopCatogryId;//"0"
    String CatogryName;//"Sıcak İçecekler"


    public Kategori(String catogryId, String topCatogryId, String catogryName) {
        CatogryId = catogryId;
        TopCatogryId = topCatogryId;
        CatogryName = catogryName;
    }

    public String getCatogryId() {
        return CatogryId;
    }

    public void setCatogryId(String catogryId) {
        CatogryId = catogryId;
    }

    public String getTopCatogryId() {
        return TopCatogryId;
    }

    public void setTopCatogryId(String topCatogryId) {
        TopCatogryId = topCatogryId;
    }

    public String getCatogryName() {
        return CatogryName;
    }

    public void setCatogryName(String catogryName) {
        CatogryName = catogryName;
    }
}
