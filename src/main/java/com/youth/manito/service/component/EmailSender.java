package com.youth.manito.service.component;

import com.youth.manito.domain.entity.User;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSender {

    private static final String SENDER_EMAIL = "no-reply@shinseungyougi.kr";

    private static final String SENDER_PERSONAL = "신승유기 Official";

    private final JavaMailSender mailSender;

    @Async("emailSendThreadPoolExecutor")
    public CompletableFuture<Void> sendEmail(final User recipient, String messageBody, String subject) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            message.addRecipients(RecipientType.TO, recipient.getEmail());
            message.setSubject(subject);
            message.setText(messageBody, "utf-8", "html");
            message.setFrom(getInternetAddress());
            mailSender.send(message);
            log.info("이메일 전송 완료");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(null);
    }

    private InternetAddress getInternetAddress() {
        try {
            return new InternetAddress(SENDER_EMAIL, SENDER_PERSONAL);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
