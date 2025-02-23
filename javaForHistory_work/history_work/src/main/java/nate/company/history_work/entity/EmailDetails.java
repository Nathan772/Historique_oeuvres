/**
 * This file contains the entity used by the email sending service.
 * It provides an object that can be used to represent emails information.
 */

package nate.company.history_work.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


/**
 * This class is the representation of a simple email. It gathers the very minimum data about a mail
 * such as the recipient address (as a String), the subject (as a String) and the body content (as a String).
 *
 * @author Dylan DE JESUS
 * @author Nathan BILINGI
 */
@Data          // Implements toString, getters ans setters (via @Getter, @Setter on all non final fields)
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
    @NonNull
    private String recipient;
    @NonNull
    private String subject;
    @NonNull
    private String bodyContent;
}
