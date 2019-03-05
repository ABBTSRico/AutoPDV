/*
 * Projekt: AutoPDV
 * Firma:   ewz Verteilnetze
 * Autor:   R. Peterhans / M. Hablützel
 * Datum:   08.03.2019
 * Version: 1.0
 *
 * Beschreibung:
 * Die Klasse DistributionNetwork wird für die Verteilnetze verwendet.  
 *
 */
package ch.abbts.autopdv.businesslogic;

public class DistributionNetwork {

    private final int mVnID;                            //Primärschlüssel
    private final String mAKSDescription;               //AKS Bezeichnung
    private final String mNetworkName;                  //Verteilnetzname
    private final String mNLZ;                          //Telefonnummer

    public DistributionNetwork(int pVnID, String pAKSDescription, String pNetworkName, String pNLZ) {
        this.mVnID = pVnID;
        this.mAKSDescription = pAKSDescription;
        this.mNetworkName = pNetworkName;
        this.mNLZ = pNLZ;
    }

    public String getBezeichnung() {
        return mAKSDescription;
    }

    public String getName() {
        return mNetworkName;
    }

    public int getVnID() {
        return mVnID;
    }

    @Override
    public String toString() {
        return mNetworkName + ", " + mAKSDescription + ", " + mNLZ;
    }

}
