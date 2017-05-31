package be.ift.wrapperClasses;
/*wrapper class voor */
public class VraagAntwoordItem {
    public String antwoord;
    public int schaalWaarde;
    public boolean type;
    public int vraagID;
    public String vraagOmschrijving;
    public int stagiairID;


    public VraagAntwoordItem(){
    }

    public VraagAntwoordItem(String antwoord, boolean type, int vraagID, String vraagOmschrijving, int stagiairID){
        this.antwoord = antwoord;
        this.type = type;
        this.vraagID = vraagID;
        this.vraagOmschrijving = vraagOmschrijving;
    }

    public String getAntwoord() {
        return antwoord;
    }

    public void setAntwoord(String antwoord) {
        this.antwoord = antwoord;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public int getVraagID() {
        return vraagID;
    }

    public void setVraagID(int vraagID) {
        this.vraagID = vraagID;
    }

    public String getVraagOmschrijving() {
        return vraagOmschrijving;
    }

    public void setVraagOmschrijving(String vraagOmschrijving) {
        this.vraagOmschrijving = vraagOmschrijving;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getStagiairID() {
        return stagiairID;
    }

    public void setStagiairID(int stagiairID) {
        this.stagiairID = stagiairID;
    }
}