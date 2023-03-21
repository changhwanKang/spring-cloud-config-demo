package com.example.springcloudconfigdemo;

import com.example.springcloudconfigdemo.sqs.AwsSqsService;
import com.example.springcloudconfigdemo.sqs.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DemoController {

  private final AwsSqsService awsSqsService;

//  @GetMapping("/send")
//  public ResponseEntity<Object> sendSqs(@RequestParam(name = "message") String message){
//
//    try {
//      return ResponseEntity.ok(awsSqsService.sendMessage(
//        MessageDto.builder().ecmId(message).build()
//      ));
//    } catch (Exception e) {
//      log.error(e.getMessage(), e);
//    }
//
//    return null;
//  }

}
