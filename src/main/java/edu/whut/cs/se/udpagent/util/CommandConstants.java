package edu.whut.cs.se.udpagent.util;

/**
 * 命令常量
 * @author huangaokai
 * @date 2024/5/29
 **/
public class CommandConstants {
    // 停止扫描 30 01 06 00 00 00
    public final static byte[]  STOP_SCANNER = new byte[]{(byte)0x30,(byte)0x01,(byte)0x06,(byte)0x00,(byte)0x00,(byte)0x00};

    // 波长模式 默认频率2KHZ 30 02 06 00 00 00
    public final static byte[]  WAVELENGTH_MODE = new byte[]{(byte)0x30,(byte)0x02,(byte)0x06,(byte)0x00,(byte)0x00,(byte)0x00};

    // 波长模式 默认频率100HZ 30 02 06 00 00 00 (好像不支持)
    // public final static byte[]  WAVELENGTH_MODE_100HZ = new byte[]{(byte)0x30,(byte)0x02,(byte)0x06,(byte)0x00,(byte)0x65,(byte)0x00};

}
