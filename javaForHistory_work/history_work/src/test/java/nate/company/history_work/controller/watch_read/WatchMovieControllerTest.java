package nate.company.history_work.controller.watch_read;

import nate.company.history_work.siteTools.watch_read.WatchMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = WatchMovieController.class)
public class WatchMovieControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WatchMovieRepository watchMovieRepository;
}