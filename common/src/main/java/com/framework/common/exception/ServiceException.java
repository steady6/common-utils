package com.framework.common.exception;

import java.util.List;
import java.util.Map;


public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 3270233872426405024L;

    /* MSG信息描述 */
    public static final String MSG_PARAM_MISSING = "参数类型错误或为空";
    public static final String MSG_PARAM_INVALID = "参数有误";

    /* 系统性异常 */
    public static final String CODE_SYS_ERROR = "1";
    public static final String MSG_SYS_ERROR = "系统异常";

    public static final String CODE_BUSI_ERROR = "2000";
    public static final String MSG_BUSI_ERROR = "业务逻辑异常";

    public static final String CODE_PARAMS_ERROR = "1000";
    public static final String MSG_PARAMS_ERROR = "参数错误";

    private String code;
    private String msg;
    private Map<String, List<String>> validate;

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String code, String msg, Map<String, List<String>> validate) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.validate = validate;
    }

    public ServiceException() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, List<String>> getValidate() {
        return validate;
    }

    public void setValidate(Map<String, List<String>> validate) {
        this.validate = validate;
    }

    private static String parseMsg(String msg, String... params) {
        StringBuilder sb = new StringBuilder(msg);
        if (null == params || params.length < 1) {
            return sb.toString();
        }
        sb.append("{");
        for (int i = 0; i < params.length; i++) {
            if (i > 0)
                sb.append(",");
            sb.append(params[i]);
        }
        sb.append("}");
        return sb.toString();
    }

    public static ServiceException sysError(String... params) {
        return new ServiceException(CODE_SYS_ERROR, parseMsg(MSG_SYS_ERROR, params));
    }

    public static ServiceException busiError(String... params) {
        return new ServiceException(CODE_BUSI_ERROR, parseMsg(MSG_BUSI_ERROR, params));
    }

    public static ServiceException paramsError(String... params) {
        return new ServiceException(CODE_PARAMS_ERROR, parseMsg(MSG_PARAMS_ERROR, params));
    }

    /**
     * Avoid Dubbo ExceptionFilter
     *
     * @param e
     * @return
     */
    public static ServiceException parse(ServiceException e) {
        return new ServiceException(e.getCode(), e.getMsg());
    }

}
