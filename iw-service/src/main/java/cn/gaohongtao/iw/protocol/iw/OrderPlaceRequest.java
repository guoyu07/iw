package cn.gaohongtao.iw.protocol.iw;

import com.google.common.base.Objects;
import java.util.Map;

/**
 * @author gaohongtao
 */
public class OrderPlaceRequest {
    
    private String userId;
    
    private Map<String, String> item;
    
    private Map<String, String> addressInfo;
    
    private String express;
    
    public Map<String, String> getAddressInfo() {
        return addressInfo;
    }
    
    public void setAddressInfo(final Map<String, String> addressInfo) {
        this.addressInfo = addressInfo;
    }
    
    public String getExpress() {
        return express;
    }
    
    public void setExpress(final String express) {
        this.express = express;
    }
    
    public Map<String, String> getItem() {
        return item;
    }
    
    public void setItem(final Map<String, String> item) {
        this.item = item;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(final String userId) {
        this.userId = userId;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderPlaceRequest that = (OrderPlaceRequest) o;
        return Objects.equal(userId, that.userId) &&
                Objects.equal(item, that.item) &&
                Objects.equal(addressInfo, that.addressInfo) &&
                Objects.equal(express, that.express);
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(userId, item, addressInfo, express);
    }
    
    @Override
    public String toString() {
        return "OrderPlaceRequest{" +
                "addressInfo=" + addressInfo +
                ", userId='" + userId + '\'' +
                ", item=" + item +
                ", express='" + express + '\'' +
                '}';
    }
}
