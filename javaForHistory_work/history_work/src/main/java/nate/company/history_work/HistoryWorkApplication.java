package nate.company.history_work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class HistoryWorkApplication {

	public static void main(String[] args) {
//		SpringApplication.run(HistoryWorkApplication.class, args);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		Date date = new Date();
		System.out.println(date);
	}

}
