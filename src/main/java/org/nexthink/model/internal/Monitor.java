package org.nexthink.model.internal;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The internal DB model for a device
 */
@Entity
@Table(name = "Monitor")
public class Monitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="SerialNumber")
    private String serialNumber;

    @Column(name="Resolution")
    private String resolution;

    /**
     * The many to many relationship as per JPA
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "monitors")
    private List<Device> devices = new ArrayList<>();

    // Emtpy constructor needed to work with frameworks
    public Monitor() {
    }

    public Monitor(String serialNumber, String resolution) {
        this.serialNumber = serialNumber;
        this.resolution = resolution;
    }
    
    public void addDevice(Device device) {
        devices.add(device);
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Override
    public String toString() {
        return "Monitor{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", resolution='" + resolution + '\'' +
                '}';
    }
}
