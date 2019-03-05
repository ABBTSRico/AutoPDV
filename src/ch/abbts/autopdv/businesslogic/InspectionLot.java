/*
 * Projekt: AutoPDV
 * Firma:   ewz Verteilnetze
 * Autor:   R. Peterhans / M. Hablützel
 * Datum:   08.03.2019
 * Version: 1.0
 *
 * Beschreibung:
 * Die Klasse InspectionLot wird für die Prüflose verwendet.  
 *
 */
package ch.abbts.autopdv.businesslogic;

public class InspectionLot {

    private final int mPlID;                            //Primächlüssel
    private final String mLotName;                      //Name von Prüflos
    private final int mStID;                            //Fremdschlüssel Status
    private final int mMaID;                            //Fremdschlüssel Mitarbeiter

    public InspectionLot(int pPlID, String pLotName, int pStID, int pMaID) {
        this.mPlID = pPlID;
        this.mLotName = pLotName;
        this.mStID = pStID;
        this.mMaID = pMaID;
    }

    public String getLotName() {
        return mLotName;
    }

    public int getStatus() {
        return mStID;
    }

    public int getPlID() {
        return mPlID;
    }

    @Override
    public String toString() {
        return "Losname: " + mLotName;
    }

}
