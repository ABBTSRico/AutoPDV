/*
 * Projekt: AutoPDV
 * Firma:   ewz Verteilnetze
 * Autor:   R. Peterhans / M. Hablützel
 * Datum:   08.03.2019
 * Version: 1.0
 *
 * Beschreibung:
 * Dies ist die Main-Klasse mit der gestartet wird.   
 *
 */
package ch.abbts.autopdv;

import ch.abbts.autopdv.businesslogic.BusinessLogic;
import ch.abbts.autopdv.businesslogic.Processor;
import ch.abbts.autopdv.data.DataBase;
import ch.abbts.autopdv.report.TestReport;
import ch.abbts.autopdv.xml.XMLHandler;
import ch.abbts.autopdv.presentation.CLI;
import java.io.IOException;

public class AutoPDV {

    public static void main(String[] args) {
        try {

            DataBase database = new DataBase();
            XMLHandler xml = new XMLHandler();
            TestReport report = new TestReport();

            BusinessLogic processor = new Processor(database, xml, report);

            CLI cli = new CLI(processor);

            cli.work();
            
        } catch (Exception ex) {
            System.out.println("Es ist ein Fehler aufgetreten, bitte Applikation mit Enter beenden X.");
            System.out.println("");
            try {
                System.in.read();
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }
}
