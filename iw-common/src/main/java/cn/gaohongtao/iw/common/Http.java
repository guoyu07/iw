package cn.gaohongtao.iw.common;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by gaoht on 15/6/27.
 */
public class Http {
    private static final Logger log = LoggerFactory.getLogger(Http.class);
    protected Client client;
    protected WebTarget webTarget;

    public Http() {

        client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
    }

    public Http path(String path) {
        this.webTarget = this.client.target(path);
        return this;
    }

    public Http param(String key, String value) {
        this.webTarget = this.webTarget.queryParam(key, value);
        return this;
    }

    public <RESPONSE> RESPONSE get(Class<RESPONSE> responseClass) {
        log.info("get :{}",this.webTarget.getUri().toString());
        Invocation.Builder builder = this.webTarget.request(MediaType.APPLICATION_JSON);
        try {
            return builder.get(responseClass);
        } catch (WebApplicationException e) {
            Response r = builder.get();
            log.error("request error code:{} response:{}", r.getStatus(), r.getEntity());
            throw e;
        }

    }
}
