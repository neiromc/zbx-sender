package ru.gm.zabbix;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


public class ZabbixServer {
    private String ip;
    private int port;
    private int connTimeout;

    public ZabbixServer(final String ip,
                        final int port,
                        final int connTimeout) {
        this.ip = ip;
        this.port = port;
        this.connTimeout = connTimeout;
    }

    public void connectAndSend(final String hostname,
                               final String key,
                               final String value) {

        Socket clientSocket = null;

        try {
            clientSocket = new Socket();
            clientSocket.connect(
                    new InetSocketAddress(ip, port),
                    connTimeout
            );

            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

            //build JSON report for sending in Zabbix
            String report = buildJSONString(hostname, key, value);

            try {
                writeMessage(dos, report.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private String buildJSONString(String host, String key, String value)
    {
        return 		  "{"
                + "\"request\":\"sender data\",\n"
                + "\"data\":[\n"
                +        "{\n"
                +                "\"host\":\"" + host + "\",\n"
                +                "\"key\":\"" + key + "\",\n"
                +                "\"value\":\"" + value.replace("\\", "\\\\") + "\"}]}\n" ;
    }

    protected void writeMessage(OutputStream out, byte[] data) throws IOException {
        int length = data.length;

        out.write(new byte[] {
                'Z', 'B', 'X', 'D',
                '\1',
                (byte)(length & 0xFF),
                (byte)((length >> 8) & 0x00FF),
                (byte)((length >> 16) & 0x0000FF),
                (byte)((length >> 24) & 0x000000FF),
                '\0','\0','\0','\0'});

        out.write(data);
    }

}
