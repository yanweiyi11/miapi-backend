package com.yanweiyi.miapi.service;

import com.yanweiyi.miapi.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanweiyi.miapi.model.entity.InterfaceInfo;

/**
* @author 27645
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-04-05 23:46:29
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    /**
     * 校验
     *
     * @param interfaceInfo
     * @param add 是否为创建校验
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}