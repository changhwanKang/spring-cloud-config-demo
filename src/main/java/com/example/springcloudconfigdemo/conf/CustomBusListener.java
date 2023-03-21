package com.example.springcloudconfigdemo.conf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.bus.BusBridge;
import org.springframework.cloud.bus.RemoteApplicationEventListener;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;


public class CustomBusListener extends RemoteApplicationEventListener {

  private final Log log = LogFactory.getLog(getClass());

  private final ServiceMatcher serviceMatcher;
  private final BusBridge busBridge;

  public CustomBusListener(ServiceMatcher serviceMatcher, BusBridge busBridge) {
    super(serviceMatcher, busBridge);
    this.serviceMatcher = serviceMatcher;
    this.busBridge = busBridge;
  }

  @Override
  public void onApplicationEvent(RemoteApplicationEvent event) {
    busBridge.send(event);
  }
}
