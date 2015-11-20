package cn.gaohongtao.iw.protocol.wechat;

import cn.gaohongtao.iw.common.Constant;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 统一下单请求接口
 * 
 * Created by gaoht on 15/7/21.
 */
@XmlRootElement(name = "xml")
public class UnifiedOrderRequest {
    
    @XmlElement(required = true)
    private String appid = Constant.APP_ID;
    
    @XmlElement(required = true)
    private String mch_id = Constant.MCH_ID;

    private String device_info = "WEB";
    
    @XmlElement(required = true)
    private String nonce_str;
    
    @XmlElement(required = true)
    private String sign;
    
    @XmlElement(required = true)
    private String body = "iw";

    private String detail;

    private String attach;
    
    @XmlElement(required = true)
    private String out_trade_no;
    
    private String fee_type;
    
    @XmlElement(required = true)
    private Integer total_fee;
    
    @XmlElement(required = true)
    private String spbill_create_ip;

    private String time_start;

    private String time_expire;
    
    private String goods_tag;
    
    @XmlElement(required = true)
    private String notify_url = "http://www.iwshoes.cn/service/iw/order/pay_notify";
    
    @XmlElement(required = true)
    private String trade_type = "JSAPI";
    
    private String product_id;
    
    private String limit_pay;
    
    @XmlElement(required = true)
    private String openid;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }
    
    public Integer getTotal_fee() {
        return total_fee;
    }
    
    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
