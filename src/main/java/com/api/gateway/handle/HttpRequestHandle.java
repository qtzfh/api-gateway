package com.api.gateway.handle;

import com.api.gateway.base.BaseHttpRequest;
import com.api.gateway.base.BaseRequestInfo;
import com.api.gateway.base.BaseResult;
import com.api.gateway.monitor.BaseLogHandle;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.apache.commons.lang3.StringUtils;

/**
 * HttpRequestHandle
 *
 * @author zhangfuhao
 * @Desc request请求处理
 * @date 2018年10月18日
 * @Version V1.0
 */
public class HttpRequestHandle {
    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(HttpRequestHandle.class);
    /**
     * 基础日志处理
     */
    private static final BaseLogHandle BASE_LOG_HANDLE = new BaseLogHandle();

    /**
     * request 请求处理
     *
     * @param requestInfo request请求信息
     * @return
     */
    public static String requestHandle(BaseRequestInfo requestInfo) {
        try {
            var verify = ApiVerifyHandle.verify(requestInfo);
            if (verify > 0) {
                return ApiVerifyHandle.getVerifyMessage(verify);
            } else {
                String result;
                String body = StringUtils.EMPTY;
                String uri = GateWayConfig.getGateWayUrl(requestInfo);
                if (requestInfo.getContent().isReadable()) {
                    body = requestInfo.getContent().toString(CharsetUtil.UTF_8);
                    result = BaseHttpRequest.send(requestInfo.getMethod(), uri, body);
                } else {
                    result = BaseHttpRequest.send(requestInfo.getMethod(), uri);
                }
                BASE_LOG_HANDLE.send(requestInfo.getMethod().name(), requestInfo.getUri(), body, result);
                return result;
            }
        } catch (Exception e) {
            LOGGER.error("request请求处理失败", e);
        }
        return BaseResult.defaultErrorMessage();
    }
}
