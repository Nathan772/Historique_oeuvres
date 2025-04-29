package nate.company.history_work.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ControllerResources {
    public final ObjectWriter JSONCONVERTER = new ObjectMapper().writer().withDefaultPrettyPrinter();
    public static final ObjectMapper FROMJSONCONVERTER = new ObjectMapper();
}
