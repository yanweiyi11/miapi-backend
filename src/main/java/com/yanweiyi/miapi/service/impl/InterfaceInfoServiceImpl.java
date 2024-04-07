package com.yanweiyi.miapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanweiyi.miapi.common.ErrorCode;
import com.yanweiyi.miapi.exception.BusinessException;
import com.yanweiyi.miapi.mapper.InterfaceInfoMapper;
import com.yanweiyi.miapi.model.entity.InterfaceInfo;
import com.yanweiyi.miapi.service.InterfaceInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author 27645
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2024-04-05 23:46:29
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo> implements InterfaceInfoService {

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {


        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long id = interfaceInfo.getId();
        String name = interfaceInfo.getName();
        String description = interfaceInfo.getDescription();
        String url = interfaceInfo.getUrl();
        String requestHeader = interfaceInfo.getRequestHeader();
        String responseHeader = interfaceInfo.getResponseHeader();
        Integer status = interfaceInfo.getStatus();
        String method = interfaceInfo.getMethod();
        Long userId = interfaceInfo.getUserId();

        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(name, url, requestHeader, responseHeader, method)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (id != null && id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口名称过长");
        }
        if (StringUtils.isNotBlank(description) && description.length() > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口描述过长");
        }
        if (StringUtils.isNotBlank(url) && name.length() > 500) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口地址过长");
        }
        if (StringUtils.isNotBlank(requestHeader) && requestHeader.length() > 3000) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求头内容过长");
        }
        if (StringUtils.isNotBlank(responseHeader) && responseHeader.length() > 3000) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "响应头内容过长");
        }
        if (status != null && (status != 0 && status != -1)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "状态类型不合法");
        }
        if (StringUtils.isNotBlank(method) && !method.matches("(GET|POST|PUT|DELETE)")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求类型不合法");
        }
    }
    
}




