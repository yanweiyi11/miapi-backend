package com.yanweiyi.miapicommon.service;


import com.yanweiyi.miapicommon.model.entity.User;

/**
 * 内部用户服务
 */
public interface InnerUserService {

    /**
     * 从数据库中查是否已分配给用户密钥
     *
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);

}
