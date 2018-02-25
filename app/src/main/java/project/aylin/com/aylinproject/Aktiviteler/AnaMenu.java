package project.aylin.com.aylinproject.Aktiviteler;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import project.aylin.com.aylinproject.Adapterler.SepetAdapter;
import project.aylin.com.aylinproject.ClassYapilari.UygulamaSabitleri;
import project.aylin.com.aylinproject.Fragmentler.GirisFragment;
import project.aylin.com.aylinproject.Fragmentler.KatergoriFragment;
import project.aylin.com.aylinproject.Fragmentler.KayitFragment;
import project.aylin.com.aylinproject.Fragmentler.SepetFragment;
import project.aylin.com.aylinproject.Fragmentler.SiparisFragment;
import project.aylin.com.aylinproject.Kutuphaneler.KayitveGirisCallback;
import project.aylin.com.aylinproject.R;

public class AnaMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, KayitveGirisCallback.KullaniciDinleyici {
    Class fragmentClass;
    SharedPreferences kulbilgi;
    SharedPreferences.Editor editor;
    ImageButton mImageBtn;
    TextView mCountTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        KayitveGirisCallback.getInstance().setListener(this);
        kulbilgi = getSharedPreferences(UygulamaSabitleri.sharedTag, MODE_PRIVATE);
        editor = kulbilgi.edit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationKurulum();
        fragmentSwitch(KatergoriFragment.class, "Ürün", "Kategorileri");
        if(getIntent().hasExtra("sepetegit")){
            fragmentSwitch(SepetFragment.class, "Alışveriş", "Sepetim");
        }
    }


    void navigationKurulum() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().clear();
        if (kulbilgi.getString("onay", "").equals("")) {
            navigationView.inflateMenu(R.menu.menu_drawer);
            TextView isim = navigationView.getHeaderView(0).findViewById(R.id.tv_nav_isim);
            TextView mail = navigationView.getHeaderView(0).findViewById(R.id.tv_nav_email);
            mail.setText("Misafir");
            isim.setText("Hoşgeldin");
        } else if (kulbilgi.getString("onay", "").equals("OK")) {
            navigationView.inflateMenu(R.menu.hatirla_menu_drawer);
            String ad = kulbilgi.getString("musteriadsoyad", "").toString();
            String email = kulbilgi.getString("musteriemail", "").toString();
            TextView isim = navigationView.getHeaderView(0).findViewById(R.id.tv_nav_isim);
            TextView mail = navigationView.getHeaderView(0).findViewById(R.id.tv_nav_email);
            mail.setText(email);
            isim.setText("Merhaba " + ad);
        }


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_kategoriler) {
            fragmentSwitch(KatergoriFragment.class, "Ürün", "Kategorileri");
        } else if (id == R.id.nav_kayit_ol) {
            fragmentSwitch(KayitFragment.class, "Kayıt", "Ekranı");
        } else if (id == R.id.nav_giris_yap) {
            fragmentSwitch(GirisFragment.class, "Giriş", "Ekranı");
        } else if (id == R.id.nav_cikis) {
            editor.putString("onay", "");
            editor.commit();
            navigationKurulum();
        } else if (id == R.id.nav_sepetim) {
            fragmentSwitch(SepetFragment.class, "Alışveriş", "Sepetim");
        } else if (id == R.id.nav_siparis_gecmisim) {
            fragmentSwitch(SiparisFragment.class, "Geçmiş", "Siparişleriml");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void fragmentSwitch(Class cls, String baslik, String altbaslik) {
        Fragment fragment = null;
        fragmentClass = cls;
        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragmentManager.beginTransaction().replace(R.id.fragmentTutucu, fragment).commit();
        actionBarSetup(baslik, altbaslik);
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
    public void kullaniciDurum(boolean durum) {
        if (!durum) Log.e("Çıkış", "OK");
        else Log.e("Giriş", "OK");
        navigationKurulum();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
                    fragmentSwitch(SepetFragment.class, "Alışveriş", "Sepetim");
                } else {
                    Toast.makeText(AnaMenu.this, "Giriş yapmadan sepetinizi göremezsiniz!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return true;
    }
}
