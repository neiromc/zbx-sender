package ru.gm.zabbix;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;


public class ZabbixServer {

    private Socket clientSocket = null;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

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


    public void connect() throws IOException {
        clientSocket = new Socket();
        clientSocket.connect(
                new InetSocketAddress(ip, port),
                connTimeout
        );

        dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        dataInputStream = new DataInputStream(clientSocket.getInputStream());
    }

    public void close() throws IOException {
        if (clientSocket != null) {
            clientSocket.close();
        }
    }

    public Boolean isConnected() {
        return ( clientSocket != null );
    }

    private String sendToZabbix(String jsonString) {
        try {
            writeMessage(dataOutputStream, jsonString.getBytes());
            return String.valueOf(dataInputStream.readByte());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String send(ZabbixObject o) {
        return sendToZabbix(buildJSONString(o));
    }

    public String send(List<ZabbixObject> o) {
        return sendToZabbix(buildJSONString(o));
    }

    protected String buildJSONString(ZabbixObject zabbixObject) {
        String jsonHeader = "{\n\t\"request\":\"sender data\",\n\t\"data\":[\n";
        String jsonFooter = "\t]\n}\n";

        return String.format("%s\t\t{\"host\":\"%s\",\"key\":\"%s\",\"value\":\"%s\"}\n%s",
                jsonHeader,
                zabbixObject.getHostname(),
                zabbixObject.getKey(),
                zabbixObject.getValue(),
                jsonFooter
        );
    }


    protected String buildJSONString(List<ZabbixObject> objects) {
        String jsonHeader = "{\n\t\"request\":\"sender data\",\n\t\"data\":[\n";
        String jsonFooter = "\t]\n}\n";
        StringBuilder jsonBody = new StringBuilder();

        for (ZabbixObject o : objects) {
            jsonBody.append(
                String.format(
                    "\t\t{\"host\":\"%s\",\"key\":\"%s\",\"value\":\"%s\"}%s\n",
                    o.getHostname(),
                    o.getKey(),
                    o.getValue(),
                    (o.equals(objects.get(objects.size()-1))) ? "" : ","
                )
            );

        }

        jsonBody.insert(0, jsonHeader);
        jsonBody.append(jsonFooter);

        return jsonBody.toString();
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
