package eu.ase.tema2android.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import eu.ase.tema2android.R;


public class PacientAdapter extends ArrayAdapter<Pacient>{

  //  private Context context;
    private int resource;
    private List<Pacient> pacients;
    private LayoutInflater inflater;

    public PacientAdapter(@NonNull Context context, int resource, @NonNull List<Pacient> objects,
                              LayoutInflater inflater) {
        super(context, resource, objects);
        this.resource = resource;
        this.pacients = objects;
        this.inflater = inflater;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View cview, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        Pacient pacient = pacients.get(position);
        if (pacient == null) {
            return view;
        }
        addNumePacient(view, pacient.getNumePacient());
        addVarstaPacient(view, pacient.getVarstaPacient());
        addDomiciliuPacient(view, pacient.getDomiciliuPacient());
        addRezultatTest(view, pacient.getRezultatTest());
        return view;
    }

    private void addNumePacient(View view, String numePacient) {
        TextView tv = view.findViewById(R.id.row_tv_nume_pacient);
        populateTvContent(tv, numePacient);
    }

    private void addVarstaPacient(View view, int varstaPacient) {
        TextView tv = view.findViewById(R.id.row_tv_varsta_pacient);
        populateTvContent(tv, String.valueOf(varstaPacient));
    }

    private void addDomiciliuPacient(View view, String domiciliuPacient) {
        TextView tv = view.findViewById(R.id.row_tv_domiciliu_pacient);
        populateTvContent(tv, domiciliuPacient);
    }

    private void addRezultatTest(View view, Boolean rezultatTest) {
        TextView tv = view.findViewById(R.id.row_tv_rezultat_test);
        populateTvContent(tv, String.valueOf(rezultatTest));
    }

    private void populateTvContent(TextView tv, String value) {

        if (value != null && !value.trim().isEmpty()) {
            tv.setText(value);
        } else {
            tv.setText(R.string.defaultValue);
        }

    }


}
