package cn.gaohongtao.iw.common;

import cn.gaohongtao.iw.common.config.Config;
import cn.gaohongtao.iw.common.config.MongoConfig;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

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
    
    public static Document convertToDocument(Object o) {
        Document result = new Document();
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(o);
            } catch (IllegalAccessException e) {
                continue;
            }
            if (value == null) {
                continue;
            }
            result.append(field.getName(), value);
        }
        return result;
    }

}
