package org.nexthink.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.nexthink.model.external.ExtDevice;
import org.nexthink.model.external.ExtMonitor;
import org.nexthink.model.internal.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = ModelMapper.class)
class ModelMapperTest {

    @Autowired
    private ModelMapper modelMapper;
    
    @Test
    void testTransformDeviceMonitor() {
        ExtDevice extDevice1 = new ExtDevice("CHDB001", "172.18.151.57", new ArrayList<>());
        extDevice1.addMonitor(new ExtMonitor("SyncMaster", "HMBBA03487-5A533637", "SAM", "24.0", "1920", "1200"));
        
        ExtDevice extDevice2 = new ExtDevice("CHDB002", "172.18.152.71", new ArrayList<>());
        extDevice2.addMonitor(new ExtMonitor("SyncMaster", "HMBBA03488-5A533637", "SAM", "24.0", "1920", "1200"));
        extDevice2.addMonitor(new ExtMonitor("SyncMaster", "HMBBA03265-5A533637", "SAM", "24.0", "1920", "1200"));

        List<ExtDevice> extDevices = Arrays.asList(extDevice1, extDevice2);
                
        List<Device> devices = modelMapper.transform(extDevices);


        Assertions.assertEquals(extDevices.size(), devices.size());
        for (int i = 0; i < devices.size() ; i++) {
            assertDevice(extDevices.get(i), devices.get(i));
        }
    }
    
    private void assertDevice(ExtDevice extDevice, Device device) {
        Assertions.assertEquals(extDevice.getName(), device.getName());
        Assertions.assertEquals(extDevice.getLastIpAddress(), device.getLastIpAddress());
        Assertions.assertEquals(extDevice.getMonitors().size(), device.getMonitors().size());
        for (int i = 0; i < device.getMonitors().size(); i++) {
            Assertions.assertEquals(1, device.getMonitors().get(i).getDevices().size());
            Assertions.assertEquals(device.getName(), device.getMonitors().get(i).getDevices().get(0).getName());
            Assertions.assertEquals(extDevice.getMonitors().get(i).getSerialNumber(), device.getMonitors().get(i).getSerialNumber());
            Assertions.assertEquals(extDevice.getMonitors().get(i).getHorizontal() + '*' + extDevice.getMonitors().get(i).getVertical(), 
                    device.getMonitors().get(i).getResolution());
        }
    }

}
