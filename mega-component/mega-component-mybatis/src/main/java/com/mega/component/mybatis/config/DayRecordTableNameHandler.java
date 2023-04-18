package com.mega.component.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DayRecordTableNameHandler implements TableNameHandler {
    @Override
    public String dynamicTableName(String sql, String tableName) {
//        if(Objects.isNull(dataSource)){
//            return tableName;
//        }
        /*
         * TABLE `hephaestus_device_task`;
         * TABLE `hephaestus_device_bottleneck`;
         * TABLE `hephaestus_instance_plate`;
         * TABLE `hephaestus_instance_task`;
         * TABLE `hephaestus_instance_step`;
         * TABLE `hephaestus_instance`;
         * TABLE `hephaestus_instance_cap`;
         * TABLE `hephaestus_experiment_group_history`;
         */

//        if (Objects.equals(tableName, "zsh_day_record")) {
//            int dataSourceIntValue = (int) dataSource;
//            if (Objects.equals(dataSourceIntValue, 1)) {
//                log.info("表名替换: " + tableName + " ---> sql:ods_zsh_day_record_s");
//                return "ods_zsh_day_record_s";
//            }
//        }
        return tableName;
    }
}
