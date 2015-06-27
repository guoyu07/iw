package cn.gaohongtao.iw.common.protocol;

import com.google.common.base.Strings;

/**
 * Access Token
 * Created by gaoht on 15/6/27.
 */
public class AccessToken extends Error {
    private String access_token;

    private String expires_in;


    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        if (Strings.isNullOrEmpty(super.getErrcode())) {
            return "AccessToken{" +
                    "access_token='" + access_token + '\'' +
                    ", expires_in='" + expires_in + '\'' +
                    '}';
        } else {
            return super.toString();
        }

    }
}
