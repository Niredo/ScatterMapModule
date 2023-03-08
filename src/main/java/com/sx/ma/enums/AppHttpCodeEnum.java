package com.sx.ma.enums;

import lombok.Getter;

/**
 * 状态码枚举
 *
 * @author nhh
 */
@Getter
public enum AppHttpCodeEnum {
    SUCCESS(200, "操作成功"),
    SYSTEM_ERROR(500, "系统错误");
    /**
     * 状态码
     */
    int code;
    /**
     * 信息
     */
    String msg;

    AppHttpCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
