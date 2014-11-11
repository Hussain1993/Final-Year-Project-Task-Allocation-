package com.Hussain.pink.triangle.Utils;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Hussain on 11/11/2014.
 */
public class TaskAllocationFile {
    private static final Logger LOG = LoggerFactory.getLogger(TaskAllocationFile.class);
    private static final String EMPLOYEE_TAG = "employee";
    private static final String ID_ATTR = "id";
    private static final String NAME_TAG = "name";
    private static final String TASK_TAG = "taskAssigned";

    public static ArrayList<String []> parseTaskAllocationFile(String filePath){
        ArrayList<String []> rows = new ArrayList<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));

            Element rootElement = document.getDocumentElement();
            NodeList list = rootElement.getElementsByTagName(EMPLOYEE_TAG);

            if (list != null && list.getLength() > 0)
            {
                for (int i = 0; i < list.getLength(); i++) {
                    String [] data =  {};
                    Element employeeElement = (Element) list.item(i);
                    String id = employeeElement.getAttribute(ID_ATTR);
                    String name = getTextValue(employeeElement,NAME_TAG);
                    String taskAssigned = getTextValue(employeeElement,TASK_TAG);
                    data = ArrayUtils.add(data,id);
                    data = ArrayUtils.add(data,name);
                    data = ArrayUtils.add(data,taskAssigned);
                    rows.add(data);
                }
            }
        }
        catch (ParserConfigurationException e) {
            LOG.error("There was an error while getting the document builder",e);
        }
        catch (SAXException e) {
            LOG.error("There was an error when trying to parse to the ta file {}",filePath,e);
        }
        catch (IOException e) {
            LOG.error("There was an error when trying to parse to the ta file {}",filePath,e);
        }
        return rows;
    }

    private static String getTextValue(Element e, String tag){
        NodeList list = e.getElementsByTagName(tag);
        if(list != null && list.getLength() > 0)
        {
            Element element = (Element) list.item(0);
            return element.getFirstChild().getNodeValue();
        }
        return null;
    }

}
