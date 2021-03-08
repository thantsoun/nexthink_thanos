package org.nexthink.parser;

import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nexthink.model.external.ExtDevice;
import org.nexthink.model.external.ExtMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = ModelParserJson.class)
class ParserTest {

    private static ExtDevice expectedCHDB001;
    private static ExtDevice expectedCHDB002;

    @Autowired
    private ModelParserJson modelParserJson;
    
    @BeforeAll
    private static void setUp() {
        
        expectedCHDB001 = new ExtDevice("CHDB001", "172.18.151.57", new ArrayList<>());
        expectedCHDB001.addMonitor(new ExtMonitor("SyncMaster", "HMBBA03487-5A533637", "SAM", "24.0", "1920", "1200"));
        
        expectedCHDB002 = new ExtDevice("CHDB002", "172.18.152.71", new ArrayList<>());
        expectedCHDB002.addMonitor(new ExtMonitor("SyncMaster", "HMBBA03488-5A533637", "SAM", "24.0", "1920", "1200"));
        expectedCHDB002.addMonitor(new ExtMonitor("SyncMaster", "HMBBA03265-5A533637", "SAM", "24.0", "1920", "1200"));
    }
    
    @Test
    void testFromStream() {
        String jsonFile = "data.json";
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(jsonFile);
        List<ExtDevice> devices = modelParserJson.parseInputDataFromJson(stream);
        Assertions.assertEquals(10, devices.size());
        ExtDevice deviceCHDB001 = devices.get(0);
        assertDevice(expectedCHDB001, deviceCHDB001);
        ExtDevice deviceCHDB002 = devices.get(1);
        assertDevice(expectedCHDB002, deviceCHDB002);
    }

    @Test
    void testEmptyArray() {
        List<ExtDevice> devices = modelParserJson.parseInputDataFromJson("[]");
        Assertions.assertEquals(0, devices.size());
    }

    @Test
    void testBadJson() {
        Assertions.assertThrows(JsonSyntaxException.class, () -> modelParserJson.parseInputDataFromJson("["));
    }
    
    @Test 
    void testCHDB001FromString() {
        String data = 
                "[{" +
                "    \"name\": \"CHDB001\"," +
                "    \"last_ip_address\": \"172.18.151.57\"," +
                "    \"monitors\": [{" +
                "        \"monitor_name\": \"SyncMaster\"," +
                "        \"monitor_serial_number\": \"HMBBA03487-5A533637\"," +
                "        \"monitor_vendor\": \"SAM\"," +
                "        \"monitor_diagonal\": \"24.0\"," +
                "        \"monitor_max_horizontal\": \"1920\"," +
                "        \"monitor_max_vertical\": \"1200\"" +
                "    }]" +
                "}]";
        assertDevice(expectedCHDB001, modelParserJson.parseInputDataFromJson(data).get(0));
    }

    @Test
    void testCHDB002FromString() {
        String data = 
                "[{" +
                "    \"name\": \"CHDB002\"," +
                "    \"last_ip_address\": \"172.18.152.71\"," +
                "    \"monitors\": [{" +
                "        \"monitor_name\": \"SyncMaster\"," +
                "        \"monitor_serial_number\": \"HMBBA03488-5A533637\"," +
                "        \"monitor_vendor\": \"SAM\"," +
                "        \"monitor_diagonal\": \"24.0\"," +
                "        \"monitor_max_horizontal\": \"1920\"," +
                "        \"monitor_max_vertical\": \"1200\"" +
                "    }," +
                "    {" +
                "        \"monitor_name\": \"SyncMaster\"," +
                "        \"monitor_serial_number\": \"HMBBA03265-5A533637\"," +
                "        \"monitor_vendor\": \"SAM\"," +
                "        \"monitor_diagonal\": \"24.0\"," +
                "        \"monitor_max_horizontal\": \"1920\"," +
                "        \"monitor_max_vertical\": \"1200\"" +
                "    }]" +
                "}]";
        assertDevice(expectedCHDB002, modelParserJson.parseInputDataFromJson(data).get(0));
    }

    private void assertDevice(ExtDevice expected, ExtDevice actual) {
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getLastIpAddress(), actual.getLastIpAddress());
        Assertions.assertEquals(expected.getMonitors(), actual.getMonitors());
    }

}
