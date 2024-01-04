package com.muyuan.system.dto;

import com.muyuan.common.bean.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;


/**
 * 操作日志记录查询参数
 *
 * @author ${author}
 * @date 2022-12-15T15:27:12.638+08:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OperLogQueryParams extends PageDTO {

    @Serial
    private static final long serialVersionUID = 3375901275255768351L;

    /** 日志主键 */
    private Long id;

    /** 模块标题 */
    private String title;

    /** 业务类型（0其它 1新增 2修改 3删除） */
    private Integer businessType;

    /** 方法名称 */
    private String method;

    /** 请求方式 */
    private String requestMethod;

    /** 操作类别（0其它 1后台用户 2手机端用户） */
    private Integer operatorType;

    /** 操作人员 */
    private String operName;

    /** 部门名称 */
    private String deptName;

    /** 请求URL */
    private String operUrl;

    /** 主机地址 */
    private String operIp;

    /** 操作地点 */
    private String operLocation;

    /** 请求参数 */
    private String operParam;

    /** 返回参数 */
    private String jsonResult;

    /** 操作状态（0正常 1异常） */
    private Integer status;

    /** 错误消息 */
    private String errorMsg;

    /** 操作时间 */
    private Date operTime;


}