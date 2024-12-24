package edu.whut.cs.se.udpagent.parser;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;
@Service
public class AwarenessRecognitionParser implements ResponseParser{
    @Autowired
    private EntranceInfoParser entranceInfoParser;

    @Override
    public String parseResponse(byte[] responseData) {
        return null;
    }
    public String getSafetyStatus(){
        Double[] safetyStatusList = {1.0,2.0,3.0,4.0};
        // 创建随机数生成器
//        Random random = new Random();

        JSONObject safetyStatusProperties = new JSONObject();
//        safetyStatusProperties.put("value", safetyStatusList[random.nextInt(safetyStatusList.length)]);
        safetyStatusProperties.put("value", (double)entranceInfoParser.getOutside());
        JSONObject safetyStatus = new JSONObject();
        safetyStatus.put("properties",safetyStatusProperties);
        // 将属性包装在"features"中
        JSONObject features = new JSONObject();
        features.put("safety_status", safetyStatus);


        // 创建headers JSONObject并添加内容
        JSONObject headers = new JSONObject();
        headers.put("content-type", "application/merge-patch+json");
        headers.put("correlation-id", "090397");

        // 创建value JSONObject并添加内容
        JSONObject value = new JSONObject();
        value.put("thingId", "edu.whut.cs.iot.se:outside");
        value.put("policyId", "ditto.default:policy");
        value.put("features", features);

        // 创建顶层的JSONObject
        JSONObject json = new JSONObject();
        json.put("topic", "edu.whut.cs.iot.se/outside/things/twin/commands/modify");
        json.put("headers", headers);
        json.put("path", "/");
        json.put("value", value);
        return json.toJSONString();
    }
    public String getConstructionDegree(){
        // 创建随机数生成器
//        Random random = new Random();

        JSONObject constructionDegreeProperties = new JSONObject();
        constructionDegreeProperties.put("value", entranceInfoParser.getStructure());
        JSONObject constructionDegree = new JSONObject();
        constructionDegree.put("properties",constructionDegreeProperties);
        // 将属性包装在"features"中
        JSONObject features = new JSONObject();
        features.put("damage_percent", constructionDegree);


        // 创建headers JSONObject并添加内容
        JSONObject headers = new JSONObject();
        headers.put("content-type", "application/merge-patch+json");
        headers.put("correlation-id", "090397");

        // 创建value JSONObject并添加内容
        JSONObject value = new JSONObject();
        value.put("thingId", "edu.whut.cs.iot.se:construction");
        value.put("policyId", "ditto.default:policy");
        value.put("features", features);

        // 创建顶层的JSONObject
        JSONObject json = new JSONObject();
        json.put("topic", "edu.whut.cs.iot.se/construction/things/twin/commands/modify");
        json.put("headers", headers);
        json.put("path", "/");
        json.put("value", value);

        return json.toJSONString();
    }
    public String getDeviceDegree(){
        // 创建随机数生成器
//        Random random = new Random();

        JSONObject deviceDegreeProperties = new JSONObject();
        deviceDegreeProperties.put("value", entranceInfoParser.getDevice());
        JSONObject deviceDegree = new JSONObject();
        deviceDegree.put("properties",deviceDegreeProperties);
        // 将属性包装在"features"中
        JSONObject features = new JSONObject();
        features.put("damage_percent", deviceDegree);


        // 创建headers JSONObject并添加内容
        JSONObject headers = new JSONObject();
        headers.put("content-type", "application/merge-patch+json");
        headers.put("correlation-id", "090397");

        // 创建value JSONObject并添加内容
        JSONObject value = new JSONObject();
        value.put("thingId", "edu.whut.cs.iot.se:device");
        value.put("policyId", "ditto.default:policy");
        value.put("features", features);

        // 创建顶层的JSONObject
        JSONObject json = new JSONObject();
        json.put("topic", "edu.whut.cs.iot.se/device/things/twin/commands/modify");
        json.put("headers", headers);
        json.put("path", "/");
        json.put("value", value);

        return json.toJSONString();

    }


}
