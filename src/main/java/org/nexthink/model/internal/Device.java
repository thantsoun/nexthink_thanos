package org.nexthink.model.internal;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The internal DB model for a device
 */
@Entity
@Table(name = "Device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="Name")
    private String name;

    @Column(name="LastIPAddress")
    private String lastIpAddress;
    
    // This is the definition as per JPA for a join table to handle the many-to-many relationship between devices and monitor
    // I have decided to use standard JPA proctices to handle the many-to-many relashionship presented in the quiz
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @Column(name="monitor_id")
    @JoinTable(name = "device_monitor",
            joinColumns = { @JoinColumn(name = "device_id") },
            inverseJoinColumns = { @JoinColumn(name = "monitor_id") }) 
    private List<Monitor> monitors = new ArrayList<>();

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMonitors(List<Monitor> monitors) {
        this.monitors = monitors;
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

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastIpAddress='" + lastIpAddress + '\'' +
                ", monitors=" + monitors +
                '}';
    }

    @PreRemove
    private void removeGroupsFromUsers() {
        for (Monitor m: monitors) {
            m.getDevices().remove(this);
        }
    }
}
