package cn.gaohongtao.iw;

/**
 * tt
 * Created by gaoht on 15/6/29.
 */
public class ServiceException extends Exception {

    private int errCode;
    private String errMsg;

    public ServiceException(String message) {
        this(500, message);
    }

    public ServiceException(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
