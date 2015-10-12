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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductListRequest)) return false;

        ProductListRequest that = (ProductListRequest) o;

        if (pageSize != that.pageSize) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (strategy != null ? !strategy.equals(that.strategy) : that.strategy != null) return false;
        if (condition != null ? !condition.equals(that.condition) : that.condition != null) return false;
        return !(lastSymbol != null ? !lastSymbol.equals(that.lastSymbol) : that.lastSymbol != null);

    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (strategy != null ? strategy.hashCode() : 0);
        result = 31 * result + (condition != null ? condition.hashCode() : 0);
        result = 31 * result + pageSize;
        result = 31 * result + (lastSymbol != null ? lastSymbol.hashCode() : 0);
        return result;
    }
}
