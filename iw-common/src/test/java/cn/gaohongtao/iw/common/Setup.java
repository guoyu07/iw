package cn.gaohongtao.iw.common;

import org.junit.Before;

/**
 * Created by gaoht on 15/6/27.
 */
public class Setup {
    @Before
    public void setup() {
        String path = this.getClass().getResource("/config.json").getPath();
        System.setProperty("configFile", path);
    }
}
