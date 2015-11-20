package cn.gaohongtao.iw.protocol.wechat;

/**
 * @author gaohongtao
 */
public class UnifiedOrderResponse {
    
    private String return_code;
    
    private String return_msg;
    
    private String appid;
    
    private String mch_id;
    
    private String device_info;
    
    private String nonce_str;
    
    private String sign;
    
    private String result_code;
    
    private String err_code;
    
    private String err_code_des;
    
    private String trade_type;
    
    private String prepay_id;
    
    private String code_url;
    
    public String getAppid() {
        return appid;
    }
    
    public void setAppid(final String appid) {
        this.appid = appid;
    }
    
    public String getCode_url() {
        return code_url;
    }
    
    public void setCode_url(final String code_url) {
        this.code_url = code_url;
    }
    
    public String getDevice_info() {
        return device_info;
    }
    
    public void setDevice_info(final String device_info) {
        this.device_info = device_info;
    }
    
    public String getErr_code() {
        return err_code;
    }
    
    public void setErr_code(final String err_code) {
        this.err_code = err_code;
    }
    
    public String getErr_code_des() {
        return err_code_des;
    }
    
    public void setErr_code_des(final String err_code_des) {
        this.err_code_des = err_code_des;
    }
    
    public String getMch_id() {
        return mch_id;
    }
    
    public void setMch_id(final String mch_id) {
        this.mch_id = mch_id;
    }
    
    public String getNonce_str() {
        return nonce_str;
    }
    
    public void setNonce_str(final String nonce_str) {
        this.nonce_str = nonce_str;
    }
    
    public String getPrepay_id() {
        return prepay_id;
    }
    
    public void setPrepay_id(final String prepay_id) {
        this.prepay_id = prepay_id;
    }
    
    public String getResult_code() {
        return result_code;
    }
    
    public void setResult_code(final String result_code) {
        this.result_code = result_code;
    }
    
    public String getReturn_code() {
        return return_code;
    }
    
    public void setReturn_code(final String return_code) {
        this.return_code = return_code;
    }
    
    public String getReturn_msg() {
        return return_msg;
    }
    
    public void setReturn_msg(final String return_msg) {
        this.return_msg = return_msg;
    }
    
    public String getSign() {
        return sign;
    }
    
    public void setSign(final String sign) {
        this.sign = sign;
    }
    
    public String getTrade_type() {
        return trade_type;
    }
    
    public void setTrade_type(final String trade_type) {
        this.trade_type = trade_type;
    }
    
    @Override
    public String toString() {
        return "UnifiedOrderResponse{" +
                "appid='" + appid + '\'' +
                ", return_code='" + return_code + '\'' +
                ", return_msg='" + return_msg + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", device_info='" + device_info + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", sign='" + sign + '\'' +
                ", result_code='" + result_code + '\'' +
                ", err_code='" + err_code + '\'' +
                ", err_code_des='" + err_code_des + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", prepay_id='" + prepay_id + '\'' +
                ", code_url='" + code_url + '\'' +
                '}';
    }
}
