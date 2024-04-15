package com.yanweiyi.miapicommon.service;

import com.yanweiyi.miapicommon.model.entity.InterfaceInfo;

/**
 * 内部接口信息服务
 */
public interface InnerInterfaceInfoService {

    /**
     * 从数据库中查询模拟接口是否存在
     *
     * @param url
     * @param method
     * @return
     */
    InterfaceInfo getInterfaceInfo(String url, String method);

}
