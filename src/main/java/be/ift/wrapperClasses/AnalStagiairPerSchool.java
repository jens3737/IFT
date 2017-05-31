package be.ift.wrapperClasses;

import java.util.Date;

/**
 * Created by JHNBD42 on 20/04/2017.
 */
public class AnalStagiairPerSchool {
    public String schoolNaam;
    public int aantalStagiaires;
    public int stageJaar;
    public int maxStagiaires;

    public AnalStagiairPerSchool() {

    }

    public AnalStagiairPerSchool(String schoolNaam, int aantalStagiaires, int maxStagiaires) {
        this.schoolNaam = schoolNaam;
        this.aantalStagiaires = aantalStagiaires;
        this.stageJaar = 0;
        this.maxStagiaires = maxStagiaires;
    }

    /*voor Fitler*/
    public AnalStagiairPerSchool(String schoolNaam, int aantalStagiaires, int maxStagiaires, int stageJaar) {
        this.schoolNaam = schoolNaam;
        this.aantalStagiaires = aantalStagiaires;
        this.stageJaar = stageJaar;
        this.maxStagiaires = maxStagiaires;
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

    public int getStageJaar() {
        return stageJaar;
    }

    public void setStageJaar(int stageJaar) {
        this.stageJaar = stageJaar;
    }

    public int getMaxStagiaires() {
        return maxStagiaires;
    }

    public void setMaxStagiaires(int maxStagiaires) {
        this.maxStagiaires = maxStagiaires;
    }
}
