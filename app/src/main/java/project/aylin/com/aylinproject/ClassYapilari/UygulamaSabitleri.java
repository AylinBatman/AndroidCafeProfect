package project.aylin.com.aylinproject.ClassYapilari;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by aylin on 18/02/2018.
 */

public class UygulamaSabitleri extends Application {
    public static String baseURL = "http://jsonbulut.com/json/";
    public static String sharedTag = "KullaniciGiris";
    public static String ref = "9ccac60641eec5be35a53f307886f715";


    public ArrayList<Urun> sepet_urunleri = new ArrayList<>();

    public ArrayList<Urun> getSepet_urunleri() {
        return sepet_urunleri;
    }

    public void setSepet_urunleri(ArrayList<Urun> sepet_urunleri) {
        this.sepet_urunleri = sepet_urunleri;
    }
}
