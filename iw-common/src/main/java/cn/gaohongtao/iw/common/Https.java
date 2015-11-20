package cn.gaohongtao.iw.common;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信https调用
 * Created by gaoht on 15/6/27.
 */
public class Https extends Http {

    private static final Logger log = LoggerFactory.getLogger(Https.class);

    public Https() {
        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, null);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    
        client = ClientBuilder.newBuilder().register(JacksonFeature.class).sslContext(sslContext).build();
    }
}
