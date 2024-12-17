package edu.whut.cs.se.udpagent;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.mathworks.toolbox.javabuilder.*;
import coordinate.Coordinate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class TestGetCoor {

    @Test
    public void getCoor() throws MWException {
        Coordinate coordinate = new Coordinate();
//        MWCharArray input = new MWCharArray("C:\\Users\\15854\\Desktop\\PSO\\5th_data.txt");
        Object[] pso = coordinate.PSO(1, "C:\\Users\\15854\\Desktop\\PSO\\5th_data.txt");
        MWArray coor = (MWCharArray)pso[0];

        JSONObject features = new JSONObject();
        features.putAll((JSONObject)JSON.parse(coor.toString()));

        JSONObject value = new JSONObject();
        value.put("thingId", "edu.whut.cs.iot.se:explosion");
        value.put("policyId", "ditto.default:policy");
        value.put("features", features);

        System.out.println(value);
    }

}
