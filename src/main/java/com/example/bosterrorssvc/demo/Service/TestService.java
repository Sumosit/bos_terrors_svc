package com.example.bosterrorssvc.demo.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

@Service
public class TestService {

  private final Log logger = LogFactory.getLog(getClass());

  private int i = 0;
  private String json = "";
  private boolean xml = false;

  private static final String TOPIC = "test_topic";

  @Autowired
  private KafkaTemplate<Object, String> kafkaTemplate;

  public void run(String id) {
    String fileName = "C:/Users/erlan/Downloads/task.xml";
    try {
      DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
          .newDocumentBuilder();
      Document doc = dBuilder.parse(fileName);
//      System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
      if (doc.hasChildNodes()) {
        printNote(doc.getChildNodes());
      }
    } catch (Exception e) {
      kafkaTemplate.send(TOPIC, e.getMessage());
    }
  }

  private void printNote(NodeList nodeList) throws JsonProcessingException {
    for (int count = 0; count < nodeList.getLength(); count++) {
      Node tempNode = nodeList.item(count);

      if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

        if (tempNode.getNodeName().equals("xml")) {
          ObjectMapper jsonMapper = new ObjectMapper();
          this.json = this.json + jsonMapper.writeValueAsString(tempNode);
          this.xml = true;
        }
        if (this.xml) {
//          System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
//          System.out.println("Node Value =" + tempNode.getTextContent());
          if (tempNode.getNodeName().equals("o:OfficeDocumentSettings") ||
              tempNode.getNodeName().equals("w:WordDocument")) {
          }

          if (tempNode.hasAttributes()) {
            NamedNodeMap nodeMap = tempNode.getAttributes();
            for (int i = 0; i < nodeMap.getLength(); i++) {
              Node node = nodeMap.item(i);
//              System.out.println("attr name : " + node.getNodeName());
//              System.out.println("attr value : " + node.getNodeValue());
            }
          }
        }
        if (tempNode.hasChildNodes()) {
          printNote(tempNode.getChildNodes());

        }
        if (tempNode.getNodeName().equals("xml")) {
          kafkaTemplate.send(TOPIC, this.json);
          this.xml = false;
        }
      }
    }
  }
}
