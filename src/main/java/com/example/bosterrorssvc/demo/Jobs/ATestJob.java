package com.example.bosterrorssvc.demo.Jobs;

import com.example.bosterrorssvc.demo.Service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ATestJob extends QuartzJobBean {

  private TestService testService;

  private static final String TOPIC = "test_topic";

  @Autowired
  private KafkaTemplate<Object, String> kafkaTemplate;

  public ATestJob(TestService testService) {
    this.testService = testService;
  }

  @Override
  protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//    log.info("ATestJob Running");
    this.kafkaTemplate.send(TOPIC, "ATestJob Running");
    try {
      String id = jobExecutionContext.getJobDetail().getKey().getName();
      testService.run(id);
    } catch (Exception e) {
      throw new JobExecutionException(e);
    }
  }

}
