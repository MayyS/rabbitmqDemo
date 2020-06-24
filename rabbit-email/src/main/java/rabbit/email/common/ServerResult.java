package rabbit.email.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @Auther:S
 * @Date:2020/6/24
 */
public class ServerResult implements Serializable {
    private static final long serialVersionUID = 7498483649536881777L;

    private Integer status;

    private String msg;

    private Object data;

    public ServerResult() {
    }

    public ServerResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResultCode.SUCCESS.getCode();
    }

    public static ServerResult success() {
        return new ServerResult(ResultCode.SUCCESS.getCode(), null, null);
    }

    public static ServerResult success(String msg) {
        return new ServerResult(ResultCode.SUCCESS.getCode(), msg, null);
    }

    public static ServerResult success(Object data) {
        return new ServerResult(ResultCode.SUCCESS.getCode(), null, data);
    }

    public static ServerResult success(String msg, Object data) {
        return new ServerResult(ResultCode.SUCCESS.getCode(), msg, data);
    }

    public static ServerResult error(String msg) {
        return new ServerResult(ResultCode.ERROR.getCode(), msg, null);
    }

    public static ServerResult error(Object data) {
        return new ServerResult(ResultCode.ERROR.getCode(), null, data);
    }

    public static ServerResult error(String msg, Object data) {
        return new ServerResult(ResultCode.ERROR.getCode(), msg, data);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

