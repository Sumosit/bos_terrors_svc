package com.example.bosterrorssvc.demo;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class testApp {

  public static void main(String[] args) throws IOException, URISyntaxException {
    String filename = "C:/Users/erlan/Downloads/task.html";

    File in = new File(filename);
    Document doc = Jsoup.parse(in, null);
    Elements names = doc.select("xml");
    for (int i = 0; i < names.size(); i++) {
      System.out.print(names.get(i));
    }
  }
}
