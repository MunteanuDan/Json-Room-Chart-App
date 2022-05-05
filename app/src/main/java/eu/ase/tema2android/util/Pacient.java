package eu.ase.tema2android.util;

import java.io.Serializable;

public class Pacient implements Serializable {

    private String numePacient;
    private int varstaPacient;
    private String domiciliuPacient;
    private Boolean rezultatTest;

    public Pacient(String numePacient, int varstaPacient, String domiciliuPacient, Boolean rezultatTest) {
        this.numePacient = numePacient;
        this.varstaPacient = varstaPacient;
        this.domiciliuPacient = domiciliuPacient;
        this.rezultatTest = rezultatTest;
    }

    public String getNumePacient() {
        return numePacient;
    }

    public void setNumePacient(String numePacient) {
        this.numePacient = numePacient;
    }

    public int getVarstaPacient() {
        return varstaPacient;
    }

    public void setVarstaPacient(int varstaPacient) {
        this.varstaPacient = varstaPacient;
    }

    public String getDomiciliuPacient() {
        return domiciliuPacient;
    }

    public void setDomiciliuPacient(String domiciliuPacient) {
        this.domiciliuPacient = domiciliuPacient;
    }

    public Boolean getRezultatTest() {
        return rezultatTest;
    }

    public void setRezultatTest(Boolean rezultatTest) {
        this.rezultatTest = rezultatTest;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "numePacient='" + numePacient + '\'' +
                ", varstaPacient=" + varstaPacient +
                ", domiciliuPacient='" + domiciliuPacient + '\'' +
                ", rezultatTest=" + rezultatTest +
                '}';
    }
}
