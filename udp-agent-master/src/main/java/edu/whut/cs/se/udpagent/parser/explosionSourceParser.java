package edu.whut.cs.se.udpagent.parser;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.math.BigDecimal;
import java.util.Random;

import com.mathworks.toolbox.javabuilder.*;
import entranceinfo.EntranceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class explosionSourceParser implements ResponseParser{
    @Value("${fiberOptic.dataDir}")
    private String sensorDataPath;

    @Autowired
    private EntranceInfoParser entranceInfoParser;

    private void getMatRet(String sensorDataPath) {
        try {
            EntranceInfo entranceInfo = new EntranceInfo();
//        MWCharArray input = new MWCharArray(sensorDataPath);
            Object[] pso = entranceInfo.PSO(1, sensorDataPath);
            MWArray entranceInfoMWA = (MWCharArray)pso[0];
            entranceInfoParser.setEntranceInfo(entranceInfoMWA.toString());
        } catch (MWException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

    public String getRandomPosition() {
        // 定义 "type" 和 "strike_level" 的可能值
        String[] typeValues = {"核爆", "温压弹", "常规弹药打击", "无打击"};

        Random random = new Random();

        getMatRet(sensorDataPath);

        // 创建各属性的JSONObject
        JSONObject propertiesType = new JSONObject();
        propertiesType.put("value", typeValues[entranceInfoParser.getWeaponType()]);
        JSONObject type = new JSONObject();
        type.put("properties", propertiesType);

        JSONObject propertiesStrikeLevel = new JSONObject();
        propertiesStrikeLevel.put("value", entranceInfoParser.getEquivalent());
        JSONObject strikeLevel = new JSONObject();
        strikeLevel.put("properties", propertiesStrikeLevel);

        JSONObject propertiesXCoordinate = new JSONObject();
        propertiesXCoordinate.put("value", entranceInfoParser.getxCoordinate() + random.nextDouble());
        JSONObject xCoordinate = new JSONObject();
        xCoordinate.put("properties", propertiesXCoordinate);

        JSONObject propertiesYCoordinate = new JSONObject();
        propertiesYCoordinate.put("value", entranceInfoParser.getyCoordinate() + random.nextDouble());
        JSONObject yCoordinate = new JSONObject();
        yCoordinate.put("properties", propertiesYCoordinate);

        // 将属性包装在"features"中
        JSONObject features = new JSONObject();
        features.put("type", type);
        features.put("strike_level", strikeLevel);
        features.put("x_coordinate", xCoordinate);
        features.put("y_coordinate", yCoordinate);

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
