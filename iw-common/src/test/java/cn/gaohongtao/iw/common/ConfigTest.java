package cn.gaohongtao.iw.common;

import cn.gaohongtao.iw.common.config.Config;
import cn.gaohongtao.iw.common.config.MongoConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;

/**
 * ConfigTest
 * Created by gaoht on 15/6/27.
 */
public class ConfigTest extends Setup {

    @Test
    public void testConfig() {
        Assert.assertEquals(1, Config.getConfig().getServiceList().size());
        MongoUtil.getDatabase("test");
    }
}
