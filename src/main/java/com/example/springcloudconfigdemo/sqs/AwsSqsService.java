package com.example.springcloudconfigdemo.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class AwsSqsService {

  @Value("${cloud.aws.sqs.queue.url}")
  private String url;
  private final ObjectMapper objectMapper;
  private final AmazonSQS amazonSQS;


  public void sendMessage(MessageDto msg) throws Exception {
    SendMessageRequest sendMessageRequest = new SendMessageRequest(url,
      objectMapper.writeValueAsString(msg))
      .withMessageGroupId("sqs-test")
      .withMessageDeduplicationId(UUID.randomUUID().toString());
    amazonSQS.sendMessage(sendMessageRequest);
  }


}
