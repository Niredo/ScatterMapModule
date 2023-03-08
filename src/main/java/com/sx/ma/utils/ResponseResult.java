package com.sx.ma.utils;

import com.sx.ma.enums.AppHttpCodeEnum;
import lombok.Data;

/**
 * 统一接口返回结构体
 *
 * @author nhh
 */
@Data
public class ResponseResult<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 信息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;

    /**
     * 无参构造函数，默认SUCCESS
     */
    public ResponseResult() {
        this.code = AppHttpCodeEnum.SUCCESS.getCode();
        this.msg = AppHttpCodeEnum.SUCCESS.getMsg();
    }

    /**
     * 有参构造器：状态码，信息
     *
     * @param code 状态码
     * @param msg  信息
     */
    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 有参构造器：状态码，数据
     *
     * @param code 状态码
     * @param data 数据
     */
    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    /**
     * 有参构造器，状态码，信息，数据
     *
     * @param code 状态码
     * @param msg  信息
     * @param data 数据
     */
    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功(默认)
     *
     * @return SUCCESS
     */
    public static ResponseResult okResult() {
        return new ResponseResult();
    }

    /**
     * 成功(自定义状态码和信息)
     *
     * @param code 状态码
     * @param msg  信息
     * @return 响应
     */
    public static ResponseResult okResult(int code, String msg) {
        return new ResponseResult().ok(code, msg, null);
    }

    /**
     * 成功(自定义数据)
     *
     * @param data 数据
     * @return 响应
     */
    public static ResponseResult okResult(Object data) {
        ResponseResult result = setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    private ResponseResult<?> ok(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        return this;
    }

    /**
     * 失败(自定义响应枚举)
     *
     * @param enums 响应枚举
     * @return 响应
     */
    public static ResponseResult errorResult(AppHttpCodeEnum enums) {
        return setAppHttpCodeEnum(enums);
    }

    /**
     * 失败(自定义响应枚举和信息)
     *
     * @param enums
     * @param msg
     * @return
     */
    public static ResponseResult errorResult(AppHttpCodeEnum enums, String msg) {
        return setAppHttpCodeEnum(enums, msg);
    }

    private ResponseResult error(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    private static ResponseResult setAppHttpCodeEnum(AppHttpCodeEnum enums) {
        return okResult(enums.getCode(), enums.getMsg());
    }

    private static ResponseResult setAppHttpCodeEnum(AppHttpCodeEnum enums, String msg) {
        return okResult(enums.getCode(), msg);
    }
}