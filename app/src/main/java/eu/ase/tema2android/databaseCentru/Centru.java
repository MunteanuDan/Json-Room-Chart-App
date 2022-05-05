package eu.ase.tema2android.databaseCentru;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "centrus")
public class Centru implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "denumire_centru")
    private String denumireCentru;
    @ColumnInfo(name = "locatie_centru")
    private String locatieCentru;
    @ColumnInfo(name = "numar_telefon_centru")
    private String numarTelefonCentru;
    @ColumnInfo(name = "capacitate_centru")
    private int capacitateCentru;

    public Centru(long id, String denumireCentru, String locatieCentru, String numarTelefonCentru, int capacitateCentru) {
        this.id = id;
        this.denumireCentru = denumireCentru;
        this.locatieCentru = locatieCentru;
        this.numarTelefonCentru = numarTelefonCentru;
        this.capacitateCentru = capacitateCentru;
    }

    @Ignore
    public Centru(String denumireCentru, String locatieCentru, String numarTelefonCentru, int capacitateCentru) {
        this.denumireCentru = denumireCentru;
        this.locatieCentru = locatieCentru;
        this.numarTelefonCentru = numarTelefonCentru;
        this.capacitateCentru = capacitateCentru;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDenumireCentru() {
        return denumireCentru;
    }

    public void setDenumireCentru(String denumireCentru) {
        this.denumireCentru = denumireCentru;
    }

    public String getLocatieCentru() {
        return locatieCentru;
    }

    public void setLocatieCentru(String locatieCentru) {
        this.locatieCentru = locatieCentru;
    }

    public String getNumarTelefonCentru() {
        return numarTelefonCentru;
    }

    public void setNumarTelefonCentru(String numarTelefonCentru) {
        this.numarTelefonCentru = numarTelefonCentru;
    }

    public int getCapacitateCentru() {
        return capacitateCentru;
    }

    public void setCapacitateCentru(int capacitateCentru) {
        this.capacitateCentru = capacitateCentru;
    }

    @Override
    public String toString() {
        return "Centru{" +
                "id=" + id +
                ", denumireCentru='" + denumireCentru + '\'' +
                ", locatieCentru='" + locatieCentru + '\'' +
                ", numarTelefonCentru='" + numarTelefonCentru + '\'' +
                ", capacitateCentru=" + capacitateCentru +
                '}';
    }
}
