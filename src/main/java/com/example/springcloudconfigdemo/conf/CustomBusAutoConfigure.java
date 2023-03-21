package com.example.springcloudconfigdemo.conf;

import com.example.springcloudconfigdemo.sqs.AwsSqsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.bus.BusBridge;
import org.springframework.cloud.bus.RemoteApplicationEventListener;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CustomBusAutoConfigure {

  private final AwsSqsService awsSqsService;

  @Bean
  public BusBridge createCustomBusBridge(){
    return new CustomBusBridge(awsSqsService);
  }

  @Bean
  public RemoteApplicationEventListener busRemoteApplicationEventListener(ServiceMatcher serviceMatcher, BusBridge busBridge) {
    return new CustomBusListener(serviceMatcher, busBridge);
  }
}
