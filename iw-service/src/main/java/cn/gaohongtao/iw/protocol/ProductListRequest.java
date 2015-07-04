package cn.gaohongtao.iw.protocol;

import java.util.List;
import java.util.Map;

/**
 * Item Request
 *
 * Created by gaoht on 15/7/2.
 */
public class ProductListRequest {

    private String userId ;

    private String strategy;

    private Map<String,List<String>> condition;

    private int pageSize;

    private String lastSymbol;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public Map<String, List<String>> getCondition() {
        return condition;
    }

    public void setCondition(Map<String, List<String>> condition) {
        this.condition = condition;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getLastSymbol() {
        return lastSymbol;
    }

    public void setLastSymbol(String lastSymbol) {
        this.lastSymbol = lastSymbol;
    }

    @Override
    public String toString() {
        return "ItemRequest{" +
                "userId='" + userId + '\'' +
                ", strategy='" + strategy + '\'' +
                ", condition=" + condition +
                ", pageSize=" + pageSize +
                ", lastSymbol='" + lastSymbol + '\'' +
                '}';
    }
}
