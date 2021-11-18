package com.example.appmodulecall.api;

public interface ICallService {

    /**
     * 添加 呼入监听
     *
     * @param callIncomeListener
     */
    void addCallIncomeListener(ICallIncomeListener callIncomeListener);

    /**
     * 移除 呼入监听
     *
     * @param callIncomeListener
     */
    void removeCallIncomeListener(ICallIncomeListener callIncomeListener);

    /**
     * 创建呼叫
     *
     *  @param calleeUserId  被呼叫者 用户id
     * @param callType  呼叫类型
     */
    void createCall(String calleeUserId, CallType callType, ICallCreateCallBack callCreateCallBack);

    /**
     * 获取 主动呼叫对象
     *
     * @return
     */
    ICaller getCaller();

    /**
     * 获取 被动呼叫对象
     *
     * @return
     */
    ICallee getCallee();

}
