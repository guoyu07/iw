package cn.gaohongtao.iw.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Config
 * <p/>
 * Created by gaoht on 15/6/27.
 */
public class Config {


    private static Config config;

    public synchronized static void init() {
        String configFile = System.getProperty("configFile");
        ObjectMapper mapper = new ObjectMapper();
        try {
            // read from file, convert it to user class
            config = mapper.readValue(new File(configFile), Config.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static Config getConfig(){
        if(config==null){
            init();
        }
        return config;
    }

    private List<String> serviceList = new ArrayList<String>();

    private MongoConfig mongoConfig = new MongoConfig();

    public List<String> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<String> serviceList) {
        this.serviceList = serviceList;
    }

    public MongoConfig getMongoConfig() {
        return mongoConfig;
    }

    public void setMongoConfig(MongoConfig mongoConfig) {
        this.mongoConfig = mongoConfig;
    }
}
