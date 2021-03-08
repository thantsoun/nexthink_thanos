package org.nexthink.model.external;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The model of device that comes from external sources (json, xml, etc)
 */
public class ExtDevice {
    
    @SerializedName("name")
    private String name;
    @SerializedName("last_ip_address")
    private String lastIpAddress;
    @SerializedName("monitors")
    private List<ExtMonitor> extMonitors;

    // Emtpy constructor needed to work with frameworks
    public ExtDevice() {
    }
    
    public ExtDevice(String name, String lastIpAddress, List<ExtMonitor> extMonitors) {
        this.name = name;
        this.lastIpAddress = lastIpAddress;
        this.extMonitors = extMonitors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastIpAddress() {
        return lastIpAddress;
    }

    public void setLastIpAddress(String lastIpAddress) {
        this.lastIpAddress = lastIpAddress;
    }

    public List<ExtMonitor> getMonitors() {
        return extMonitors;
    }

    public void setMonitor(List<ExtMonitor> extMonitors) {
        this.extMonitors = extMonitors;
    }
    
    public void addMonitor(ExtMonitor extMonitor) {
        extMonitors.add(extMonitor);
    }

    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", lastIpAddress='" + lastIpAddress + '\'' +
                ", monitors=" + extMonitors +
                '}';
    }
}
