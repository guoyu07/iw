package cn.gaohongtao.iw.services;

import java.util.ArrayList;
import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import cn.gaohongtao.iw.ServiceException;
import cn.gaohongtao.iw.common.MongoUtil;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * User
 * Created by gaoht on 15/7/4.
 */
@Path("user")
public class User {

    private static final Logger log = LoggerFactory.getLogger(User.class);

    @GET
    @Path("addr")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Object addr(@QueryParam("userId") String userId) {
        log.debug("get user's address userId: {}", userId);
        Document doc = MongoUtil.getDatabase("basic").getCollection("user").find(eq("_id", userId))
                .projection(and(eq("_id", 0), eq("address", 1))).first();
        if (doc == null) {
            return new ArrayList<>(0);
        } else {
            return doc.get("address");
        }
    }

    @GET
    @Path("sendPacket")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Object sendPacket(@QueryParam("userId") String userId
            , @QueryParam("packetParam") String ambCode) throws ServiceException {
        log.debug("sendPacket userId:{} ambCode: {}", userId, ambCode);
    
        MongoCollection<Document> col = MongoUtil.getDatabase("basic").getCollection("user");
        Document fromUser = col.find(eq("ambCode", ambCode))
                .first();
        if (fromUser == null) {
            log.error("大使唯一编码不正确");
            throw new ServiceException("大使唯一编码不正确");
        }
    
        String fromUserId = fromUser.getString("_id");
        Document user = col.find(eq("_id", userId))
                .first();

        if (user == null) {
            Document packet = createNewPacket(ambCode, fromUserId);
            log.debug("user {} send packet {} to new user {}", fromUserId, packet, userId);
            col.insertOne(new Document().append("_id", userId).append("packet", packet));
            return new Document().append("packetId", ambCode);
        } else {
            if (user.containsKey("packet")) {

                Document packet = (Document) user.get("packet");
                if ("new".equals(packet.getString("state"))) {
                    log.debug("user {} get new packet {} ", userId, packet.getString("packetId"));
                    return new Document().append("packetId", packet.getString("packetId"));
                } else {
                    log.debug("user {} have not packet to use ");
                    return new Document().append("packetId", "");
                }

            } else {
                Document packet = createNewPacket(ambCode, fromUserId);
                log.debug("user {} send packet {} to user {}", fromUserId, packet, userId);
                col.updateOne(eq("_id", userId), new Document().append("$set", new Document("packet", packet)));
                return new Document().append("packetId", packet.getString("packetId"));

            }
        }

    }
    
    @GET
    @Path("packetParam")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Object packetParam(@QueryParam("userId") String userId) throws ServiceException {
        log.debug("get packetParam info userId:{}", userId);
        
        MongoCollection<Document> col = MongoUtil.getDatabase("basic").getCollection("user");
        Document user = col.find(eq("_id", userId))
                .first();
        if (user == null) {
            throw new ServiceException("没有用户信息");
        }
        
        if (user.containsKey("ambCode")) {
            return String.format("{\"packetParam\":\"%s\"}", user.get("ambCode"));
        } else {
            throw new ServiceException("该用户不是大使");
        }
    }

    @GET
    @Path("score")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Object score(@QueryParam("userId") String userId) {
        log.debug("get score info userId:{}", userId);

        MongoCollection<Document> col = MongoUtil.getDatabase("basic").getCollection("user");
        Document user = col.find(eq("_id", userId))
                .first();
        if (user == null) {
            return new Document().append("score", "0").append("level", "无").append("amout", "0");
        }

        if (user.containsKey("score")) {
            return user.get("score");
        } else {
            return new Document().append("score", "0").append("level", "无").append("amout", "0");
        }
    }
    
    private Document createNewPacket(String ambCode, String userId) {
        Date now = new Date();
        return new Document()
                .append("packetId", ambCode).append("from", userId)
                .append("create_date", now).append("state", "new").append("state_date", now);
    }
}
