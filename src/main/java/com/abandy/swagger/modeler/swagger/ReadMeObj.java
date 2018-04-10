package com.abandy.swagger.modeler.swagger;

import com.abandy.swagger.modeler.SwaggerEntry;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by alvadbandy on 1/6/17.
 */
public class ReadMeObj {
    private final SwaggerEntry entry;
    private List<String> swaggerUrls = new ArrayList<String>();

    public ReadMeObj(SwaggerEntry entry) {
        this.entry = entry;
    }

    public String getSwaggerPrefix() {
        return this.entry.swaggerUrl().substring(0, this.entry.swaggerUrl().lastIndexOf("/") + 1);
    }

    public void initialize() throws IOException {
        String version = entry.queryParams().get("tag");;
        Objects.requireNonNull(version);
        String endLineMarker = String.format("$(tag) == '%s'", version);
        String urlPrefix = this.getSwaggerPrefix();
        SwaggerParser.loadUrlData(entry.swaggerUrl(),
                new SwaggerParser.ProcessUrlData() {
                    private boolean foundLine;

                    @Override
                    public boolean process(String line) {
                        if(line.startsWith("``` yaml") &&
                                line.endsWith(endLineMarker)) {
                            foundLine = true;
                        }else if(foundLine && line.startsWith("```")) {
                            return false;
                        }

                        if(foundLine && line.startsWith("-" )) {
                            swaggerUrls.add(urlPrefix + line.substring(1).trim());
                        }

                        return true;
                    }
                });
    }

    public List<String> swaggerUrls() {
        return this.swaggerUrls;
    }
}
