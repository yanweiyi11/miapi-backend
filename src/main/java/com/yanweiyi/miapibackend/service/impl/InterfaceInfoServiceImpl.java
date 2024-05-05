package com.yanweiyi.miapibackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanweiyi.miapibackend.common.ErrorCode;
import com.yanweiyi.miapibackend.exception.BusinessException;
import com.yanweiyi.miapibackend.mapper.InterfaceInfoMapper;
import com.yanweiyi.miapibackend.service.InterfaceInfoService;
import com.yanweiyi.miapicommon.model.entity.InterfaceInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 接口信息服务实现类
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
        String requestParams = interfaceInfo.getRequestParams();
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
        if (StringUtils.isNotBlank(url) && url.length() > 500) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口地址过长");
        }
        if (StringUtils.isNotBlank(requestParams) && requestParams.length() > 1000) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数过长");
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




