package ru.gm.zabbix;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by neiro on 28.02.17.
 */
public class ZabbixServerTest {

    private ZabbixServer zs;
    private ArrayList<ZabbixObject> metricList;
    private ZabbixObject metricItem;

    private void init() {
        zs = new ZabbixServer(null, 0, 0);
        metricList = new ArrayList<>();
        metricList.add(new ZabbixObject("host1", "key1", "val1"));
        metricList.add(new ZabbixObject("host2", "key2", "val2"));
        metricList.add(new ZabbixObject("host3", "key3", "val3"));
        metricList.add(new ZabbixObject("host4", "key4", "val4"));

        metricItem = new ZabbixObject("host55", "key55", "val55");
    }

    private String normalize(final String s) {
        String result = s.trim();

        result = result.replaceAll("\t", "");
        result = result.replaceAll("\n", "");
        result = result.replaceAll(" ", "");

        return result;
    }

    @Test
    public void validateServer() {
        init();

        Assert.assertNotNull(zs);
        Assert.assertNotNull(metricItem);
        Assert.assertNotNull(metricList);
        Assert.assertEquals(4, metricList.size());
    }

    @Test
    public void buildJSONStringSingle() {
        init();

        String result = "{\n" +
                "\t\"request\":\"sender data\",\n" +
                "\t\"data\":[\n" +
                "\t\t{\"host\":\"host55\",\"key\":\"key55\",\"value\":\"val55\"}\n" +
                "\t]" +
                "}";

        Assert.assertEquals(normalize(zs.buildJSONString(metricItem)), normalize(result));
    }

    @Test
    public void buildJSONStringBulk() {
        init();

        String assertResult = "{\n" +
                "\t\"request\":\"sender data\",\n" +
                "\t\"data\":[\n" +
                "\t\t{\"host\":\"host1\",\"key\":\"key1\",\"value\":\"val1\"},\n" +
                "\t\t{\"host\":\"host2\",\"key\":\"key2\",\"value\":\"val2\"},\n" +
                "\t\t{\"host\":\"host3\",\"key\":\"key3\",\"value\":\"val3\"},\n" +
                "\t\t{\"host\":\"host4\",\"key\":\"key4\",\"value\":\"val4\"}\n" +
                "\t]\n" +
                "}\n";

        Assert.assertEquals(normalize(zs.buildJSONString(metricList)), normalize(assertResult));
    }

}
