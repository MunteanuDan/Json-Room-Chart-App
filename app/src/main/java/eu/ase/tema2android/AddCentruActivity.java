package eu.ase.tema2android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import eu.ase.tema2android.databaseCentru.Centru;

public class AddCentruActivity extends AppCompatActivity {

    public static final String ADD_CENTRU_KEY = "ADD_CENTRU_KEY"; // pt json
    public static final String CENTRU_KEY = "centruKey"; // pt room

    EditText etDenumireCentru, etLocatieCentru, etNumarTelefonCentru;
    SeekBar skCapacitateCentru;
    Button btnAddCentru;

    private Centru centru;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_centru);

        initComponents();
        intent = getIntent();

        if (intent.hasExtra(CENTRU_KEY)) { // ne aplam pe operatie de update
            centru = (Centru) intent.getSerializableExtra(CENTRU_KEY);

            // pt update
            createViewsFromCentru();

        }
    }



    private void initComponents() {

        etDenumireCentru = findViewById(R.id.et_denumire_centru);
        etLocatieCentru = findViewById(R.id.et_locatie_centru);
        etNumarTelefonCentru = findViewById(R.id.et_numar_telefon_centru);
        skCapacitateCentru = findViewById(R.id.sk_capacitate_centru);

        btnAddCentru = findViewById(R.id.btn_add_centru);

        btnAddCentru.setOnClickListener(saveCentruEventListener());

    }

    private View.OnClickListener saveCentruEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    createFromViews();
                    intent.putExtra(CENTRU_KEY, centru);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
    }

    private void createFromViews() {

        String denumireCentru = etDenumireCentru.getText().toString();
        String locatieCentru = etLocatieCentru.getText().toString();
        String numarTelefonCentru = etNumarTelefonCentru.getText().toString();
        int capacitateCentru = Integer.parseInt(String.valueOf(skCapacitateCentru.getProgress()));
        if (centru == null) {
            //modul insert
            centru = new Centru(denumireCentru, locatieCentru, numarTelefonCentru, capacitateCentru);
        } else {
            //modul update. atentie sa nu pierdem id-ul
            centru.setDenumireCentru(denumireCentru);
            centru.setLocatieCentru(locatieCentru);
            centru.setNumarTelefonCentru(numarTelefonCentru);
            centru.setCapacitateCentru(capacitateCentru);
        }
    }

    private boolean isValid() {

        if (etDenumireCentru.getText() == null
                || etDenumireCentru.getText().toString().trim().length() < 3) {
            Toast.makeText(getApplicationContext(),
                    R.string.denumire_centru_error,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }

        if (etLocatieCentru.getText() == null
                || etLocatieCentru.getText().toString().trim().length() <3) {
            Toast.makeText(getApplicationContext(),
                    R.string.locatie_centru_error,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }

        if (etNumarTelefonCentru.getText() == null
                || etNumarTelefonCentru.getText().toString().trim().length() <3) {
            Toast.makeText(getApplicationContext(),
                    R.string.numar_telefon_centru_error,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }


        return true;
    }

    private void createViewsFromCentru() {
        if (centru == null) {
            return;
        }

        if(centru.getDenumireCentru() !=null){
            etDenumireCentru.setText(centru.getDenumireCentru());
        }

        if(centru.getLocatieCentru() !=null){
            etLocatieCentru.setText(centru.getLocatieCentru());
        }

        if(centru.getNumarTelefonCentru() !=null){
            etNumarTelefonCentru.setText(centru.getNumarTelefonCentru());
        }

        if(centru.getCapacitateCentru() !=0){
            skCapacitateCentru.setProgress(centru.getCapacitateCentru());
        }


    }


}