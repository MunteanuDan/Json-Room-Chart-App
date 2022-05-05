package eu.ase.tema2android;

import android.os.Bundle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import eu.ase.tema2android.utilChart.ChartView;
import eu.ase.tema2android.databaseCentru.Centru;


public class ChartCentruActivity extends AppCompatActivity {

    public static final String CENTRU_KEY = "centruKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Centru> centrus = (List<Centru>) getIntent().getSerializableExtra(CENTRU_KEY);
        setContentView(new ChartView(getApplicationContext(), getSource(centrus)));
    }

    private Map<String, Integer> getSource(List<Centru> centrus) {
        if (centrus == null || centrus.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, Integer> source = new HashMap<>();
        for (Centru centru : centrus) {
            if (source.containsKey(centru.getLocatieCentru())) { // daca exista deja o cheie din student
                Integer currentValue = source.get(centru.getLocatieCentru());
                source.put(centru.getLocatieCentru(), currentValue + 1);
            } else {
                source.put(centru.getLocatieCentru(), 1);
            }
        }
        return source;
    }
}