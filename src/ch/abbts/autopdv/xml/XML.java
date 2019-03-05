/*
 * Projekt: AutoPDV
 * Firma:   ewz Verteilnetze
 * Autor:   R. Peterhans / M. Hablützel
 * Datum:   08.03.2019
 * Version: 1.0
 *
 * Beschreibung:
 * Dies ist ein Interface zwischen der Business-Logic und dem Data-Layer im Bereich XML-Files.   
 *
 */
package ch.abbts.autopdv.xml;

import ch.abbts.autopdv.businesslogic.ObjectContainer;
import java.util.List;

public interface XML {

    void generateXMLFile(ObjectContainer objectcontainer) throws Exception;

    List<String> getXMLResultFiles() throws Exception;

    ObjectContainer readXMLResultFile (String pResultFile) throws Exception;

}
