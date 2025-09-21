package com.albertopaim.bazar.com.services;

import com.albertopaim.bazar.com.controllers.dtos.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void EmailSender(EmailDto dto) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom("golpeDaLua@gmail.com");
            helper.setTo(dto.to());
            helper.setSubject(dto.subject());
            helper.setText(dto.body(), true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Não foi possível enviar o e-mail", e);
        }
    }
}