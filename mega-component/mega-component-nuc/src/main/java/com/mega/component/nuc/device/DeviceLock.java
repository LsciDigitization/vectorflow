package com.mega.component.nuc.device;

public interface DeviceLock {

    /**
     * 重试获取锁。
     * <p>
     * 第一次获取锁失败后，在重试时间retryTimeout时间内，会挂起线程睡眠一定时间，不断重试，
     * 如果重试成功，则直接返回成功；
     * 如果重试失败，直到超时时间结束，返回失败
     *
     * @param instanceId  锁的值； 使用 实例ID instance_id 确保加锁和释放锁为同一实例
     * @param retryTimeout   重试超时时间，时间内，不断重试
     * @return 锁获取成功，返回true；否则，返回false
     */
    boolean retryLock(String instanceId, int retryTimeout);

    /**
     * 获取锁。
     *
     * @param instanceId 锁的值； 使用 实例ID instance_id 确保加锁和释放锁为同一实例
     * @return 锁获取成功，返回true；否则，返回false
     */
    boolean lock(String instanceId);

    /**
     * 检查当前实例是否获得该设备的锁
     * @param instanceId 锁的值； 使用 实例ID instance_id 确保加锁和释放锁为同一实例
     * @return 锁获取成功，返回true；否则，返回false
     */
    boolean checkLock(String instanceId);

    /**
     * 释放锁
     *
     * @param instanceId 锁的值； 使用 实例ID instance_id 确保加锁和释放锁为同一实例
     */
    boolean unlock(String instanceId);

    /**
     * 强制释放锁
     *
     */
    boolean forceUnlock();

}
