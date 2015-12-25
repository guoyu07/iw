package cn.gaohongtao.iw.common;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import cn.gaohongtao.iw.common.protocol.AccessTokenProtocol;
import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取token.
 *
 * @author gaohongtao
 */
public class AccessToken {
    
    private static final Cache<String, String> cache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build();
    
    private static final Logger log = LoggerFactory.getLogger(AccessToken.class);
    
    public static String get() throws ExecutionException {
        return cache.get("token", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        log.info("start fresh token");
                        AccessTokenProtocol t = new Https().path("https://api.weixin.qq.com/cgi-bin/token")
                                .param("grant_type", "client_credential")
                                .param("appid", Constant.APP_ID)
                                .param("secret", Constant.APP_SECRET)
                                .get(AccessTokenProtocol.class);
                        if (Strings.isNullOrEmpty(t.getErrcode())) {
                            log.info("{}", t);
                            return t.getAccess_token();
                        } else {
                            log.error("{}", t);
                            throw new RuntimeException("weixin interface error " + t);
                        }
                    }
                }
        );
    }
}
