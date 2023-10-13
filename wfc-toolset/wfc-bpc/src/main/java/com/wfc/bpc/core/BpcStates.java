package com.wfc.bpc.core;

/**
 * @author hui.guo
 * @since 2022/7/7 9:52 上午
 */
public interface BpcStates {

    /**
     * 检查pipeline将是否被中断。
     *
     * @return 检查pipeline将是否被中断。
     */
    boolean isBroken();

    /**
     * 检查pipeline将是否已执行完成。
     *
     * @return 检查pipeline将是否已执行完成
     */
    boolean isFinished();

    /**
     * 设置
     *
     * @param broken
     */
    void setBroken(boolean broken);

    /**
     * 设置
     *
     * @param finished
     */
    void setFinished(boolean finished);

}
