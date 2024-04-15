package com.yanweiyi.miapicommon.service;

/**
 * 内部用户关联接口信息服务
 */
public interface InnerUserInterfaceInfoService {

    /**
     * 调用接口统计
     *
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);

    /**
     * 获取调用次数
     *
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    int getLeftNum(long interfaceInfoId, long userId);
}
