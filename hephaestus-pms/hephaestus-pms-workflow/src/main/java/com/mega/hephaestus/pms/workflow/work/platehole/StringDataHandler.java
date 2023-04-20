package com.mega.hephaestus.pms.workflow.work.platehole;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/24 16:46
 */
public class StringDataHandler implements Sample.DataHandler {
    @Override
    public String handleData(Sample sample) {
        DataWrapper<?> dataWrapper = (DataWrapper<?>) sample.getData("data");
        if (dataWrapper != null && dataWrapper.getData() instanceof String) {
            String data = (String) dataWrapper.getData();
            return data.toUpperCase();
        }
        return "";
    }
}
