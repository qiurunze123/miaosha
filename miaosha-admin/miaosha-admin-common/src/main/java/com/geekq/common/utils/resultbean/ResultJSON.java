package com.geekq.common.utils.resultbean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 邱润泽
 */
@Getter
@Setter
public class ResultJSON {
    private Boolean success = false;
    private String msg;

    public ResultJSON() {
        super();
    }

    public ResultJSON(Boolean success, String msg) {
        super();
        this.success = success;
        this.msg = msg;
    }

    public ResultJSON(String msg) {
        super();
        this.msg = msg;
    }

}
