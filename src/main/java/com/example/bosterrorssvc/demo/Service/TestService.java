package com.example.bosterrorssvc.demo.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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

  private static final String TOPIC = "test_topic";

  @Autowired
  private KafkaTemplate<Object, String> kafkaTemplate;

  public void run(String id) {
    String fileName = "C:/Users/erlan/Downloads/task.xml";
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(new File(fileName));
      document.getDocumentElement().normalize();
      Element root = document.getDocumentElement();
      System.out.println(root.getNodeName());

      DocumentTraversal traversal = (DocumentTraversal) document;
      NodeIterator it = traversal.createNodeIterator(document.getDocumentElement(),
          NodeFilter.SHOW_ELEMENT, null, true);

      for (Node node = it.nextNode(); node != null;
           node = it.nextNode()) {

        String name = node.getNodeName();
        String text = node.getTextContent();

        if (text != null) {
          System.out.printf("%s: %s: ", name, text);
        }

        System.out.println();
      }
    } catch (Exception e) {
      kafkaTemplate.send(TOPIC, e.getMessage());
    }
  }
}
