package com.mega.hephaestus.pms.workflow.work.platehole;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/24 14:45
 */
public class Sample {

    private String id;
    private String sampleType;
    private Map<String, Object> data;
    private DataHandler dataHandler;

    public Sample(String id, String sampleType) {
        this.id = id;
        this.sampleType = sampleType;
        this.data = new HashMap<>();
    }

    public void addData(String key, Object value) {
        data.put(key, value);
    }

    public Object getData(String key) {
        return data.get(key);
    }

    public void removeData(String key) {
        data.remove(key);
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public String handleData() {
        if (dataHandler != null) {
            return dataHandler.handleData(this);
        }
        return "";
    }

    public interface DataHandler {
        String handleData(Sample sample);
    }


}
