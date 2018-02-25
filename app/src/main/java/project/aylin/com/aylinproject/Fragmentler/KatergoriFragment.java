package project.aylin.com.aylinproject.Fragmentler;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import project.aylin.com.aylinproject.Adapterler.KategoriAdapter;
import project.aylin.com.aylinproject.ClassYapilari.GaleriResim;
import project.aylin.com.aylinproject.ClassYapilari.Kategori;
import project.aylin.com.aylinproject.ClassYapilari.UygulamaSabitleri;
import project.aylin.com.aylinproject.Kutuphaneler.DataInterface;
import project.aylin.com.aylinproject.Kutuphaneler.GenelKutuphane;
import project.aylin.com.aylinproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KatergoriFragment extends Fragment {

    GenelKutuphane kutuphane;
    GridView katergoriListeleyici;

    public KatergoriFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_urun_kategorileri, container, false);
        gorselAta(v);
        return v;
    }

    void gorselAta(View v) {
        kutuphane = new GenelKutuphane(getActivity());
        katergoriListeleyici = (GridView) v.findViewById(R.id.gridview);
        kategoriKur();
    }


    void kategoriKur() {
        final ArrayList<GaleriResim> galeriResims = new ArrayList<>();
        final ArrayList<Kategori> kategoris = new ArrayList<>();
//        http://jsonbulut.com/json/companyCategory.php?ref=9ccac60641eec5be35a53f307886f715
        String url = UygulamaSabitleri.baseURL + "companyCategory.php?ref=" + UygulamaSabitleri.ref;
        kutuphane.GetData(url, Request.Method.GET, new DataInterface() {
            @Override
            public void cevap(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("Kategoriler");
                    JSONObject icsel = (JSONObject) jsonArray.get(0);
                    JSONArray arrayKAT = icsel.getJSONArray("Categories");
                    for (int i = 0; i < arrayKAT.length(); i++) {
                        JSONObject ktOBJ = (JSONObject) arrayKAT.get(i);
                        kategoris.add(new Kategori(
                                ktOBJ.getString("CatogryId"),
                                ktOBJ.getString("TopCatogryId"),
                                ktOBJ.getString("CatogryName")
                        ));
                    }
                    String urlresim = UygulamaSabitleri.baseURL + "gallery.php?ref=" + UygulamaSabitleri.ref + "&galleryId=84";
                    kutuphane.GetData(urlresim, Request.Method.GET, new DataInterface() {
                        @Override
                        public void cevap(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                JSONArray jsonArray = object.getJSONArray("Galleries");
                                JSONObject icsel = (JSONObject) jsonArray.get(0);
                                JSONArray arrayKAT = icsel.getJSONArray("Gallery");
                                for (int i = 0; i < arrayKAT.length(); i++) {
                                    JSONObject ktOBJ = (JSONObject) arrayKAT.get(i);
                                    galeriResims.add(new GaleriResim(
                                            ktOBJ.getString("id"),
                                            ktOBJ.getString("id_galeri"),
                                            ktOBJ.getString("img"),
                                            ktOBJ.getString("thumbImg"),
                                            ktOBJ.getString("alt")
                                    ));
                                }
                                katergoriListeleyici.setAdapter(new KategoriAdapter(getActivity(), kategoris,galeriResims));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void hata(String error) {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void hata(String error) {

            }
        });
    }

}
