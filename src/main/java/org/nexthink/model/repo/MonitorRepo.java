package org.nexthink.model.repo;

import org.nexthink.model.internal.Monitor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MonitorRepo extends CrudRepository<Monitor, Long> {

    List<Monitor> findBySerialNumber(String serialNumber);

}
