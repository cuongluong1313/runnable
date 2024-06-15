package org.winnie.runnable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.winnie.runnable.spring.springevent.TaskProducer;

@SpringBootApplication
public class RunnableApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(RunnableApplication.class, args);
        runSpringEvent(applicationContext);
    }

    public static void runSpringEvent(ApplicationContext context) {
        TaskProducer taskProducer = context.getBean(TaskProducer.class);
//        taskProducer.publishEventWithoutTransaction();
//        taskProducer.publishEventWithTransactionButError();
        taskProducer.publishEventWithTransaction("1");
    }

}
