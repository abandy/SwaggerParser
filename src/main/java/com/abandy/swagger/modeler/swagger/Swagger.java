package com.abandy.swagger.modeler.swagger;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.ArrayList;
import java.util.List;

public class Swagger extends BaseSwagger {
    String version;
    String host;
    String basePath;
    List<String> schemas = new ArrayList<>();
    List<String> consumes = new ArrayList<>();
    List<String> produces = new ArrayList<>();
    List<Path> paths = new ArrayList<Path>();
    List<Schema> definitions = new ArrayList<>();
    List<SwaggerParameter> parameters = new ArrayList<>();
    List<SwaggerResponse> responses = new ArrayList<>();
    List<Tag> tags = new ArrayList<>();

    public Swagger parse(JsonObject jsonObj) {
        super.parse(jsonObj, "");
        this.version = jsonObj.getString("version", null);
        this.host = jsonObj.getString("host", null);
        this.basePath = jsonObj.getString("basePath", null);
        Swagger.addStringArray(jsonObj, "schemas", this.schemas);
        Swagger.addStringArray(jsonObj, "consumes", this.consumes);
        Swagger.addStringArray(jsonObj, "produces", this.produces);
        Swagger.addObjectProperties(jsonObj,
                "paths",
                new ObjectParser<Path>() {
                    @Override
                    public Path parse(JsonObject jsonObj, String key) {
                        return new Path(Swagger.this).parse(jsonObj, key);
                    }
                },
                this.paths);
        Swagger.addObjectProperties(jsonObj,
                "definitions",
                new ObjectParser<Schema>() {
                    @Override
                    public Schema parse(JsonObject jsonObj, String key) {
                        return new Schema().parse(jsonObj, key);
                    }
                },
                this.definitions);
        Swagger.addObjectProperties(jsonObj,
                "parameters",
                new ObjectParser<SwaggerParameter>() {
                    @Override
                    public SwaggerParameter parse(JsonObject jsonObj, String key) {
                        return new SwaggerParameter().parse(jsonObj, key);
                    }
                },
                this.parameters);
        Swagger.addObjectProperties(jsonObj,
                "responses",
                new ObjectParser<SwaggerResponse>() {
                    @Override
                    public SwaggerResponse parse(JsonObject jsonObj, String key) {
                        return new SwaggerResponse().parse(jsonObj, key);
                    }
                },
                this.responses);
        Swagger.addObjectProperties(jsonObj,
                "paths",
                new ObjectParser<Tag>() {
                    @Override
                    public Tag parse(JsonObject jsonObj, String key) {
                        return new Tag().parse(jsonObj, key);
                    }
                },
                this.tags);
        return this;
    }

    public static void addStringArray(JsonObject jsonObj, String key, List<String> data) {
        JsonArray array = jsonObj.getJsonArray(key);
        if(array != null) {
            for (int i = 0; i < array.size(); i++) {
                data.add(array.getString(i));
            }
        }
    }

    public static <T> void addObjectProperties(JsonObject jsonObj, String key, ObjectParser<T> parser, List<T> data) {
        JsonObject propObj = jsonObj.getJsonObject(key);
        if (propObj != null) {
            for (String propKey : propObj.keySet()) {
                if(propObj.get(propKey).getValueType() == JsonValue.ValueType.OBJECT) {
                    T obj = parser.parse(propObj.get(propKey).asJsonObject(), propKey);
                    data.add(obj);
                }

            }
        }
    }

    public static <T> void addObjectArray(JsonObject jsonObj, String key, ObjectParser<T> parser, List<T> data) {
        JsonArray array = jsonObj.getJsonArray(key);
        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                T obj = parser.parse(array.getJsonObject(i), "");
                data.add(obj);
            }
        }
    }

    public static void addObjectArray(JsonObject jsonObj, String key, List<String> data) {
        JsonArray array = jsonObj.getJsonArray(key);
        if(array != null) {
            for (int i = 0; i < array.size(); i++) {
                data.add(array.getString(i));
            }
        }
    }

    public interface ObjectParser<T> {
        T parse(JsonObject jsonOb, String keyj);
    }

    public String version() {
        return this.version;
    }

    public String host() {
        return this.host;
    }

    public String basePath() {
        return this.basePath;
    }

    public List<String> schemas() {
        return this.schemas;
    }

    public List<String> consumes() {
        return this.consumes;
    }

    public List<String> produces() {
        return this.produces;
    }

    public List<Path> paths() {
        return this.paths;
    }

    public List<Schema> definitions() {
        return this.definitions;
    }

    public List<SwaggerParameter> parameters() {
        return this.parameters;
    }

    public List<SwaggerResponse> responses() {
        return this.responses;
    }

    public List<Tag> tags() {
        return this.tags;
    }
}
