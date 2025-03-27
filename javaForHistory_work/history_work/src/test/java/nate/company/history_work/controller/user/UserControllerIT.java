package nate.company.history_work.controller.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
/**
 * Structure of an integration test for a controller.
 */
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    /**
     * TODO : Actually, we have some postman tests that may do the thing
     */
    @Test
    public void firstTest(){
        //
    }
}