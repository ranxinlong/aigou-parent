package cn.itsource.basic.util;

/**
 * Ajax请求封装的工具类
 * @author ranmoumou
 */
public class AjaxResult {

    private Boolean success = true;
    private String message = "操作成功";
    private Object object;

    public AjaxResult() {
    }

    public static AjaxResult me(){
        return new AjaxResult();
    }

    public Boolean getSuccess() {
        return success;
    }

    public AjaxResult setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AjaxResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public AjaxResult setObject(Object object) {
        this.object = object;
        return this;
    }
}
