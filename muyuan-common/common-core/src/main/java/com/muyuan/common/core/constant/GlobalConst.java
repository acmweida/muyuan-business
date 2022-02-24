package com.muyuan.common.core.constant;

public interface GlobalConst {

    String ID = "id";

    long KB = 1024;

    long MB = KB << 10;

    long GB = MB << 10;

    Object NULL = null;

    String TOKEN = "token";

    String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    String HTTP = "http://";

    String HTTPS = "https://";

    String UTF8 = "UTF-8";

    /** 菜单类型（目录） */
     String TYPE_DIR = "M";

    /** 菜单类型（菜单） */
    String TYPE_MENU = "C";

    /** 菜单类型（按钮） */
    String TYPE_BUTTON = "F";

    int NO_FRAME = 1;

    /** Layout组件标识 */
    String LAYOUT = "Layout";

    /** InnerLink组件标识 */
    String INNER_LINK = "InnerLink";

    /** ParentView组件标识 */
    String PARENT_VIEW = "ParentView";

}