package org.nexthink.model;

import org.nexthink.model.external.ExtDevice;
import org.nexthink.model.external.ExtMonitor;
import org.nexthink.model.internal.Device;
import org.nexthink.model.internal.Monitor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to map the one-to-many devices and monitors external schema
 * to the many-to-many internal DB model
 */
@Service
public class ModelMapper {
    
    public List<org.nexthink.model.internal.Device> transform(List<ExtDevice> input) {
        return input.stream()
                .map(this::transformDevice)
                .collect(Collectors.toList());
    }
    
    private org.nexthink.model.internal.Monitor transformMonitor(ExtMonitor mon) {
        return new Monitor(mon.getSerialNumber(), mon.getHorizontal() + '*' + mon.getVertical());
    }

    private org.nexthink.model.internal.Device transformDevice(ExtDevice dev) {
        List<Monitor> monitors = dev.getMonitors()
                .stream()
                .map(this::transformMonitor)
                .collect(Collectors.toList());
        Device device = new Device(dev.getName(), dev.getLastIpAddress(), monitors);
        monitors.forEach(mon -> mon.addDevice(device));
        return device;
    }
}
