package com.abandy.swagger.modeler.autorest;

import com.abandy.swagger.modeler.swagger.Extension;
import com.abandy.swagger.modeler.swagger.Schema;

import java.util.ArrayList;
import java.util.List;

public class EnumType extends ModelType {
    private List<EnumValue> values = new ArrayList<EnumValue>();;
    private boolean modelAsString;
    private PrimaryType underlyingType;

    public EnumType(CodeModel codeModel) {
        this.codeModel = codeModel;
    }

    @Override
    public void consume(Schema schema) {
        this.name = schema.name();
        String extensionKey = Extension.EnumExtensionData.getExtensionKey();
        KnownType knownType = KnownType.forString(schema.type());
        this.underlyingType = new PrimaryType(schema.format(), knownType);

        if(schema.extensions().containsKey(extensionKey)) {
            Extension.EnumExtensionData enumExtensionData =
                    (Extension.EnumExtensionData)schema.extensions().get(extensionKey).value();
            this.modelAsString = enumExtensionData.modelAsString();
        }

        for (String enumValueStr : schema.enumValues()) {
            EnumValue enumValue = new EnumValue();
            enumValue.memberName(enumValueStr);
        }
    }

    public List<EnumValue> values() { return this.values; }

    public PrimaryType getUnderlyingType() { return this.underlyingType; }

    public boolean modelAsString() { return this.modelAsString; }
}
