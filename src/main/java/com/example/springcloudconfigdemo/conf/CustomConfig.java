package com.example.springcloudconfigdemo.conf;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.config.monitor.CompositePropertyPathNotificationExtractor;
import org.springframework.cloud.config.monitor.PropertyPathEndpoint;
import org.springframework.cloud.config.monitor.PropertyPathNotificationExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CustomConfig {


  private final List<PropertyPathNotificationExtractor> extractors;

  @Bean
  @Primary
  @ConditionalOnBean(BusProperties.class)
  public PropertyPathEndpoint propertyPathEndpoint(BusProperties busProperties) {
    return new CustomPropertyPathEndpoint(new CompositePropertyPathNotificationExtractor(this.extractors),
      busProperties.getId());
  }

  @Bean
  @Primary
  @ConditionalOnMissingBean(BusProperties.class)
  public PropertyPathEndpoint noBusBeanPropertyPathEndpoint(
    @Value("${spring.cloud.bus.id:application}") String id) {
    return new CustomPropertyPathEndpoint(new CompositePropertyPathNotificationExtractor(this.extractors), id);
  }


}
