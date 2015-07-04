package cn.gaohongtao.iw;

import com.google.common.base.Charsets;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.net.URLEncoder;

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
