package com.mega.hephaestus.pms.workflow.manager.plan;


import com.mega.component.bioflow.task.ProcessRecordId;
import com.mega.hephaestus.pms.workflow.manager.model.ProcessIterationModel;

import java.util.List;
import java.util.Optional;


public interface ProcessIterationManager {

    /**
     * 消费一个通量
     * @param id 通量id
     * @return 是否成功
     */
    boolean consumeIteration(long id);


    /**
     *   通量消费
     * @param processRecordId 记录id
     * @param iterationNo 通量号
     * @return 是否成功消费
     */
    boolean consumeIteration(ProcessRecordId processRecordId, int iterationNo);

    /**
     *   通量消费
     * @param processRecordId 记录id
     * @param iterationNo 通量号
     * @return 是否成功消费
     */
    boolean consumeIteration(long processRecordId,int iterationNo);

    /**
     * 完成一个通量
     * @param id 通量id
     * @return 是否成功
     */
    boolean finishedIteration(long id);


    /**
     * 完成当前记录的 通量
     * @param processRecordId 记录id
     * @param iterationNo 通量号
     * @return 是否完成
     */
    boolean finishedIteration(ProcessRecordId processRecordId, int iterationNo);



    /**
     * 完成当前记录的 通量
     * @param processRecordId 记录id
     * @param iterationNo 通量号
     * @return 是否完成
     */
    boolean finishedIteration(long processRecordId,int iterationNo);


    /**
     * 根据流程记录id 拿到未消费的通量
     * @param processRecordId 记录id
     * @return 通量
     */
    Optional<ProcessIterationModel> getUnConsumed(ProcessRecordId processRecordId);
    /**
     * 根据流程记录id 拿到未消费的通量
     * @param processRecordId 记录id
     * @return 通量
     */
    Optional<ProcessIterationModel> getUnConsumed(long processRecordId);
    /**
     * 根据通量号 拿到未消费的通量
     * @param processRecordId 记录id
     * @param iterationNo 通量号
     * @return 通量
     */
    Optional<ProcessIterationModel> getUnConsumed(long processRecordId, int iterationNo);

    /**
     * 根据通量id 获取通量model
     * @param id id
     * @return 通量model
     */
    Optional<ProcessIterationModel> getById(long id, long processRecordId);


    /**
     * 获取未消费的 通量列表
     * @param processRecordId 通量
     * @return 未消费的通量列表
     */
    List<ProcessIterationModel> listByUnConsumed(ProcessRecordId processRecordId);

    /**
     * 获取未消费的 通量列表
     * @param processRecordId 通量
     * @return 未消费的通量列表
     */
    List<ProcessIterationModel> listByUnConsumed(long processRecordId);
}
