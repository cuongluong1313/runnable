package org.winnie.runnable.spring.springevent;

import java.util.Date;

public class TaskScheduledEvent {

  private String taskId;
  private String byWho;
  private Date when;

  public TaskScheduledEvent(String taskId, String byWho, Date when) {
    this.taskId = taskId;
    this.byWho = byWho;
    this.when = when;
  }

  public String getTaskId() {
    return taskId;
  }

  public String getByWho() {
    return byWho;
  }

  public Date getWhen() {
    return when;
  }

  @Override
  public String toString() {
    return "TaskScheduledEvent{" +
        "taskId='" + taskId + '\'' +
        "byWho='" + byWho + '\'' +
        "when='" + when + '\'' +
        '}';
  }
}
