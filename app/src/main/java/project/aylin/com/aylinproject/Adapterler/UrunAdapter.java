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

import project.aylin.com.aylinproject.Aktiviteler.UrunIcerikGoster;
import project.aylin.com.aylinproject.ClassYapilari.Urun;
import project.aylin.com.aylinproject.Kutuphaneler.GenelKutuphane;
import project.aylin.com.aylinproject.R;

/**
 * Created by aylin on 18/02/2018.
 */

public class UrunAdapter extends BaseAdapter {
    ArrayList<Urun> uruns;
    Activity activity;
    LayoutInflater layoutInflater;
    GenelKutuphane kutuphane;

    public UrunAdapter(ArrayList<Urun> uruns, Activity activity) {
        this.uruns = uruns;
        this.activity = activity;
        layoutInflater = LayoutInflater.from(activity);
        kutuphane = new GenelKutuphane(activity);
    }

    @Override
    public int getCount() {
        return uruns.size();
    }

    @Override
    public Urun getItem(int i) {
        return uruns.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.urun_row, null);
        TextView fiyat = (TextView) v.findViewById(R.id.urun_fiyat);
        TextView isim = (TextView) v.findViewById(R.id.urun_isim);
        final TextView ack = (TextView) v.findViewById(R.id.urun_ack);
        ImageView resm = (ImageView) v.findViewById(R.id.urun_resim);

        fiyat.setText("" + getItem(i).getPrice() + " TL");
        isim.setText("" + getItem(i).getProductName());
        ack.setText("" + getItem(i).getDescription());

        if (getItem(i).getImages().size() > 0) {
            kutuphane.ImageNET(resm, getItem(i).getImages().get(0));
        } else {
            resm.setImageDrawable(activity.getResources().getDrawable(R.drawable.bos));
        }


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, UrunIcerikGoster.class);
                intent.putExtra("urunaktar", getItem(i));
                activity.startActivity(intent);
            }
        });

        return v;
    }
}
