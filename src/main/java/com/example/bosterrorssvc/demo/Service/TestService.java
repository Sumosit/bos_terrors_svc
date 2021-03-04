package com.example.bosterrorssvc.demo.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class TestService {

  private final Log logger = LogFactory.getLog(getClass());

  private int i = 0;

  private static final String TOPIC = "test_topic";

  @Autowired
  private KafkaTemplate<Object, String> kafkaTemplate;

  public void run(String id) throws IOException, URISyntaxException, SAXException, ParserConfigurationException {
    DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

    String filename = "C:/Users/erlan/Downloads/task.xml";
    Document document = documentBuilder.parse(filename);

    Node root = document.getDocumentElement();

    NodeList temps = root.getChildNodes();
    for (int i = 0; i < temps.getLength(); i++) {
      Node temp = temps.item(i);
      if (temp.getNodeType() != Node.TEXT_NODE) {
        NodeList tempProps = temp.getChildNodes();
        for (int j = 0; j < tempProps.getLength(); j++) {
          Node tempProp = tempProps.item(j);
          if (tempProp.getNodeType() != Node.TEXT_NODE) {
            System.out.println(tempProp.getNodeName() + ":" + tempProp.getChildNodes().item(0).getTextContent());
          }
        }
        System.out.println("");
      }
    }

  }
}
