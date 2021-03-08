package org.nexthink.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.nexthink.model.external.ExtDevice;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ModelParserJson {
    
    private final Gson gson = new Gson();
    
    public List<ExtDevice> parseInputDataFromJson(String data) {
        return parseInputDataFromJson(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
    }

    public List<ExtDevice> parseInputDataFromJson(InputStream stream) {
        Type listType = new TypeToken<ArrayList<ExtDevice>>(){}.getType();
        return gson.fromJson(new BufferedReader(new InputStreamReader(stream)), listType);
    }
}
