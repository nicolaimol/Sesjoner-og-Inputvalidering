package no.oslomet.webprog.Sesjoner.og.Inputvalidering;

public class Kunde {

    private String brukernavn;
    private String passord;

    public Kunde(String brukernavn, String passord) {
        this.brukernavn = brukernavn;
        this.passord = passord;
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
}
