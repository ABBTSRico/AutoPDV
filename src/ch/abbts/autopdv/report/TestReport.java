/*
 * Projekt: AutoPDV
 * Firma:   ewz Verteilnetze
 * Autor:   R. Peterhans / M. Hablützel
 * Datum:   08.03.2019
 * Version: 1.0
 *
 * Beschreibung:
 * Die Klasse TestReport wird für die Verarbeitung von Text-Files verwendet.   
 *
 */
package ch.abbts.autopdv.report;

import ch.abbts.autopdv.businesslogic.ObjectContainer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TestReport implements Report {

    private int mCoreCount;
    private String mResultFile;
    private String mSourceFile;
    private String mDateTime;

    private final static String PATH = "C:\\ProgramData\\AutoPDV\\";
    private final static String TEMPLATE_FOLDER = "00_Templates\\";

    private final static String TESTRESULT_FOLDER = "02_TestResults\\";
    private final static String TESTREPORT_FOLDER = "03_TestReports\\";
    private final static String APPROVED_REPORT_FOLDER = "04_Approved_TestReports\\";
    private final static String ARCHIVE_FOLDER = "Archive\\";

    private final static String VT_TEMPLATE = "Template_UW_TestReport.txt";
    private final static String CT_TEMPLATE = "Template_IW_TestReport.txt";

    //**********************************************************************
    // Konstruktor
    //**********************************************************************
    public TestReport(){
        mCoreCount = 0;
        mResultFile = "";

    }

    //**********************************************************************
    // In dieser Methode werden die Testreports erstellt
    //**********************************************************************    
    @Override
    public void generateTestReport(ObjectContainer pTestResults, ObjectContainer pReportData, String pAdAcount) throws Exception {

        mDateTime = new SimpleDateFormat("MM/dd/YYYY HH:mm:ss").format(Calendar.getInstance().getTime());
        mResultFile = pReportData.getReportObject(0).getAKSDescription() + ".txt";
        mSourceFile = pReportData.getReportObject(0).getAKSDescription() + ".xml";
        mCoreCount = pTestResults.getResultObjects().size();

        PrintWriter pw = null;
        if ("UWÜbers 1".equals(pTestResults.getResultObject(0).getName())) {
            FileReader fr = new FileReader(PATH + TEMPLATE_FOLDER + VT_TEMPLATE);
            BufferedReader br = new BufferedReader(fr);
            pw = new PrintWriter(PATH + TESTREPORT_FOLDER + mResultFile);

            String zeile;

            while ((zeile = br.readLine()) != null) {

                //Dokumentenkontrolle
                zeile = zeile.replace("#0_GenDateTime", mDateTime);
                zeile = zeile.replace("#0_Tester", pAdAcount);
                zeile = zeile.replace("#0_Location", pReportData.getReportObject(0).getLocation());
                zeile = zeile.replace("#0_EqNr", pReportData.getReportObject(0).getEquipment());

                //1-Funktionswahl
                zeile = zeile.replace("#1_CoreCount", Integer.toString(pReportData.getReportObject(0).getMaxCoreNumber()));

                //2-Stammdaten 
                zeile = zeile.replace("#2_Manufacturer", pReportData.getReportObject(0).getManufacturerName());
                zeile = zeile.replace("#2_PartNr", pReportData.getReportObject(0).getPartNumber());
                zeile = zeile.replace("#2_SerialNr", pReportData.getReportObject(0).getSerialNumber());
                zeile = zeile.replace("#2_EqNr", pReportData.getReportObject(0).getEquipment());
                zeile = zeile.replace("#2_Description", pReportData.getReportObject(0).getAKSDescription());

                //3-Prüfung
                //CoreA(zeile, objectContainer);
                //Kern A
                //UW_Übers 1
                zeile = zeile.replace("#3A_01", pTestResults.getResultObject(0).getVPrim());
                zeile = zeile.replace("#3A_02", pTestResults.getResultObject(0).getVPrimPhase());
                zeile = zeile.replace("#3A_03", pTestResults.getResultObject(0).getVSec());
                zeile = zeile.replace("#3A_04", pTestResults.getResultObject(0).getVSecPhase());
                zeile = zeile.replace("#3A_05", pTestResults.getResultObject(0).getRatio());
                zeile = zeile.replace("#3A_06", pTestResults.getResultObject(0).getDeviation());
                //UW_Bürde
                zeile = zeile.replace("#3A_07", pTestResults.getResultObject(1).getVSec2());
                zeile = zeile.replace("#3A_08", pTestResults.getResultObject(1).getVSecPhase2());
                zeile = zeile.replace("#3A_09", pTestResults.getResultObject(1).getISec());
                zeile = zeile.replace("#3A_10", pTestResults.getResultObject(1).getISecPhase());
                zeile = zeile.replace("#3A_11", pTestResults.getResultObject(1).getBurden());
                zeile = zeile.replace("#3A_12", pTestResults.getResultObject(1).getCosPhi());

                if (mCoreCount >= 4) {
                    //Kern B
                    //UW_Übers 1
                    zeile = zeile.replace("#3B_01", pTestResults.getResultObject(2).getVPrim());
                    zeile = zeile.replace("#3B_02", pTestResults.getResultObject(2).getVPrimPhase());
                    zeile = zeile.replace("#3B_03", pTestResults.getResultObject(2).getVSec());
                    zeile = zeile.replace("#3B_04", pTestResults.getResultObject(2).getVSecPhase());
                    zeile = zeile.replace("#3B_05", pTestResults.getResultObject(2).getRatio());
                    zeile = zeile.replace("#3B_06", pTestResults.getResultObject(2).getDeviation());
                    //UW_Bürde
                    zeile = zeile.replace("#3B_07", pTestResults.getResultObject(3).getVSec2());
                    zeile = zeile.replace("#3B_08", pTestResults.getResultObject(3).getVSecPhase2());
                    zeile = zeile.replace("#3B_09", pTestResults.getResultObject(3).getISec());
                    zeile = zeile.replace("#3B_10", pTestResults.getResultObject(3).getISecPhase());
                    zeile = zeile.replace("#3B_11", pTestResults.getResultObject(3).getBurden());
                    zeile = zeile.replace("#3B_12", pTestResults.getResultObject(3).getCosPhi());
                } else {
                    //Kern B
                    //UW_Übers 1
                    zeile = zeile.replace("#3B_01", "-");
                    zeile = zeile.replace("#3B_02", "-");
                    zeile = zeile.replace("#3B_03", "-");
                    zeile = zeile.replace("#3B_04", "-");
                    zeile = zeile.replace("#3B_05", "-");
                    zeile = zeile.replace("#3B_06", "-");
                    //UW_Bürde
                    zeile = zeile.replace("#3B_07", "-");
                    zeile = zeile.replace("#3B_08", "-");
                    zeile = zeile.replace("#3B_09", "-");
                    zeile = zeile.replace("#3B_10", "-");
                    zeile = zeile.replace("#3B_11", "-");
                    zeile = zeile.replace("#3B_12", "-");
                }

                if (mCoreCount >= 6) {
                    //Kern C
                    //UW_Übers 1
                    zeile = zeile.replace("#3C_01", pTestResults.getResultObject(4).getVPrim());
                    zeile = zeile.replace("#3C_02", pTestResults.getResultObject(4).getVPrimPhase());
                    zeile = zeile.replace("#3C_03", pTestResults.getResultObject(4).getVSec());
                    zeile = zeile.replace("#3C_04", pTestResults.getResultObject(4).getVSecPhase());
                    zeile = zeile.replace("#3C_05", pTestResults.getResultObject(4).getRatio());
                    zeile = zeile.replace("#3C_06", pTestResults.getResultObject(4).getDeviation());
                    //UW_Bürde
                    zeile = zeile.replace("#3C_07", pTestResults.getResultObject(5).getVSec2());
                    zeile = zeile.replace("#3C_08", pTestResults.getResultObject(5).getVSecPhase2());
                    zeile = zeile.replace("#3C_09", pTestResults.getResultObject(5).getISec());
                    zeile = zeile.replace("#3C_10", pTestResults.getResultObject(5).getISecPhase());
                    zeile = zeile.replace("#3C_11", pTestResults.getResultObject(5).getBurden());
                    zeile = zeile.replace("#3C_12", pTestResults.getResultObject(5).getCosPhi());
                } else {
                    //Kern C
                    //UW_Übers 1
                    zeile = zeile.replace("#3C_01", "-");
                    zeile = zeile.replace("#3C_02", "-");
                    zeile = zeile.replace("#3C_03", "-");
                    zeile = zeile.replace("#3C_04", "-");
                    zeile = zeile.replace("#3C_05", "-");
                    zeile = zeile.replace("#3C_06", "-");
                    //UW_Bürde
                    zeile = zeile.replace("#3C_07", "-");
                    zeile = zeile.replace("#3C_08", "-");
                    zeile = zeile.replace("#3C_09", "-");
                    zeile = zeile.replace("#3C_10", "-");
                    zeile = zeile.replace("#3C_11", "-");
                    zeile = zeile.replace("#3C_12", "-");
                }

                if (mCoreCount >= 8) {
                    //Kern D
                    //UW_Übers 1
                    zeile = zeile.replace("#3D_01", pTestResults.getResultObject(6).getVPrim());
                    zeile = zeile.replace("#3D_02", pTestResults.getResultObject(6).getVPrimPhase());
                    zeile = zeile.replace("#3D_03", pTestResults.getResultObject(6).getVSec());
                    zeile = zeile.replace("#3D_04", pTestResults.getResultObject(6).getVSecPhase());
                    zeile = zeile.replace("#3D_05", pTestResults.getResultObject(6).getRatio());
                    zeile = zeile.replace("#3D_06", pTestResults.getResultObject(6).getDeviation());
                    //UW_Bürde
                    zeile = zeile.replace("#3D_07", pTestResults.getResultObject(7).getVSec2());
                    zeile = zeile.replace("#3D_08", pTestResults.getResultObject(7).getVSecPhase2());
                    zeile = zeile.replace("#3D_09", pTestResults.getResultObject(7).getISec());
                    zeile = zeile.replace("#3D_10", pTestResults.getResultObject(7).getISecPhase());
                    zeile = zeile.replace("#3D_11", pTestResults.getResultObject(7).getBurden());
                    zeile = zeile.replace("#3D_12", pTestResults.getResultObject(7).getCosPhi());
                } else {
                    //Kern D
                    //UW_Übers 1
                    zeile = zeile.replace("#3D_01", "-");
                    zeile = zeile.replace("#3D_02", "-");
                    zeile = zeile.replace("#3D_03", "-");
                    zeile = zeile.replace("#3D_04", "-");
                    zeile = zeile.replace("#3D_05", "-");
                    zeile = zeile.replace("#3D_06", "-");
                    //UW_Bürde
                    zeile = zeile.replace("#3D_07", "-");
                    zeile = zeile.replace("#3D_08", "-");
                    zeile = zeile.replace("#3D_09", "-");
                    zeile = zeile.replace("#3D_10", "-");
                    zeile = zeile.replace("#3D_11", "-");
                    zeile = zeile.replace("#3D_12", "-");
                }
                pw.println(zeile);
            }
            pw.close();
            br.close();
            fr.close();
            pw.flush();
            pw.close();

            moveSourceFile(Paths.get(PATH + TESTRESULT_FOLDER + mSourceFile), Paths.get(PATH + TESTRESULT_FOLDER + ARCHIVE_FOLDER + mSourceFile));

        } else if ("IWÜbers 1".equals(pTestResults.getResultObject(0).getName())) {
            FileReader fr = new FileReader(PATH + TEMPLATE_FOLDER + CT_TEMPLATE);
            BufferedReader br = new BufferedReader(fr);
            pw = new PrintWriter(PATH + TESTREPORT_FOLDER + mResultFile);

            String zeile;

            while ((zeile = br.readLine()) != null) {

                //Dokumentenkontrolle
                zeile = zeile.replace("#0_GenDateTime", mDateTime);
                zeile = zeile.replace("#0_Tester", pAdAcount);
                zeile = zeile.replace("#0_Location", pReportData.getReportObject(0).getLocation());
                zeile = zeile.replace("#0_EqNr", pReportData.getReportObject(0).getEquipment());

                //1-Funktionswahl
                zeile = zeile.replace("#1_CoreCount", Integer.toString(pReportData.getReportObject(0).getMaxCoreNumber()));

                //2-Stammdaten 
                zeile = zeile.replace("#2_Manufacturer", pReportData.getReportObject(0).getManufacturerName());
                zeile = zeile.replace("#2_PartNr", pReportData.getReportObject(0).getPartNumber());
                zeile = zeile.replace("#2_SerialNr", pReportData.getReportObject(0).getSerialNumber());
                zeile = zeile.replace("#2_EqNr", pReportData.getReportObject(0).getEquipment());
                zeile = zeile.replace("#2_Description", pReportData.getReportObject(0).getAKSDescription());

                //CoreA(zeile, objectContainer);
                //Kern A
                //IW_Übers 1
                zeile = zeile.replace("#3A_01", pTestResults.getResultObject(0).getIPrim());
                zeile = zeile.replace("#3A_02", pTestResults.getResultObject(0).getIPrimPhase());
                zeile = zeile.replace("#3A_03", pTestResults.getResultObject(0).getISec());
                zeile = zeile.replace("#3A_04", pTestResults.getResultObject(0).getISecPhase());
                zeile = zeile.replace("#3A_05", pTestResults.getResultObject(0).getVSec());
                zeile = zeile.replace("#3A_06", pTestResults.getResultObject(0).getVSecPhase());
                zeile = zeile.replace("#3A_07", pTestResults.getResultObject(0).getRatio());
                zeile = zeile.replace("#3A_08", pTestResults.getResultObject(0).getDeviation());
                zeile = zeile.replace("#3A_09", pTestResults.getResultObject(0).getBurden());
                zeile = zeile.replace("#3A_10", pTestResults.getResultObject(0).getCosPhi());
                //IWMag 1
                zeile = zeile.replace("#3A_11", pTestResults.getResultObject(1).getVknee());
                zeile = zeile.replace("#3A_12", pTestResults.getResultObject(1).getIknee());
                if (mCoreCount >= 4) {
                    //Kern B
                    //IW_Übers 2
                    zeile = zeile.replace("#3B_01", pTestResults.getResultObject(2).getIPrim());
                    zeile = zeile.replace("#3B_02", pTestResults.getResultObject(2).getIPrimPhase());
                    zeile = zeile.replace("#3B_03", pTestResults.getResultObject(2).getISec());
                    zeile = zeile.replace("#3B_04", pTestResults.getResultObject(2).getISecPhase());
                    zeile = zeile.replace("#3B_05", pTestResults.getResultObject(2).getVSec());
                    zeile = zeile.replace("#3B_06", pTestResults.getResultObject(2).getVSecPhase());
                    zeile = zeile.replace("#3B_07", pTestResults.getResultObject(2).getRatio());
                    zeile = zeile.replace("#3B_08", pTestResults.getResultObject(2).getDeviation());
                    zeile = zeile.replace("#3B_09", pTestResults.getResultObject(2).getBurden());
                    zeile = zeile.replace("#3B_10", pTestResults.getResultObject(2).getCosPhi());
                    //IWMag 2
                    zeile = zeile.replace("#3B_11", pTestResults.getResultObject(3).getVknee());
                    zeile = zeile.replace("#3B_12", pTestResults.getResultObject(3).getIknee());
                } else {
                    //Kern B
                    //IW_Übers 2
                    zeile = zeile.replace("#3B_01", "-");
                    zeile = zeile.replace("#3B_02", "-");
                    zeile = zeile.replace("#3B_03", "-");
                    zeile = zeile.replace("#3B_04", "-");
                    zeile = zeile.replace("#3B_05", "-");
                    zeile = zeile.replace("#3B_06", "-");
                    zeile = zeile.replace("#3B_07", "-");
                    zeile = zeile.replace("#3B_08", "-");
                    zeile = zeile.replace("#3B_09", "-");
                    zeile = zeile.replace("#3B_10", "-");
                    //IWMag 2
                    zeile = zeile.replace("#3B_11", "-");
                    zeile = zeile.replace("#3B_12", "-");
                }
                if (mCoreCount >= 6) {
                    //Kern C
                    //IW_Übers 3
                    zeile = zeile.replace("#3C_01", pTestResults.getResultObject(4).getIPrim());
                    zeile = zeile.replace("#3C_02", pTestResults.getResultObject(4).getIPrimPhase());
                    zeile = zeile.replace("#3C_03", pTestResults.getResultObject(4).getISec());
                    zeile = zeile.replace("#3C_04", pTestResults.getResultObject(4).getISecPhase());
                    zeile = zeile.replace("#3C_05", pTestResults.getResultObject(4).getVSec());
                    zeile = zeile.replace("#3C_06", pTestResults.getResultObject(4).getVSecPhase());
                    zeile = zeile.replace("#3C_07", pTestResults.getResultObject(4).getRatio());
                    zeile = zeile.replace("#3C_08", pTestResults.getResultObject(4).getDeviation());
                    zeile = zeile.replace("#3C_09", pTestResults.getResultObject(4).getBurden());
                    zeile = zeile.replace("#3C_10", pTestResults.getResultObject(4).getCosPhi());
                    //IWMag 3
                    zeile = zeile.replace("#3C_11", pTestResults.getResultObject(5).getVknee());
                    zeile = zeile.replace("#3C_12", pTestResults.getResultObject(5).getIknee());
                } else {
                    //Kern C
                    //IW_Übers 3
                    zeile = zeile.replace("#3C_01", "-");
                    zeile = zeile.replace("#3C_02", "-");
                    zeile = zeile.replace("#3C_03", "-");
                    zeile = zeile.replace("#3C_04", "-");
                    zeile = zeile.replace("#3C_05", "-");
                    zeile = zeile.replace("#3C_06", "-");
                    zeile = zeile.replace("#3C_07", "-");
                    zeile = zeile.replace("#3C_08", "-");
                    zeile = zeile.replace("#3C_09", "-");
                    zeile = zeile.replace("#3C_10", "-");
                    //IWMag 3
                    zeile = zeile.replace("#3C_11", "-");
                    zeile = zeile.replace("#3C_12", "-");
                }
                if (mCoreCount >= 8) {
                    //Kern D
                    //IW_Übers 4
                    zeile = zeile.replace("#3D_01", pTestResults.getResultObject(6).getIPrim());
                    zeile = zeile.replace("#3D_02", pTestResults.getResultObject(6).getIPrimPhase());
                    zeile = zeile.replace("#3D_03", pTestResults.getResultObject(6).getISec());
                    zeile = zeile.replace("#3D_04", pTestResults.getResultObject(6).getISecPhase());
                    zeile = zeile.replace("#3D_05", pTestResults.getResultObject(6).getVSec());
                    zeile = zeile.replace("#3D_06", pTestResults.getResultObject(6).getVSecPhase());
                    zeile = zeile.replace("#3D_07", pTestResults.getResultObject(6).getRatio());
                    zeile = zeile.replace("#3D_08", pTestResults.getResultObject(6).getDeviation());
                    zeile = zeile.replace("#3D_09", pTestResults.getResultObject(6).getBurden());
                    zeile = zeile.replace("#3D_10", pTestResults.getResultObject(6).getCosPhi());
                    //IWMag 4
                    zeile = zeile.replace("#3D_11", pTestResults.getResultObject(7).getVknee());
                    zeile = zeile.replace("#3D_12", pTestResults.getResultObject(7).getIknee());
                } else {
                    //Kern D
                    //IW_Übers 4
                    zeile = zeile.replace("#3D_01", "-");
                    zeile = zeile.replace("#3D_02", "-");
                    zeile = zeile.replace("#3D_03", "-");
                    zeile = zeile.replace("#3D_04", "-");
                    zeile = zeile.replace("#3D_05", "-");
                    zeile = zeile.replace("#3D_06", "-");
                    zeile = zeile.replace("#3D_07", "-");
                    zeile = zeile.replace("#3D_08", "-");
                    zeile = zeile.replace("#3D_09", "-");
                    zeile = zeile.replace("#3D_10", "-");
                    //IWMag 4
                    zeile = zeile.replace("#3D_11", "-");
                    zeile = zeile.replace("#3D_12", "-");
                }
                pw.println(zeile);
            }
            pw.close();
            br.close();
            fr.close();
            pw.flush();
            pw.close();

            moveSourceFile(Paths.get(PATH + TESTRESULT_FOLDER + mSourceFile), Paths.get(PATH + TESTRESULT_FOLDER + ARCHIVE_FOLDER + mSourceFile));
        }
    }

    //**********************************************************************
    // Mit dieser Methode werden die ursprünglichen Files ins Archiv
    // verschoben
    //**********************************************************************
    private void moveSourceFile(Path pSource, Path pDestination) throws Exception {
        Files.move(pSource, pDestination);
    }

    //**********************************************************************
    // Mit dieser Methoden werden die Filenamen für den Linienvorgesetzten
    // eingelesen
    //**********************************************************************
    @Override
    public List<String> getReportFiles() throws Exception {
        List<String> reportFiles = new ArrayList<>();
        File directory = new File(PATH + TESTREPORT_FOLDER);

        for (File file : directory.listFiles()) {
            if (file.getName().toLowerCase().endsWith(".txt")) {
                reportFiles.add(file.getName());
            }
        }
        return reportFiles;
    }

    //**********************************************************************
    // Pfadübergabe um Inhalt Testreport in CLI darzustellen
    //**********************************************************************    
    @Override
    public String getReportPath() throws Exception {
        return PATH + TESTREPORT_FOLDER;
    }

    //**********************************************************************
    // Mit dieser Methode wird das Testprotokoll freigegeben
    //**********************************************************************
    @Override
    public void approveTestReport(String pReportFile, String pAdAcount) throws Exception {
        mDateTime = new SimpleDateFormat("MM/dd/YYYY HH:mm:ss").format(Calendar.getInstance().getTime());
        PrintWriter pw = null;
        String zeile;
        FileReader fr = new FileReader(PATH + TESTREPORT_FOLDER + pReportFile);
        BufferedReader br = new BufferedReader(fr);
        pw = new PrintWriter(PATH + APPROVED_REPORT_FOLDER + pReportFile);

        while ((zeile = br.readLine()) != null) {
            //Dokumentenkontrolle
            zeile = zeile.replace("#0_ApprDateTime", mDateTime);
            zeile = zeile.replace("#0_Manager", pAdAcount);
            pw.println(zeile);
        }

        pw.close();
        br.close();
        fr.close();
        pw.flush();
        pw.close();

        moveSourceFile(Paths.get(PATH + TESTREPORT_FOLDER + pReportFile), Paths.get(PATH + TESTREPORT_FOLDER + ARCHIVE_FOLDER + pReportFile));
    }
}
