package com.example.springcloudconfigdemo.sqs;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

  @NotEmpty
  private String ecmId;
  private String serviceName;



}
