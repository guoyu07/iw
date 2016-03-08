package cn.gaohongtao.iw.services;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import cn.gaohongtao.iw.ServiceException;
import cn.gaohongtao.iw.common.Constant;
import cn.gaohongtao.iw.common.Https;
import cn.gaohongtao.iw.common.MongoUtil;
import cn.gaohongtao.iw.common.protocol.OpenidProtocol;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mongodb.client.model.Filters.eq;

/**
 * Hook of get openid
 * <p/>
 * Created by gaoht on 15/6/29.
 */
@Path("user_from_code")
public class UserFromCode {
    private static final Logger log = LoggerFactory.getLogger(UserFromCode.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Document get(@QueryParam("code") String code) throws ServiceException {
        log.debug("code {}", code);
        OpenidProtocol p;
        try {
            p = new Https().path("https://api.weixin.qq.com/sns/oauth2/access_token")
                    .param("appid", Constant.APP_ID).param("secret", Constant.APP_SECRET)
                    .param("code", code).param("grant_type", "authorization_code")
                    .get(OpenidProtocol.class);
        } catch (Exception e) {
            log.error("request wechat oauth2 interface exception", e);
            throw new ServiceException(e.getMessage());
        }

        log.debug("{}", p);
        if (p.getErrcode() == null) {
            List<Document> archList = new ArrayList<>();
            for (Document document : MongoUtil.getDatabase("basic").getCollection("page_architecture").find()) {
                archList.add(document);
            }
    
            Document userDoc = new Document().append("userId", p.getOpenid());
            Document user = MongoUtil.getDatabase("basic").getCollection("user").find(eq("_id", p.getOpenid()))
                    .first();
            if (null != user && user.containsKey("ambCode")) {
                userDoc.append("ambCode", user.getString("ambCode"));
            }
            return new Document().append("user", userDoc).append("architecture", archList);
        } else {
            log.error("request wechat oauth2 interface error {}", p);
            return new Document().append("errcode", p.getErrcode())
                    .append("errmsg", "request wechat oauth2 interface error:" + p.getErrmsg());
        }
    }
}
