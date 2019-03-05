/*
 * Projekt: AutoPDV
 * Firma:   ewz Verteilnetze
 * Autor:   R. Peterhans / M. Hablützel
 * Datum:   08.03.2019
 * Version: 1.0
 *
 * Beschreibung:
 * Die Klasse NetworkLevel wird für die Netzebenen verwendet.  
 *
 */
package ch.abbts.autopdv.businesslogic;

public class NetworkLevel {

    private final int mNeID;                            //Primärschlüssel
    private final String mAKSDescription;               //AKS Bezeichnung
    private final String mNetName;                      //Netzbezeichnung
    private final String mRange;                        //Spannungsbereich

    public NetworkLevel(int pNeID, String pAKSDescription, String pNetName, String pRange) {
        this.mNeID = pNeID;
        this.mAKSDescription = pAKSDescription;
        this.mNetName = pNetName;
        this.mRange = pRange;
    }

    public String getBezeichnung() {
        return mNetName;
    }

    public String getAKS_Bezeichnung() {
        return mAKSDescription;
    }

    public int getNeID() {
        return mNeID;
    }

    @Override
    public String toString() {
        return mAKSDescription + " " + mNetName + " " + mRange;
    }

}
