/**
 * Used by all the email services.
 *
 * It manages the email sending logic.
 */

package nate.company.history_work.service;

import nate.company.history_work.entity.EmailDetails;


/**
 * This interface gives a way to send simple mails.
 *
 * @author Dylan DE JESUS
 * @author Nathan BILINGI
 */
public interface EmailService {
    public boolean sendSimpleMessage(EmailDetails details);
}