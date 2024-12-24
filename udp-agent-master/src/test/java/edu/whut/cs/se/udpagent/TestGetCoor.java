package edu.whut.cs.se.udpagent;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.mathworks.toolbox.javabuilder.*;
import edu.whut.cs.se.udpagent.parser.EntranceInfoParser;
import entranceinfo.EntranceInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class TestGetCoor {
    @Autowired
    private EntranceInfoParser entranceInfoParser;

    @Test
    public void testEntranceInfo() throws MWException {
        System.out.println(getMatlabRet());
        entranceInfoParser.setEntranceInfo(getMatlabRet());
        System.out.println(entranceInfoParser);


//        JSONObject value = new JSONObject();
//        value.put("thingId", "edu.whut.cs.iot.se:explosion");
//        value.put("policyId", "ditto.default:policy");
//        value.put("features", features);

//        System.out.println(value);
    }

    private String getMatlabRet() {
        try {
            EntranceInfo entranceInfo = new EntranceInfo();
//        MWCharArray input = new MWCharArray("C:\\Users\\15854\\Desktop\\PSO\\5th_data.txt");
            Object[] pso = entranceInfo.PSO(1, "C:\\Users\\15854\\Desktop\\matlab\\整体代码(6)\\整体代码\\data");
            MWArray mwArray = (MWCharArray)pso[0];

            return mwArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
