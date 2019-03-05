/*
 * Projekt: AutoPDV
 * Firma:   ewz Verteilnetze
 * Autor:   R. Peterhans / M. Hablützel
 * Datum:   08.03.2019
 * Version: 1.0
 *
 * Beschreibung:
 * Dies ist ein Interface zwischen der Business-Logic und dem Data-Layer im Bereich Text-Files.   
 *
 */
package ch.abbts.autopdv.report;

import ch.abbts.autopdv.businesslogic.ObjectContainer;
import java.util.List;

public interface Report {

    void generateTestReport(ObjectContainer pTestResults, ObjectContainer pReportData, String pAdAcount) throws Exception;

    List<String> getReportFiles() throws Exception;

    String getReportPath() throws Exception;

    void approveTestReport(String pReportFile, String pAdAcount) throws Exception;

}
