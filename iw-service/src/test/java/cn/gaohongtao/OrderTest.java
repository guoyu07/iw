package cn.gaohongtao;

import cn.gaohongtao.iw.common.Https;
import cn.gaohongtao.iw.protocol.PreorderRequest;
import org.junit.Test;

/**
 * Test Order
 * Created by gaoht on 15/7/21.
 */
public class OrderTest {

    @Test
    public void testOrder(){
        PreorderRequest request = new PreorderRequest();
       System.out.println(new Https().path("").post(request,String.class));
    }
}
