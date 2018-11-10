package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SchedulerEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    private static final String SUBJECT = "Tasks: once a day email";
    private static final String MESSAGE = "Currently in database you got: ";

    @Autowired
    //private SimpleEmailService simpleEmailService;
    private SchedulerEmailService schedulerEmailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    @Scheduled(cron = "0 0 10 * * *") //(fixedDelay = 10000)
    public void sendInformationEmail() {
        //simpleEmailService.send(new Mail(
        schedulerEmailService.send(new Mail(
                adminConfig.getAdminMail(),
                SUBJECT,
                messageToBeSent(),
                null)
        );
    }

    private String messageToBeSent() {
        long size = taskRepository.count();
        if(size==1) {
            return MESSAGE + "1 task";
        }
        return MESSAGE + size + " tasks";
    }
}

