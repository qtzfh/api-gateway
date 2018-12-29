package com.api.gateway.exception;

/**
 * ServiceException
 *
 * @author zhangfuhao
 * @Desc 自定义服务异常
 * @date 2018年12月29日
 * @Version V1.0
 */
public class ServiceException extends RuntimeException {
    /**
     * 服务异常
     *
     * @param message
     */
    public ServiceException(String message) {
        super(message);
    }
}
