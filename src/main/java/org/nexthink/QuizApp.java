package org.nexthink;

import org.nexthink.model.repo.DeviceRepo;
import org.nexthink.model.ModelMapper;
import org.nexthink.model.external.ExtDevice;
import org.nexthink.model.internal.Device;
import org.nexthink.parser.ModelParserJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class QuizApp implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(QuizApp.class);
    
    @Autowired
    private ModelParserJson modelParserJson;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeviceRepo deviceRepo;
    
    public static void main(String[] args) {
        SpringApplication.run(QuizApp.class, args);
    }

    @Override
    public void run(String... args) {

        // following code is for testing the app. It loads the devices from the json file in resources
        // and store it in the in memory DB
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("data.json");
        List<ExtDevice> externalDevices = modelParserJson.parseInputDataFromJson(stream);
        List<Device> devices = modelMapper.transform(externalDevices);
        deviceRepo.saveAll(devices);
        deviceRepo.findAll().forEach(dev -> log.info(dev.toString()));
    }
}
