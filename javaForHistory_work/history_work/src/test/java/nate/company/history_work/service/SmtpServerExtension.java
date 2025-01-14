package nate.company.history_work.service;


import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.util.TestSocketUtils;

import javax.mail.internet.MimeMessage;

import static com.icegreen.greenmail.util.ServerSetup.PROTOCOL_SMTP;

@Component
public class SmtpServerExtension implements BeforeAllCallback, AfterAllCallback {
    private int port = 3025;

    private String user = "ddjm.developer@gmail.com";

    private String password = "xxxxx";

    private GreenMail smtpServer;




    @Override
    public void beforeAll(ExtensionContext context) {
        System.out.println("starting Greenmail on port: {" + port + "}");
        //
        smtpServer = new GreenMail(new ServerSetup(port, null, PROTOCOL_SMTP));
        smtpServer.setUser(user, password);
        smtpServer.start();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        System.out.println("stopping Greenmail");
        smtpServer.stop();
    }

    public MimeMessage[] getMessages() {
        return smtpServer.getReceivedMessages();
    }
}
