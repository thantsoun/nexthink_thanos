package org.nexthink.web;

import org.nexthink.model.ModelMapper;
import org.nexthink.model.external.ExtDevice;
import org.nexthink.model.internal.Device;
import org.nexthink.model.internal.Monitor;
import org.nexthink.model.repo.DeviceRepo;
import org.nexthink.model.repo.MonitorRepo;
import org.nexthink.parser.ModelParserJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DevicesResource {

    @Autowired
    private DeviceRepo deviceRepo;
    @Autowired
    private MonitorRepo monitorRepo;
    @Autowired
    private ModelParserJson modelParserJson;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/devices")
    public List<Device> findAllDevices() {
        List<Device> result = new ArrayList<>();
        deviceRepo.findAll().forEach(result::add);
        return result;
    }

    @GetMapping("/monitors")
    public List<Monitor> findAllMonitors() {
        List<Monitor> result = new ArrayList<>();
        monitorRepo.findAll().forEach(result::add);
        return result;
    }

    @DeleteMapping("/devices/{id}")
    public void deleteDevice(@PathVariable Long id) {
        deviceRepo.deleteById(id);
    }

    @DeleteMapping("/monitors/{id}")
    public void deleteMonitor(@PathVariable Long id) {
        monitorRepo.deleteById(id);
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/devices")
    public Device createDevice(@RequestBody Device device) {
        return deviceRepo.save(device);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/monitors")
    public Monitor createMonitor(@RequestBody Monitor monitor) {
        return monitorRepo.save(monitor);
    }

    @GetMapping("/devices/{id}")
    public Device findDeviceById(@PathVariable Long id) {
        return deviceRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Device with ID: " + id + " could not be found"));
    }

    @GetMapping("/monitors/{id}")
    public Monitor findMonitorById(@PathVariable Long id) {
        return monitorRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Monitor with ID: " + id + " could not be found"));
    }

    @GetMapping("/devices/query/{name}")
    public List<Device> findDeviceByName(@PathVariable String name) {
        return deviceRepo.findByName(name);
    }

    @GetMapping("/monitors/query/{serialNumber}")
    public List<Monitor> findMonitorByName(@PathVariable String serialNumber) {
        return monitorRepo.findBySerialNumber(serialNumber);
    }

    @PostMapping(path = "/devices/json/file-upload")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Device> createDevices(@RequestParam("file") MultipartFile file) {
        try(InputStream stream = new BufferedInputStream(file.getInputStream())) {
            List<ExtDevice> extDevices = modelParserJson.parseInputDataFromJson(stream);
            List<Device> devices = modelMapper.transform(extDevices);
            List<Device> result = new ArrayList<>();
            deviceRepo.saveAll(devices).forEach(result::add);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed parsing the json file: " + file.getOriginalFilename(), e);
        }
    }
    
    // PUT/PATCH methods omitted. Did not have time to investigate how updating a resource would affect the join table between devices and monitors 

}