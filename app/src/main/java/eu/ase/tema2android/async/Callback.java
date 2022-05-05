package eu.ase.tema2android.async;

public interface Callback<R>{
    void runResultOnUiThread(R result);
}
