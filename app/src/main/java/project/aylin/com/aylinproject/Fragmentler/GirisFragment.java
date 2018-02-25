package project.aylin.com.aylinproject.Fragmentler;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import project.aylin.com.aylinproject.ClassYapilari.UygulamaSabitleri;
import project.aylin.com.aylinproject.Kutuphaneler.DataInterface;
import project.aylin.com.aylinproject.Kutuphaneler.GenelKutuphane;
import project.aylin.com.aylinproject.Kutuphaneler.KayitveGirisCallback;
import project.aylin.com.aylinproject.R;

import static android.content.Context.MODE_PRIVATE;
import static project.aylin.com.aylinproject.Kutuphaneler.GenelKutuphane.urlEncoder;


public class GirisFragment extends Fragment {

    Button girisyap;
    EditText kisimail;
    EditText kisisifre;
    GenelKutuphane kutuphane;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_giris, container, false);
        gorselAta(v);
        return v;
    }

    void gorselAta(View v) {

        kutuphane = new GenelKutuphane(getActivity());
        girisyap = (Button) v.findViewById(R.id.kullanici_giris);
        kisimail = (EditText) v.findViewById(R.id.kullanici_id);
        kisisifre = (EditText) v.findViewById(R.id.kullanici_sifre);

        girisyap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                girisyap();
            }
        });


    }

    void girisyap() {

        String mail = "" + kisimail.getText().toString().trim().replaceAll(" ", "");
        String sifre = "" + kisisifre.getText().toString().trim();


        if (mail.length() > 0 && sifre.length() > 0) {

            String url = UygulamaSabitleri.baseURL + "userLogin.php?ref=" + UygulamaSabitleri.ref +
                    "&userEmail=" + mail +
                    "&userPass=" + urlEncoder(sifre) +
                    "&face=no";


            kutuphane.GetData(url, Request.Method.GET, new DataInterface() {
                @Override
                public void cevap(String response) {
                    Log.e("Cevap kayıt: ", response);

                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray("user");
                        JSONObject icsel = (JSONObject) jsonArray.get(0);
                        if (icsel.getBoolean("durum")) {
                            Toast.makeText(getActivity(), "Giriş Başarılı Yönlendiriliyorsunuz!", Toast.LENGTH_SHORT).show();
                            SharedPreferences kulbilgi;
                            SharedPreferences.Editor editor;
                            kulbilgi = getActivity().getSharedPreferences(UygulamaSabitleri.sharedTag, MODE_PRIVATE);
                            editor = kulbilgi.edit();
                            JSONObject object1 = (JSONObject) icsel.getJSONObject("bilgiler");
                            editor.putString("onay", "OK");
                            editor.putString("musteriadsoyad", "" + object1.getString("userName") + " " + object1.getString("userSurname"));
                            editor.putString("musteriemail", "" + object1.getString("userEmail"));
                            editor.putString("musteriid", "" + object1.getString("userId"));

                            editor.commit();
                            Fragment fragment = null;
                            Class fragmentClass = KatergoriFragment.class;
                            FragmentManager fragmentManager = getFragmentManager();
                            try {
                                fragment = (Fragment) fragmentClass.newInstance();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            KayitveGirisCallback.getInstance().changeState(true);
                            fragmentManager.beginTransaction().replace(R.id.fragmentTutucu, fragment).commit();
                        } else {
                            Toast.makeText(getActivity(), "" + icsel.getString("mesaj"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {

                    }
                }

                @Override
                public void hata(String error) {

                }
            });
        } else {
            Toast.makeText(getActivity(), "Lütfen tüm boşlukları doldurduğunuzdan emin olun!", Toast.LENGTH_LONG).show();
        }

    }

}
