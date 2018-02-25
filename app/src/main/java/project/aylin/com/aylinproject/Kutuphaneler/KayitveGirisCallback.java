package project.aylin.com.aylinproject.Kutuphaneler;

/**
 * Created by aylin on 18/02/2018.
 */

public class KayitveGirisCallback {

    public interface KullaniciDinleyici {
        void kullaniciDurum(boolean durum);
    }

    private static KayitveGirisCallback mInstance;
    private KullaniciDinleyici mListener;
    private boolean mState;

    private KayitveGirisCallback() {}

    public static KayitveGirisCallback getInstance() {
        if(mInstance == null) {
            mInstance = new KayitveGirisCallback();
        }
        return mInstance;
    }

    public void setListener(KullaniciDinleyici listener) {
        mListener = listener;
    }

    public void changeState(boolean change) {
        if(mListener != null) {
            mState = change;
            durumDegistir();
        }
    }
    public boolean kdurum() {
        return mState;
    }

    private void durumDegistir() {
        mListener.kullaniciDurum(kdurum());
    }

}

