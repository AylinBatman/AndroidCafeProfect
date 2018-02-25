package project.aylin.com.aylinproject.Fragmentler;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;

import project.aylin.com.aylinproject.ClassYapilari.UygulamaSabitleri;
import project.aylin.com.aylinproject.Kutuphaneler.DataInterface;
import project.aylin.com.aylinproject.Kutuphaneler.GenelKutuphane;
import project.aylin.com.aylinproject.Kutuphaneler.KayitveGirisCallback;
import project.aylin.com.aylinproject.R;

import static android.content.Context.MODE_PRIVATE;
import static project.aylin.com.aylinproject.Kutuphaneler.GenelKutuphane.urlEncoder;

/**
 * A simple {@link Fragment} subclass.
 */
public class KayitFragment extends Fragment {

    EditText kisi_adi_et, kisi_soyadi_et, telefon_et, mail_et, sifre_et;
    CheckBox onay_cb;
    GenelKutuphane kutuphane;
    Button kaydetsecim;

    public KayitFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_kayit, container, false);
        gorselAta(v);
        return v;
    }


    void gorselAta(View v) {

        kutuphane = new GenelKutuphane(getActivity());

        kisi_adi_et = (EditText) v.findViewById(R.id.adsecim);
        kisi_soyadi_et = (EditText) v.findViewById(R.id.soyadsecim);
        telefon_et = (EditText) v.findViewById(R.id.telefonsecim);
        mail_et = (EditText) v.findViewById(R.id.emailsecim);
        sifre_et = (EditText) v.findViewById(R.id.sifresecim);

        kaydetsecim = (Button) v.findViewById(R.id.kaydetsecim);


        kaydetsecim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kayitOl();
            }
        });
    }

    void kayitOl() {

        final String kisiadi = "" + kisi_adi_et.getText().toString().trim();
        final String kisisoyadi = "" + kisi_soyadi_et.getText().toString().trim();
        String telefon = "" + telefon_et.getText().toString().trim();
        final String mail = "" + mail_et.getText().toString().trim().replaceAll(" ", "");
        String sifre = "" + sifre_et.getText().toString().trim();


        if (kisiadi.length() > 0 && kisisoyadi.length() > 0 && telefon.length() > 0 && mail.length() > 0 && sifre.length() > 5) {

            String url = UygulamaSabitleri.baseURL + "userRegister.php?ref=" + UygulamaSabitleri.ref +
                    "&userName=" + urlEncoder(kisiadi) +
                    "&userSurname=" + urlEncoder(kisisoyadi) +
                    "&userPhone=" + telefon +
                    "&userMail=" + mail +
                    "&userPass=" + urlEncoder(sifre);


            kutuphane.GetData(url, Request.Method.GET, new DataInterface() {
                @Override
                public void cevap(String response) {
                    Log.e("Cevap kayıt: ", response);

                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray("user");
                        JSONObject icsel = (JSONObject) jsonArray.get(0);
                        if (icsel.getBoolean("durum")) {
                            Toast.makeText(getActivity(), "Kayıt Başarılı Yönlendiriliyorsunuz!", Toast.LENGTH_SHORT).show();
                            SharedPreferences kulbilgi;
                            SharedPreferences.Editor editor;
                            kulbilgi = getActivity().getSharedPreferences(UygulamaSabitleri.sharedTag, MODE_PRIVATE);
                            editor = kulbilgi.edit();
                            editor.putString("musteriadsoyad", "" + kisiadi + " " + kisisoyadi);
                            editor.putString("musteriemail", "" + mail);
                            editor.putString("musteriid", "" + icsel.getString("kullaniciId"));
                            editor.putString("onay", "OK");
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
                        e.printStackTrace();
                    }
                }

                @Override
                public void hata(String error) {

                }
            });
        } else {
            Toast.makeText(getActivity(), "Lütfen tüm boşlukları doldurduğunuzdan ve şifrenizin en az 6 karakterden oluştuğundan emin olun!", Toast.LENGTH_LONG).show();
        }

    }

}
