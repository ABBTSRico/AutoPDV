/*
 * Projekt: AutoPDV
 * Firma:   ewz Verteilnetze
 * Autor:   R. Peterhans / M. Hablützel
 * Datum:   08.03.2019
 * Version: 1.0
 *
 * Beschreibung:
 * Die Klasse ObjectContainer wird verwendet, um Objekte von einem Layer an einen anderen zu übergeben.  
 *
 */
package ch.abbts.autopdv.businesslogic;

import java.util.ArrayList;
import java.util.List;

public class ObjectContainer {

    private String mName;
    private List<DistributionNetwork> mNetworkList;
    private List<NetworkLevel> mNetworkLevelList;
    private List<InspectionLot> mInsepctionLotList;
    private List<TransformerTestData> mTransformerTestDataList;
    private List<TestResults> mTestResultList;
    private List<TransformerReportData> mTransformerReportDataList;

    public ObjectContainer() {
        this("Unbenannt");
    }

    public ObjectContainer(String pName) {
        this.mName = pName;
        mNetworkList = new ArrayList<>();
        mNetworkLevelList = new ArrayList<>();
        mInsepctionLotList = new ArrayList<>();
        mTransformerTestDataList = new ArrayList<>();
        mTestResultList = new ArrayList<>();
        mTransformerReportDataList = new ArrayList<>();
    }

    public void setName(String pName) {
        this.mName = pName;
    }

    public String getName() {
        return mName;
    }

    public void add(DistributionNetwork pNetwork) {
        mNetworkList.add(pNetwork);
    }

    public DistributionNetwork getNetwork(int pIndex) {
        return mNetworkList.get(pIndex);
    }

    public List<DistributionNetwork> getNetworks() {
        return mNetworkList;
    }

    public void add(NetworkLevel pLevel) {
        mNetworkLevelList.add(pLevel);
    }

    public NetworkLevel getLevel(int pIndex) {
        return mNetworkLevelList.get(pIndex);
    }

    public List<NetworkLevel> getLevels() {
        return mNetworkLevelList;
    }

    public void add(InspectionLot pLot) {
        mInsepctionLotList.add(pLot);
    }

    public InspectionLot getLot(int pIndex) {
        return mInsepctionLotList.get(pIndex);
    }

    public List<InspectionLot> getLots() {
        return mInsepctionLotList;
    }

    public void add(TransformerTestData pTestData) {
        mTransformerTestDataList.add(pTestData);
    }

    public TransformerTestData getTestObject(int pIndex) {
        return mTransformerTestDataList.get(pIndex);
    }

    public List<TransformerTestData> getTestObjects() {
        return mTransformerTestDataList;
    }

    public void add(TestResults pTestResults) {
        mTestResultList.add(pTestResults);
    }

    public TestResults getResultObject(int pIndex) {
        return mTestResultList.get(pIndex);
    }

    public List<TestResults> getResultObjects() {
        return mTestResultList;
    }

    public void add(TransformerReportData pReportData) {
        mTransformerReportDataList.add(pReportData);
    }

    public TransformerReportData getReportObject(int pIndex) {
        return mTransformerReportDataList.get(pIndex);
    }

    public List<TransformerReportData> getReportObjects() {
        return mTransformerReportDataList;
    }

    @Override
    public String toString(){
        return mName;
    }
    
}
