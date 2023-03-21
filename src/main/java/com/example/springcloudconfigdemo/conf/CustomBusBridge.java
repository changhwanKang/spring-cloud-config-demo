package com.example.springcloudconfigdemo.conf;

import com.example.springcloudconfigdemo.sqs.AwsSqsService;
import com.example.springcloudconfigdemo.sqs.MessageDto;
import org.springframework.cloud.bus.BusBridge;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;


public class CustomBusBridge implements BusBridge {


  private final AwsSqsService awsSqsService;
  public CustomBusBridge(AwsSqsService awsSqsService) {
    this.awsSqsService = awsSqsService;
  }

  @Override
  public void send(RemoteApplicationEvent event) {
    try {
      awsSqsService.sendMessage(MessageDto.builder().ecmId(event.getId()).serviceName(event.getDestinationService()).build());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
