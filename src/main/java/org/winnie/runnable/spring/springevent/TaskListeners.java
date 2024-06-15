package org.winnie.runnable.spring.springevent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TaskListeners {

    static final Logger logger = LoggerFactory.getLogger(TaskListeners.class);

    @EventListener
    public void handleWithoutTransaction(TaskScheduledEvent event) {
      logger.info("{} thread -- handling todo event without transaction", Thread.currentThread().getName());
    }

    @TransactionalEventListener
    public void handleAfterCommit(TaskScheduledEvent event) {
      logger.info("{} thread -- handling todo event after commit", Thread.currentThread().getName());
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleBeforeCommit(TaskScheduledEvent event) throws InterruptedException {
      logger.info("{} thread -- handling todo event before commit", Thread.currentThread().getName());
      System.out.println("Sleep 2s");
      Thread.sleep(2000);
      System.out.println("Done Sleep");
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handleAfterCompletion(TaskScheduledEvent event) {
      logger.info("{} thread -- handling todo event after completion", Thread.currentThread().getName());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleAfterRollback(TaskScheduledEvent event) {
      logger.info("{} thread -- handling todo event after rollback", Thread.currentThread().getName());
      logger.info("{}", event);
    }

}
