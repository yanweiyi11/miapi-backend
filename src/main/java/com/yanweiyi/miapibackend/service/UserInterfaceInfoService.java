package com.yanweiyi.miapibackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanweiyi.miapicommon.model.entity.UserInterfaceInfo;

/**
 * 用户关联接口信息服务
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean b);
}
