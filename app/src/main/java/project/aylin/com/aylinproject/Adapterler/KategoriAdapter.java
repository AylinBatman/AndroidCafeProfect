package project.aylin.com.aylinproject.Adapterler;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import project.aylin.com.aylinproject.Aktiviteler.KategoriGoster;
import project.aylin.com.aylinproject.ClassYapilari.GaleriResim;
import project.aylin.com.aylinproject.ClassYapilari.Kategori;
import project.aylin.com.aylinproject.Kutuphaneler.GenelKutuphane;
import project.aylin.com.aylinproject.R;

/**
 * Created by aylin on 10/02/2018.
 */

public class KategoriAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<Kategori> kategoriArrayList = new ArrayList<>();
    LayoutInflater layoutInflater;
    ArrayList<GaleriResim> galeriResims = new ArrayList<>();
    GenelKutuphane kutuphane;

    public KategoriAdapter(Activity activity, ArrayList<Kategori> kategoriArrayList, ArrayList<GaleriResim> galeriResims) {
        this.activity = activity;
        this.kategoriArrayList = kategoriArrayList;
        this.galeriResims = galeriResims;
        kutuphane = new GenelKutuphane(activity);
        layoutInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return kategoriArrayList.size();
    }

    @Override
    public Kategori getItem(int i) {
        return kategoriArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.kategori_row, null);

        TextView tv_isim = (TextView) v.findViewById(R.id.kategori_isim);
        ImageView iv_resim = (ImageView) v.findViewById(R.id.kategori_resim);
        for (int a = 0; a < galeriResims.size(); a++) {
            if (galeriResims.get(a).getAlt().equals(getItem(i).getCatogryName())) {
                kutuphane.ImageNET(iv_resim, galeriResims.get(a).getImg());
            }
        }

        tv_isim.setText("" + kategoriArrayList.get(i).getCatogryName());
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, KategoriGoster.class);
                intent.putExtra("kat_id", getItem(i).getCatogryId());
                activity.startActivity(intent);
            }
        });
        return v;
    }
}
