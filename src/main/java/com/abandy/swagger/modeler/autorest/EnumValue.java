package com.abandy.swagger.modeler.autorest;

public class EnumValue extends ModelType {
    public void memberName(String memberName) {
        this.name = memberName;
    }

    public String memberName() {
        return this.name;
    }
}
