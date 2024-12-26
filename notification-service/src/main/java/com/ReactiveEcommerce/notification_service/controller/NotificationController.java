package com.ReactiveEcommerce.notification_service.controller;

import com.ReactiveEcommerce.notification_service.NotificationService;
import com.ReactiveEcommerce.notification_service.dto.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/notifications")

public class NotificationController {

    private final NotificationService notificationService;
   Logger logger= LoggerFactory.getLogger(NotificationController.class);
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

   @GetMapping("/send")
    public String sendemail(EmailRequest emailRequest) {
          notificationService.sendEmail("beamlaktatek@gmail.com","notification test", "beimnet sent you ..");

          // notificationService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
           return "email sent ";
    }


    @PostMapping("/send-order-notification")
    public String sendOrderNotification(@RequestBody EmailRequest email) {
        String Email=email.getEmail();
        notificationService.sendOrderPlacementNotification(Email);
        logger.info("order Notification");

        return "Order placement email sent!";
    }

    @PostMapping("/send-login-notification")
    public String sendRegistrationNotification(@RequestBody EmailRequest email) {
        String Email=email.getEmail();
        notificationService.sendUserLoginNotification(Email);
        return "a device has email logged sent!";
    }

    @PostMapping("/send-signup-confirmation")
    public String sendSignUpConfirmation(@RequestBody EmailRequest email) {
      String Email= email.getEmail();
        notificationService.sendSignUpConfirmation(Email);
        return "Sign-up confirmation email sent!";
    }


}




