package com.api.gateway.common;

import com.api.gateway.enums.BaseVerifyCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * BaseResult
 *
 * @author zhangfuhao
 * @Desc
 * @date 2018年10月16日
 * @Version V1.0
 */
@Getter
@Setter
public class BaseResult<T> {
    /**
     * 返回值code
     */
    private Integer code;
    /**
     * 返回消息
     */
    private String  message;
    /**
     * 返回实体类
     */
    private T       responseObject;

    private BaseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private BaseResult(Integer code, T responseObject) {
        this.code = code;
        this.responseObject = responseObject;
    }


    /**
     * 统一成功
     *
     * @param responseObject 返回实体
     * @return
     */
    public static <T> BaseResult success(T responseObject) {
        return new BaseResult(BaseVerifyCodeEnum.VERIFY_SUCCESS.getCode(), responseObject);
    }

    /**
     * 统一错误
     *
     * @param baseVerifyCode 统一定义的错误
     * @return
     */
    public static <T> BaseResult fail(BaseVerifyCodeEnum baseVerifyCode) {
        return new BaseResult(baseVerifyCode.getCode(), baseVerifyCode.getDescribe());
    }
}
