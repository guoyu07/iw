package cn.gaohongtao.iw.services;

import cn.gaohongtao.iw.common.MongoUtil;
import cn.gaohongtao.iw.protocol.iw.ProductListRequest;
import cn.gaohongtao.iw.protocol.iw.ProductListResponse;
import com.google.common.base.Strings;
import com.mongodb.client.MongoCollection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.lt;

/**
 * get user info
 * Created by gaoht on 15/6/27.
 */
@Path("product")
public class Product {
    private static final Logger log = LoggerFactory.getLogger(Product.class);
    
    private String _corsHeaders;

    @POST
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response list(ProductListRequest request) throws ParseException {
        log.debug("Item list request: {}", request);

        List<Bson> conditionFilters = new ArrayList<>();

        if (request.getCondition() != null)
            for (String key : request.getCondition().keySet()) {
                conditionFilters.add(in(key, request.getCondition().get(key)));
            }

        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        if (!Strings.isNullOrEmpty(request.getLastSymbol()))
            conditionFilters.add(lt("onsale_date", format.parse(request.getLastSymbol())));

        conditionFilters.add(eq("strategy" ,request.getStrategy()));
        Bson filter;
        if(conditionFilters.size()>0){
            filter = and(conditionFilters);
        }else{
            filter = new BsonDocument();
        }
        MongoCollection<Document> collection = MongoUtil.getDatabase("product").getCollection("items");

        List<Document> result = new ArrayList<>();
        for (Document item : collection.find(filter)
                .projection(and(eq("name", 1), eq("price", 1), eq("discount", 1), eq("sales", 1), eq("images", 1)))
                .sort(eq("onsale_date", -1)).limit(request.getPageSize())) {
            item.append("id", item.getString("_id"));
            item.remove("_id");
            result.add(item);
        }
        ProductListResponse response = new ProductListResponse();

        if (request.getPageSize() == 0 || result.size() < request.getPageSize()) {
            response.setLastSymbol("LAST");
        } else {
            response.setLastSymbol(format.format(result.get(result.size() - 1).getDate("onsale_date")));
        }
        response.setItemList(result);
        log.debug("Item list response: {}", response);
        return makeCORS(Response.ok().entity(response));
    }

    @OPTIONS
    @Path("list")
    public Response corsList(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    private Response makeCORS(Response.ResponseBuilder req) {
        return makeCORS(req, _corsHeaders);
    }


    private Response makeCORS(Response.ResponseBuilder req, String returnMethod) {
        Response.ResponseBuilder rb = req.header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS");

        if (!"".equals(returnMethod)) {
            rb.header("Access-Control-Allow-Headers", returnMethod);
        }

        return rb.build();
    }


    @GET
    @Path("item")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Document item(@QueryParam("productId") String productId) {
        log.debug("get item id: {}", productId);

        return MongoUtil.getDatabase("product").getCollection("items").find(eq("_id", productId))
                .projection(and(eq("_id", 0), eq("images", 1), eq("name", 1), eq("price", 1)
                        , eq("discount", 1), eq("sizeList", 1), eq("itemStyle", 1), eq("desc", 1))).first();
    }

}