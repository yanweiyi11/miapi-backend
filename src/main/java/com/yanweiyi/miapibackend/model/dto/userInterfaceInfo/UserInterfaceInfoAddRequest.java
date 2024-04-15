package com.yanweiyi.miapibackend.model.dto.userInterfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 */
@Data
public class UserInterfaceInfoAddRequest implements Serializable {

    private static final long serialVersionUID = 5018268020560208308L;

    /**
     * 调用用户 id
     */
    private Long userId;

    /**
     * 接口 id
     */
    private Long interfaceInfoId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

}