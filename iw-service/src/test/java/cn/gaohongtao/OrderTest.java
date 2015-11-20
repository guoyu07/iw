package cn.gaohongtao;

import cn.gaohongtao.iw.common.Hash;
import cn.gaohongtao.iw.protocol.wechat.UnifiedOrderRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test Order
 * Created by gaoht on 15/7/21.
 */
public class OrderTest {
    
    @Test
    public void testSignature() throws IllegalAccessException {
        UnifiedOrderRequest request = new UnifiedOrderRequest();
        request.setAppid("wxd930ea5d5a258f4f");
        request.setMch_id("10000100");
        request.setDevice_info("1000");
        request.setBody("test");
        request.setNonce_str("ibuaiVcKdpRxkhJA");
        request.setNotify_url(null);
        request.setTrade_type(null);
        request.setTotal_fee(null);
        assertEquals("9A0A8659F005D6984697E2CA0A9CF3B7", Hash.signature(request, "192006250b4c09247ec02edce69f6a2d"));
    }
}
