package com.muyuan.common.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.Date;

@Data
@ApiModel
public class Result {

    private int code;

    private String type;

    private String msg;

    private Object data;

    private Date optionTime = DateTime.now().toDate();

    public Result(int code, String type, String msg) {
        this.code = code;
        this.type = type;
        this.msg = msg;
    }

    public Result(int code, String type, String msg, Object data) {
        this.code = code;
        this.type = type;
        this.msg = msg;
        this.data = data;
    }
}
