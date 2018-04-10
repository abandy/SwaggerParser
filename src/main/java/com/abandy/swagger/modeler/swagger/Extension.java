package com.abandy.swagger.modeler.swagger;

import javax.json.JsonObject;
import javax.json.JsonValue;

public class Extension {
    String name;
    ExtensionData value;

    public Extension parse(JsonObject jsonObj, String key) {
        this.name = jsonObj.getString("name", null);
        if(key.toLowerCase().equals("x-ms-enum")) {
            EnumExtensionData enumData = new EnumExtensionData();
            enumData.modelAsString(jsonObj.getBoolean("modelAsString"));
        }else if(key.toLowerCase().equals("x-ms-parameter-location")) {
            ParameterLocationExtensionData  paramData = new ParameterLocationExtensionData();
            paramData.location(jsonObj.toString());
        }else {
            this.value = new ExtensionData();
        }

        this.value.data(jsonObj.get(key));
        return this;
    }

    public String name() {
        return this.name;
    }

    public ExtensionData value() {
        return this.value;
    }

    public static class ExtensionData {
        private JsonValue data;
        public void data(JsonValue data) {this.data = data;}
        public JsonValue data() { return this.data; }
    }

    public static class EnumExtensionData extends ExtensionData {
        private static String extensionKey = "x-ms-enum";
        private boolean modelAsString;

        public static String getExtensionKey() { return extensionKey; }
        public boolean modelAsString() { return this.modelAsString; }
        public void modelAsString(boolean modelAsString) { this.modelAsString = modelAsString; }
    }

    public static class ParameterLocationExtensionData extends ExtensionData {
        private static String extensionKey = "x-ms-parameter-location";
        private String location;

        public static String getExtensionKey() { return extensionKey; }
        public String location() { return this.location; }
        public void location(String location) { this.location = location; }
    }
}
