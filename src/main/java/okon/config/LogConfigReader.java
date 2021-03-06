package okon.config;

import okon.Log;
import okon.LogMatch;
import okon.exception.AppException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LogConfigReader {
    public static ArrayList<Log> readParams(File file) {
        Element config = parseXml(file);
        ArrayList<Log> result = new ArrayList<>();
        NodeList logs = config.getElementsByTagName("log");
        if (logs != null && logs.getLength() > 0) {
            for (int i = 0; i < logs.getLength(); i++) {
                Node log = logs.item(i);
                if (log.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) log;
                    String directory = element.getElementsByTagName("directory").item(0).getTextContent();
                    List<LogMatch> matches = new ArrayList<>();
                    for (int j = 0, size = element.getElementsByTagName("filename").getLength(); j < size; j++) {
                        String match = element.getElementsByTagName("filename").item(j).getAttributes().item(0).getNodeValue();
                        String filename = element.getElementsByTagName("filename").item(j).getTextContent();
                        matches.add(new LogMatch(match, filename));
                    }
                    Integer lines = Integer.valueOf(element.getElementsByTagName("lines").item(0).getTextContent());
                    String host_interface = element.getElementsByTagName("host_interface").item(0).getTextContent();
                    String credentials_interface = element.getElementsByTagName("credentials_interface").item(0).getTextContent();
                    result.add(new Log(directory, matches, lines, host_interface, credentials_interface));
                }
            }
        }
        return result;
    }

    private static Element parseXml(File file) {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse(file);
            return document.getDocumentElement();
        } catch (Exception e) {
            throw new AppException(e);
        }
    }
}