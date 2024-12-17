package edu.whut.cs.se.udpagent.parser;

import com.alibaba.fastjson2.JSONObject;
import edu.whut.cs.se.udpagent.util.ByteUtils;
import edu.whut.cs.se.udpagent.util.FiberOpticUtils;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 波长模式响应指令转换
 **/
@Service
public class WavelengthModeResponseParser implements ResponseParser {

    // @Autowired
    // private ChannelConfig channelConfig;

    @Override
    public String parseResponse(byte[] responseData) {

        //创建最外层的JSONObject
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("topic","edu.whut.cs.iot.se/fiberoptics/things/twin/commands/modify");
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
        valueObject.put("thingId", "edu.whut.cs.iot.se:fiberoptics");
        valueObject.put("policyId", "ditto.default:policy");

        //创建features对象，用于存放所有波长信息
        JSONObject features = new JSONObject();
        int channelPreIndex = 6;

        for (int number = 1; number <= 3; number++) {
            // 当前编号在responseData数组中的开始位置
            int startIndex = channelPreIndex + 4 * (number - 1) + 1;
            int endIndex = startIndex + 3;

            // 计算波长并构建相应的JSON结构
            long frequency = ByteUtils.byteArrayToDecimal(responseData, startIndex, endIndex);
            double wavelength = FiberOpticUtils.convertFrequencyToWavelength(frequency);

            // 为每个波长创建一个JSONObject，并设置其value
            JSONObject wavelengthProperties = new JSONObject();
            wavelengthProperties.put("value", wavelength);

            JSONObject wavelengthObject = new JSONObject();
            wavelengthObject.put("properties", wavelengthProperties);

            // 将波长对象加入到features中
            features.put("wavelength" + number, wavelengthObject);
        }
        valueObject.put("features", features);
        // 将value对象加入到jsonObject
        jsonObject.put("value", valueObject);



// jsonObject现在包含了所需的结构



        /**
         * 根据通道和编号灵活修改json
         List<ChannelConfig.Channel> channels = channelConfig.getChannels();
         for (ChannelConfig.Channel channel : channels) {
         // 通道id
         int channelId = channel.getId();
         // 当前通道在responseData数组中的开始位置
         int channelPreIndex = 6 + channelId * 108;
         // 编号列表
         List<Integer> numbers = channel.getNumbers();
         for (Integer number : numbers) { //number为编号
         // 当前编号在responseData数组中的开始位置
         int startIndex = channelPreIndex + 4 * (number - 1) + 1;
         int endIndex = startIndex + 3;

         // 计算波长并放入json中
         long frequency = ByteUtils.byteArrayToDecimal(responseData, startIndex, endIndex);
         double wavelength = FiberOpticUtils.convertFrequencyToWavelength(frequency);
         jsonObject.put("channel" + channelId + "_number" + number + "_wavelength", wavelength);
         }
         }*/

        return jsonObject.toJSONString();
    }
}



































