package project.aylin.com.aylinproject.Adapterler;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class SepetAdapter extends BaseAdapter {
    public urunCikart urunCikart;
    ArrayList<Urun> uruns = new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;
    GenelKutuphane kutuphane;

    public SepetAdapter(ArrayList<Urun> uruns, Activity activity) {
        this.uruns = uruns;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        kutuphane = new GenelKutuphane(activity);
        urunCikart = new urunCikart() {
            @Override
            public void urunCikti(int i) {

            }
        };
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
        View v = inflater.inflate(R.layout.sepet_row, null);

        TextView fiyat = (TextView) v.findViewById(R.id.sepet_fiyat);
        TextView isim = (TextView) v.findViewById(R.id.sepet_isim);
        TextView ack = (TextView) v.findViewById(R.id.sepet_acik);
        ImageView resm = (ImageView) v.findViewById(R.id.sepet_image);
        Button cikart = (Button) v.findViewById(R.id.sepet_cikart);

        fiyat.setText("" + getItem(i).getPrice() + " TL");
        isim.setText("" + getItem(i).getProductName());
        ack.setText("" + getItem(i).getDescription());

        if (getItem(i).getImages().size() > 0) {
            kutuphane.ImageNET(resm, getItem(i).getImages().get(0));
        } else {
            resm.setImageDrawable(activity.getResources().getDrawable(R.drawable.bos));
        }

        cikart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urunCikart.urunCikti(i);
            }
        });

        return v;
    }

    public interface urunCikart {
        void urunCikti(int i);
    }
}
