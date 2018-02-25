package project.aylin.com.aylinproject.ClassYapilari;

/**
 * Created by aylin on 16/02/2018.
 */

public class GaleriResim {
    String id;//"152"
    String id_galeri;//"84"
    String img;//"http://jsonbulut.com/admin/upload/NTM=_20180218012205.jpg"
    String thumbImg;//"http://jsonbulut.com/admin/upload/tmb/NTM=_20180218012205.jpg"
    String alt;//"Sıcak İçecekler"

    public GaleriResim(String id, String id_galeri, String img, String thumbImg, String alt) {

        this.id = id;
        this.id_galeri = id_galeri;
        this.img = img;
        this.thumbImg = thumbImg;
        this.alt = alt;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_galeri() {
        return id_galeri;
    }

    public void setId_galeri(String id_galeri) {
        this.id_galeri = id_galeri;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getThumbImg() {
        return thumbImg;
    }

    public void setThumbImg(String thumbImg) {
        this.thumbImg = thumbImg;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
