package project.aylin.com.aylinproject.Adapterler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import project.aylin.com.aylinproject.ClassYapilari.Siparis;
import project.aylin.com.aylinproject.ClassYapilari.Urun;
import project.aylin.com.aylinproject.Kutuphaneler.GenelKutuphane;
import project.aylin.com.aylinproject.R;

/**
 * Created by aylin on 18/02/2018.
 */

public class SiparisAdapter extends BaseAdapter {
    ArrayList<Siparis> uruns = new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;
    GenelKutuphane kutuphane;

    public SiparisAdapter(ArrayList<Siparis> uruns, Activity activity) {
        this.uruns = uruns;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        kutuphane = new GenelKutuphane(activity);

    }


    @Override
    public int getCount() {
        return uruns.size();
    }

    @Override
    public Siparis getItem(int i) {
        return uruns.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.siparis_row, null);

        TextView fiyat = (TextView) v.findViewById(R.id.sepet_fiyat);
        TextView isim = (TextView) v.findViewById(R.id.sepet_isim);
        TextView ack = (TextView) v.findViewById(R.id.sepet_acik);
        TextView tarih = (TextView) v.findViewById(R.id.sepet_tarih);

        tarih.setText("" + getItem(i).getDate());
        fiyat.setText("" + getItem(i).getPrice() + " TL");
        isim.setText("" + getItem(i).getProductName());
        ack.setText("" + getItem(i).getDescription());

        return v;
    }

}
