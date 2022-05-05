package eu.ase.tema2android;

import android.os.Bundle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import eu.ase.tema2android.utilChart.ChartView;
import eu.ase.tema2android.util.Pacient;


public class ChartPacientActivity extends AppCompatActivity {

    public static final String PACIENT_KEY = "pacientKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Pacient> pacients = (List<Pacient>) getIntent().getSerializableExtra(PACIENT_KEY);
        setContentView(new ChartView(getApplicationContext(), getSource(pacients)));
    }

    private Map<String, Integer> getSource(List<Pacient> pacients) {
        if (pacients == null || pacients.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, Integer> source = new HashMap<>();
        for (Pacient pacient : pacients) {
            if (source.containsKey(pacient.getDomiciliuPacient())) { // daca exista deja o cheie din student
                Integer currentValue = source.get(pacient.getDomiciliuPacient());
                source.put(pacient.getDomiciliuPacient(), currentValue + 1);
            } else {
                source.put(pacient.getDomiciliuPacient(), 1);
            }
        }
        return source;
    }
}