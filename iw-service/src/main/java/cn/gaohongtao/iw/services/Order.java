package cn.gaohongtao.iw.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import cn.gaohongtao.iw.ServiceException;
import cn.gaohongtao.iw.common.Constant;
import cn.gaohongtao.iw.common.Hash;
import cn.gaohongtao.iw.common.MongoUtil;
import cn.gaohongtao.iw.protocol.iw.OrderPlaceRequest;
import cn.gaohongtao.iw.protocol.iw.OrderPlaceResponse;
import cn.gaohongtao.iw.protocol.wechat.NotifyPayRequest;
import cn.gaohongtao.iw.protocol.wechat.NotifyPayResponse;
import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * 订单服务
 * <p/>
 * Created by gaoht on 15/7/21.
 */
@Path("order")
public class Order {

    private static final Logger log = LoggerFactory.getLogger(Order.class);

    @POST
    @Path("place")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized OrderPlaceResponse place(OrderPlaceRequest request, @Context HttpServletRequest req) throws ServiceException {
        log.debug("place order request: {}", request);
//        UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        String date = format.format(now);
        String orderId = date + Hash.randomString(6);
//        unifiedOrderRequest.setOut_trade_no(orderId);
//        unifiedOrderRequest.setNonce_str(Hash.randomString(32));
//        unifiedOrderRequest.setTotal_fee(Integer.valueOf(request.getItem().get("cost")));
//        unifiedOrderRequest.setSpbill_create_ip(req.getRemoteAddr());
//        unifiedOrderRequest.setOpenid(request.getUserId());
//
//        unifiedOrderRequest.setSign(Hash.signature(unifiedOrderRequest, Constant.PAY_KEY));
//        UnifiedOrderResponse unifiedOrderResponse;
//        try {
//            unifiedOrderResponse = new Https().path("https://api.mch.weixin.qq.com/pay/unifiedorder").post(unifiedOrderRequest, UnifiedOrderResponse.class);
//        } catch (Exception e) {
//            log.error("request https://api.mch.weixin.qq.com/pay/unifiedorder interface error", e);
//            throw new ServiceException(e.getMessage());
//        }
//
        OrderPlaceResponse response = new OrderPlaceResponse();
//        if (Constant.SUCCESS.equals(unifiedOrderResponse.getReturn_code())) {
//            if (Constant.SUCCESS.equals(unifiedOrderResponse.getResult_code())) {
//                response.setOrderId(orderId);
//                response.setPrepayId(unifiedOrderResponse.getPrepay_id());
//                response.setMessage(Constant.SUCCESS);
//            } else {
//                throw new ServiceException(String.format("err_code:%s err_msg:%s", unifiedOrderResponse.getErr_code(), unifiedOrderResponse.getErr_code_des()));
//            }
//        } else {
//            throw new ServiceException(unifiedOrderResponse.getReturn_msg());
//        }

        Document prodDoc = MongoUtil.getDatabase("product").getCollection("items").find(eq("_id", new ObjectId(request.getItem().get("itemId")))).first();
        if (prodDoc == null) {
            throw new ServiceException("不存在下单的商品");
        }
        request.getItem().put("name", prodDoc.getString("name"));
        request.getItem().put("color", ((List) prodDoc.get("color")).get(0).toString());


        Document orderDoc = MongoUtil.convertToDocument(request);
        orderDoc.append("_id", orderId);
//        orderDoc.append("prepayId", response.getPrepayId());
        orderDoc.append("prepayId", "-1");
        orderDoc.append("state", "0");
        orderDoc.append("order_date", now);
        orderDoc.append("pay_date", now);
        orderDoc.append("state_date", now);
        orderDoc.append("msg", "已经成功下单，等待支付结果");
        MongoUtil.getDatabase("order").getCollection("items").insertOne(orderDoc);

        MongoCollection<Document> col = MongoUtil.getDatabase("basic").getCollection("user");
        Document user = col.find(eq("_id", request.getUserId()))
                .first();
        if (user == null) {
            col.insertOne(new Document("_id", request.getUserId()).append("address", Lists.newArrayList(request.getAddressInfo())));
        } else {
            col.updateOne(new Document("_id", request.getUserId()), new Document("$addToSet", new Document("address", request.getAddressInfo())));
        }
        int sizeNumber = 0;
        for (Document productDoc : (List<Document>) prodDoc.get("sizeList")) {
            if (productDoc.getString("size").equals(request.getItem().get("size"))) {
                sizeNumber = Integer.valueOf(productDoc.getString("count"));
            }
        }
        if (sizeNumber < 1) {
            throw new ServiceException("该号码已经售完");
        }
        MongoUtil.getDatabase("product").getCollection("items").updateOne(new Document("_id", new ObjectId(request.getItem().get("itemId"))).append("sizeList.size", request.getItem().get("size")),
                new Document("$set", new Document("sizeList.$.count", --sizeNumber)));

        response.setOrderId(orderId);
        response.setPrepayId("-1");
        response.setMessage(Constant.SUCCESS);
        return response;
    }

    @POST
    @Path("pay_notify")
    @Produces(MediaType.APPLICATION_XML + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_XML)
    public NotifyPayResponse payNotify(NotifyPayRequest request) {
        log.debug("place order request: {}", request);
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = format.format(new Date());
        Document updateDoc = new Document("state", -1).append("state_date", date);
        if (Constant.SUCCESS.equals(request.getReturn_code())) {
            if (Constant.SUCCESS.equals(request.getResult_code())) {
                updateDoc.put("state", 1);
                updateDoc.append("msg", "支付成功").append("pay_date", request.getTime_end());
            } else {
                updateDoc.append("msg", request.getErr_code() + ":" + request.getErr_code_des());
            }
        } else {
            updateDoc.append("msg", request.getReturn_msg());
        }
        NotifyPayResponse response = new NotifyPayResponse();
        response.setReturn_code(Constant.SUCCESS);
        response.setReturn_msg("OK");
        try {
            MongoUtil.getDatabase("order").getCollection("items").updateOne(Filters.eq("_id", request.getOut_trade_no()), updateDoc);
        } catch (final Exception e) {
            response.setReturn_code("FAIL");
            response.setReturn_msg(e.getMessage());
        }
        return response;
    }

    @GET
    @Path("signature")
    @Consumes(MediaType.APPLICATION_JSON)
    public String signature(Map<String, Object> request) throws ServiceException {
        return Hash.signatureFromMap(request, Constant.PAY_KEY);
    }

    @GET
    @Path("change_state")
    public String changeState(@QueryParam("orderId") String orderId, @QueryParam("state") int state) throws ServiceException {
        UpdateResult result = MongoUtil.getDatabase("order").getCollection("items").updateOne(Filters.eq("orderId", orderId), new Document("state", state).append("state_date", new Date()));
        if (result.getMatchedCount() < 1) {
            throw new ServiceException("service can not find orderId:" + orderId);
        }
        if (result.getModifiedCount() < 1) {
            throw new ServiceException("service dose not modify orderId:" + orderId);
        }

        return Constant.SUCCESS;
    }
}
