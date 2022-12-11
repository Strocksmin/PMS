package com.example.pms.service;

import com.example.pms.DTO.MessageData;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;

public interface EmailService {

    void sendSimpleEmail(final MessageData messageData);

    void sendEmailWithAttachment(final String toAddress, final String subject,
                                 final String message, final String attachment)
            throws MessagingException, FileNotFoundException;
}