package cn.gaohongtao.iw.common;

import cn.gaohongtao.iw.common.config.Config;
import cn.gaohongtao.iw.common.config.MongoConfig;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Mongo util
 *
 * Created by gaoht on 15/6/27.
 */
public class MongoUtil {

    private static MongoClient client;


    public static MongoDatabase getDatabase(String db) {
        if (client == null) {
            init();
        }
        return client.getDatabase(db);
    }

    private static synchronized void init() {

        final MongoConfig mongoConfig = Config.getConfig().getMongoConfig();
        client = new MongoClient(new ArrayList<ServerAddress>() {
            {
                List<String> list = mongoConfig.getSeeds();

                for (int i = 0; i < list.size(); i = i + 2) {
                    this.add(new ServerAddress(list.get(i), (Integer.valueOf(list.get(i + 1))).intValue()));
                }
            }
        }, mongoConfig.build());
    }

}
