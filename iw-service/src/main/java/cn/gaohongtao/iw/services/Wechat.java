package cn.gaohongtao.iw.services;


import cn.gaohongtao.iw.common.Hash;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 加密/校验流程如下：
 * 1. 将token、timestamp、nonce三个参数进行字典序排序
 * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
 * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
 * <p/>
 * Created by gaoht on 15/6/25.
 */
@Path("wechat")
public class Wechat {

    private static final Logger log = LoggerFactory.getLogger(Wechat.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String checkSignature(@QueryParam("signature") String signature, @QueryParam("timestamp") String timestamp
            , @QueryParam("nonce") String nonce, @QueryParam("echostr") String echostr) {
        List<String> array = new ArrayList<>();
        array.add(PkgConst.TOKEN);
        array.add(timestamp);
        array.add(nonce);
        Collections.sort(array);
        String arrayStr = Joiner.on("").join(array);
        String sha1Str = Hash.sha1(arrayStr);
        if (signature.equals(sha1Str)) {
            return echostr;
        } else {
            log.error("signature check fail, array:{}, input:{} sha1:{}", arrayStr, signature, sha1Str);
            return "";
        }
    }


}
