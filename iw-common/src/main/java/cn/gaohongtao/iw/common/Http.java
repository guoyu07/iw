package cn.gaohongtao.iw.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Http util
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
        log.info("get :{}", this.webTarget.getUri().toString());
        Invocation.Builder builder = this.webTarget.request(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        try {
            // read from file, convert it to user class
            return mapper.readValue(builder.get(String.class), responseClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public <REQUEST, RESPONSE> RESPONSE post(REQUEST request, Class<RESPONSE> responseClass) {
        log.info("post :{}", this.webTarget.getUri().toString());
        Invocation.Builder builder = this.webTarget.request();
        try {
            // read from file, convert it to user class
            return builder.post(Entity.entity(request, MediaType.APPLICATION_XML), responseClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
