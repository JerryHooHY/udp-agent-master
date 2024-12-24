package cn.edu.whut.dhdas.protocol;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class InstrumentProtocol { // 协议组包类

    static final int NETWORK_REQUEST_SAMPLEFREQ = 103;          // 请求当前采样频率
    static final int NETWORK_REQUEST_SET_SAMPLEFREQ = 105;      // 设置采样频率
    static final int NETWORK_REQUEST_START_SAMPLE = 113;        // 启动采集
    static final int NETWORK_REQUEST_STOP_SAMPLE = 115;         // 停止采集

    private byte[] m_ProtocolBuffer;          // 发送数据协议缓存

    public InstrumentProtocol() {
        m_ProtocolBuffer = new byte[256];  // 缓存1k的数据, 协议最长不会超过256个字节
    }

    public byte[] GetStartSampleCmd(String strRunName)
    {
        int nOffset = 0;
        m_ProtocolBuffer[0] = 0x55;
        m_ProtocolBuffer[1] = (byte) 0xaa;
        m_ProtocolBuffer[2] = (byte) 0xaa;
        m_ProtocolBuffer[3] = 0x55;
        nOffset += 4;

        byte[] tmp = IntToBytes(NETWORK_REQUEST_START_SAMPLE);
        System.arraycopy(tmp, 0, m_ProtocolBuffer, nOffset, tmp.length);
        nOffset += tmp.length;
        int nDataLen = strRunName.length();
        tmp = IntToBytes(nDataLen);
        System.arraycopy(tmp, 0, m_ProtocolBuffer, nOffset, tmp.length);
        nOffset += tmp.length;

        tmp = strRunName.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(tmp, 0, m_ProtocolBuffer, nOffset, strRunName.length());

        return m_ProtocolBuffer;
    }

    public byte[] GetStopSampleCmd()
    {
        int nOffset = 0;
        m_ProtocolBuffer[0] = 0x55;
        m_ProtocolBuffer[1] = (byte) 0xaa;
        m_ProtocolBuffer[2] = (byte) 0xaa;
        m_ProtocolBuffer[3] = 0x55;
        nOffset += 4;

        byte[] tmp = IntToBytes(NETWORK_REQUEST_STOP_SAMPLE);
        System.arraycopy(tmp, 0, m_ProtocolBuffer, nOffset, tmp.length);
        nOffset += tmp.length;
        int nDataLength = 0;
        tmp = IntToBytes(nDataLength);
        System.arraycopy(tmp, 0, m_ProtocolBuffer, nOffset, tmp.length);

        return m_ProtocolBuffer;
    }

    public byte[] GetSetSampleFreqCmd(float fltSampleFreq)
    {
        int nOffset = 0;
        m_ProtocolBuffer[0] = 0x55;
        m_ProtocolBuffer[1] = (byte) 0xaa;
        m_ProtocolBuffer[2] = (byte) 0xaa;
        m_ProtocolBuffer[3] = 0x55;
        nOffset += 4;

        byte[] tmp = IntToBytes(NETWORK_REQUEST_SET_SAMPLEFREQ);
        System.arraycopy(tmp, 0, m_ProtocolBuffer, nOffset, tmp.length);
        nOffset += tmp.length;

        int nDataLength = 4;
        tmp = IntToBytes(nDataLength);
        System.arraycopy(tmp, 0, m_ProtocolBuffer, nOffset, tmp.length);
        nOffset += tmp.length;

        tmp = FloatToBytes(fltSampleFreq);
        System.arraycopy(tmp, 0, m_ProtocolBuffer, nOffset, tmp.length);

        return m_ProtocolBuffer;
    }

    public byte[] GetSampleFreqCmd()
    {
        int nOffset = 0;
        m_ProtocolBuffer[0] = 0x55;
        m_ProtocolBuffer[1] = (byte) 0xaa;
        m_ProtocolBuffer[2] = (byte) 0xaa;
        m_ProtocolBuffer[3] = 0x55;
        nOffset += 4;

        byte[] tmp = IntToBytes(NETWORK_REQUEST_SAMPLEFREQ);
        System.arraycopy(tmp, 0, m_ProtocolBuffer, nOffset, tmp.length);
        nOffset += tmp.length;

        int nDataLength = 0;
        tmp = IntToBytes(nDataLength);
        System.arraycopy(tmp, 0, m_ProtocolBuffer, nOffset, tmp.length);

        return m_ProtocolBuffer;
    }

    public static byte[] IntToBytes(int nValue)
    {
        byte[] b = new byte[4];
        int value = Integer.reverseBytes(nValue);
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (value >> (24 - i * 8));
        }
        return b;
    }

    public static byte[] FloatToBytes(float fltValue)
    {
        byte[] b = new byte[4];
        int value = Integer.reverseBytes(Float.floatToIntBits(fltValue));
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (value >> (24 - i * 8));
        }
        return b;
    }

}
