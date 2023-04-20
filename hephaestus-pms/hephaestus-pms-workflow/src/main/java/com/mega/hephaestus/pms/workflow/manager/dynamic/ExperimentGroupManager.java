package com.mega.hephaestus.pms.workflow.manager.dynamic;


/**
 * 实验组操作
 */
public interface ExperimentGroupManager {

    /**
     * 根据id，运行一个实验组
     *
     * @param id 实验组ID
     * @return 提交结果成功与否
     */
    boolean start(long id);

    /**
     * 根据id 修改实验组状态为完成
     *
     * @param id 实验组ID
     */
    void finishGroup(long id);

    /**
     * 根据id，运行一个实验组 新
     *
     * @param id 实验组ID
     * @return 提交结果成功与否
     */
    boolean startNew(long id);

}
