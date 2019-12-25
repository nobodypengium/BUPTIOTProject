package database;

import entity.Doctor;
import entity.Patient;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

public class XMLOption {
    private static final String DoctorXML = "src/XML/Doctor.xml";
    private static final String PatientXML = "src/XML/Patient.xml";

    /**
     *
     * loadXML -  This function is used to load a XML file by SAX Reader
     * @param url The relative address of the XML file
     * @return
     * Document - The desiered XML document
     */
    static synchronized Document loadXML(String url) {
        Document document;
        try {
            SAXReader reader = new SAXReader();
            document = reader.read(url);
            return document;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * writeXML -  Used to write to a specific XML file by XMLWriter
     * @param document The changed document
     * @param url      The XML file to be write
     * void -
     */
    static synchronized void writeXML(Document document, String url) {
        try (FileWriter fileWriter = new FileWriter(url)) {
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            XMLWriter writer = new XMLWriter(fileWriter,format);
            writer.write(document);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param doctor 新建一个医生并存储
     * @return 唯一的医生ID或错误代码
     */
    public static int setDoctor(Doctor doctor) {
        Document document = loadXML(DoctorXML);
        Element rootElm = document.getRootElement();
        List<Element> elems=rootElm.elements();
        Iterator<Element> iter = elems.iterator();
        int tmpID = -1;
        int elemIndex = 0;
        Element doctorElm = null;

        if(!(iter.hasNext())) {
            tmpID = 0;
            doctorElm = rootElm.addElement("Doctor");
        }
        else {//产生一个唯一的ID
            int tmpIDPre = -1;
            int tmpIDThis = 0;
            for(elemIndex = 0; iter.hasNext(); elemIndex++) {
                Element tmpElem = iter.next();
                tmpIDThis = Integer.parseInt(tmpElem.attributeValue("id"));
                if(tmpIDThis-tmpIDPre!=1) {
                    tmpID=tmpIDPre+1;
                    break;
                }
                else {
                    tmpIDPre=tmpIDThis;
                }
            }
            if(!(iter.hasNext())&&tmpID==-1) {
                tmpID=tmpIDPre+1;
            }
            doctorElm = DocumentHelper.createElement("Doctor");
            elems.add(elemIndex, doctorElm);
        }

        DecimalFormat df = new DecimalFormat("00000");

        doctorElm.addAttribute("id", ""+df.format(tmpID));
        doctorElm.addElement("name").setText(doctor.getName());
        doctorElm.addElement("epc").setText(doctor.getEpc());
        writeXML(document,DoctorXML);
        return tmpID;
    }

    /**
     *
     * @param epc 验证卡片信息是否属于一个医生。
     * @return
     */
    public static boolean hasDoctor(String epc) {
        boolean hasCustomer = false;
        Document document = loadXML(DoctorXML);
        Element rootElm = document.getRootElement();
        List<Node> sameNameCustomerList = rootElm.selectNodes("//Doctor[epc='"+epc+"']");
        Iterator<Node> iter = sameNameCustomerList.iterator();
        if(iter.hasNext()){
            hasCustomer=true;
        }
        return hasCustomer;
    }

    public static Doctor getDoctor(String epc) {
        Doctor doctor = null;//如果找不到就返回null
        Document document = loadXML(DoctorXML);
        Element rootElm = document.getRootElement();
        List<Node> sameNameCustomerList = rootElm.selectNodes("//Doctor[epc='"+epc+"']");
        Iterator<Node> iter = sameNameCustomerList.iterator();
        if(iter.hasNext()){
            Element docElm = (Element) iter.next();
            int id = Integer.parseInt(docElm.attributeValue("id"));
            doctor = new Doctor(id,docElm.element("name").getText(),docElm.element("epc").getText());
        }
        return doctor;
    }

    public static int setPatient(Patient patient) {
        Document document = loadXML(PatientXML);
        Element rootElm = document.getRootElement();
        List<Element> elems=rootElm.elements();
        Iterator<Element> iter = elems.iterator();
        int tmpID = -1;
        int elemIndex = 0;
        Element patientElm = null;

        if(!(iter.hasNext())) {
            tmpID = 0;
            patientElm = rootElm.addElement("Patient");
        }
        else {
            int tmpIDPre = -1;
            int tmpIDThis = 0;
            for(elemIndex = 0; iter.hasNext(); elemIndex++) {
                Element tmpElem = iter.next();
                tmpIDThis = Integer.parseInt(tmpElem.attributeValue("id"));
                if(tmpIDThis-tmpIDPre!=1) {
                    tmpID=tmpIDPre+1;
                    break;
                }
                else {
                    tmpIDPre=tmpIDThis;
                }
            }
            if(!(iter.hasNext())&&tmpID==-1) {
                tmpID=tmpIDPre+1;
            }
            patientElm = DocumentHelper.createElement("Patient");
            elems.add(elemIndex, patientElm);
        }

        DecimalFormat df = new DecimalFormat("00000");

        patientElm.addAttribute("id", ""+df.format(tmpID));
        patientElm.addElement("name").setText(patient.getName());
        patientElm.addElement("epc").setText(patient.getEpc());
        patientElm.addElement("record").setText(patient.getRecord());
        writeXML(document,PatientXML);
        return tmpID;
    }

    public static Patient getPatient(String epc) {
        Patient patient = null;//如果找不到就返回null
        Document document = loadXML(PatientXML);
        Element rootElm = document.getRootElement();
        List<Node> sameNameCustomerList = rootElm.selectNodes("//Patient[epc='"+epc+"']");
        Iterator<Node> iter = sameNameCustomerList.iterator();
        if(iter.hasNext()){
            Element patientElm = (Element) iter.next();
            int id = Integer.parseInt(patientElm.attributeValue("id"));
            patient = new Patient(id, patientElm.element("name").getText(), patientElm.element("epc").getText(), patientElm.element("record").getText());
        }
        return patient;
    }

    public static int changePatient(String epc, String element, String value) {
        @SuppressWarnings("unused")
        Patient tmpPatient =null;
        Element patientElm;
        Document document = loadXML(PatientXML);
        Element rootElm = document.getRootElement();
        List<Node> sameNameCustomerList = rootElm.selectNodes("//Patient[epc='"+epc+"']");
        Iterator<Node> iter = sameNameCustomerList.iterator();
        if(iter.hasNext()){
            patientElm = (Element) iter.next();
        } else{
            return -1;//错误码，没有这个病人
        }
        patientElm.element(element).setText(value);
        writeXML(document, PatientXML);
        return 0;//一切正常没有错误
    }
}
