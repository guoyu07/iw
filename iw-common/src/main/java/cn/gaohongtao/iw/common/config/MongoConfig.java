package cn.gaohongtao.iw.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.mongodb.MongoClientOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * MongoConfig
 * <p/>
 * Created by gaoht on 15/6/27.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class MongoConfig extends MongoClientOptions.Builder {

    private List<String> seeds = new ArrayList<>();

    public List<String> getSeeds() {
        return seeds;
    }
}
