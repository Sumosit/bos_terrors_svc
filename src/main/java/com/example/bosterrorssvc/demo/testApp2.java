package com.example.bosterrorssvc.demo;


import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class testApp2 {

  public static void main(String[] args) throws IOException, URISyntaxException, SAXException, ParserConfigurationException {
    DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    // Создается дерево DOM документа из файла
    String filename = "C:/Users/erlan/Downloads/task.xml";
    Document document = documentBuilder.parse(filename);

    // Получаем корневой элемент
    Node root = document.getDocumentElement();

    System.out.println("List of books:");
    System.out.println();
    // Просматриваем все подэлементы корневого - т.е. книги
    NodeList books = root.getChildNodes();
    for (int i = 0; i < books.getLength(); i++) {
      Node book = books.item(i);
      // Если нода не текст, то это книга - заходим внутрь
      if (book.getNodeType() != Node.TEXT_NODE) {
        NodeList bookProps = book.getChildNodes();
        for (int j = 0; j < bookProps.getLength(); j++) {
          Node bookProp = bookProps.item(j);
          // Если нода не текст, то это один из параметров книги - печатаем
          if (bookProp.getNodeType() != Node.TEXT_NODE) {
            System.out.println(bookProp.getNodeName() + ":" + bookProp.getChildNodes().item(0).getTextContent());
          }
        }
        System.out.println("===========>>>>");
      }
    }
  }
}
