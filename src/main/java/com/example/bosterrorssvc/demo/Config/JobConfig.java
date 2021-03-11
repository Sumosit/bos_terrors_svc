package com.example.bosterrorssvc.demo.Config;

import com.example.bosterrorssvc.demo.Jobs.ATestJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {

  @Bean
  public JobDetail aTestJob() {
    return JobBuilder
        .newJob(ATestJob.class).withIdentity("ATestJob")
        .storeDurably().build();
  }

  @Bean
  public Trigger aTestTrigger(JobDetail jobADetails) {

    return TriggerBuilder.newTrigger().forJob(jobADetails)

        .withIdentity("ATestJobTrigger")
        .withSchedule(CronScheduleBuilder.cronSchedule("19 * * * * ?"))
        .build();
  }

}
