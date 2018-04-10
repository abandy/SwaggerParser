package com.abandy.swagger.modeler.swagger;

import javax.json.JsonObject;
import java.util.HashMap;
import java.util.Map;

public class BaseSwagger {
    String key;
    Map<String, Extension> extensions = new HashMap<String, Extension>();

    public BaseSwagger parse(JsonObject jsonObj, String key) {
        this.key = key;
        return this;
    }

    public String key() { return this.key; }

    public Map<String, Extension> extensions() {
        return this.extensions;
    }
}
