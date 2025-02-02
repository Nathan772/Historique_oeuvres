/**
 * This class is called from the authentication module.
 *
 * It manages the email sending logic.
 */

package nate.company.history_work.service;

import nate.company.history_work.entity.EmailDetails;
import org.springframework.stereotype.Service;


/**
 * This service is based on the JavaMailSender implementation.
 *
 * From a content, it creates a mail and send it.
 */
public interface EmailService {
    public boolean sendSimpleMessage(EmailDetails details);
}