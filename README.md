<h2>Zbx Sender Library</h2>

This is a simple java library for sending data to Zabbix Trapper.</br>
**JAR Library:** ./out/artifacts/zabbix_server/gmZabbixServerSender-X.X.X.jar

<h3>Official zabbix sender documentation:</h3>

https://www.zabbix.org/wiki/Docs/protocols/zabbix_sender/2.0 </br>
https://www.zabbix.org/wiki/Docs/protocols/zabbix_sender/1.8/java_example </br>

Supported all actual versions of Zabbix Server (1.8 and later).

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
metrics.add(new ZabbixObject("host1", "key1_1", "val1"));
metrics.add(new ZabbixObject("host1", "key1_2", "val2"));
metrics.add(new ZabbixObject("host2", "key2",   "val3"));
metrics.add(new ZabbixObject("host3", "key3",   "val4"));
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


<h3>License</h3>
The MIT License (MIT)
