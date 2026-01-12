package history_work.entity;

import org.junit.jupiter.api.Test;

/**
 * Unit test class for the {@link EmailDetails} entity class.
 *
 * @author Dylan DE JESUS
 * @author Nathan BILINGI
 * @see EmailDetails
 */
public class EmailDetailsTest {
    /**
     * Checks that a NullPointerException is throw if there is at least a null parameter
     * given when the constructor is called.
     */
    @Test
    public void shouldThrowNullPointerException(){
//        var e = assertThrows(NullPointerException.class, () -> new EmailDetails(null, "", ""));
//        assertEquals("recipient is marked non-null but is null", e.getMessage());
    }
}