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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import project.aylin.com.aylinproject.ClassYapilari.Urun;
import project.aylin.com.aylinproject.ClassYapilari.UygulamaSabitleri;
import project.aylin.com.aylinproject.R;

public class UrunIcerikGoster extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {


    TextView iceirk;
    TextView isim;
    Button urunekle;
    SliderLayout mDemoSlider;
    ImageButton mImageBtn;
    TextView mCountTv;
    UygulamaSabitleri sabitleri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_icerik_goster);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gorselAta();
    }


    void gorselAta() {
        sabitleri = (UygulamaSabitleri) getApplicationContext();
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        iceirk = (TextView) findViewById(R.id.icerik_detay);
        isim = (TextView) findViewById(R.id.isim_detay);
        urunekle = (Button) findViewById(R.id.urun_sepete_ekle);

        if (getIntent().hasExtra("urunaktar")) {
            final Urun urun = (Urun) getIntent().getSerializableExtra("urunaktar");
            iceirk.setText("Ürün Açıklaması:\n" + urun.getDescription());
            isim.setText("Ürün İsimi:\n" + urun.getProductName());
            urunekle.setText("Sepete Ekle Fiyat: " + urun.getPrice());
            actionBarSetup("Ürün", "" + urun.getProductName());


            for (String name : urun.getImages()) {
                TextSliderView textSliderView = new TextSliderView(this);

                textSliderView

                        .image(name)
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle().putString("extra", name);
                mDemoSlider.setPresetTransformer("Tablet");
                mDemoSlider.addSlider(textSliderView);
            }

            urunekle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sabitleri.getSepet_urunleri().add(urun);
                    mCountTv.setText("" + sabitleri.getSepet_urunleri().size());
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

            mImageBtn = actionView.findViewById(R.id.image_btn_layout);
            mCountTv = actionView.findViewById(R.id.count_tv);
            mCountTv.setText("" + sabitleri.getSepet_urunleri().size());

        }

        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSharedPreferences(UygulamaSabitleri.sharedTag, Context.MODE_PRIVATE).getString("onay", "").equals("OK")) {
                    Intent intent = new Intent(UrunIcerikGoster.this, AnaMenu.class);
                    intent.putExtra("sepetegit", "GO");
                    startActivity(intent);
                } else {
                    Toast.makeText(UrunIcerikGoster.this, "Giriş yapmadan sepetinizi göremezsiniz!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return true;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    protected void onStop() {

        mDemoSlider.stopAutoCycle();
        super.onStop();
    }
}
