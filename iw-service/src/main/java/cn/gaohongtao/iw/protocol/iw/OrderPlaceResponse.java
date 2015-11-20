package cn.gaohongtao.iw.protocol.iw;

/**
 * @author gaohongtao
 */
public class OrderPlaceResponse {
    
    private String orderId;
    
    private String prepayId;
    
    private String message;
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(final String orderId) {
        this.orderId = orderId;
    }
    
    public String getPrepayId() {
        return prepayId;
    }
    
    public void setPrepayId(final String prepayId) {
        this.prepayId = prepayId;
    }
}
