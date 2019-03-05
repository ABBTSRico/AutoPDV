/*
 * Projekt: AutoPDV
 * Firma:   ewz Verteilnetze
 * Autor:   R. Peterhans / M. Hablützel
 * Datum:   08.03.2019
 * Version: 1.0
 *
 * Beschreibung:
 * Die Klasse Processor übernimmt die Verwaltung in der Logik-Schicht.  
 *
 */
package ch.abbts.autopdv.businesslogic;

import ch.abbts.autopdv.data.Data;
import ch.abbts.autopdv.data.DataBase;
import ch.abbts.autopdv.report.Report;
import ch.abbts.autopdv.report.TestReport;
import ch.abbts.autopdv.xml.XML;
import ch.abbts.autopdv.xml.XMLHandler;
import java.util.*;
import java.util.stream.Collectors;

public class Processor implements BusinessLogic {

    private final Data mDatabase;
    private final XML mXMLHandler;
    private final Report mTestReport;
    private User mUser;
    private List<String> mResultFiles;
    private List<String> mReportFiles;
    private String mAdAcount;
    private String mUserGroup;
    private String mReportPath;

    private ObjectContainer mTestResults;

    public Processor(DataBase pDatabase, XMLHandler pXMLHandler, TestReport pTestReport) {
        mDatabase = pDatabase;
        mXMLHandler = pXMLHandler;
        mTestReport = pTestReport;
    }

    @Override
    public String getAdAcount() throws Exception{
        com.sun.security.auth.module.NTSystem NTSystem = new com.sun.security.auth.module.NTSystem();
        mAdAcount = NTSystem.getName();
        return mAdAcount;
    }

    @Override
    public String getUserGroup() throws Exception{
        mUser = new User(mAdAcount);
        mUserGroup = mDatabase.getUserGroup(mUser.getAdAcount());
        return mUserGroup;
    }

    @Override
    public ObjectContainer getDistributionNetworks() throws Exception{
        ObjectContainer distributionNetworks = new ObjectContainer();
        distributionNetworks = mDatabase.getDistributionNetworks();
        return distributionNetworks;
    }

    @Override
    public ObjectContainer getNetworkLevel() throws Exception{
        ObjectContainer networkLevel = new ObjectContainer();
        networkLevel = mDatabase.getNetworkLevel();
        return networkLevel;
    }

    @Override
    public void savePlantData(int pVnID, int pNeID, int pValue_AKS, String pPlantName, String pPlantShortName) throws Exception {
        mDatabase.savePlantData(pVnID, pNeID, pValue_AKS, pPlantName, pPlantShortName);
    }
    

    @Override
    public ObjectContainer getInspectionLot() throws Exception{
        ObjectContainer inspectionLot = new ObjectContainer();
        inspectionLot = mDatabase.getInspectionLot(mAdAcount);
        return inspectionLot;
    }

    @Override
    public void generateXML(int pPlID) throws Exception{
        ObjectContainer xml = new ObjectContainer();
        List<TransformerTestData> testData = new ArrayList<>();
        xml = mDatabase.getTransformerTestData(pPlID);
        testData = xml.getTestObjects();

        //Liste mit einzelenen AKS_Bezeichnungen ( Distinct) erstellen:
        List<String> distinctAKS = new ArrayList<>();
        for (TransformerTestData transformerTestData : xml.getTestObjects()) {
            distinctAKS.add(transformerTestData.getAKSDescription());
        }

        List<String> distinctElements = distinctAKS.stream().distinct().collect(Collectors.toList());
        for (String aksDescription : distinctElements) {
            ObjectContainer objectContainer = new ObjectContainer();
            for (int i = 0; i < testData.size(); i++) {
                if (aksDescription.equals(xml.getTestObject(i).getAKSDescription())) {
                    objectContainer.add(xml.getTestObject(i));
                }
            }
            mXMLHandler.generateXMLFile(objectContainer);
        }
    }

    @Override
    public List<String> getXMLResultFiles() throws Exception{
        mResultFiles = mXMLHandler.getXMLResultFiles();
        return mResultFiles;
    }

    @Override
    public ObjectContainer readXMLResultFile(String pResultFile) throws Exception {
        mTestResults = mXMLHandler.readXMLResultFile(pResultFile);
        return mTestResults;
    }

    @Override
    public void generateTestReport(String pResultFile) throws Exception{
        ObjectContainer testReport = new ObjectContainer();
        testReport = mDatabase.getTransformerReportData(pResultFile);
        mTestReport.generateTestReport(mTestResults, testReport, mAdAcount);
    }

    @Override
    public List<String> getReportFiles() throws Exception{
        mReportFiles = mTestReport.getReportFiles();
        return mReportFiles;
    }

    @Override
    public String getReportPath() throws Exception{
        mReportPath = mTestReport.getReportPath();
        return mReportPath;
    }

    @Override
    public void approveTestReport(String pReportFile) throws Exception{
        mTestReport.approveTestReport(pReportFile, mAdAcount);
    }

}
