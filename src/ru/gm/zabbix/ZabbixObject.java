package ru.gm.zabbix;

public class ZabbixObject {

    private String hostname, key, value;

    public ZabbixObject() {
    }

    public ZabbixObject(String hostname, String key, String value) {
        this.hostname = hostname;
        this.key = key;
        this.value = value;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ZabbixObject{" +
                "hostname='" + hostname + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
