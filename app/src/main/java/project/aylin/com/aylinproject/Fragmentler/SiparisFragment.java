package project.aylin.com.aylinproject.Fragmentler;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import project.aylin.com.aylinproject.Adapterler.SiparisAdapter;
import project.aylin.com.aylinproject.ClassYapilari.Siparis;
import project.aylin.com.aylinproject.ClassYapilari.UygulamaSabitleri;
import project.aylin.com.aylinproject.Kutuphaneler.DataInterface;
import project.aylin.com.aylinproject.Kutuphaneler.GenelKutuphane;
import project.aylin.com.aylinproject.R;


public class SiparisFragment extends Fragment {
    SharedPreferences preferences;
    ListView siprais_gecmisi;
    GenelKutuphane kutuphane;
    UygulamaSabitleri sabitleri;
    TextView toplam_fiyat;
    double toplam_f = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_siparis, container, false);
        gorselAta(v);
        return v;
    }


    void gorselAta(View v) {
        preferences = getActivity().getSharedPreferences(UygulamaSabitleri.sharedTag, Context.MODE_PRIVATE);
        kutuphane = new GenelKutuphane(getActivity());
        toplam_fiyat = (TextView) v.findViewById(R.id.toplam_fiyat);
        sabitleri = (UygulamaSabitleri) getActivity().getApplicationContext();
        siprais_gecmisi = (ListView) v.findViewById(R.id.siprais_gecmisi);
        siparisgecmisiDoldur();
    }

    void siparisgecmisiDoldur() {
        String url = UygulamaSabitleri.baseURL + "orderList.php?ref=" + UygulamaSabitleri.ref + "&musterilerID=" + preferences.getString("musteriid", "");
        kutuphane.GetData(url, Request.Method.GET, new DataInterface() {
            @Override
            public void cevap(String response) {
                try {
                    ArrayList<Siparis> siparises = new ArrayList<>();
                    ArrayList<String> images = new ArrayList<>();
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("orderList");
                    JSONArray inArr = (JSONArray) jsonArray.get(0);
                    for (int i = 0; i < inArr.length(); i++) {
                        JSONObject jsonObject = (JSONObject) inArr.get(i);

                        siparises.add(new Siparis(
                                jsonObject.getString("tarih"),
                                jsonObject.getString("urun_adi"),
                                jsonObject.getString("aciklama"),
                                jsonObject.getString("fiyat"),
                                images));
                        toplam_f += Double.parseDouble(jsonObject.getString("fiyat"));
                    }

                    siprais_gecmisi.setAdapter(new SiparisAdapter(siparises, getActivity()));
                    toplam_fiyat.setText("" + toplam_f);
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
