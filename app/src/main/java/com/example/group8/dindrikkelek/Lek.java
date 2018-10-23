package com.example.group8.dindrikkelek;

public class Lek {
    private int idLek_PK;
    private String lekNavn;
    private String utfall_FK;
    private String beskrivelse;

   // public Lek() {}
    public Lek (int id, String leknavn, String beskrivelsen) {
        this.idLek_PK = id;
        this.lekNavn = leknavn;
        this.beskrivelse = beskrivelsen;
    }

    public int getIdLek_PK() {
        return idLek_PK;
    }

    public void setIdLek_PK(int idLek_PK) {
        this.idLek_PK = idLek_PK;
    }

    public String getLekNavn() {
        return lekNavn;
    }

    public void setLekNavn(String lekNavn) {
        this.lekNavn = lekNavn;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }


}
