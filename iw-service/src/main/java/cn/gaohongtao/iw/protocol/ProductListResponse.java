package cn.gaohongtao.iw.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.Document;

import java.util.List;

/**
 * Product list response
 * <p/>
 * Created by gaoht on 15/7/2.
 */
public class ProductListResponse {
   public ProductListResponse(){}


    private String lastSymbol;

    private List<Document> itemList;

    public String getLastSymbol() {
        return lastSymbol;
    }

    public void setLastSymbol(String lastSymbol) {
        this.lastSymbol = lastSymbol;
    }

    public List<Document> getItemList() {
        return itemList;
    }

    public void setItemList(List<Document> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "ProductListResponse{" +
                "lastSymbol='" + lastSymbol + '\'' +
                ", itemList=" + itemList +
                '}';
    }
}
