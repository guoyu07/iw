package cn.gaohongtao.iw.common;

import cn.gaohongtao.iw.common.protocol.AccessToken;
import junit.framework.Assert;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * HttpsTest
 * Created by gaoht on 15/6/27.
 */
public class HttpsTest {


    public static final Logger log = LoggerFactory.getLogger(HttpsTest.class);

    @Test
    public void testAccessToken() {
        AccessToken t = new Https().path("https://api.weixin.qq.com/cgi-bin/token")
                .param("grant_type", "client_credential")
                .param("appid", Constant.APP_ID)
                .param("secret", Constant.APP_SECRET)
                .get(AccessToken.class);
        Assert.assertNotNull(t);
        Assert.assertTrue("code:" + t.getErrcode() + " msg:" + t.getErrmsg(), t.getErrcode() == null);

        log.info("token:{} expried:{}", t.getAccess_token(), t.getExpires_in());
    }

    @Test
    public void testSso(){

    }
}
