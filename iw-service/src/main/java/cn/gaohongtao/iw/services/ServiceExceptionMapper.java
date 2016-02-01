package cn.gaohongtao.iw.services;

import cn.gaohongtao.iw.ServiceException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Service Exception Mapper
 *
 * Created by gaoht on 15/7/4.
 */
@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {

    public Response toResponse(ServiceException ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"errcode\":" + ex.getErrCode() + ",\"errmsg\":\"" + ex.getErrMsg() + "\"}")
                .type(MediaType.APPLICATION_JSON).build();
    }

}
