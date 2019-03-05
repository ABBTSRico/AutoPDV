/*
 * Projekt: AutoPDV
 * Firma:   ewz Verteilnetze
 * Autor:   R. Peterhans / M. Hablützel
 * Datum:   08.03.2019
 * Version: 1.0
 *
 * Beschreibung:
 * Die Klasse TransformerReportData wird für die Generierung der Prüfprotokolle verwendet.
 *
 */
package ch.abbts.autopdv.businesslogic;

public class TransformerReportData {

    private final String mAKSDescription;               //AKS Bezeichnung
    private final String mEquipment;                    //Gerätebezeichnung
     private final String mLocation;                    //Einbauort
    private final String mSerialNumber;                 //Serien-Nummer
    private final int mMaxCore;                         //maximaler Kern (Entspricht Anzahl)
    private final String mItemNumber;                   //Artikelnummer
    private final String mManufacturerName;             //Herstellername
    private final String mFunctionName;                 //Funktionsname

    public TransformerReportData(String pAKSDescription, String pEquipment, String pLocation, String pSerialNumber, int pMaxCore, String pItemNumber, String pManufacturerName, String pFunctionName) {
        this.mAKSDescription = pAKSDescription;
        this.mEquipment = pEquipment;
        this.mLocation = pLocation;
        this.mSerialNumber = pSerialNumber;
        this.mMaxCore = pMaxCore;
        this.mItemNumber = pItemNumber;
        this.mManufacturerName = pManufacturerName;
        this.mFunctionName = pFunctionName;
    }

    public String getAKSDescription() {
        return mAKSDescription;
    }

    public String getEquipment(){
        return mEquipment;
    }
    
    public String getLocation(){
        return mLocation;
    }
    
    public String getSerialNumber() {
        return mSerialNumber;
    }

    public int getMaxCoreNumber() {
        return mMaxCore;
    }

    public String getPartNumber() {
        return mItemNumber;
    }

    public String getManufacturerName() {
        return mManufacturerName;
    }

    public String getFunctionName() {
        return mFunctionName;
    }

    @Override
    public String toString() {
        return mAKSDescription + " " + mSerialNumber + " " + mMaxCore + " " + mItemNumber + " " + mManufacturerName + " " + mFunctionName;
    }

}
