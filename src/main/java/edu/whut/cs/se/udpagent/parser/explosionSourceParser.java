package edu.whut.cs.se.udpagent.parser;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.math.BigDecimal;
import java.util.Random;

import com.mathworks.toolbox.javabuilder.*;
import coordinate.Coordinate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class explosionSourceParser implements ResponseParser{
    @Value("${fiberOptic.dataPath}")
    private String sensorDataPath;
    private void putCoordinate(JSONObject features, String sensorDataPath) {
        // 创建随机数生成器
//        Random random = new Random();

        try {
            Coordinate coordinate = new Coordinate();
//        MWCharArray input = new MWCharArray(sensorDataPath);
            Object[] pso = coordinate.PSO(1, sensorDataPath);
            MWArray coor = (MWCharArray)pso[0];

            JSONObject coorObj = JSON.parseObject(coor.toString());
//            features.putAll(JSON.parseObject(coor.toString()));

            JSONObject propertiesXCoordinate = new JSONObject();
//            propertiesXCoordinate.put("value", ((BigDecimal)coorObj.get("x_coordinate")).doubleValue() + (-0.5 + random.nextDouble()));
            propertiesXCoordinate.put("value", ((BigDecimal)coorObj.get("x_coordinate")).doubleValue());
            JSONObject xCoordinate = new JSONObject();
            xCoordinate.put("properties", propertiesXCoordinate);

            JSONObject propertiesYCoordinate = new JSONObject();
//            propertiesYCoordinate.put("value", ((BigDecimal)coorObj.get("y_coordinate")).doubleValue() + (-0.5 + random.nextDouble()));
            propertiesYCoordinate.put("value", ((BigDecimal)coorObj.get("y_coordinate")).doubleValue());
            JSONObject yCoordinate = new JSONObject();
            yCoordinate.put("properties", propertiesYCoordinate);

            features.put("x_coordinate", xCoordinate);
            features.put("y_coordinate", yCoordinate);
        } catch (MWException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

    public String getRandomPosition() {
        // 定义 "type" 和 "strike_level" 的可能值
        String[] typeValues = {"核爆", "温压弹", "常规弹药打击", "无打击"};
        Double[] strikeLevelValues = {1.0,2.0,3.0,4.0};

        // 创建随机数生成器
        Random random = new Random();

        // 创建各属性的JSONObject
        JSONObject propertiesType = new JSONObject();
        propertiesType.put("value", typeValues[random.nextInt(typeValues.length)]);
        JSONObject type = new JSONObject();
        type.put("properties", propertiesType);

        JSONObject propertiesStrikeLevel = new JSONObject();
        propertiesStrikeLevel.put("value", strikeLevelValues[random.nextInt(strikeLevelValues.length)]);
        JSONObject strikeLevel = new JSONObject();
        strikeLevel.put("properties", propertiesStrikeLevel);

        JSONObject propertiesXCoordinate = new JSONObject();
        propertiesXCoordinate.put("value", -500 + 1000 * random.nextDouble());
        JSONObject xCoordinate = new JSONObject();
        xCoordinate.put("properties", propertiesXCoordinate);

        JSONObject propertiesYCoordinate = new JSONObject();
        propertiesYCoordinate.put("value", -500 + 1000 * random.nextDouble());
        JSONObject yCoordinate = new JSONObject();
        yCoordinate.put("properties", propertiesYCoordinate);

        // 将属性包装在"features"中
        JSONObject features = new JSONObject();
        features.put("type", type);
        features.put("strike_level", strikeLevel);
//        features.put("x_coordinate", xCoordinate);
//        features.put("y_coordinate", yCoordinate);
        putCoordinate(features, sensorDataPath);

        // 创建headers JSONObject并添加内容
        JSONObject headers = new JSONObject();
        headers.put("content-type", "application/merge-patch+json");
        headers.put("correlation-id", "090397");

        // 创建value JSONObject并添加内容
        JSONObject value = new JSONObject();
        value.put("thingId", "edu.whut.cs.iot.se:explosion");
        value.put("policyId", "ditto.default:policy");
        value.put("features", features);

        // 创建顶层的JSONObject
        JSONObject json = new JSONObject();
        json.put("topic", "edu.whut.cs.iot.se/explosion/things/twin/commands/modify");
        json.put("headers", headers);
        json.put("path", "/");
        json.put("value", value);
        return json.toJSONString();
    }
    @Override
    public String parseResponse(byte[] responseData) {
        return null;
    }
}
