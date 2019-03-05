/*
 * Projekt: AutoPDV
 * Firma:   ewz Verteilnetze
 * Autor:   R. Peterhans / M. Hablützel
 * Datum:   08.03.2019
 * Version: 1.0
 *
 * Beschreibung:
 * Dies ist ein Interface zwischen der Business-Logic und dem Data-Layer im Bereich Datenbank.   
 *
 */
package ch.abbts.autopdv.data;

import ch.abbts.autopdv.businesslogic.ObjectContainer;

public interface Data {

    String getUserGroup(String pAdAcount) throws Exception;

    ObjectContainer getDistributionNetworks() throws Exception;

    ObjectContainer getNetworkLevel() throws Exception;

    void savePlantData(int pVnID, int pNeID, int pValue_AKS, String pPlantName, String pPlantShortName) throws Exception;

    ObjectContainer getInspectionLot(String pAdAcount) throws Exception;

    ObjectContainer getTransformerTestData(int pPlID) throws Exception;

    ObjectContainer getTransformerReportData(String pResultFile) throws Exception;

}
