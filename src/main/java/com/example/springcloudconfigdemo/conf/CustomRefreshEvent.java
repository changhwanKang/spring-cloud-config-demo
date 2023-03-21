package com.example.springcloudconfigdemo.conf;

import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class CustomRefreshEvent extends RemoteApplicationEvent {

  public CustomRefreshEvent() {
  }

  public CustomRefreshEvent(Object source, String originService, Destination destination) {
    super(source, originService, destination);
  }


}
