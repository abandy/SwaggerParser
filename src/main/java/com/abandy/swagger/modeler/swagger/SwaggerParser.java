package com.abandy.swagger.modeler.swagger;

import com.abandy.swagger.modeler.SwaggerEntry;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SwaggerParser {
    public List<Swagger> parse(SwaggerEntry entry) throws IOException {
        Objects.requireNonNull(entry);
        List<Swagger> swaggers = new ArrayList<Swagger>();
        if(entry.isReadMe()) {
            ReadMeObj readMeObj = new ReadMeObj(entry);
            readMeObj.initialize();
            for(String swaggerUrl : readMeObj.swaggerUrls()) {
                JsonObject jsonObj = this.getSwaggerEntryData(swaggerUrl);
                if(jsonObj != null) {
                    swaggers.add(new Swagger().parse(jsonObj));
                }
            }
        }else {
            JsonObject jsonObj = this.getSwaggerEntryData(entry.swaggerUrl());
            if(jsonObj != null) {
                swaggers.add(new Swagger().parse(jsonObj));
            }
        }

        return swaggers;
    }

    public JsonObject getSwaggerEntryData(String url) throws IOException {
        StringBuffer buffer = new StringBuffer();
        SwaggerParser.loadUrlData(url, new SwaggerParser.ProcessUrlData() {
            @Override
            public boolean process(String line) {
                buffer.append(line);
                return true;
            }
        });

        JsonReader jsonReader = Json.createReader(
                new StringReader(buffer.toString()));
        try {
            return jsonReader.readObject();
        }catch(Exception ex) {
            System.out.println("error with url: " + url);
        }

        return null;
    }

    public static void loadUrlData(String path, SwaggerParser.ProcessUrlData processUrlData) throws IOException {
        Objects.requireNonNull(path);
        String s = path.trim().toLowerCase();
        boolean hasProtocol = s.startsWith("http://") || s.startsWith("https://") || s.startsWith("file://");
        URL newUrl = hasProtocol ? new URL(path) : new File(path).toURI().toURL();
        try(InputStreamReader streamReader = new InputStreamReader(newUrl.openStream())) {
            try(BufferedReader reader = new BufferedReader(streamReader)) {
                String line;
                while((line = reader.readLine()) != null) {
                    if(!processUrlData.process(line)) {
                        break;
                    }
                }
            }
        }
    }

    public interface ProcessUrlData {
        boolean process(String line);
    }
}
