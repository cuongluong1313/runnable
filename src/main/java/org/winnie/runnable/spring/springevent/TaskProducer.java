package org.winnie.runnable.spring.springevent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class TaskProducer {

  @Autowired
  private ApplicationEventPublisher publisher;

  public ApplicationEventPublisher getPublisher() {
    return publisher;
  }

  public void publishEventWithoutTransaction() {
    publisher.publishEvent(new TaskScheduledEvent("abc", "123", new Date()));
  }

  @Transactional
  public void publishEventWithTransaction(String temp) {
    if (temp.equals("")) {
      throw new RuntimeException("Run time");
    }
    publisher.publishEvent(new TaskScheduledEvent("bcd", "234", new Date()));
    System.out.println("publishEventWithTransaction");
  }

  @Transactional
  public void publishEventWithTransactionButError() {
    publisher.publishEvent(new TaskScheduledEvent("def", "345", new Date()));
    throw new RuntimeException("error");
  }
}
