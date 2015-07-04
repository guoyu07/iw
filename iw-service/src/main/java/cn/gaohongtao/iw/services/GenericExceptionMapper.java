package cn.gaohongtao.iw.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * GenericExceptionMapper
 * Created by gaoht on 15/7/4.
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger log = LoggerFactory.getLogger(GenericExceptionMapper.class);

    @Override
    public Response toResponse(Throwable ex) {
        log.error("error", ex);
        return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{errcode:500,errmsg:\"" + ex + "\"}")
                .type(MediaType.APPLICATION_JSON).build();
    }
}
