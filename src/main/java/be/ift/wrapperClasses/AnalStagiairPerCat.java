package be.ift.wrapperClasses;

/**
 * Created by VXFBD43 on 20/04/2017.
 */
public class AnalStagiairPerCat {

    public String catnaam;
    public int aantalStag;
    public int jaar ;

    public AnalStagiairPerCat(){

    }

    public AnalStagiairPerCat(String catnaam, int aantalStag) {
        this.catnaam = catnaam;
        this.aantalStag = aantalStag;
        this.jaar = 0;
    }

    public AnalStagiairPerCat(String catnaam, int aantalStag, int jaar) {
        this.catnaam = catnaam;
        this.aantalStag = aantalStag;
        this.jaar = jaar;
    }

    public String getCatnaam() {
        return catnaam;
    }

    public void setCatnaam(String catnaam) {
        this.catnaam = catnaam;
    }

    public int getAantalStag() {
        return aantalStag;
    }

    public void setAantalStag(int aantalStag) {
        this.aantalStag = aantalStag;
    }

    public int getJaar() {
        return jaar;
    }

    public void setJaar(int jaar) {
        this.jaar = jaar;
    }
}
