package org.nexthink.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nexthink.QuizApp;
import org.nexthink.model.internal.Device;
import org.nexthink.model.internal.Monitor;
import org.nexthink.model.repo.DeviceRepo;
import org.nexthink.model.repo.MonitorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = QuizApp.class)
class RepoTest {

    @Autowired
    private DeviceRepo deviceRepo;

    @Autowired
    private MonitorRepo monitorRepo;
    
    private Device device1, device2;

    @BeforeEach
    void setup() {
        deviceRepo.deleteAll();
        monitorRepo.deleteAll();

        device1 = new Device("CHDB001", "172.18.151.57", new ArrayList<>());
        device1.getMonitors().add(new Monitor("HMBBA03487-5A533637", "1920*1200"));

        device2 = new Device("CHDB002", "172.18.152.71", new ArrayList<>());
        device2.getMonitors().add(new Monitor("HMBBA03488-5A533637", "192081200"));
        device2.getMonitors().add(new Monitor("HMBBA03265-5A533637", "1920*1200"));
    }

    @Test
    void testFindDeviceByName() {
        deviceRepo.save(device1);
        List<Device> devices = deviceRepo.findByName("CHDB001");
        assertEquals(1, devices.size());
        assertEquals("172.18.151.57", devices.get(0).getLastIpAddress());
        assertEquals(1, devices.get(0).getMonitors().size());
        assertEquals("HMBBA03487-5A533637", devices.get(0).getMonitors().get(0).getSerialNumber());
        List<Monitor> monitors = monitorRepo.findBySerialNumber("HMBBA03487-5A533637");
        assertEquals(1, monitors.size());
        assertEquals(devices.get(0).getMonitors().get(0).getResolution(), monitors.get(0).getResolution());
        assertEquals(devices.get(0).getMonitors().get(0).getDevices().get(0).getName(), devices.get(0).getName());
        
        devices = deviceRepo.findByName("CHDB002");
        assertEquals(0, devices.size());
    }

    @Test
    void testFindDeviceByNameMulti() {
        deviceRepo.saveAll(Arrays.asList(device1, device2));
        List<Device> devices = deviceRepo.findByName("CHDB001");
        assertEquals(1, devices.size());
        
        devices = deviceRepo.findByName("CHDB002");
        assertEquals(1, devices.size());
        assertEquals("172.18.152.71", devices.get(0).getLastIpAddress());
        assertEquals(2, devices.get(0).getMonitors().size());
        assertEquals("HMBBA03488-5A533637", devices.get(0).getMonitors().get(0).getSerialNumber());
        assertEquals("HMBBA03265-5A533637", devices.get(0).getMonitors().get(1).getSerialNumber());
    }

}
