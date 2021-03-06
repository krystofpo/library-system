package com.kr.librarysystem.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class LibraryScheduler {

@Autowired
ExpirationService expirationService;

@Scheduled(cron = "${scheduler.expirationNotification}")
public void sendNotifications(){
    expirationService.notifyMembers();
}
}
