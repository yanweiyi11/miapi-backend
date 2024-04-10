package com.yanweiyi.miapi.service;

import com.yanweiyi.miapi.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 27645
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-04-08 20:33:05
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean b);

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
