package bean;


/**
 * Created by xlf on 2017/5/10.
 */
public class BaseBean {
    private int code;
    private String msg;

    public BaseBean() {
    }

    public BaseBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
