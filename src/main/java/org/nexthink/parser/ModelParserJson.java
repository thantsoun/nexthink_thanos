package org.nexthink.parser;

import com.google.gson.Gson;
import org.nexthink.model.input.Device;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;


public class ModelParserJson {
    
    // DI framework would be used normally
    private final Gson gson = new Gson();
    
    public List<Device> parseInputDataFromJson(String data) {
        return parseInputDataFromJson(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
    }

    public List<Device> parseInputDataFromJson(InputStream stream) {
        Type listType = new TypeToken<ArrayList<Device>>(){}.getType();
        return gson.fromJson(new BufferedReader(new InputStreamReader(stream)), listType);
    }
}
