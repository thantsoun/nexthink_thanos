package org.nexthink.model.repo;

import org.nexthink.model.internal.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeviceRepo extends CrudRepository<Device, Long> {

    List<Device> findByName(String name);

}
