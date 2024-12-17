package edu.whut.cs.se.udpagent.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 光纤相关工具类
 * @author huangaokai
 * @date 2024/5/30
 **/
@Slf4j
public class FiberOpticUtils {
    // 光速
    private static final double SPEED_OF_LIGHT = 299792458;

    /**
     * 把光栅频率转为波长，保留4位小数
     * @param frequency 评率
     * @return double
     */
    public static double convertFrequencyToWavelength(long frequency) {
        if (frequency <= 0) {
            log.error("解析数据异常，频率必须是正数");
        }
        //这里可能要做修改
        // (SPEED_OF_LIGHT / frequency * 10) 为波长，乘10000再除10000保留四位小数
        return Math.round((SPEED_OF_LIGHT / frequency * 10) * 10000.0) / 10000.0;
    }

}
