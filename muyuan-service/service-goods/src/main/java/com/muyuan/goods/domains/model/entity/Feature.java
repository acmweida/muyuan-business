package com.muyuan.goods.domains.model.entity;

import java.util.Date;
import lombok.Data;

/**
 * 通用特征量对象 t_feature
 *
 * @author ${author}
 * @date 2022-12-29T16:35:53.035+08:00
 */
@Data
public class Feature {

    @Data
    static public class Identify {

        private Long id;

        public Identify(Long id) {
            this.id = id;
        }
    }

    /** $column.columnComment */
    private Long id;

    /** 属性名称 */
    private String name;

    /** $column.columnComment */
    private String parentId;

    /** 是否叶子节点 0-是 1-否 */
    private Integer leaf;

    /** 状态 */
    private Long status;

    /** $column.columnComment */
    private String creator;

    /** $column.columnComment */
    private Long createBy;

    /** $column.columnComment */
    private Date createTime;




}
