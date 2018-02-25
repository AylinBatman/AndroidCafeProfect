package project.aylin.com.aylinproject.Kutuphaneler;

/**
 * Created by aylin on 31.01.2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import project.aylin.com.aylinproject.R;


public class GenelKutuphane {

    /**
     * we have to be sure about activity must exist in library because some libraries require Activity
     **/

    Activity activity;

    /**
     * Constructor of Library
     **/

    public GenelKutuphane(Activity activity) {
        this.activity = activity;
    }

    public void GetData(String URL, int Method, final DataInterface callback) {

        Log.e("İstek URLsi: ", URL);  //we see our url request

        if (internetControl())
            try {
                RequestQueue mRequestQueue;
                StringRequest stringRequest = (StringRequest) new StringRequest(Method, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    response = URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"), "UTF-8");
                                    Log.e("Gelen Veri: ", response);
                                    callback.cevap(response.toString());

                                } catch (Exception e) {
                                    Log.e("Kütüphane Hatası: ", e.toString());
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.hata(error.toString());

                            }
                        }).setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                mRequestQueue = Volley.newRequestQueue(activity.getApplicationContext());
                mRequestQueue.add(stringRequest);
            } catch (Exception e) {
                Log.e("ERROR :: ", e.toString());

            }
        else
            DialogFunc("" + activity.getString(R.string.internet_yok_hatasi_icerik), "" + activity.getString(R.string.internet_yok_hatasi_baslik), new desicion() {
                @Override
                public void desicion(boolean bool) {
                    if (bool)
                        openWifi();
                }
            });


    }

    public interface desicion {
        void desicion(boolean bool);
    }


    public boolean internetControl() {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            Toast.makeText(activity, "" + activity.getString(R.string.internet_yok_hatasi_icerik), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (info.isRoaming()) {
            return false;
        }
        return true;
    }

    public void ImageNET(final ImageView imageView,final String url) {


        Picasso.with(activity)
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(activity)
                                .load(url)
                                .error(R.drawable.bos)
                                .into(imageView, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
                                        Log.v("Picasso", "resim okunamadı");
                                    }
                                });
                    }
                });


    }

    public void DialogFunc(String icerik, String baslik, final desicion ofUser) {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(activity, R.style.myTheme);
        } else {
            builder = new AlertDialog.Builder(activity);
        }

        final AlertDialog dialog = builder.setTitle(baslik)
                .setMessage(icerik)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ofUser.desicion(true);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ofUser.desicion(false);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
            }
        });
    }

    public static String urlEncoder(String input) {
        try {
            return URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }


    public void openWifi() {
        activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }
}

