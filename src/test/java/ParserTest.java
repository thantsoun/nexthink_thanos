import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nexthink.model.input.Device;
import org.nexthink.model.input.Monitor;
import org.nexthink.parser.ModelParserJson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {

    private static Device expectedCHDB001;
    private static Device expectedCHDB002;
    // DI framework would be used normally, would not be static
    private static ModelParserJson modelParserJson;
        
    @BeforeAll
    private static void setUp() {
        modelParserJson = new ModelParserJson();
        
        expectedCHDB001 = new Device("CHDB001", "172.18.151.57", new ArrayList<>());
        expectedCHDB001.addMonitor(new Monitor("SyncMaster", "HMBBA03487-5A533637", "SAM", "24.0", "1920", "1200"));
        
        expectedCHDB002 = new Device("CHDB002", "172.18.152.71", new ArrayList<>());
        expectedCHDB002.addMonitor(new Monitor("SyncMaster", "HMBBA03488-5A533637", "SAM", "24.0", "1920", "1200"));
        expectedCHDB002.addMonitor(new Monitor("SyncMaster", "HMBBA03265-5A533637", "SAM", "24.0", "1920", "1200"));
    }
    
    @Test
    void testFromStream() {
        String jsonFile = "data.json";
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(jsonFile);
        List<Device> devices = modelParserJson.parseInputDataFromJson(stream);
        assertEquals(10, devices.size());
        Device deviceCHDB001 = devices.get(0);
        assertDevice(expectedCHDB001, deviceCHDB001);
        Device deviceCHDB002 = devices.get(1);
        assertDevice(expectedCHDB002, deviceCHDB002);
    }

    @Test
    void testEmptyArray() {
        List<Device> devices = modelParserJson.parseInputDataFromJson("[]");
        assertEquals(0, devices.size());
    }

    @Test
    void testBadJson() {
        assertThrows(JsonSyntaxException.class, () -> modelParserJson.parseInputDataFromJson("["));
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

    private void assertDevice(Device expected, Device actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getLastIpAddress(), actual.getLastIpAddress());
        assertEquals(expected.getMonitors(), actual.getMonitors());
    }

}
