package com.example.demo.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{
  @Autowired
  private JavaMailSender mailSender;

  @Override
  public void sendEmail(String toEmail, String subject, String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("aonoringo4@gmail.com");
    message.setTo(toEmail);
    message.setText(body);
    message.setSubject(subject);
    mailSender.send(message);
  }
}
