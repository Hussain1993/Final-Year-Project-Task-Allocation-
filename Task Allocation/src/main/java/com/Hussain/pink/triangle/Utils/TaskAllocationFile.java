package com.Hussain.pink.triangle.Utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Hussain on 11/11/2014.
 * This class will be used to save a TaskAllocation from
 * the table where a suggested allocation will be held.
 *
 * This class will also be used to parse a TaskAllocation file
 * to load it within the UI and the user will be able to assign the
 * users within the table
 */
public class TaskAllocationFile {
    private static final Logger LOG = LoggerFactory.getLogger(TaskAllocationFile.class);
    private static final String ROOT_ELEMENT = "taskAllocation";
    private static final String EMPLOYEE_TAG = "employee";
    private static final String ID_ATTR = "id";
    private static final String NAME_TAG = "name";
    private static final String TASK_TAG = "taskAssigned";
    private static final String TASK_ID_TAG = "taskID";
    private static final String ASSIGN_TAG  = "assigned";

    private static final int ID_COLUMN_INDEX = 0;
    private static final int NAME_COLUMN_INDEX = 1;
    private static final int TASK_COLUMN_INDEX = 2;
    private static final int TASK_ID_COLUMN_INDEX = 3;
    private static final int ASSIGNED_COLUMN_INDEX = 4;

    /**
     * This method will parse a ta file and return an arraylist of all the
     * row data that is contained within the file
     * @param filePath The file path to the ta file the user would like
     *                 to open
     * @return An arraylist where each element within the list is a String array
     * each String array will contain the information for each row
     * of data to be added to the allocation table.
     */
    public static ArrayList<Object []> parseTaskAllocationFile(String filePath){
        ArrayList<Object []> rows = new ArrayList<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            if(!FileIO.fileExists(filePath))
            {
                throw new FileNotFoundException("The task allocation file was not found");
            }
            LOG.info("Parsing the task allocation file located at {}",filePath);
            Document document = documentBuilder.parse(new File(filePath));

            Element rootElement = document.getDocumentElement();
            NodeList list = rootElement.getElementsByTagName(EMPLOYEE_TAG);

            if (list != null && list.getLength() > 0)
            {
                for (int i = 0; i < list.getLength(); i++) {
                    Object [] data =  {};
                    Element employeeElement = (Element) list.item(i);
                    String id = employeeElement.getAttribute(ID_ATTR);
                    String name = getTextValue(employeeElement,NAME_TAG);
                    String taskAssigned = getTextValue(employeeElement,TASK_TAG);
                    String taskID = getTextValue(employeeElement,TASK_ID_TAG);
                    boolean isTaskAssigned = Boolean.valueOf(getTextValue(employeeElement,ASSIGN_TAG));
                    data = ArrayUtils.add(data,id);
                    data = ArrayUtils.add(data,name);
                    data = ArrayUtils.add(data,taskAssigned);
                    data = ArrayUtils.add(data,taskID);
                    data = ArrayUtils.add(data,isTaskAssigned);
                    rows.add(data);
                }
            }
        }
        catch (ParserConfigurationException e) {
            LOG.error("There was an error while getting the document builder",e);
        }
        catch (SAXException | IOException e) {
            LOG.error("There was an error when trying to parse the ta file {}",filePath,e);
        }
        return rows;
    }

    /**
     * Get the text element for a given
     * XML tag element
     * @param e The element to get the text element from
     * @param tag The name of the tag that is used within
     *            the XML file
     * @return The String value that is used within the
     * tag
     */
    private static String getTextValue(Element e, String tag){
        NodeList list = e.getElementsByTagName(tag);
        if(list != null && list.getLength() > 0)
        {
            Element element = (Element) list.item(0);
            return element.getFirstChild().getNodeValue();
        }
        return null;
    }

    public static boolean saveTaskAllocationFile(String filePath, DefaultTableModel allocationTableModel){
        DocumentBuilderFactory documentBuilderFactory = new DocumentBuilderFactoryImpl();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.newDocument();
            Element rootElement = doc.createElement(ROOT_ELEMENT);
            doc.appendChild(rootElement);

            addEmployees(doc,rootElement,allocationTableModel);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(filePath);
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source,result);
            LOG.info("The file has been saved to {}",filePath);
            return FileIO.fileExists(filePath);
        }
        catch (ParserConfigurationException e) {
            LOG.error("There was an error while getting the document builder", e);
        }
        catch (TransformerConfigurationException e) {
            LOG.error("There was an error while getting the transformer", e);
        }
        catch (TransformerException e) {
            LOG.error("There was an error while transforming the file", e);
        }

        return false;
    }

    /**
     * Adds all the employees from the table model to the document to be saved
     * @param document The task allocation file
     * @param root The root element of the document
     * @param model The allocation table model
     */
    private static void addEmployees(Document document, Element root, DefaultTableModel model){
        String id = null;
        String employeeName = null;
        String taskAssigned = null;
        String taskID = null;
        String assigned = null;
        for (int row = 0; row < model.getRowCount(); row++) {
            for (int column = 0; column < model.getColumnCount(); column++) {
                switch (column)
                {
                    case ID_COLUMN_INDEX:
                        id = String.valueOf(model.getValueAt(row,column));
                        break;
                    case NAME_COLUMN_INDEX:
                        employeeName = String.valueOf(model.getValueAt(row,column));
                        break;
                    case TASK_COLUMN_INDEX:
                        taskAssigned = String.valueOf(model.getValueAt(row,column));
                        break;
                    case TASK_ID_COLUMN_INDEX:
                        taskID = String.valueOf(model.getValueAt(row,column));
                        break;
                    case ASSIGNED_COLUMN_INDEX:
                        assigned = String.valueOf(model.getValueAt(row,column));
                        break;
                }
            }
            Element employee = document.createElement(EMPLOYEE_TAG);
            root.appendChild(employee);

            Attr attribute = document.createAttribute(ID_ATTR);
            attribute.setValue(id);
            employee.setAttributeNode(attribute);

            Element employeeNameElement = document.createElement(NAME_TAG);
            employeeNameElement.appendChild(document.createTextNode(employeeName));
            employee.appendChild(employeeNameElement);

            Element task = document.createElement(TASK_TAG);
            task.appendChild(document.createTextNode(taskAssigned));
            employee.appendChild(task);

            Element taskIDElement = document.createElement(TASK_ID_TAG);
            taskIDElement.appendChild(document.createTextNode(taskID));
            employee.appendChild(taskIDElement);

            Element assignedElement = document.createElement(ASSIGN_TAG);
            assignedElement.appendChild(document.createTextNode(assigned));
            employee.appendChild(assignedElement);
        }
    }

}
