package com.Hussain.pink.triangle.Utils;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Created by Hussain on 11/11/2014.
 */
public class Test {
    private static Element rootElement;
    private static Document doc;

    public static void main(String[] args) {
        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //root elements
            doc = docBuilder.newDocument();
            rootElement = doc.createElement("taskAllocation");
            doc.appendChild(rootElement);

            addEmployees();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("/Users/Hussain/Desktop/Example CSV Files/File.ta"));
            transformer.transform(domSource,result);
            System.out.println("Finished");
            parseXML();
            System.out.println("Finished");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void addEmployees(){
        for (int i = 0; i < 10; i++) {
            //employee element
            Element employee = doc.createElement("employee");
            rootElement.appendChild(employee);

            Attr attr = doc.createAttribute("id");
            attr.setValue(String.valueOf(i));
            employee.setAttributeNode(attr);

            Element firstName = doc.createElement("firstName");
            firstName.appendChild(doc.createTextNode("Hussain"));
            employee.appendChild(firstName);

            Element task = doc.createElement("taskAssigned");
            task.appendChild(doc.createTextNode("t1"));
            employee.appendChild(task);
        }
    }

    private static void parseXML(){
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(new File("/Users/Hussain/Desktop/Example CSV Files/File.ta"));

            //Get the root element
            Element rootElement = doc.getDocumentElement();
            NodeList list = rootElement.getElementsByTagName("employee");
            if(list != null && list.getLength() > 0)
            {
                for (int i = 0; i < list.getLength(); i++) {
                    Element employeeElement = (Element) list.item(i);
                    String id = employeeElement.getAttribute("id");
                    String name = getTextValue(employeeElement,"firstName");
                    String taskAssigned = getTextValue(employeeElement,"taskAssigned");
                    System.out.println(String.format("The id is %s and name is %s and the task is %s",id,name,taskAssigned));
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static String getTextValue(Element element, String tagName){
        NodeList list = element.getElementsByTagName(tagName);
        if(list != null && list.getLength() > 0)
        {
            Element el = (Element)list.item(0);
            return el.getFirstChild().getNodeValue();
        }
        return null;
    }
}
