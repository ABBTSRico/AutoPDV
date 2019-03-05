/*
 * Projekt: AutoPDV
 * Firma:   ewz Verteilnetze
 * Autor:   R. Peterhans / M. Hablützel
 * Datum:   08.03.2019
 * Version: 1.0
 *
 * Beschreibung:
 * Dies ist das Interface zwischen dem Presentation Layer und der Business-Logic.   
 *
 */
package ch.abbts.autopdv.businesslogic;

import java.util.List;

public interface BusinessLogic {


    String getAdAcount() throws Exception;

    String getUserGroup() throws Exception;

    ObjectContainer getDistributionNetworks() throws Exception;

    ObjectContainer getNetworkLevel() throws Exception;

    void savePlantData(int pVnID, int pNeID, int pValue_AKS, String pPlantName, String pPlantShortName) throws Exception;

    ObjectContainer getInspectionLot() throws Exception;

    void generateXML(int pPlID) throws Exception;

    List<String> getXMLResultFiles() throws Exception;

    ObjectContainer readXMLResultFile(String pResultFile) throws Exception;

    void generateTestReport(String pResultFile) throws Exception;

    List<String> getReportFiles() throws Exception;

    String getReportPath() throws Exception;

    void approveTestReport(String pReportFile) throws Exception;

}
