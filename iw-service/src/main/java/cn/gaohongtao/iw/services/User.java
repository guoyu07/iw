package cn.gaohongtao.iw.services;

import cn.gaohongtao.iw.ServiceException;
import cn.gaohongtao.iw.common.MongoUtil;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.Date;

import static com.mongodb.client.model.Filters.*;

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
            , @QueryParam("packetParam") String fromUserId) throws ServiceException {
        log.debug("sendPacket userId:{} fromUserId: {}", userId, fromUserId);

        MongoCollection<Document> col = MongoUtil.getDatabase("basic").getCollection("user");
        Document fromUser = col.find(eq("_id", fromUserId))
                .first();
        if (fromUser == null) {
            log.error("大使唯一编码不正确");
            throw new ServiceException("大使唯一编码不正确");
        }


        Document user = col.find(eq("_id", userId))
                .first();

        if (user == null) {
            Document packet = createNewPacket(fromUserId);
            log.debug("user {} send packet {} to new user {}", fromUserId, packet, userId);
            col.insertOne(new Document().append("_id", userId).append("packet", packet));
            return new Document().append("packetId", packet.getString("packetId"));
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
                Document packet = createNewPacket(fromUserId);
                log.debug("user {} send packet {} to user {}", fromUserId, packet, userId);
                col.updateOne(eq("_id", userId), new Document().append("$set", new Document("packet", packet)));
                return new Document().append("packetId", packet.getString("packetId"));

            }
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

    private Document createNewPacket(String fromUserId) {
        String packetId = "PAK" + new ObjectId().toString();
        Date now = new Date();
        return new Document()
                .append("packetId", packetId).append("from", fromUserId)
                .append("create_date", now).append("state", "new").append("state_date", now);
    }
}
