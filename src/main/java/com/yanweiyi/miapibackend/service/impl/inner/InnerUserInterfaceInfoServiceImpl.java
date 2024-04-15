package com.yanweiyi.miapibackend.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yanweiyi.miapibackend.common.ErrorCode;
import com.yanweiyi.miapibackend.exception.BusinessException;
import com.yanweiyi.miapibackend.mapper.UserInterfaceInfoMapper;
import com.yanweiyi.miapibackend.service.UserInterfaceInfoService;
import com.yanweiyi.miapicommon.model.entity.UserInterfaceInfo;
import com.yanweiyi.miapicommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    /**
     * 调用接口统计
     *
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        LambdaUpdateWrapper<UserInterfaceInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId);
        updateWrapper.eq(UserInterfaceInfo::getUserId, userId);
        updateWrapper.gt(UserInterfaceInfo::getLeftNum, 0);
        updateWrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
        return userInterfaceInfoService.update(updateWrapper);
    }

    /**
     * 获取调用次数
     *
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    @Override
    public int getLeftNum(long interfaceInfoId, long userId) {
        LambdaQueryWrapper<UserInterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId);
        queryWrapper.eq(UserInterfaceInfo::getUserId, userId);
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoMapper.selectOne(queryWrapper);
        return userInterfaceInfo.getLeftNum();
    }
}
