package eu.ase.tema2android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import eu.ase.tema2android.network.HttpManager;
import eu.ase.tema2android.util.Pacient;
import eu.ase.tema2android.util.PacientAdapter;
import eu.ase.tema2android.util.PacientJsonParser;

public class MainActivity extends AppCompatActivity {

    private final static String PACIENTS_URL = "https://jsonkeeper.com/b/UZ0L";
  //  private final static String PACIENTS_URL = "https://jsonkeeper.com/b/2WJK";
    private ListView lvPacients;
    private List<Pacient> pacients = new ArrayList<>();

    private Button btnListaCentre, btnChartPacient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        pacients.add(new Pacient("Dan",
                22, "Bucuresti", false));

        loadPacientsFromHttp();

    }

    private void initComponents() {
        lvPacients = findViewById(R.id.main_lv_bank_accounts);
        addPacientAdapter();

        btnListaCentre = findViewById(R.id.btn_lista_centre);
        btnListaCentre.setOnClickListener(listaCentre());

        // chart
        btnChartPacient = findViewById(R.id.btn_chart_pacient);
        btnChartPacient.setOnClickListener(getChartPacientClickEvent());

    }

    private View.OnClickListener getChartPacientClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChartPacientActivity.class);
                intent.putExtra(ChartPacientActivity.PACIENT_KEY, (Serializable) pacients);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener listaCentre() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CentreActivity.class);
               startActivity(intent);
            }
        };
    }

    private void addPacientAdapter() {
        PacientAdapter adapter = new PacientAdapter(getApplicationContext(), R.layout.lv_row, pacients, getLayoutInflater());
        lvPacients.setAdapter(adapter);
    }

    private void loadPacientsFromHttp() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                HttpManager manager = new HttpManager(PACIENTS_URL);
                String result = manager.process();
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mainThreadGetPacientsFromHttpCallback(result);
                    }
                });
            }
        };
        thread.start();
    }

    private void mainThreadGetPacientsFromHttpCallback(String result) {
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        pacients.addAll(PacientJsonParser.fromJson(result));
        notifyAdapter();
    }

    private void notifyAdapter() {
        PacientAdapter adapter = (PacientAdapter) lvPacients.getAdapter();
        adapter.notifyDataSetChanged();
    }


}