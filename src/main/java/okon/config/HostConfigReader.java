package okon.config;

import okon.Host;
import okon.exception.AppException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class HostConfigReader {
    public static ArrayList<Host> readParams(File file) {
        Element config = parseXml(file);
        ArrayList<Host> result = new ArrayList<>();
        NodeList hosts = config.getElementsByTagName("host");
        if (hosts != null && hosts.getLength() > 0) {
            for (int i = 0; i < hosts.getLength(); i++) {
                Node host = hosts.item(i);
                if (host.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) host;
                    String hostInterface = element.getElementsByTagName("host_interface").item(0).getTextContent();
                    String system = element.getElementsByTagName("system").item(0).getTextContent();
                    String ip = element.getElementsByTagName("ip").item(0).getTextContent();
                    Integer port = element.getElementsByTagName("port").item(0).getTextContent().equals("") ? null :
                            Integer.valueOf(element.getElementsByTagName("port").item(0).getTextContent());
                    result.add(new Host(hostInterface, system, ip, port));
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