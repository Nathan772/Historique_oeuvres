/**
 * This class is used as an extension by the integration tests for the email service.
 */

package history_work.service;



import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

import static com.icegreen.greenmail.util.ServerSetup.PROTOCOL_SMTP;


@Component
/**
 * Manages a server that waits for incoming emails. This class uses the {@link GreenMail} implementation.
 *
 * @author Dylan DE JESUS
 * @author Nathan BILINGI
 * @see EmailServiceIT
 * @see GreenMail
 * @see BeforeAllCallback
 * @see AfterAllCallback
 * @see AfterEachCallback
 */
public class SmtpServerExtension implements BeforeAllCallback, AfterAllCallback, AfterEachCallback {

    /**
     * The port number on which, the server will listen.
     */
    private final int port = 3025; // 3025 is used by smtp

    /**
     * The value of the email account login. Should be the same as the one
     * given in the application.properties (inside test resource)
     */
    private final String user = "toto";

    /**
     * The value of the email account password. Should be the same as the one
     * given in the application.properties (inside test resource)
     */
    private final String password = "totosPassword";

    /**
     * The reference on the smtp server.
     */
    private GreenMail smtpServer;

    /**
     * Callback that is invoked once before all tests in the current container. It starts the server.
     *
     * @param context the current extension context, never null.
     */
    @Override
    public void beforeAll(ExtensionContext context) {
        smtpServer = new GreenMail(new ServerSetup(port, null, PROTOCOL_SMTP));
        smtpServer.setUser(user, password);
        smtpServer.start();
    }

    /**
     * Callback that is invoked once after all tests in the current container. It stops the server.
     *
     * @param context the current extension context, never null.
     */
    @Override
    public void afterAll(ExtensionContext context) {
        smtpServer.stop();
    }

    /**
     * Retrieves all the mails stored by the server.
     *
     * @return an array of messages.
     */
    public MimeMessage[] getMessages() {
        return smtpServer.getReceivedMessages();
    }

    /**
     * Callback that is invoked once after each test in the current container. It purges the server from
     * all the potentially stored emails.
     *
     * @param extensionContext the current extension context, never null.
     */
    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        smtpServer.purgeEmailFromAllMailboxes();
    }
}