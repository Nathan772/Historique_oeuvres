package src.main.java.nate.company.history_work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//@SpringBootApplication

/**
 * TODO : Put some doc here
 */
public class HistoryWorkApplication {

	/**
	 * Program entry.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
//		SpringApplication.run(HistoryWorkApplication.class, args);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		Date date = new Date();
		System.out.println(date);
	}

}
