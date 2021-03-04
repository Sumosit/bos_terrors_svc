package com.example.bosterrorssvc.demo.Controllers;

import com.example.bosterrorssvc.demo.Models.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastInstanceFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestController {

  private final Producer producer;

  @Autowired
  public TestController(Producer producer) {
    this.producer = producer;
  }
  @PostMapping("/publish")
  public void messageToTopic(@RequestParam("message") String message) throws IOException {

    this.producer.sendMessage(message);
  }
}
