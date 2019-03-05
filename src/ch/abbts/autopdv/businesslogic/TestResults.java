/*
 * Projekt: AutoPDV
 * Firma:   ewz Verteilnetze
 * Autor:   R. Peterhans / M. Hablützel
 * Datum:   08.03.2019
 * Version: 1.0
 *
 * Beschreibung:
 * Die Klasse TestResults wird für die eingelesenen Prüfwerte verwendet.
 *
 */
package ch.abbts.autopdv.businesslogic;

public class TestResults {

    private String mName;                               //Name
    private String mVPrim;                              //Spannung Primärseite
    private String mVPrimPhase;                         //Phasenwinkel Primärseite
    private String mVSec;                               //Spannung Sekundärseite
    private String mVSecPhase;                          //Phasenwinkel Sekundärseite
    private String mVSec2;                              //Spannung zwei Sekundärseite
    private String mVSecPhase2;                         //Phasenwinkel zwei Sekundärseite
    private String mRatio;                              //Verhältnis
    private String mDeviation;                          //Abweichung
    private String mISec;                               //Strom Sekundärseite
    private String mISecPhase;                          //Phasenwinkel Sekundärseite
    private String mBurden;                             //Bürde
    private String mCosPhi;                             //Leistungsfaktor
    private String mIPrim;                              //Strom Primärseite
    private String mIPrimPhase;                         //Phasenwinkel Primärseite
    private String mVknee;                              //Kniepunkt Spannung
    private String mIknee;                              //Kniepunkt Strom

    public void setName(String pName) {
        this.mName = pName;
    }

    public String getName() {
        return mName;
    }

    public void setVPrim(String pVPrim) {
        this.mVPrim = pVPrim;
    }

    public String getVPrim() {
        return mVPrim;
    }

    public void setVPrimPhase(String pVPrimPhase) {
        this.mVPrimPhase = pVPrimPhase;
    }

    public String getVPrimPhase() {
        return mVPrimPhase;
    }

    public void setVSec(String pVSec) {
        this.mVSec = pVSec;
    }

    public String getVSec() {
        return mVSec;
    }

    public void setVSecPhase(String pVSecPhase) {
        this.mVSecPhase = pVSecPhase;
    }

    public String getVSecPhase() {
        return mVSecPhase;
    }

    public void setVSec2(String pVSec2) {
        this.mVSec2 = pVSec2;
    }

    public String getVSec2() {
        return mVSec2;
    }

    public void setVSecPhase2(String pVSecPhase2) {
        this.mVSecPhase2 = pVSecPhase2;
    }

    public String getVSecPhase2() {
        return mVSecPhase2;
    }

    public void setRatio(String pRatio) {
        this.mRatio = pRatio;
    }

    public String getRatio() {
        return mRatio;
    }

    public void setDeviation(String pDeviation) {
        this.mDeviation = pDeviation;
    }

    public String getDeviation() {
        return mDeviation;
    }

    public void setISec(String pISec) {
        this.mISec = pISec;
    }

    public String getISec() {
        return mISec;
    }

    public void setISecPhase(String pISecPhase) {
        this.mISecPhase = pISecPhase;
    }

    public String getISecPhase() {
        return mISecPhase;
    }

    public void setBurden(String pBurden) {
        this.mBurden = pBurden;
    }

    public String getBurden() {
        return mBurden;
    }

    public void setCosPhi(String pCosPhi) {
        this.mCosPhi = pCosPhi;
    }

    public String getCosPhi() {
        return mCosPhi;
    }

    public void setIPrim(String pIPrim) {
        this.mIPrim = pIPrim;
    }

    public String getIPrim() {
        return mIPrim;
    }

    public void setIPrimPhase(String pIPrimPhase) {
        this.mIPrimPhase = pIPrimPhase;
    }

    public String getIPrimPhase() {
        return mIPrimPhase;
    }

    public void setVknee(String pVknee) {
        this.mVknee = pVknee;
    }

    public String getVknee() {
        return mVknee;
    }

    public void setIknee(String pIknee) {
        this.mIknee = pIknee;
    }

    public String getIknee() {
        return mIknee;
    }

    @Override
    public String toString() {
        return mName;
    }
}
