/*
 * Projekt: AutoPDV
 * Firma:   ewz Verteilnetze
 * Autor:   R. Peterhans / M. Hablützel
 * Datum:   08.03.2019
 * Version: 1.0
 *
 * Beschreibung:
 * Die Klasse User wird verwendet um dem Anwender das entsprechende Menü gemäss seiner Benutzergruppe anzuzeigen.
 *
 */
package ch.abbts.autopdv.businesslogic;

public class User {

    private String mAdAcount;                           //AdAcount

    public User() {
        this("Unbekannt");
    }

    public User(String pAdAcount) {
        mAdAcount = pAdAcount;
    }

    public String getAdAcount() {
        return mAdAcount;
    }

    @Override
    public String toString() {
        return mAdAcount;
    }

}
