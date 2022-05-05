package eu.ase.tema2android.utilCentru;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import eu.ase.tema2android.R;
import eu.ase.tema2android.databaseCentru.Centru;

public class CentruAdapter extends ArrayAdapter<Centru> {

    private int resource;
    private List<Centru> centrus;
    private LayoutInflater inflater;

    public CentruAdapter(@NonNull Context context, int resource, @NonNull List<Centru> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.resource = resource;
        this.centrus = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        Centru centru = centrus.get(position);
        if (centru != null) {
            addDenumireCentru(view, centru.getDenumireCentru());
            addLocatieCentru(view, centru.getLocatieCentru());
            addNumarTelefonCentru(view, centru.getNumarTelefonCentru());
            addCapacitateCentru(view, centru.getCapacitateCentru());

        }
        return view;
    }

    private void addDenumireCentru(View view, String denumireCentru) {
        TextView tv = view.findViewById(R.id.row_tv_denumire_centru);
        addTextViewContent(tv, denumireCentru);
    }

    private void addLocatieCentru(View view, String locatieCentru) {
        TextView tv = view.findViewById(R.id.row_tv_locatie_centru);
        addTextViewContent(tv, locatieCentru);
    }

    private void addNumarTelefonCentru(View view, String numarTelefonCentru) {
        TextView tv = view.findViewById(R.id.row_tv_numar_telefon_centru);
        addTextViewContent(tv, numarTelefonCentru);
    }

    private void addCapacitateCentru(View view, int capacitateCentru) {
        TextView tv = view.findViewById(R.id.row_tv_capacitate_centru);
        addTextViewContent(tv, String.valueOf(capacitateCentru));
    }

    private void addTextViewContent(TextView textView, String value) {
        if (value != null && !value.isEmpty()) {
            textView.setText(value);
        } else {
            textView.setText(R.string.lv_expense_row_default_value);
        }
    }

}
