/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.autopdv.presentation;

import ch.abbts.autopdv.businesslogic.BusinessLogic;
import ch.abbts.autopdv.businesslogic.DistributionNetwork;
import ch.abbts.autopdv.businesslogic.InspectionLot;
import ch.abbts.autopdv.businesslogic.NetworkLevel;
import ch.abbts.autopdv.businesslogic.ObjectContainer;
import ch.abbts.autopdv.businesslogic.TestResults;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI {

    private final Scanner mInput;
    private final BusinessLogic mProcessor;            //Beziehung zu processor

    private String mAdAcount;
    private String mUserGroup;
    private String mPlantName;
    private String mPlantShortName;
    private String mResultFile;
    private String mReportFile;
    private int mVnID;
    private int mNeID;
    private int mValue_AKS;
    private int mPlID;

    private boolean mEndApplication;
    private boolean mExit;

    private final int MIN_MENU_DEFAULT = 0;
    private final int MAX_MENU_AD = 4;      //Administrator
    private final int MAX_MENU_CE = 4;      //Construction-Engineer
    private final int MAX_MENU_TE = 2;      //Tester
    private final int MAX_MENU_LM = 1;      //Linemanager
    private final int MAX_MENU_TA = 0;      //TemplateAdministrator

    public CLI(BusinessLogic pProcessor) {                //Konstruktor
        mInput = new Scanner(System.in);
        mProcessor = pProcessor;                    //Beziehung setzen zu processor ??? - Korrekt?
        mExit = false;
    }

    public void work() {

        mEndApplication = false;
        mExit = false;

        try {

            mAdAcount = mProcessor.getAdAcount();
            mUserGroup = mProcessor.getUserGroup();

            while (mEndApplication == false) {
                mEndApplication = showMenu(mUserGroup);
            }
        } catch (Exception ex) {
            System.out.println("Es ist ein Fehler aufgetreten, bitte Aplikation mit Enter beenden");
            System.out.println("");
            ex.printStackTrace();
            waitForEnter();
        } finally {
            closeApplication();
        }
    }

    public boolean showMenu(String pUserGroup) throws Exception {

        int choice;

        switch (pUserGroup) {
            case "Administrator": {
                if (mExit == false) {
                    showMenuAdminstrator();
                    choice = waitForInput(MIN_MENU_DEFAULT, MAX_MENU_AD);
                    switch (choice) {
                        case 1: {
                            pUserGroup = "Anlagebewirtschafter";
                            showMenu(pUserGroup);
                            break;
                        }
                        case 2: {
                            pUserGroup = "Prüfer";
                            showMenu(pUserGroup);
                            break;
                        }
                        case 3: {
                            pUserGroup = "Linienvorgesetzter";
                            showMenu(pUserGroup);
                            break;
                        }
                        case 4: {
                            pUserGroup = "Vorlagenverwalter";
                            showMenu(pUserGroup);
                            break;
                        }
                        case 0: {
                            mExit = true;
                            break;
                        }
                    }
                }
                break;
            }
            case "Anlagebewirtschafter": {
                if (!("Administrator".equals(mUserGroup))) {
                    showMenuConstructionEngineer(pUserGroup);
                    choice = waitForInput(MIN_MENU_DEFAULT, MAX_MENU_CE);
                    switch (choice) {
                        case 1: {
                            boolean next = true;
                            next = showMenuDistributionNetworks();
                            if (next == true) {
                                next = showMenuNetworkLevel();
                            }
                            if (next == true) {
                                showMenuEnterData();
                                mProcessor.savePlantData(mVnID, mNeID, mValue_AKS, mPlantName, mPlantShortName);
                                System.out.println("Die Daten wurden erfolgreich gespeichert.");
                                System.out.println();
                                System.out.println("Bitte Enter drücken um fortzufahren.");
                                waitForEnter();
                            }
                            break;
                        }
                        case 2: {
                            notImplemented();
                            //mTask.changeConstructionData();
                            break;
                        }
                        case 3: {
                            notImplemented();
                            //mTask.showConstructionData();
                            break;
                        }
                        case 4: {
                            notImplemented();
                            //mTask.deleteConstructionData();
                            break;
                        }
                        case 0: {
                            mExit = true;
                            break;
                        }
                    }
                } else {
                    showMenuConstructionEngineer(mUserGroup);               //Als Administrator gestartet
                    choice = waitForInput(MIN_MENU_DEFAULT, MAX_MENU_CE + 1);
                    switch (choice) {
                        case 1: {
                            boolean next = true;
                            next = showMenuDistributionNetworks();
                            if (next == true) {
                                next = showMenuNetworkLevel();
                            }
                            if (next == true) {
                                showMenuEnterData();
                                mProcessor.savePlantData(mVnID, mNeID, mValue_AKS, mPlantName, mPlantShortName);
                                System.out.println("Die Daten wurden erfolgreich gespeichert.");
                                System.out.println();
                                System.out.println("Bitte Enter drücken um fortzufahren.");;
                                waitForEnter();
                            }
                            break;
                        }
                        case 2: {
                            notImplemented();
                            //mTask.changeConstructionData();
                            break;
                        }
                        case 3: {
                            notImplemented();
                            //mTask.showConstructionData();
                            break;
                        }
                        case 4: {
                            notImplemented();
                            //mTask.deleteConstructionData();
                            break;
                        }
                        case 5: {
                            mExit = false;       //Zurück
                            break;
                        }
                        case 0: {
                            mExit = true;       //Beenden
                            break;
                        }
                    }
                }
                break;
            }
            case "Prüfer": {
                if (!("Administrator".equals(mUserGroup))) {
                    showMenuTester(pUserGroup);
                    choice = waitForInput(MIN_MENU_DEFAULT, MAX_MENU_TE);
                    switch (choice) {
                        case 1: {
                            boolean next = true;
                            next = showMenuGenerateXML();
                            if (next == true) {
                                mProcessor.generateXML(mPlID);
                                System.out.println("Alle Prüf-XML wurden erfolgreich erstellt");
                                System.out.println();
                                System.out.println("Bitte Enter drücken um fortzufahren.");
                                waitForEnter();
                            }
                            break;
                        }
                        case 2: {
                            boolean next = true;
                            next = showMenuReadXML();
                            if (next == true) {
                                showMenuGenerateTestreport();
                                waitForEnter();
                            }
                            break;
                        }
                        case 0: {
                            mExit = true;
                            break;
                        }
                    }
                    break;
                } else {
                    showMenuTester(mUserGroup);                             //Als Administrator gestartet
                    choice = waitForInput(MIN_MENU_DEFAULT, MAX_MENU_TE + 1);
                    switch (choice) {
                        case 1: {
                            boolean next = true;
                            next = showMenuGenerateXML();
                            if (next == true) {
                                mProcessor.generateXML(mPlID);
                                System.out.println("Alle Prüf-XML wurden erfolgreich erstellt");
                                System.out.println();
                                System.out.println("Bitte Enter drücken um fortzufahren.");;
                                waitForEnter();
                            }
                            break;
                        }
                        case 2: {
                            boolean next = true;
                            next = showMenuReadXML();
                            if (next == true) {
                                showMenuGenerateTestreport();
                                waitForEnter();
                            }
                            break;
                        }
                        case 3: {
                            mExit = false;
                            break;
                        }
                        case 0: {
                            mExit = true;
                            break;
                        }
                    }
                    break;
                }
            }
            case "Linienvorgesetzter": {
                if (!("Administrator".equals(mUserGroup))) {
                    showMenuLineManager(pUserGroup);
                    choice = waitForInput(MIN_MENU_DEFAULT, MAX_MENU_LM);
                    switch (choice) {
                        case 1: {
                            boolean next = true;
                            next = showMenuReadReport();
                            if (next == true) {
                                showMenuGenerateReport();
                                waitForEnter();
                            }
                            break;
                        }
                        case 0: {
                            mExit = true;
                            break;
                        }
                    }
                    break;
                } else {
                    showMenuLineManager(mUserGroup);            //Als Administrator
                    choice = waitForInput(MIN_MENU_DEFAULT, MAX_MENU_LM + 1);
                    switch (choice) {
                        case 1: {
                            boolean next = true;
                            next = showMenuReadReport();
                            if (next == true) {
                                showMenuGenerateReport();
                                waitForEnter();
                            }
                            break;
                        }
                        case 2: {
                            mExit = false;
                            break;
                        }
                        case 0: {
                            mExit = true;
                            break;
                        }
                    }
                    break;
                }
            }
            case "Vorlagenverwalter": {
                if (!("Administrator".equals(mUserGroup))) {
                    showMenuTemplateAdministrator(pUserGroup);
                    choice = waitForInput(MIN_MENU_DEFAULT, MAX_MENU_TA);
                    switch (choice) {
                        case 0: {
                            mExit = true;
                            break;
                        }
                    }
                    break;
                } else {
                    showMenuTemplateAdministrator(mUserGroup);
                    choice = waitForInput(MIN_MENU_DEFAULT, MAX_MENU_TA + 1);
                    switch (choice) {
                        case 0: {
                            mExit = true;
                            break;
                        }
                        case 1: {
                            mExit = false;
                            break;
                        }
                    }
                    break;
                }
            }
            default: {
                System.out.println("Sie sind keiner vorgesehenen Benutzergruppe zugeordnet");
                waitForEnter();
                mExit = true;
            }
        }
        return mExit;
    }

    private void showMenuAdminstrator() {
        System.out.println("Mit welcher Rolle wollen Sie sich anmelden");
        System.out.println("1. Anlagebewirtschafter");
        System.out.println("2. Prüfer");
        System.out.println("3. Linienvorgesetzter");
        System.out.println("4. Vorlagenverwalter");
        System.out.println("0. Applikation beenden");
    }

    private void showMenuConstructionEngineer(String pUserGroup) {
        System.out.println("Was möchten Sie machen?");
        System.out.println("1. Anlagedaten eingeben");
        System.out.println("2. Anlagedaten ändern");
        System.out.println("3. Anlagedaten anzeigen");
        System.out.println("4. Anlagedaten löschen");
        if ("Administrator".equals(pUserGroup)) {
            System.out.println("5. Zurück");
        }
        System.out.println("0. Applikation beenden");
    }

    private void showMenuTester(String pUserGroup) {
        System.out.println("Was möchten Sie machen?");
        System.out.println("1. XML für Prüfung erstellen");
        System.out.println("2. XML einlesen und Testprotokoll generieren");
        if ("Administrator".equals(pUserGroup)) {
            System.out.println("3. Zurück");
        }
        System.out.println("0. Applikation beenden");
    }

    private void showMenuLineManager(String pUserGroup) {
        System.out.println("Was möchten Sie machen?");
        System.out.println("1. Testprotokoll einlesen und freigeben");
        if ("Administrator".equals(pUserGroup)) {
            System.out.println("2. Zurück");
        }
        System.out.println("0. Applikation beenden");
    }

    private void showMenuTemplateAdministrator(String pUserGroup) {
        System.out.println("Was möchten Sie machen?");
        System.out.println("0. Applikation beenden");
        if ("Administrator".equals(pUserGroup)) {
            System.out.println("1. Zurück");
        }
    }

    private boolean showMenuDistributionNetworks() throws Exception {
        System.out.println("Für welches Verteilnetz möchten Sie Daten anlegen?");
        System.out.println("0. Zurück");
        ObjectContainer distributionNetworks = new ObjectContainer();
        List<DistributionNetwork> networks = new ArrayList<>();
        distributionNetworks = mProcessor.getDistributionNetworks();
        networks = distributionNetworks.getNetworks();

        for (int i = 0; i < networks.size(); ++i) {
            System.out.println(i + 1 + ". " + distributionNetworks.getNetwork(i));
        }
        int choice = waitForInput(MIN_MENU_DEFAULT, networks.size());
        if (choice == 0) {
            return false;
        } else {
            mVnID = distributionNetworks.getNetwork(choice - 1).getVnID();
        }
        return true;
    }

    private boolean showMenuNetworkLevel() throws Exception {
        System.out.println("Für welche Netzebene möchten Sie Daten anlegen?");
        System.out.println("0. Zurück");
        ObjectContainer networkLevel = new ObjectContainer();
        List<NetworkLevel> levels = new ArrayList<>();
        networkLevel = mProcessor.getNetworkLevel();
        levels = networkLevel.getLevels();

        for (int i = 0; i < levels.size(); ++i) {
            System.out.println(i + 1 + ". " + networkLevel.getLevel(i));
        }
        int choice = waitForInput(MIN_MENU_DEFAULT, levels.size());
        if (choice == 0) {
            return false;
        } else {
            mNeID = networkLevel.getLevel(choice - 1).getNeID();
        }
        return true;
    }

    private void showMenuEnterData() {
        System.out.println("Bitte geben Sie einen Wert für AKS_Bezeichnung ein (0-99):");
        mValue_AKS = waitForInput(0, 99);     
        System.out.println("Bitte geben Sie den Anlagennamen ein (45):");
        mPlantName = getString(45);
        System.out.println("");
        System.out.println("Bitte geben Sie ein Kurzzeichen ein (3):"); 
        mPlantShortName = getString(3);
        System.out.println("");
    }

    private boolean showMenuGenerateXML() throws Exception {
        System.out.println("Für welches Ihrer Prüflose soll das XML erstellt werden?");
        System.out.println("0. Zurück");
        ObjectContainer xml = new ObjectContainer();
        List<InspectionLot> inspectionLots = new ArrayList<>();
        xml = mProcessor.getInspectionLot();
        inspectionLots = xml.getLots();

        for (int i = 0; i < inspectionLots.size(); ++i) {
            System.out.println(i + 1 + ". " + xml.getLot(i));
        }
        int choice = waitForInput(MIN_MENU_DEFAULT, inspectionLots.size());
        if (choice == 0) {
            return false;
        } else {
            mPlID = xml.getLot(choice - 1).getPlID();
        }
        return true;
    }

    private boolean showMenuReadXML() throws Exception {

        System.out.println("Welches File wollen Sie einlesen?");
        System.out.println("0. Zurück");

        List<String> resultFiles = new ArrayList<>();
        resultFiles = mProcessor.getXMLResultFiles();

        for (int i = 0; i < resultFiles.size(); i++) {
            System.out.println(i + 1 + ". " + resultFiles.get(i));
        }
        int choice = waitForInput(MIN_MENU_DEFAULT, resultFiles.size());
        if (choice == 0) {
            return false;
        } else {
            mResultFile = resultFiles.get(choice - 1);
        }

        System.out.println();
        System.out.println("File: " + mResultFile);
        System.out.println();

        ObjectContainer testResults = new ObjectContainer();
        List<TestResults> results = new ArrayList<>();
        testResults = mProcessor.readXMLResultFile(mResultFile);
        results = testResults.getResultObjects();

        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).getName());

            if (results.get(i).getName().startsWith("UW")) {
                if (results.get(i).getVPrim() != null) {
                    System.out.println("VPrim:       " + results.get(i).getVPrim());           //UW-1.1
                }
                if (results.get(i).getVPrimPhase() != null) {
                    System.out.println("VPrimPhase:  " + results.get(i).getVPrimPhase());      //UW-1.2
                }
                if (results.get(i).getVSec() != null) {
                    System.out.println("VSec:        " + results.get(i).getVSec());            //UW-1.3
                }
                if (results.get(i).getVSecPhase() != null) {
                    System.out.println("VSecPhase    " + results.get(i).getVSecPhase());       //UW-1.4
                }
                if (results.get(i).getRatio() != null) {
                    System.out.println("Ratio:       " + results.get(i).getRatio());           //UW-1.5 IW-1.5
                }
                if (results.get(i).getDeviation() != null) {
                    System.out.println("Deviation:   " + results.get(i).getDeviation());       //UW-1.6 IW-1.6
                }
                if (results.get(i).getVSec2() != null) {
                    System.out.println("VSec:        " + results.get(i).getVSec2());            //UW-2.1
                }
                if (results.get(i).getVSecPhase2() != null) {
                    System.out.println("VSecPhase    " + results.get(i).getVSecPhase2());       //UW-2.2
                }
                if (results.get(i).getISec() != null) {
                    System.out.println("ISec:        " + results.get(i).getISec());            //UW-2.3 IW-1.3
                }
                if (results.get(i).getISecPhase() != null) {
                    System.out.println("ISecPhase(): " + results.get(i).getISecPhase());       //UW-2.4 IW-1.4
                }
                if (results.get(i).getBurden() != null) {
                    System.out.println("Burden:      " + results.get(i).getBurden());          //UW-2.5 IW-1.7
                }
                if (results.get(i).getCosPhi() != null) {
                    System.out.println("CosPhi:      " + results.get(i).getCosPhi());          //UW-2.6 IW-1.
                }
            } else if (results.get(i).getName().startsWith("IW")) {
                if (results.get(i).getIPrim() != null) {
                    System.out.println("IPrim:       " + results.get(i).getIPrim());           //IW-1.1
                }
                if (results.get(i).getIPrimPhase() != null) {
                    System.out.println("IPrimPhase:  " + results.get(i).getIPrimPhase());      //IW-1.2
                }
                if (results.get(i).getISec() != null) {
                    System.out.println("ISec:        " + results.get(i).getISec());            //UW-2.3 IW-1.3
                }
                if (results.get(i).getISecPhase() != null) {
                    System.out.println("ISecPhase(): " + results.get(i).getISecPhase());       //UW-2.4 IW-1.4
                }
                if (results.get(i).getRatio() != null) {
                    System.out.println("Ratio:       " + results.get(i).getRatio());           //UW-1.5 IW-1.5
                }
                if (results.get(i).getDeviation() != null) {
                    System.out.println("Deviation:   " + results.get(i).getDeviation());       //UW-1.6 IW-1.6
                }
                if (results.get(i).getBurden() != null) {
                    System.out.println("Burden:      " + results.get(i).getBurden());          //UW-2.5 IW-1.7
                }
                if (results.get(i).getCosPhi() != null) {
                    System.out.println("CosPhi:      " + results.get(i).getCosPhi());          //UW-2.6 IW-1.8
                }
                if (results.get(i).getVknee() != null) {
                    System.out.println("Vknee :      " + results.get(i).getVknee());           //IW-2.1
                }
                if (results.get(i).getIknee() != null) {
                    System.out.println("Iknee :      " + results.get(i).getIknee());           //IW-2.2
                }
            }
        }
        return true;
    }

    private void showMenuGenerateTestreport() throws Exception {
        System.out.println();
        System.out.println("Möchten Sie zu diesen Daten das Prüfprotokoll erstellen? (1=Ja,2=Nein)");
        int choice = waitForInput(1, 2);
        switch (choice) {
            case 1: {
                mProcessor.generateTestReport(mResultFile);
                System.out.println("Das Testprotokoll wurde abgelegt");
                System.out.println();
                System.out.println("Bitte Enter drücken um fortzufahren.");
                break;
            }
            default: {
                System.out.println("Es wurde kein Testprotokoll erstellt");
                System.out.println();
                System.out.println("Bitte Enter drücken um fortzufahren.");
            }
        }
    }

    private boolean showMenuReadReport() throws Exception {
        System.out.println("Welches File wollen Sie einlesen?");
        System.out.println("0. Zurück");

        List<String> reportFiles = new ArrayList<>();
        reportFiles = mProcessor.getReportFiles();
        for (int i = 0; i < reportFiles.size(); i++) {
            System.out.println(i + 1 + ". " + reportFiles.get(i));
        }

        int choice = waitForInput(MIN_MENU_DEFAULT, reportFiles.size());
        if (choice == 0) {
            return false;
        } else {
            mReportFile = reportFiles.get(choice - 1);
        }

        System.out.println();
        System.out.println("File: " + mReportFile);
        System.out.println();

        FileReader fr = new FileReader(mProcessor.getReportPath() + mReportFile);
        BufferedReader br = new BufferedReader(fr);

        String zeile;

        while ((zeile = br.readLine()) != null) {
            System.out.println(zeile);
        }

        br.close();
        fr.close();

        return true;
    }

    private void showMenuGenerateReport() throws Exception {
        System.out.println();
        System.out.println("Möchten Sie das Prüfprotokoll freigeben? (1=Ja,2=Nein)");
        int choice = waitForInput(1, 2);
        switch (choice) {
            case 1: {
                mProcessor.approveTestReport(mReportFile);
                System.out.println("Das Testprotokoll wurde freigegeben und abgelegt");
                System.out.println();
                System.out.println("Bitte Enter drücken um fortzufahren.");
                break;
            }
            default: {
                System.out.println("Es wurde kein Testprotokoll freigegeben");
                System.out.println();
                System.out.println("Bitte Enter drücken um fortzufahren.");
            }
        }
    }

    private void closeApplication() {
        System.out.println("Applikation wird beendet");
    }

    private int waitForInput(int pMinMenuPoints, int pMaxMenuPoints) {
        int choice;
        System.out.println();
        System.out.print("Eingabe: ");
        choice = readInteger(pMinMenuPoints, pMaxMenuPoints);
        System.out.println();
        return choice;
    }

    private void waitForEnter() {
        try {
            System.in.read();
        } catch (Exception io) {
            io.printStackTrace();
        }
    }

    //**********************************************************************
    // Prüfung ob Eingegebener Wert Integer im gültigen Bereich ist
    // Basis Übernommen aus dem ExMan Projekt von Herr Bontognali
    //**********************************************************************
    private int readInteger(int pMinimum, int pMaximum) {
        int value = 0;
        while (mInput.hasNext()) {
            if (mInput.hasNextInt()) {
                value = mInput.nextInt();
                if (value >= pMinimum && value <= pMaximum) {
                    break;
                } else {
                    System.out.println("Bitte geben Sie eine Ganzzahl im gültigen Bereich ein");
                    System.out.println();
                    System.out.print("Eingabe: ");
                }
            } else {
                System.out.println("Bitte geben Sie eine Ganzzahl ein");
                System.out.println();
                System.out.print("Eingabe: ");
                mInput.next();
            }
        }
        return value;
    }

    //**********************************************************************
    // Prüfung ob eingegebener String im gültigen Bereich ist
    //**********************************************************************
    String getString(int pMaxLength) {
        System.out.println();
        System.out.print("Eingabe: ");
        String temp = "";
        Scanner input = new Scanner(System.in);
        while (true) {
            temp = input.nextLine();
            if (temp.length() > pMaxLength) {
                System.out.println("Bitte Länge beachten");
                System.out.println();
                System.out.print("Eingabe: ");
            } else {
                break;
            }
        }
        return temp;
    }

    //**********************************************************************
    // Ausgabe für Funktionen welche noch nicht implementiert wurden
    //**********************************************************************
    private void notImplemented() {
        System.out.println("Diese Funktion wurde (noch) nicht implementiert.");
        System.out.println("Bitte mit Enter bestätigen");
        System.out.println("");
        waitForEnter();
    }

}
