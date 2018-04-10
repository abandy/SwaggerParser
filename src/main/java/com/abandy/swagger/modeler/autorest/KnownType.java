package com.abandy.swagger.modeler.autorest;

import java.util.Objects;

public enum KnownType {
    None("none"),
    Object("object"),
    Int("int", "integer", "number"),
    Long("long"),
    Double("double"),
    Decimal("decimal"),
    _String("string"),
    Stream("stream"),
    ByteArray("nytearray"),
    Date("date"),
    DateTime("datetime"),
    DateTimeRfc1123("datetimerfc1123"),
    TimeSpan("timespan"),
    Boolean("boolean"),
    Credentials("credentials"),
    Uuid("uuid"),
    Base64Url("base64url"),
    UnixTime("unixtime"),
    Array("array");

    private String[] typeName;
    KnownType(String... typeName) {
        this.typeName = typeName;
    }

    public static KnownType forString(String typeName) {
        Objects.requireNonNull(typeName);
        typeName = typeName.toLowerCase();
        for(KnownType kpType : KnownType.values()) {
            for(String kpTypeName : kpType.typeName) {
                if (kpTypeName.equals(typeName)) {
                    return kpType;
                }
            }
        }

        throw new RuntimeException(String.format("KnownType %s not found.", typeName));
    }
}
