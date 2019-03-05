/*
 * Projekt: AutoPDV
 * Firma:   ewz Verteilnetze
 * Autor:   R. Peterhans / M. Hablützel
 * Datum:   08.03.2019
 * Version: 1.0
 *
 * Beschreibung:
 * Die Klasse DataBase wird für die Datenbankzugriffe verwendet.   
 *
 */
package ch.abbts.autopdv.data;

import ch.abbts.autopdv.businesslogic.DistributionNetwork;
import ch.abbts.autopdv.businesslogic.InspectionLot;
import ch.abbts.autopdv.businesslogic.NetworkLevel;
import ch.abbts.autopdv.businesslogic.ObjectContainer;
import ch.abbts.autopdv.businesslogic.TransformerReportData;
import ch.abbts.autopdv.businesslogic.TransformerTestData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBase implements Data {

    private String mUserGroup;
    private final int mGeloescht = 0;
    private final String PROTOCOL = "jdbc:mysql";
    private final String HOST = "autopdv.internet-box.ch";
    private final int PORT = 3306;
    private final String DATABASE = "Anlagedaten";
    private final String OPTIONS = "useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final String URL = PROTOCOL + "://" + HOST + ":" + PORT + "/" + DATABASE + "?" + OPTIONS;
    private final String USER = "abbts";
    private final String PASSWORD = "abbts";

    private Connection connection;

    
    //**********************************************************************
    // Konstruktor - Verbindungsprüfung
    //**********************************************************************
    public DataBase() throws Exception {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception ex) {
            System.out.println("");
            System.out.println("Es konnte keine Verbindung zur Datenbank hergestellt werden!");
            System.out.println("");
        }
    }

    //**********************************************************************
    // toString
    //**********************************************************************
    @Override
    public String toString() {
        return "URL: " + URL + "\n" + "USER: " + USER + "\n" + "PASSWORD: " + PASSWORD;
    }

    //**********************************************************************
    // Gibt die Benutzergruppe zurück, damit das korrekte Menü angezeigt wird
    //**********************************************************************
    @Override
    public String getUserGroup(String pUser) throws Exception {                            //Beziehung setzen zu User ok?
        Statement statement = connection.createStatement();
        String query = "SELECT BENUTZERGRUPPE.Gruppenname";
        query = query + " FROM BENUTZERGRUPPE INNER JOIN MITARBEITER ON BENUTZERGRUPPE.GrID = MITARBEITER.GrID";
        query = query + " WHERE (((MITARBEITER.Kurzzeichen)=\'" + pUser + "\'));";
        ResultSet data = statement.executeQuery(query);
        data.first();
        mUserGroup = data.getString("Gruppenname");
        return mUserGroup;
    }

    //**********************************************************************
    // Gibt die Verteilnetze zurück um die Anlagedaten einzutragen
    //**********************************************************************
    @Override
    public ObjectContainer getDistributionNetworks() throws Exception {
        ObjectContainer distributionNetworks = new ObjectContainer("DistributionNetwork");
        Statement statement = connection.createStatement();
        String query = "SELECT VERTEILNETZ.VnID, VERTEILNETZ.AKS_Bezeichnung, VERTEILNETZ.Verteilnetzname, VERTEILNETZ.NLZ";
        query = query + " FROM VERTEILNETZ";
        ResultSet data = statement.executeQuery(query);
        while (data.next()) {
            DistributionNetwork network = new DistributionNetwork(
                    data.getInt("VnID"),
                    data.getString("AKS_Bezeichnung"),
                    data.getString("Verteilnetzname"),
                    data.getString("NLZ"));
            distributionNetworks.add(network);
        }
        return distributionNetworks;
    }

    //**********************************************************************
    // Gibt die Netzebenen zurück um die Anlagedaten einzutragen
    //**********************************************************************
    @Override
    public ObjectContainer getNetworkLevel() throws Exception {
        ObjectContainer networkLevel = new ObjectContainer("NetworkLevel");
        Statement statement = connection.createStatement();
        String query = "SELECT NETZEBENE.NeID, NETZEBENE.AKS_Bezeichnung, NETZEBENE.Bezeichnung, NETZEBENE.Bereich";
        query = query + " FROM NETZEBENE";
        ResultSet data = statement.executeQuery(query);
        while (data.next()) {
            NetworkLevel level = new NetworkLevel(
                    data.getInt("NeID"),
                    data.getString("AKS_Bezeichnung"),
                    data.getString("Bezeichnung"),
                    data.getString("Bereich"));
            networkLevel.add(level);
        }
        return networkLevel;
    }

    //**********************************************************************
    // Diese Methode speichert die eingegebenen Anlagedaten in die Datenbank
    //**********************************************************************
    @Override
    public void savePlantData(int pVnID, int pNeID, int pValue_AKS, String pPlantName, String pPlantShortName) throws Exception {
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO ANLAGE ("
                + "VnID, "
                + "NeID, "
                + "AKS_Bezeichnung, "
                + "Anlagename, "
                + "Kurzzeichen, "
                + "Geloescht) "
                + "VALUES ("
                + "\'" + pVnID + "\', "
                + "\'" + pNeID + "\', "
                + "\'" + pValue_AKS + "\', "
                + "\'" + pPlantName + "\', "
                + "\'" + pPlantShortName + "\', "
                + "\'" + mGeloescht + "\')");
    }

    //**********************************************************************
    // Diese Methode gibt die Prüflose zurück, welche für den Anwender
    // angelegt sin (pUser) und Status 3 (Prüfung) haben
    //**********************************************************************
    @Override
    public ObjectContainer getInspectionLot(String pUser) throws Exception {
        ObjectContainer inspectionLot = new ObjectContainer("InspectionLot");
        Statement statement = connection.createStatement();
        String query = "SELECT PRUEFLOS.PlID, PRUEFLOS.Losname, PRUEFLOS.StID, PRUEFLOS.MaID";
        query = query + " FROM (MITARBEITER INNER JOIN PRUEFLOS ON MITARBEITER.MaID = PRUEFLOS.MaID)";
        query = query + " INNER JOIN STATUS ON PRUEFLOS.StID = STATUS.StID";
        query = query + " WHERE (((MITARBEITER.Kurzzeichen)=\'" + pUser + "\') AND ((STATUS.StID)=3))";
        ResultSet data = statement.executeQuery(query);
        while (data.next()) {
            InspectionLot lot = new InspectionLot(
                    data.getInt("PlID"),
                    data.getString("Losname"),
                    data.getInt("StID"),
                    data.getInt("MaID"));
            inspectionLot.add(lot);
        }
        return inspectionLot;
    }

    //**********************************************************************
    // Hier werden die Daten zur Erstellung des Prüf-XMLs aus der Datenbank
    // geholt
    //**********************************************************************
    @Override
    public ObjectContainer getTransformerTestData(int pPlID) throws Exception {
        ObjectContainer transformerTestData = new ObjectContainer("TransformerTestData");
        Statement statement = connection.createStatement();
        String query = "SELECT view_getTestObject.PlID,";
        query = query + " view_getTestObject.AKS_Bezeichnung,";
        query = query + " view_getTestObject.SerienNr,";
        query = query + " view_getTestObject.Kern,";
        query = query + " view_getTestObject.ArtikelNr,";
        query = query + " view_getTestObject.Herstellername,";
        query = query + " view_getTestObject.Funktionsname,";
        query = query + " view_getTestObject.PrimWert,";
        query = query + " view_getTestObject.SekWert,";
        query = query + " view_getTestObject.Einheitszeichen,";
        query = query + " view_getTestObject.Buerde";
        query = query + " FROM view_getTestObject";
        query = query + " WHERE (((view_getTestObject.PlID)=\'" + pPlID + "\'))";
        ResultSet data = statement.executeQuery(query);
        while (data.next()) {
            TransformerTestData testData = new TransformerTestData(
                    data.getInt("PlID"),
                    data.getString("AKS_Bezeichnung"),
                    data.getString("SerienNr"),
                    data.getInt("Kern"),
                    data.getString("ArtikelNr"),
                    data.getString("Herstellername"),
                    data.getString("Funktionsname"),
                    data.getInt("PrimWert"),
                    data.getInt("SekWert"),
                    data.getString("Einheitszeichen"),
                    data.getInt("Buerde"));
            transformerTestData.add(testData);
        }
        return transformerTestData;
    }
    
    //**********************************************************************
    // Hier werden die Daten zur Erstellung des Prüfprotokolls aus der 
    // Datenbank geholt
    //**********************************************************************
    @Override
    public ObjectContainer getTransformerReportData(String pResultFile) throws Exception {
        ObjectContainer transformerReportData = new ObjectContainer("TransformerReportData");
        String AKS_Description = pResultFile.substring(0, pResultFile.length() - 4);
        Statement statement = connection.createStatement();
        String query = "SELECT view_getTestObject.AKS_Bezeichnung,";
        query = query + " view_getTestObject.Equipment,";
        query = query + " view_getTestObject.Einbauort,";
        query = query + " view_getTestObject.SerienNr,";
        query = query + " Max(view_getTestObject.Kern) AS MaxKern,";
        query = query + " view_getTestObject.ArtikelNr,";
        query = query + " view_getTestObject.Herstellername,";
        query = query + " view_getTestObject.Funktionsname";
        query = query + " FROM view_getTestObject";
        query = query + " GROUP BY view_getTestObject.AKS_Bezeichnung,";
        query = query + " view_getTestObject.Equipment,";
        query = query + " view_getTestObject.Einbauort,";
        query = query + " view_getTestObject.SerienNr,";
        query = query + " view_getTestObject.ArtikelNr,";
        query = query + " view_getTestObject.Herstellername,";
        query = query + " view_getTestObject.Funktionsname";
        query = query + " HAVING (((view_getTestObject.AKS_Bezeichnung)=\'" + AKS_Description + "\'))";
        ResultSet data = statement.executeQuery(query);
        while (data.next()) {
            TransformerReportData reportData = new TransformerReportData(
                    data.getString("AKS_Bezeichnung"),
                    data.getString("Equipment"),
                    data.getString("Einbauort"),
                    data.getString("SerienNr"),
                    data.getInt("MaxKern"),
                    data.getString("ArtikelNr"),
                    data.getString("Herstellername"),
                    data.getString("Funktionsname"));
            transformerReportData.add(reportData);
        }
        return transformerReportData;
    }

}
