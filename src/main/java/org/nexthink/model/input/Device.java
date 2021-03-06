package org.nexthink.model.input;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Device {
    
    @SerializedName("name")
    private String name;
    @SerializedName("last_ip_address")
    private String lastIpAddress;
    @SerializedName("monitors")
    private List<Monitor> monitors;

    // Emtpy constructor needed to work with frameworks
    public Device() {
    }
    
    public Device(String name, String lastIpAddress, List<Monitor> monitors) {
        this.name = name;
        this.lastIpAddress = lastIpAddress;
        this.monitors = monitors;
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

    public List<Monitor> getMonitors() {
        return monitors;
    }

    public void setMonitor(List<Monitor> monitors) {
        this.monitors = monitors;
    }
    
    public void addMonitor(Monitor monitor) {
        monitors.add(monitor);
    }

    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", lastIpAddress='" + lastIpAddress + '\'' +
                ", monitors=" + monitors +
                '}';
    }
}
