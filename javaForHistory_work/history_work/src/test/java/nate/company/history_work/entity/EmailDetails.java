package nate.company.history_work.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nate.company.history_work.service.EmailService;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails implements EmailService {
    private String bodyContent;
    private String subject;
}
