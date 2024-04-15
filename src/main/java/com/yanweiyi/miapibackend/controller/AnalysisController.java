package com.yanweiyi.miapibackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yanweiyi.miapibackend.annotation.AuthCheck;
import com.yanweiyi.miapibackend.common.BaseResponse;
import com.yanweiyi.miapibackend.common.ErrorCode;
import com.yanweiyi.miapibackend.common.ResultUtils;
import com.yanweiyi.miapibackend.exception.BusinessException;
import com.yanweiyi.miapibackend.mapper.UserInterfaceInfoMapper;
import com.yanweiyi.miapibackend.service.InterfaceInfoService;
import com.yanweiyi.miapicommon.model.entity.InterfaceInfo;
import com.yanweiyi.miapicommon.model.entity.UserInterfaceInfo;
import com.yanweiyi.miapicommon.model.vo.InterfaceInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 管理员统计分析
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    /**
     * 获取调用次数最多的接口信息列表（饼图）
     *
     * @return
     */
    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterface() {
        // 获取前5个调用次数最多的接口信息列表
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopInvokeInterface(5);
        // 将接口信息按照接口ID分组
        Map<Long, List<UserInterfaceInfo>> interfaceInfoObjMap = userInterfaceInfoList
                .stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));

        // 查询对应的接口信息
        LambdaQueryWrapper<InterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(InterfaceInfo::getId, interfaceInfoObjMap.keySet());
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        if (CollectionUtils.isEmpty(interfaceInfoList)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        // 将查询到的接口信息转换为VO对象
        List<InterfaceInfoVO> interfaceInfoVOList = interfaceInfoList.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
            int totalNum = interfaceInfoObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());

        return ResultUtils.success(interfaceInfoVOList);
    }
}
