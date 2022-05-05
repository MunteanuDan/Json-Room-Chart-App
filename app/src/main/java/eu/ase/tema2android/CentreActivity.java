package eu.ase.tema2android;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import eu.ase.tema2android.async.Callback;
import eu.ase.tema2android.databaseCentru.Centru;
import eu.ase.tema2android.databaseCentru.CentruService;
import eu.ase.tema2android.utilCentru.CentruAdapter;

public class CentreActivity extends AppCompatActivity {

    private ListView lvCentrus;
    private Button btnCentru, btnChartAlert, btnChart;

    private List<Centru> centrus = new ArrayList<>();
    private ActivityResultLauncher<Intent> addCentruLauncher;
    private ActivityResultLauncher<Intent> updateCentruLauncher;

    private CentruService centruService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centre);

        initComponents();

        addCentruLauncher = getAddCentruLauncher();

        //initializare ExpenseService pentru procesari asupra tabelei Expense
        centruService = new CentruService(getApplicationContext());
        //selectarea tuturor cheltuielilor
        centruService.getAll(getAllCentrusCallback());

        // pentru update:
        updateCentruLauncher = getUpdateCentruLauncher();

    }




    private ActivityResultLauncher<Intent> getAddCentruLauncher() {
        ActivityResultCallback<ActivityResult> callback = getAddCentruActivityResultCallback();
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
    }

    private ActivityResultCallback<ActivityResult> getAddCentruActivityResultCallback() {
        return new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result != null && result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Centru centru = (Centru) result.getData().getSerializableExtra(AddCentruActivity.CENTRU_KEY);
                    //inserare in baza de date
                    centruService.insert(centru, getInsertCentruCallback()); // al doilea param este locul in care ni se precizeaza daca insert-ul a functionat
                    //apelare varianta v2
                    //expenseService.insertV2(expense, getInsertExpenseCallback());
                }
            }
        };
    }



    private void initComponents() {
        lvCentrus = findViewById(R.id.lv_centre);
        btnCentru = findViewById(R.id.btn_adauga_centre);
        addAdapter();
        btnCentru.setOnClickListener(addCentruEventListener());

        // pentru update:
        lvCentrus.setOnItemClickListener(getItemClickEvent());

        // pentru delete:
        lvCentrus.setOnItemLongClickListener(getItemLongClickEvent());

        // pentru chart alert
        btnChartAlert = findViewById(R.id.btn_chart_alert);
        btnChartAlert.setOnClickListener(getChartAlertClickEvent());

        btnChart = findViewById(R.id.btn_chart);
        btnChart.setOnClickListener(getChartClickEvent());


    }

// chart

    private View.OnClickListener getChartClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChartCentruActivity.class);
                intent.putExtra(ChartCentruActivity.CENTRU_KEY, (Serializable) centrus);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener getChartAlertClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder build = new AlertDialog.Builder(CentreActivity.this)
                        .setTitle(R.string.chart_generation_alert_title)
                        .setMessage(R.string.chart_generation_alert_message)
                        .setPositiveButton(R.string.chart_generation_alert_positive_button, getChartGenerationPositiveEvent())
                        .setNegativeButton(R.string.chart_generation_alert_negative_button, getChartGegenerationNegativeEvent());
                build.create().show();

            }
        };
    }

    private DialogInterface.OnClickListener getChartGegenerationNegativeEvent() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), R.string.chart_generation_alert_quit,
                        Toast.LENGTH_SHORT).show();
            }
        };
    }

    private DialogInterface.OnClickListener getChartGenerationPositiveEvent() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), ChartCentruActivity.class);
                intent.putExtra(ChartCentruActivity.CENTRU_KEY, (Serializable) centrus);
                startActivity(intent);
            }
        };
    }


    // pana aici chart




    private View.OnClickListener addCentruEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddCentruActivity.class);
                addCentruLauncher.launch(intent);
            }
        };
    }

    private void addAdapter() {
        CentruAdapter adapter = new CentruAdapter(getApplicationContext(), R.layout.lv_row_centru,
                centrus, getLayoutInflater());
        lvCentrus.setAdapter(adapter);
    }

    private void notifyAdapter() {
        CentruAdapter adapter = (CentruAdapter) lvCentrus.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private Callback<Centru> getInsertCentruCallback() {
        return new Callback<Centru>() {
            @Override
            public void runResultOnUiThread(Centru centru) {
                //aici suntem notificati din thread-ul secundar cand operatia de adaugare in baza de date s-a executat
                if (centru != null) {
                    centrus.add(centru);
                    notifyAdapter();
                }
            }
        };
    }

    private Callback<List<Centru>> getAllCentrusCallback() {
        return new Callback<List<Centru>>() {
            @Override
            public void runResultOnUiThread(List<Centru> results) {
                if (results != null) { // daca avem cv in lista noastra
                    centrus.clear(); // o golim
                    centrus.addAll(results); // adaugam tot ce am citit din baza de date
                    notifyAdapter(); // notificam adapter-ul ca sa se actualizeze vizual
                }
            }
        };
    }

    // pentru update:

    private AdapterView.OnItemClickListener getItemClickEvent() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AddCentruActivity.class);
                intent.putExtra(AddCentruActivity.CENTRU_KEY, centrus.get(position)); // trimitem informatiile in formularul de adaugare pt update
                // cu putExtra comunicam informatii intre 2 activitati
                updateCentruLauncher.launch(intent);
            }
        };
    }

    private ActivityResultLauncher<Intent> getUpdateCentruLauncher() {
        ActivityResultCallback<ActivityResult> callback = getUpdateCentruActivityResultCallback();
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
    }

    private ActivityResultCallback<ActivityResult> getUpdateCentruActivityResultCallback() {
        return new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result != null && result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Centru centru = (Centru) result.getData().getSerializableExtra(AddCentruActivity.CENTRU_KEY);
                    //update in baza de date
                    centruService.update(centru, updateCentruCallback());
                }
            }
        };
    }

    private Callback<Centru> updateCentruCallback() {
        return new Callback<Centru>() {
            @Override
            public void runResultOnUiThread(Centru result) {
                if (result != null) { // daca a mers operatie de update
                    //actualizare ListView
                    for (Centru centru : centrus) {
                        if (centru.getId() == result.getId()) {
                            // modificare date in update dupa bunul plac
                            centru.setDenumireCentru(result.getDenumireCentru());
                            centru.setLocatieCentru(result.getLocatieCentru());
                            centru.setNumarTelefonCentru(result.getNumarTelefonCentru());
                            centru.setCapacitateCentru(result.getCapacitateCentru());
                            break;
                        }
                    }
                    notifyAdapter();
                }
            }
        };
    }

    // pt delete
    private AdapterView.OnItemLongClickListener getItemLongClickEvent() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //stergere element
                centruService.delete(centrus.get(position), deleteCentruCallback(position));
                return true;
            }
        };
    }

    private Callback<Boolean> deleteCentruCallback(int position) {
        return new Callback<Boolean>() {
            @Override
            public void runResultOnUiThread(Boolean result) {
                if (result) {
                    centrus.remove(position);
                    notifyAdapter();
                }
            }
        };
    }


}