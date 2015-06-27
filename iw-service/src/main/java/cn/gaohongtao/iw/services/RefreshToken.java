package cn.gaohongtao.iw.services;

import cn.gaohongtao.iw.common.MemoryCache;
import cn.gaohongtao.iw.common.protocol.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * internal service
 * Created by gaoht on 15/6/27.
 */

@Path("refresh_token")
public class RefreshToken {

    private static final Logger log = LoggerFactory.getLogger(RefreshToken.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Error token(@QueryParam("token") String token) {
        log.info("refresh token {}", token);
        MemoryCache.keep().put("access_token", token);
        Error error = new Error();
        error.setErrcode("0");
        return error;
    }
}
