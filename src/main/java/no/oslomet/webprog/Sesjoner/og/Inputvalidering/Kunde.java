package no.oslomet.webprog.Sesjoner.og.Inputvalidering;

public class Kunde {

    private String brukernavn;
    private String passord;
    private String rettighet;

    public Kunde(String brukernavn, String passord, String rettighet) {
        this.brukernavn = brukernavn;
        this.passord = passord;
        this.rettighet = rettighet;

    }

    public Kunde() {
    }

    public String getBrukernavn() {
        return brukernavn;
    }

    public void setBrukernavn(String brukernavn) {
        this.brukernavn = brukernavn;
    }

    public String getPassord() {
        return passord;
    }

    public void setPassord(String passord) {
        this.passord = passord;
    }

    public String getRettighet() {
        return rettighet;
    }

    public void setRettighet(String rettighet) {
        this.rettighet = rettighet;
    }
}
