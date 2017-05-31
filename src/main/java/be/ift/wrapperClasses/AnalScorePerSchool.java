package be.ift.wrapperClasses;


import static org.codehaus.groovy.runtime.DefaultGroovyMethods.round;

/**
 * Created by JHNBD42 on 8/05/2017.
 */
public class AnalScorePerSchool {
    public String schoolNaam;
    public int aantalStagiaires;
    public double gemiddelde;

    public AnalScorePerSchool(String schoolNaam, int aantalStagiaires, double gemiddelde) {
        this.schoolNaam = schoolNaam;
        this.aantalStagiaires = aantalStagiaires;
        this.gemiddelde = round(gemiddelde, 2);
    }

    public String getSchoolNaam() {
        return schoolNaam;
    }

    public void setSchoolNaam(String schoolNaam) {
        this.schoolNaam = schoolNaam;
    }

    public int getAantalStagiaires() {
        return aantalStagiaires;
    }

    public void setAantalStagiaires(int aantalStagiaires) {
        this.aantalStagiaires = aantalStagiaires;
    }

    public double getGemiddelde() {
        return gemiddelde;
    }

    public void setGemiddelde(double gemiddelde) {
        this.gemiddelde = round(gemiddelde, 2);
    }
}
