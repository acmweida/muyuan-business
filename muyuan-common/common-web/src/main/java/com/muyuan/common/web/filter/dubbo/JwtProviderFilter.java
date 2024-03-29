package com.muyuan.common.web.filter.dubbo;

import com.muyuan.common.core.constant.SecurityConst;
import com.muyuan.common.core.context.SecurityContextHolder;
import com.muyuan.common.web.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * @ClassName JwtProviderFilter
 * Description Dobbo RPC JWT 共享
 * @Author 2456910384
 * @Date 2022/7/25 11:43
 * @Version 1.0
 */
@Slf4j
@Activate(group = {CommonConstants.PROVIDER})
public class JwtProviderFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result;
        try {
            if (GenericService.class != invoker.getInterface()) {
                String jwtPayload = invocation.getAttachments().get(SecurityConst.JWT_PAYLOAD_KEY);
                if (ObjectUtils.isNotEmpty(jwtPayload)) {
                    SecurityUtils.setJwtPayLoad(jwtPayload);
                }
            }
            result = invoker.invoke(invocation);
        } finally {
            SecurityContextHolder.remove();
        }
        return result;
    }
}
