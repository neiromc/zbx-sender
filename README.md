<h2>Zabbix Server Sender Library</h2>

This is a simple java library for sending data to Zabbix Trapper.</br>
**JAR Library:** ./out/artifacts/zabbix_server/zabbix_server-X.X.X.jar

<h3>Examples</h3>

<h4>One item send:</h4>
<pre><code>ZabbixServer zs = new ZabbixServer("192.168.0.1, 10051, 5000);
ZabbixObject zo = new ZabbixObject("host1", "key1", "val1");
try {
    zs.connect();
    zs.send(zo);
} catch (IOException e) {
    e.printStackTrace();
} finally {
    if (zs.isConnected())
        try {
            zs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
}</code></pre>

<h4>Bulk send:</h4>
<pre><code>ZabbixServer zs = new ZabbixServer("192.168.0.1, 10051, 5000);

ArrayList<ZabbixObject> metrics = new ArrayList<>();
metrics.add(new ZabbixObject("host1", "key1", "val1"));
metrics.add(new ZabbixObject("host2", "key2", "val2"));
metrics.add(new ZabbixObject("host3", "key3", "val3"));
metrics.add(new ZabbixObject("host4", "key4", "val4"));

try {
    zs.connect();
    zs.send(metrics);
} catch (IOException e) {
    e.printStackTrace();
} finally {
    if (zs.isConnected())
        try {
            zs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
}</code></pre>

---
GeekMonkey Lab.</br>
www.geekmonkey.ru
