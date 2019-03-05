/*
 * Projekt: AutoPDV
 * Firma:   ewz Verteilnetze
 * Autor:   R. Peterhans / M. Hablützel
 * Datum:   08.03.2019
 * Version: 1.0
 *
 * Beschreibung:
 * Die Klasse XMLHandler wird für die Verarbeitung von XML-Files verwendet.   
 *
 */
package ch.abbts.autopdv.xml;

import ch.abbts.autopdv.businesslogic.ObjectContainer;
import ch.abbts.autopdv.businesslogic.TestResults;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLHandler implements XML {

    private int mCoreCount;
    private String mFileDestinationPath;
    private String mSourcePath;

    private final static String PATH = "C:\\ProgramData\\AutoPDV\\";
    private final static String TEMPLATE_FOLDER = "00_Templates\\";
    private final static String TESTFILE_FOLDER = "01_TestFiles\\";
    private final static String TESTRESULT_FOLDER = "02_TestResults\\";

    private final static String VT_TEMPLATE = "Template_UW.xml";
    private final static String CT_TEMPLATE = "Template_IW.xml";

    private final String DateTime = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(Calendar.getInstance().getTime());

    Document doc = null;

    //**********************************************************************
    // In dieser Methode wird aus der Vorlage das XML für die Prüfung
    // generiert mit den Daten aus der Datenbank
    //**********************************************************************
    @Override
    public void generateXMLFile(ObjectContainer objectContainer) throws Exception {
        
        mCoreCount = objectContainer.getTestObjects().size();
        mFileDestinationPath = PATH + TESTFILE_FOLDER + objectContainer.getTestObject(0).getAKSDescription() + ".xml";

        if (null == objectContainer.getTestObject(0).getFunctionName()) {
        } else {
            switch (objectContainer.getTestObject(0).getFunctionName()) {
                case "U-Wandler":
                    mSourcePath = PATH + TEMPLATE_FOLDER + VT_TEMPLATE;
                    break;
                case "I-Wandler":
                    mSourcePath = PATH + TEMPLATE_FOLDER + CT_TEMPLATE;
                    break;
                default:
                    break;
            }
        }

        File xmlFile = new File(mSourcePath);
        SAXBuilder builder = new SAXBuilder();
        doc = builder.build(xmlFile);
        Element eRoot = doc.getRootElement();
        int childCount = eRoot.getChildren().size();
        for (int i = 0; i < childCount; i++) {
            Element eChild = eRoot.getChildren().get(i);
            if (null != eChild.getName()) {
                switch (eChild.getName()) {
                    case "Filename":
                        eChild.setText(mFileDestinationPath);
                        break;
                    case "DateTime":
                        eChild.setText(DateTime);
                        break;
                    case "Cards":
                        Element eCard = eChild;
                        int cardItemCount = eCard.getChildren().size();
                        for (int j = 0; j < cardItemCount; j++) {
                            Element eCardItem = eCard.getChildren().get(j);
                            for (int k = 1; k < mCoreCount; k++) {
                                eCard.addContent(eCardItem.clone());
                            }
                        }
                        int pCounter = 0;
                        for (int j = 0; j < mCoreCount; j++) {
                            for (int k = 0; k <= 1; k++) {
                                Element eNewCardItem = eCard.getChildren().get(pCounter);
                                pCounter++;
                                int cardSubElementCount = eNewCardItem.getChildren().size();
                                for (int l = 0; l < cardSubElementCount; l++) {
                                    Element eCardSubElement = eNewCardItem.getChildren().get(l);
                                    if ("Date".equals(eCardSubElement.getName())) {
                                        eCardSubElement.setText(DateTime);
                                    }
                                    if ("bstrName".equals(eCardSubElement.getName())) {
                                        String test = eCardSubElement.getText();
                                        test = test.replace("1", Integer.toString(objectContainer.getTestObject(j).getCoreNumber()));
                                        eCardSubElement.setText(test);
                                    }

                                    if ("VPrimNom".equals(eCardSubElement.getName())) {
                                        int vPrimSubElementCount = eCardSubElement.getChildren().size();
                                        for (int m = 0; m < vPrimSubElementCount; m++) {
                                            Element eVPrimSubElement = eCardSubElement.getChildren().get(m);
                                            if ("bstrUnit".equals(eVPrimSubElement.getName())) {
                                                eVPrimSubElement.setText(String.valueOf(objectContainer.getTestObject(j).getUnit()));
                                            }
                                            if ("dValue".equals(eVPrimSubElement.getName())) {
                                                eVPrimSubElement.setText(String.valueOf(objectContainer.getTestObject(j).getPrimaryValue()));
                                            }
                                        }
                                    }
                                    if ("VSecNom".equals(eCardSubElement.getName())) {
                                        int vSecSubElementCount = eCardSubElement.getChildren().size();
                                        for (int m = 0; m < vSecSubElementCount; m++) {
                                            Element eVSecSubElement = eCardSubElement.getChildren().get(m);
                                            if ("bstrUnit".equals(eVSecSubElement.getName())) {
                                                eVSecSubElement.setText(String.valueOf(objectContainer.getTestObject(j).getUnit()));
                                            }
                                            if ("dValue".equals(eVSecSubElement.getName())) {
                                                eVSecSubElement.setText(String.valueOf(objectContainer.getTestObject(j).getSecondaryValue()));
                                            }
                                        }
                                    }
                                    if ("IPrimNom".equals(eCardSubElement.getName())) {
                                        int iPrimSubElementCount = eCardSubElement.getChildren().size();
                                        for (int m = 0; m < iPrimSubElementCount; m++) {
                                            Element eIPrimSubElement = eCardSubElement.getChildren().get(m);
                                            if ("bstrUnit".equals(eIPrimSubElement.getName())) {
                                                eIPrimSubElement.setText(String.valueOf(objectContainer.getTestObject(j).getUnit()));
                                            }
                                            if ("dValue".equals(eIPrimSubElement.getName())) {
                                                eIPrimSubElement.setText(String.valueOf(objectContainer.getTestObject(j).getPrimaryValue()));
                                            }
                                        }
                                    }
                                    if ("ISecNom".equals(eCardSubElement.getName())) {
                                        int iSecSubElementCount = eCardSubElement.getChildren().size();
                                        for (int m = 0; m < iSecSubElementCount; m++) {
                                            Element eISecSubElement = eCardSubElement.getChildren().get(m);
                                            if ("bstrUnit".equals(eISecSubElement.getName())) {
                                                eISecSubElement.setText(String.valueOf(objectContainer.getTestObject(j).getUnit()));
                                            }
                                            if ("dValue".equals(eISecSubElement.getName())) {
                                                eISecSubElement.setText(String.valueOf(objectContainer.getTestObject(j).getSecondaryValue()));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        FileOutputStream fOut = new FileOutputStream(new File(mFileDestinationPath));
        Format format = Format.getPrettyFormat();
        format.setEncoding("ISO-8859-1");
        XMLOutputter xmlOutput = new XMLOutputter(format);
        xmlOutput.output(doc, fOut);

//        //ohne ISO-8859-1 sind die Umlaute falsch wenn .jar mit BatchDatei gestartet wird!        
//        XMLOutputter xmlOutput = new XMLOutputter();
//        xmlOutput.setFormat(Format.getPrettyFormat());
//        xmlOutput.output(doc, new FileWriter(mFileDestinationPath));
    
    }

    //**********************************************************************
    // Diese Methode gibt die gerpüften XML-Files zurück für die Auswahl
    // zur Erstellung des Testprotokolls
    //**********************************************************************
    @Override
    public List<String> getXMLResultFiles() throws Exception {
        List<String> xmlFiles = new ArrayList<>();
        File directory = new File(PATH + TESTRESULT_FOLDER);
        for (File file : directory.listFiles()) {
            if (file.getName().toLowerCase().endsWith(".xml")) {
                xmlFiles.add(file.getName());
            }
        }
        return xmlFiles;
    }

    //**********************************************************************
    // Die folgende Methode liest die Resultate aus dem geprüften XML-File
    // (Kann für Spannungs und Stromwandler verwendet werden)
    //**********************************************************************
    @Override
    public ObjectContainer readXMLResultFile(String pResultFile) throws Exception {

        List<String> resultList = new ArrayList<>();
        ObjectContainer objectContainer = new ObjectContainer("Pruefresultate");

        //Search for results
        resultList.add("VPrim");
        resultList.add("VPrimPhase");
        resultList.add("VSec");
        resultList.add("VSecPhase");
        resultList.add("Ratio");
        resultList.add("Deviation");
        resultList.add("ISec");
        resultList.add("ISecPhase");
        resultList.add("Burden");
        resultList.add("CosPhi");
        resultList.add("IPrim");
        resultList.add("IPrimPhase");
        resultList.add("Vknee");
        resultList.add("Iknee");

        File xmlFile = new File(PATH + TESTRESULT_FOLDER + pResultFile);
        SAXBuilder builder = new SAXBuilder();
        doc = builder.build(xmlFile);
        Element eRoot = doc.getRootElement();
        int childCount = eRoot.getChildren().size();
        for (int i = 0; i < childCount; i++) {
            Element eChild = eRoot.getChildren().get(i);
            if ("Cards".equals(eChild.getName())) {
                int cardItemCount = eChild.getChildren().size();
                for (int j = 0; j < cardItemCount; j++) {
                    Element eCardItem = eChild.getChildren().get(j);
                    int cardItemSubElementsCount = eCardItem.getChildren().size();
                    TestResults testResult = new TestResults();
                    for (int k = 0; k < cardItemSubElementsCount; k++) {
                        Element eCardItemSubElement = eCardItem.getChildren().get(k);
                        if ("bstrName".equals(eCardItemSubElement.getName())) {
                            testResult.setName(eCardItemSubElement.getValue());
                        }
                        for (int l = 0; l < resultList.size(); l++) {
                            if (resultList.get(l).equals(eCardItemSubElement.getName())) {
                                int subElementCount = eCardItemSubElement.getChildren().size();
                                for (int m = 0; m < subElementCount; m++) {
                                    Element eSubElement = eCardItemSubElement.getChildren().get(m);
                                    if ("dValue".equals(eSubElement.getName())) {
                                        switch (resultList.get(l)) {
                                            case "VPrim": {
                                                testResult.setVPrim(eSubElement.getValue());
                                                break;
                                            }
                                            case "VPrimPhase": {
                                                testResult.setVPrimPhase(eSubElement.getValue());
                                                break;
                                            }
                                            case "VSec": {
                                                if (testResult.getName().startsWith("UWÜbers") || testResult.getName().startsWith("IWÜbers")) {
                                                    testResult.setVSec(eSubElement.getValue());
                                                }
                                                if (testResult.getName().startsWith("UWBürde")) {
                                                    testResult.setVSec2(eSubElement.getValue());
                                                }
                                                break;
                                            }
                                            case "VSecPhase": {
                                                if (testResult.getName().startsWith("UWÜbers") || testResult.getName().startsWith("IWÜbers")) {
                                                    testResult.setVSecPhase(eSubElement.getValue());
                                                }
                                                if (testResult.getName().startsWith("UWBürde")) {
                                                    testResult.setVSecPhase2(eSubElement.getValue());
                                                }
                                                break;
                                            }
                                            case "Ratio": {
                                                testResult.setRatio(eSubElement.getValue());
                                                break;
                                            }
                                            case "Deviation": {
                                                testResult.setDeviation(eSubElement.getValue());
                                                break;
                                            }
                                            case "ISec": {
                                                testResult.setISec(eSubElement.getValue());
                                                break;
                                            }
                                            case "ISecPhase": {
                                                testResult.setISecPhase(eSubElement.getValue());
                                                break;
                                            }
                                            case "Burden": {
                                                testResult.setBurden(eSubElement.getValue());
                                                break;
                                            }
                                            case "CosPhi": {
                                                testResult.setCosPhi(eSubElement.getValue());
                                                break;
                                            }
                                            case "IPrim": {
                                                testResult.setIPrim(eSubElement.getValue());
                                                break;
                                            }
                                            case "IPrimPhase": {
                                                testResult.setIPrimPhase(eSubElement.getValue());
                                                break;
                                            }
                                            case "Vknee": {
                                                testResult.setVknee(eSubElement.getValue());
                                                break;
                                            }
                                            case "Iknee": {
                                                testResult.setIknee(eSubElement.getValue());
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    objectContainer.add(testResult);
                }
            }
        }
        return objectContainer;
    }
}
