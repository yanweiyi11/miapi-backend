package com.yanweiyi.miapibackend.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yanweiyi.miapibackend.common.ErrorCode;
import com.yanweiyi.miapibackend.exception.BusinessException;
import com.yanweiyi.miapibackend.mapper.InterfaceInfoMapper;
import com.yanweiyi.miapicommon.model.entity.InterfaceInfo;
import com.yanweiyi.miapicommon.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    /**
     * 从数据库中查询模拟接口是否存在
     *
     * @param url
     * @param method
     * @return
     */
    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if (StringUtils.isAnyBlank(url, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        LambdaQueryWrapper<InterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InterfaceInfo::getUrl, url);
        queryWrapper.eq(InterfaceInfo::getMethod, method);
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectOne(queryWrapper);

        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return interfaceInfo;
    }
}
