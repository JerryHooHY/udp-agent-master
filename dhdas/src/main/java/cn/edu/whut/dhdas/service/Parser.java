package cn.edu.whut.dhdas.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author huangaokai
 * @date 2024/10/24
 **/
@Component
public class Parser {
    public<T> String parser(ArrayList<T> list){
        //创建最外层的JSONObject
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("topic","edu.whut.cs.iot.se/dhdas/things/twin/commands/modify");
        // 创建headers对象，并添加thingId和policyId
        JSONObject headersObject = new JSONObject();
        headersObject.put("content-type", "application/merge-patch+json");
        headersObject.put("correlation-id", "090397");

        // 将headers对象加入到jsonObject
        jsonObject.put("headers", headersObject);

        // 添加path数据
        jsonObject.put("path", "/");

        // 创建value对象，并添加thingId和policyId
        JSONObject valueObject = new JSONObject();
        valueObject.put("thingId", "edu.whut.cs.iot.se:dhdas");
        valueObject.put("policyId", "ditto.default:policy");

        //创建features对象，用于存放所有波长信息
        JSONObject features = new JSONObject();
        for (int i = 0; i < list.size(); i++) {
            // 当前编号在responseData数组中的开始位置
            JSONObject dhdasProperties = new JSONObject();
            dhdasProperties.put("value", list.get(i));

            JSONObject dhdasObject = new JSONObject();
            dhdasObject.put("properties", dhdasProperties);

            // 将波长对象加入到features中
            features.put("channel" + i, dhdasObject);
        }
        valueObject.put("features", features);
        // 将value对象加入到jsonObject
        jsonObject.put("value", valueObject);

        return jsonObject.toJSONString();
    }
}
