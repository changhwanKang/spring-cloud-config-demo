package com.example.springcloudconfigdemo.conf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.bus.event.PathDestinationFactory;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.cloud.config.monitor.PropertyPathEndpoint;
import org.springframework.cloud.config.monitor.PropertyPathNotification;
import org.springframework.cloud.config.monitor.PropertyPathNotificationExtractor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/custom-monitor")
@ResponseBody
public class CustomPropertyPathEndpoint extends PropertyPathEndpoint {


  private static final Log log = LogFactory.getLog(PropertyPathEndpoint.class);

  private final PropertyPathNotificationExtractor extractor;
  private final String busId;
  private ApplicationEventPublisher applicationEventPublisher;


  public CustomPropertyPathEndpoint(PropertyPathNotificationExtractor extractor, String busId) {
    super(extractor, busId);
    this.extractor = extractor;
    this.busId = busId;
  }

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @PostMapping
  public Set<String> notifyByPath(@RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> request) {
    PropertyPathNotification notification = this.extractor.extract(headers, request);
    if (notification != null) {

      Set<String> services = new LinkedHashSet<>();

      for (String path : notification.getPaths()) {
        services.addAll(guessServiceName(path));
      }
      if (this.applicationEventPublisher != null) {
        for (String service : services) {
          log.info("Refresh for: " + service);
          this.applicationEventPublisher
            .publishEvent(new CustomRefreshEvent(this, this.busId,  new PathDestinationFactory().getDestination(service)));
        }
        return services;
      }
    }
    return Collections.emptySet();
  }

  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public Set<String> notifyByForm(@RequestHeader HttpHeaders headers, @RequestParam("path") List<String> request) {
    Map<String, Object> map = new HashMap<>();
    String key = "path";
    map.put(key, request);
    return notifyByPath(headers, map);
  }

  private Set<String> guessServiceName(String path) {
    Set<String> services = new LinkedHashSet<>();
    if (path != null) {
      String stem = StringUtils.stripFilenameExtension(StringUtils.getFilename(StringUtils.cleanPath(path)));
      // TODO: correlate with service registry
      String name = stem + "-";
      int index;
      // support application name with dashes
      while ((index = name.lastIndexOf("-")) >= 0) {
        name = name.substring(0, index);
        if ("application".equals(name)) {
          services.add("*");
        } else {
          services.add(name);
        }
      }
    }
    return services;
  }

}
