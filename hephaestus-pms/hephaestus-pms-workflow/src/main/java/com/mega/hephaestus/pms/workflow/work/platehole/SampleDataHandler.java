package com.mega.hephaestus.pms.workflow.work.platehole;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/24 16:44
 */
public class SampleDataHandler implements Sample.DataHandler {

    @Override
    public String handleData(Sample sample) {
        String type = (String) sample.getData("type");
        if (type != null) {
            switch (type) {
                case "text":
                    return handleTextData(sample);
                case "number":
                    return handleNumberData(sample);
                case "date":
                    return handleDateData(sample);
                // Handle other types of data as needed
            }
        }
        return "";
    }

    private String handleTextData(Sample sample) {
        String value = (String) sample.getData("value");
        if (value != null) {
            return value.toUpperCase();
        }
        return "";
    }

    private String handleNumberData(Sample sample) {
        Integer value = (Integer) sample.getData("value");
        if (value != null) {
            return String.valueOf(value * 2);
        }
        return "";
    }

    private String handleDateData(Sample sample) {
        LocalDate value = (LocalDate) sample.getData("value");
        if (value != null) {
            return DateTimeFormatter.ISO_DATE.format(value);
        }
        return "";
    }

}
