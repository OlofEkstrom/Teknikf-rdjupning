package oe.teknikfordjupning.event.listener;

import oe.teknikfordjupning.event.RegistrationCompleteEvent;
import oe.teknikfordjupning.Models.User;
import oe.teknikfordjupning.Services.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;
    private final JavaMailSender mailSender;
    private User user;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        user = event.getUser();
        String verificationToken = UUID.randomUUID().toString();
        userService.saveUserVerificationToken(user, verificationToken);
        String url = event.getApplicationUrl() + "/register/verifyEmail?token=" + verificationToken;
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to complete your registration : {}", url);
    }

    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email verifiering";
        String senderName = "Teknikfördjupning";
        String mailContent = "<p> Hej, " + user.getFirstName() + ", </p>" +
                "<p> Tack för att du registrerade dig hos Momsbalans,"+"" +
                "Vänligen klicka på länken för att fullfölja din registrering. </p>"+
                "<a href=\"" +url+ "\">Verifiera din email för att aktivera ditt konto</a>"+
                "<p> Tack <br> OE.";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("momsbalans@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

    public void sendPasswordResetVerificationEmail(String url, User user) throws MessagingException, UnsupportedEncodingException {
        String subject = "Återställning av lösenord";
        String senderName = "Teknikfördjupning";
        String mailContent = "<p> Hej, " + user.getFirstName() + ", </p>" +
                "<p>Du har begärt återställning av ditt lösenord,"+"" +
                "<p>Har du inte begärt detta så kan du ignorera detta mailet." +
                "Vänligen klicka på länken för att återställa ditt lösenord. </p>"+
                "<a href=\"" +url+ "\">Återställning av lösenord</a>"+
                "<p> Tack <br> OE.";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("momsbalans@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

}
