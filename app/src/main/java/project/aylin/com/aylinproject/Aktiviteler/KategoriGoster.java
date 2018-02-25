package project.aylin.com.aylinproject.Aktiviteler;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import project.aylin.com.aylinproject.Adapterler.KategoriAdapter;
import project.aylin.com.aylinproject.Adapterler.UrunAdapter;
import project.aylin.com.aylinproject.ClassYapilari.GaleriResim;
import project.aylin.com.aylinproject.ClassYapilari.Kategori;
import project.aylin.com.aylinproject.ClassYapilari.Urun;
import project.aylin.com.aylinproject.ClassYapilari.UygulamaSabitleri;
import project.aylin.com.aylinproject.Kutuphaneler.DataInterface;
import project.aylin.com.aylinproject.Kutuphaneler.GenelKutuphane;
import project.aylin.com.aylinproject.R;

public class KategoriGoster extends AppCompatActivity {
    GenelKutuphane kutuphane;
    GridView urunListeleyici;

    ImageButton mImageBtn;
    TextView mCountTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_goster);
        gorselAta();
        actionBarSetup("Seçili Kategori", "Ürünleri Gösteriliyor...");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    void gorselAta() {
        kutuphane = new GenelKutuphane(this);
        urunListeleyici = (GridView) findViewById(R.id.gridview);
//        http://jsonbulut.com/json/product.php?ref=9ccac60641eec5be35a53f307886f715&start=1&categoryId=1
        urunleriListele();
    }


    void urunleriListele() {

        final ArrayList<Urun> kategoris = new ArrayList<>();

        if (getIntent().hasExtra("kat_id")) {
            String url = UygulamaSabitleri.baseURL + "product.php?ref=" + UygulamaSabitleri.ref + "&start=1&categoryId=" + getIntent().getExtras().getString("kat_id");
            kutuphane.GetData(url, Request.Method.GET, new DataInterface() {
                @Override
                public void cevap(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray("Products");
                        JSONObject icsel = (JSONObject) jsonArray.get(0);
                        JSONArray arrayKAT = icsel.getJSONArray("bilgiler");
                        for (int i = 0; i < arrayKAT.length(); i++) {
                            final ArrayList<String> galeriResims = new ArrayList<>();
                            JSONObject ktOBJ = (JSONObject) arrayKAT.get(i);
                            if (ktOBJ.getBoolean("image")) {

                                JSONArray resimler = ktOBJ.getJSONArray("images");
                                for (int j = 0; j < resimler.length(); j++) {
                                    JSONObject resim = (JSONObject) resimler.get(j);
                                    galeriResims.add(resim.getString("normal"));
                                }

                            }

                            kategoris.add(new Urun(
                                    ktOBJ.getString("productId"),
                                    ktOBJ.getString("productName"),
                                    ktOBJ.getString("description"),
                                    ktOBJ.getString("price"),
                                    galeriResims
                            ));


                        }

                        urunListeleyici.setAdapter(new UrunAdapter(kategoris, KategoriGoster.this));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void hata(String error) {

                }
            });
        } else {
            finish();
        }


    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void actionBarSetup(String baslik, String altbaslik) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar ab = getSupportActionBar();
            ab.setTitle(baslik);
            ab.setSubtitle(altbaslik);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem mCartIconMenuItem;
        getMenuInflater().inflate(R.menu.sepetmenusu, menu);
        mCartIconMenuItem = menu.findItem(R.id.cart_count_menu_item);

        View actionView = mCartIconMenuItem.getActionView();

        if (actionView != null) {
            UygulamaSabitleri sabitleri = (UygulamaSabitleri) getApplicationContext();
            mImageBtn = actionView.findViewById(R.id.image_btn_layout);
            mCountTv = actionView.findViewById(R.id.count_tv);
            mCountTv.setText("" + sabitleri.getSepet_urunleri().size());

        }

        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSharedPreferences(UygulamaSabitleri.sharedTag, Context.MODE_PRIVATE).getString("onay", "").equals("OK")) {
                    Intent intent = new Intent(KategoriGoster.this,AnaMenu.class);
                    intent.putExtra("sepetegit","GO");
                    startActivity(intent);
                } else {
                    Toast.makeText(KategoriGoster.this, "Giriş yapmadan sepetinizi göremezsiniz!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return true;
    }
}
