package eu.ase.tema2android.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PacientJsonParser {

    public static final String NUME_PACIENT = "numePacient";
    public static final String VARSTA_PACIENT = "varstaPacient";
    public static final String DOMICILIU_PACIENT = "domiciliuPacient";
    public static final String REZULTAT_TEST = "rezultatTest";

    public static List<Pacient> fromJson(String json) {
        try {
            JSONArray array = new JSONArray(json);
            return readPacients(array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static List<Pacient> readPacients(JSONArray array) throws JSONException {
        List<Pacient> results = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Pacient pacient = readPacient(array.getJSONObject(i));
            results.add(pacient);
        }
        return results;
    }

    private static Pacient readPacient(JSONObject object) throws JSONException {
        String numePacient = object.getString(NUME_PACIENT);
        int varstaPacient = object.getInt(VARSTA_PACIENT);
        String domiciliuPacient = object.getString(DOMICILIU_PACIENT);
        Boolean rezultatTest = object.getBoolean(REZULTAT_TEST);
        return new Pacient(numePacient, varstaPacient, domiciliuPacient, rezultatTest);
    }

}
