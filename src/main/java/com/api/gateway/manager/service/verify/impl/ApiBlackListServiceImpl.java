package com.api.gateway.manager.service.verify.impl;

import com.api.gateway.base.BaseInitFactory;
import com.api.gateway.manager.service.verify.ApiBlackListService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Objects;

/**
 * ApiBlackListServiceImpl
 *
 * @author zhangfuhao
 * @Desc
 * @date 2018年12月29日
 * @Version V1.0
 */
public class ApiBlackListServiceImpl implements ApiBlackListService {
    @Override
    public Integer getLevelByIp(String ip) {
        if (StringUtils.isBlank(ip)) {
            return NumberUtils.INTEGER_MINUS_ONE;
        }
        Integer value = BaseInitFactory.BLACK_LIST_HASH_MAP.get(ip);
        return Objects.isNull(value) ? NumberUtils.INTEGER_MINUS_ONE : value;
    }
}
