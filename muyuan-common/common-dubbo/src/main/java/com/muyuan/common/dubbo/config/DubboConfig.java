
package com.muyuan.common.dubbo.config;

import com.muyuan.common.dubbo.exception.DubboExceptionHandlerAdvice;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * dubbo相关配置类
 *
 * @author
 * @since 2021-08-06 9:47
 */

@Configuration
@Import(DubboExceptionHandlerAdvice.class)
public class DubboConfig {

}

