package project.aylin.com.aylinproject.Fragmentler;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;

import java.util.ArrayList;

import project.aylin.com.aylinproject.Adapterler.SepetAdapter;
import project.aylin.com.aylinproject.ClassYapilari.Urun;
import project.aylin.com.aylinproject.ClassYapilari.UygulamaSabitleri;
import project.aylin.com.aylinproject.Kutuphaneler.DataInterface;
import project.aylin.com.aylinproject.Kutuphaneler.GenelKutuphane;
import project.aylin.com.aylinproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SepetFragment extends Fragment {


    Button sepet_onayla;
    ListView sepet_listele;
    UygulamaSabitleri sabitleri;
    GenelKutuphane kutuphane;
    TextView toplam_fiyat;
    SharedPreferences preferences;
    ArrayList<Urun> sepetmevcut;
    SepetAdapter sepetAdapter;

    public SepetFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sepet, container, false);
        gorselAta(v);
        return v;
    }

    int count = 0;

    void gorselAta(View v) {
        preferences = getActivity().getSharedPreferences(UygulamaSabitleri.sharedTag, Context.MODE_PRIVATE);
        kutuphane = new GenelKutuphane(getActivity());
        sabitleri = (UygulamaSabitleri) getActivity().getApplicationContext();
        toplam_fiyat = (TextView) v.findViewById(R.id.toplam_fiyat);
        sepet_listele = (ListView) v.findViewById(R.id.siprais_listview);
        sepet_onayla = (Button) v.findViewById(R.id.siparis_ver);
        sepetAdapter = new SepetAdapter(sabitleri.getSepet_urunleri(), getActivity());
        sepet_listele.setAdapter(sepetAdapter);
        sepetmevcut = sabitleri.getSepet_urunleri();
        toplam_fiyat.setText("" + toplamFiyat() + " TL");
        sepetAdapter.urunCikart = new SepetAdapter.urunCikart() {
            @Override
            public void urunCikti(int i) {
                Log.e("Silinen index: ", "" + i);
                sepetmevcut.remove(i);
                sepetAdapter.notifyDataSetChanged();
                sabitleri.setSepet_urunleri(sepetmevcut);
                toplam_fiyat.setText("" + toplamFiyat() + " TL");
            }
        };


        sepet_onayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sepet_onayla.setEnabled(false);
                sepet_onayla.setText("Siparişleriniz Onaylandı");
                count = 0;

                recursive();


            }
        });

    }


    void recursive() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (count < sepetmevcut.size()) {
                    try {

                        String url = UygulamaSabitleri.baseURL + "orderForm.php?ref=" + UygulamaSabitleri.ref + "&customerId=" + preferences.getString("musteriid", "") +
                                "&productId=" + sepetmevcut.get(count).getProductId() + "&html=1";
                        kutuphane.GetData(url, Request.Method.GET, new DataInterface() {
                            @Override
                            public void cevap(String response) {
                                count++;
                                Log.e("Gelen index: ", "" + count);
                                sepet_onayla.setText(count + ".  Siparişiniz Yazılıyor.");


                                if (count == sepetmevcut.size()) {
                                    sepet_onayla.setText("Siparişleriniz Verildi...");
                                    sepetmevcut = new ArrayList<>();
                                    sabitleri.setSepet_urunleri(sepetmevcut);
                                    sepet_listele.setAdapter(new SepetAdapter(sepetmevcut, getActivity()));
                                    toplam_fiyat.setText("" + toplamFiyat());
                                } else if (count < sepetmevcut.size()) {
                                    recursive();
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
            }
        }, 300);


    }

    double toplamFiyat() {
        double dob = 0;
        for (int i = 0; i < sabitleri.getSepet_urunleri().size(); i++) {
            dob += Double.parseDouble(sabitleri.getSepet_urunleri().get(i).getPrice());
        }
        return dob;
    }

}
