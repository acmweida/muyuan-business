package com.muyuan.manager.system.model;

import com.muyuan.common.mybatis.common.BaseRepo;
import com.muyuan.common.core.util.StrUtil;
import com.muyuan.manager.system.base.common.GenConstants;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 代码生成业务字段表 gen_table_column
 *
 * @author 
 */
@Data
public class GenTableColumn {

    /**
     * 编号
     */
    private Long id;

    /**
     * 归属表编号
     */
    private Long tableId;

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 列描述
     */
    private String columnComment;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * JAVA类型
     */
    private String javaType;

    /**
     * JAVA字段名
     */
    private String javaField;

    /**
     * 是否主键（1是）
     */
    private String isPk;

    /**
     * 是否自增（1是）
     */
    private String isIncrement;

    /**
     * 是否必填（1是）
     */
    private String isRequired;

    /**
     * 是否为插入字段（1是）
     */
    private String isInsert;

    /**
     * 是否编辑字段（1是）
     */
    private String isEdit;

    /**
     * 是否列表字段（1是）
     */
    private String isList;

    /**
     * 是否查询字段（1是）
     */
    private String isQuery;

    /**
     * 查询方式（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围）
     */
    private String queryType;

    /**
     * 显示类型（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、image图片上传控件、upload文件上传控件、editor富文本控件）
     */
    private String htmlType;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建者
     */
    private String createBy;

    private String updateBy;

    private Date createTime;
    private Date updateTime;

    private String remark;

    public void setAutoIncrement(Long id) {
        this.id = id;
    }

    public boolean isPk() {
        return isPk(this.isPk);
    }

    public boolean isPk(String isPk) {
        return isPk != null && StringUtils.equals("1", isPk);
    }

    public void setIsInsert(String isInsert) {
        this.isInsert = isInsert;
    }

    public boolean isUsableColumn() {
        return isUsableColumn(javaField);
    }

    public static boolean isUsableColumn(String javaField) {
        // isSuperColumn()中的名单用于避免生成多余Domain属性，若某些属性在生成页面时需要用到不能忽略，则放在此处白名单
        return StringUtils.equalsAnyIgnoreCase(javaField, "parentId", "orderNum", "remark");
    }

    public boolean isInsert() {
        return isInsert(this.isInsert);
    }

    public boolean isInsert(String isInsert) {
        return isInsert != null && StringUtils.equals("1", isInsert);
    }

    public boolean isEdit() {
        return isInsert(this.isEdit);
    }

    public boolean isEdit(String isEdit) {
        return isEdit != null && StringUtils.equals("1", isEdit);
    }


    public boolean isList() {
        return isList(this.isList);
    }

    public boolean isList(String isList) {
        return isList != null && StringUtils.equals("1", isList);
    }

    public boolean isQuery() {
        return isQuery(this.isQuery);
    }

    public boolean isQuery(String isQuery) {
        return isQuery != null && StringUtils.equals("1", isQuery);
    }

    public String getCapJavaField() {
        return StringUtils.capitalize(javaField);
    }

    public String getUpperCaseJavaField() {
        return StringUtils.upperCase(StrUtil.humpToUnderline(javaField));
    }

    public boolean isIgnoreColumn() {
        return StringUtils.equalsAnyIgnoreCase(javaField,
                ArrayUtils.addAll(ArrayUtils.addAll(GenConstants.TREE_ENTITY, GenConstants.BASE_ENTITY),
                        BaseRepo.ID, BaseRepo.STATUS, BaseRepo.TYPE, BaseRepo.UPDATER, BaseRepo.UPDATE_BY,
                        BaseRepo.CREATOR, BaseRepo.CREATE_BY, BaseRepo.CREATE_TIME, BaseRepo.UPDATE_TIME
                ));
    }
}