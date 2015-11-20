package cn.gaohongtao.iw.protocol.iw;

import java.util.List;
import org.bson.Document;

/**
 * Product list response
 * <p/>
 * Created by gaoht on 15/7/2.
 */
public class ProductListResponse {
    private String lastSymbol;

    private List<Document> itemList;
    
    public ProductListResponse() {
    }

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
