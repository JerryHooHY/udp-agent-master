package cn.edu.whut.dhdas.protocol;

import cn.edu.whut.dhdas.entity.DataColumn;
import cn.edu.whut.dhdas.service.KafkaService;
import cn.edu.whut.dhdas.service.Parser;
import cn.edu.whut.dhdas.service.ScheduledTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Component
public class ParseBuffer {
    private static final byte HEADMAJOR = 0x55;
    private static final byte HEADMINTOR = (byte) 0xaa;
    private static final byte SUBHEADMAJOR = (byte)0xaa;
    private static final byte SUBHEADMINTOR = 0x55;
    private byte[] m_NetData;           // 网络缓存数据
    private int m_nVaildDataLength;     // 数据有效长度

    @Autowired
    private ExecutorService executorService;

    @Autowired
    ScheduledTask scheduledTask;

    public ParseBuffer() {
        m_NetData = new byte[1024 * 1024];  // 缓存1M的数据
        m_nVaildDataLength = 0;
    }

    @Autowired
    private Parser parser;
    @Autowired
    private KafkaService kafkaService;

    public void appendNetData(byte[] byteData, int nDataLength)
    {
        System.arraycopy(byteData,0,m_NetData,m_nVaildDataLength,nDataLength);
        m_nVaildDataLength += nDataLength;
        int nDealLength = parse(m_NetData, m_nVaildDataLength);
        System.arraycopy(m_NetData,nDealLength,m_NetData,0,m_nVaildDataLength - nDealLength);
        m_nVaildDataLength -= nDealLength;
    }

    private int parse(byte[] byteData, int nLength)
    {
        if (nLength <= 0)
            return  0;
        // 数据偏移位置
        int nDealPos = 0;
        int nDataOffset = 0;
        int nHeadSize = 4;// 0x55aaaa55
        while (nDataOffset < nLength - nHeadSize)
        {
            byte cFramID0 = byteData[nDataOffset];
            byte cFramID1 = byteData[nDataOffset + 1];
            byte cFramID2 = byteData[nDataOffset + 2];
            byte cFramID3 = byteData[nDataOffset + 3];
            if (cFramID0 != HEADMAJOR || cFramID1 != HEADMINTOR || cFramID2 != SUBHEADMAJOR || cFramID3 != SUBHEADMINTOR)
            {
                nDataOffset ++;
                continue;
            }

            nDataOffset += 4;

            // 命令码
            int nCmd = readInt(byteData,nDataOffset);
            nDataOffset += 4;
            // 包内容长度
            int nPacketLength = readInt(byteData,nDataOffset);
            nDataOffset += 4;

            if (nLength - nDataOffset < nPacketLength)
                break;

            DealNetData(byteData,nDataOffset,nPacketLength, nCmd);
            nDataOffset += nPacketLength;

            nDealPos = nDataOffset;
        }

        return nDealPos;
    }

    private void DealNetData(byte[] byteData, int nOffset, int nLength, int nCmd) {
        System.out.println("nCmd=" + nCmd);
        switch (nCmd)
        {
            case 114: // 启动采样反馈
            {
                // 开始采样时 某些状态设置
            }
            break;
            case 116:
            {
                // 停止采样时 某些状态设置
            }
            break;
            case 124:  // 时域数据(数据排列方式：ABCABCABC...)
            {
//                // 解析数据
//                long lPos = readLong(byteData,nOffset);
//                nOffset += 8;
//                // 每通道数据量
//                int nRecount = readInt(byteData,nOffset);
//                nOffset += 4;
//                // 通道个数
//                int nChnCount = readInt(byteData,nOffset);
//                nOffset += 4;
//                System.out.println("lPos:" + lPos);
//                System.out.println("nRecount:" + nRecount);
//                System.out.println("nChnCount:" + nChnCount);
//
//                // 数据量
//                // 20221020 解析出一组通道，立即发布mqtt消息
//                for (int j = 0; j < nRecount; j++)
//                {
//                    for (int i = 0; i < nChnCount; i++)
//                    {
//                        float fltVal = readFloat(byteData,nOffset+(j*nChnCount + i)*4);
//                        System.out.println(fltVal);
//                    }
//                }

                break;
            }
            case 125: // 统计数据
            {
                int nSignalNum = readInt(byteData, nOffset);
                nOffset += 4;

                float time = 0;
                // 输出统计信息序号、时域信息、具体值
                ArrayList<Float> result = new ArrayList<>();
                for (int i = 0; i < nSignalNum; i++)
                {
                    time = readFloat(byteData, nOffset);
                    nOffset += 4;
                    float value = readFloat(byteData, nOffset);
                    nOffset += 4;
                    String strMsg = "统计信号序号：" + i + ",time：" + time + ",value:" + value;
                    result.add(value);
                    System.out.println(strMsg);
                }

                // 保存结果
                scheduledTask.addData(new DataColumn(time));

                // 解析数据为JsonString
                String jsonString = parser.parser(result);
                // 发送消息到kafka中
                kafkaService.sendMessage(jsonString);

                // 解析信号长度 输出信号的名称和单位
                int nNameLen = readInt(byteData, nOffset);
                nOffset += 4;
                String strInfo = readString(byteData,nOffset,nNameLen);
                String[] strArray = strInfo.split("\\|");
                for (int i = 0; i < strArray.length; i++)
                {
                    String[] subInfo = strArray[i].split("\\,");
                    if (subInfo.length == 2)
                    {
                        String strMsg = "统计数据信号信息："+subInfo[0] + "，" + subInfo[1];
                        // System.out.println(strMsg);
                    }
                }

                // DHDAS版本 >= 6.21.1.7支持
                // 绝对时间
                nOffset += nNameLen;
                long time_stamp = readLong(byteData, nOffset);  // s数
                nOffset += 8;
                int ms = readInt(byteData, nOffset);  // 毫秒数

            }
            break;
        }

    }

    private int readInt(byte[] byteData, int nOffset)
    {
        return (0xff & byteData[nOffset]) | (0xff00 & (byteData[nOffset+1] << 8)) | (0xff0000 & (byteData[nOffset+2] << 16)) | (0xff000000 & (byteData[nOffset+3] << 24));
    }

    private long readLong(byte[] byteData, int nOffset)
    {
        return(0xffL & (long)byteData[nOffset]) | (0xff00L & ((long)byteData[nOffset + 1] << 8)) | (0xff0000L & ((long)byteData[nOffset + 2] << 16)) | (0xff000000L & ((long)byteData[nOffset + 3] << 24))
                | (0xff00000000L & ((long)byteData[nOffset + 4] << 32)) | (0xff0000000000L & ((long)byteData[nOffset+5] << 40)) | (0xff000000000000L & ((long)byteData[nOffset+6] << 48)) | (0xff00000000000000L & ((long)byteData[nOffset+7] << 56));
    }

    private float readFloat(byte[] byteData, int nOffset)
    {
        return Float.intBitsToFloat(readInt(byteData,nOffset));
    }

    private String readString(byte[] byteData, int nOffset, int nLength)
    {
        byte[] dstByteData = new byte[nLength];
        System.arraycopy(byteData,nOffset,dstByteData,0,nLength);
        try {
            return new String(dstByteData, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean readBoolen(byte[] byteData, int nOffset)
    {
        return byteData[nOffset] != 0x00;
    }

}
