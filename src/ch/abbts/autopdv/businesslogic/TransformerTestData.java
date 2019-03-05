/*
 * Projekt: AutoPDV
 * Firma:   ewz Verteilnetze
 * Autor:   R. Peterhans / M. Hablützel
 * Datum:   08.03.2019
 * Version: 1.0
 *
 * Beschreibung:
 * Die Klasse TransformerTestData wird für die Generierung der Prüf-XML verwendet.
 *
 */
package ch.abbts.autopdv.businesslogic;

public class TransformerTestData {

    private final int mPlID;                            //Sequenznummer Prüflos
    private final String mAKSDescription;               //AKS Bezeichnung
    private final String mSerialNumber;                 //Serien-Nummer
    private final int mCore;                            //Kern
    private final String mItemNumber;                   //Artikelnummer
    private final String mManufacturerName;             //Herstellername
    private final String mFunctionName;                 //Funktionsname
    private final int mPrimaryValue;                    //Primär Wert
    private final int mSecondaryValue;                  //Sekundär Wert
    private final String mUnit;                         //Einheitszeichen
    private final int mBurden;                          //Bürde

    public TransformerTestData(int pPlID, String pAKSDescription, String pSerialNumber, int pCore, String pItemNumber, String pManufacturerName, String pFunctionName, int pPrimaryValue, int pSecondaryValue, String pUnit, int pBurden) {
        this.mPlID = pPlID;
        this.mAKSDescription = pAKSDescription;
        this.mSerialNumber = pSerialNumber;
        this.mCore = pCore;
        this.mItemNumber = pItemNumber;
        this.mManufacturerName = pManufacturerName;
        this.mFunctionName = pFunctionName;
        this.mPrimaryValue = pPrimaryValue;
        this.mSecondaryValue = pSecondaryValue;
        this.mUnit = pUnit;
        this.mBurden = pBurden;
    }

    public String getAKSDescription() {
        return mAKSDescription;
    }

    public String getSerialNumber() {
        return mSerialNumber;
    }

    public int getCoreNumber() {
        return mCore;
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

    public int getPrimaryValue() {
        return mPrimaryValue;
    }

    public int getSecondaryValue() {
        return mSecondaryValue;
    }

    public String getUnit() {
        return mUnit;
    }

    public int getBurden() {
        return mBurden;
    }

    @Override
    public String toString() {
        return mPlID + " " + mAKSDescription + " " + mSerialNumber + " " + mCore + " " + mItemNumber + " " + mManufacturerName + " " + mFunctionName + " " + mPrimaryValue + " " + mSecondaryValue + " " + mUnit + " " + mBurden;
    }

}
