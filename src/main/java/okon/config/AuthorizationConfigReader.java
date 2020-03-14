package okon.config;

import okon.Authorization;
import okon.exception.AppException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

import static okon.security.HexDecryptor.convert;

public class AuthorizationConfigReader {
    public static ArrayList<Authorization> readParams(File file) {
        Element config = parseXml(file);
        ArrayList<Authorization> result = new ArrayList<>();
        NodeList users = config.getElementsByTagName("user");
        if (users != null && users.getLength() > 0) {
            for (int i = 0; i < users.getLength(); i++) {
                Node user = users.item(i);
                if (user.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) user;
                    String credentialsInterface = element.getElementsByTagName("authorization_interface").item(0).getTextContent();
                    String login = element.getElementsByTagName("login").item(0).getTextContent();
                    String password = element.getElementsByTagName("password").item(0).getTextContent();
                    String domain = element.getElementsByTagName("domain").item(0).getTextContent();
                    result.add(new Authorization(credentialsInterface, convert(login), convert(password), domain));
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