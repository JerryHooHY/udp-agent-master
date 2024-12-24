package cn.edu.whut.dhdas.service;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Component
public  class TcpClient {
    private Socket m_SocketClient;
    private static final int BUFFERLENGTH = 1024;
    // tcp客户端
    public TcpClient() {
        m_SocketClient = new Socket();
    }

    // 连接
    public void Connect(String strIP, int nPort)
    {
        try {
            m_SocketClient.connect(new InetSocketAddress(strIP,nPort));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 断开
    public void Close()
    {
        try {
            m_SocketClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 发送数据
    public boolean SendCmdToServer(byte[] byteData, int nLength)
    {
        if (m_SocketClient == null)
            return false;

        try {
            m_SocketClient.getOutputStream().write(byteData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Socket getM_SocketClient() {
        return m_SocketClient;
    }
}
