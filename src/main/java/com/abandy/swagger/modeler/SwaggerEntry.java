package com.abandy.swagger.modeler;

import java.util.HashMap;
import java.util.Map;

public class SwaggerEntry {
    private final SwaggerEntryType type;
    private final String swaggerUrl;
    private String packageName;
    private String managerName;
    private Map<String, String> queryParams = new HashMap<String, String>();

    private SwaggerEntry(SwaggerEntryType type, String packageName, String swaggerUrl) {
        this.type = type;
        this.packageName = packageName;
        this.swaggerUrl = this.processQueryParams(swaggerUrl);

    }

    private SwaggerEntry(SwaggerEntryType type, String managerName, String packageName, String swaggerUrl) {
        this(type, packageName, swaggerUrl);
        this.managerName = managerName;
    }

    public static SwaggerEntry parse(String entry) {
        String[] lineParts = entry.split(";");
        if (lineParts.length == 2){
            return new SwaggerEntry(SwaggerEntryType.PACKAGE_AND_URL, lineParts[0], lineParts[1]);
        } else if(lineParts.length == 3){
            return new SwaggerEntry(SwaggerEntryType.PACKAGE_MANAGER_AND_URL, lineParts[0], lineParts[1], lineParts[2]);
        } else {
            String packageName = entry.substring(entry.lastIndexOf("/") + 1, entry.lastIndexOf("."));
            if (entry.contains("specification/")) {
                int startIndex = entry.indexOf("specification/") + 14;
                packageName = entry.substring(startIndex, entry.indexOf("/", startIndex));
                String[] packageNameParts = packageName.split("-");
                StringBuilder packageBuilder = new StringBuilder();
                if (packageNameParts.length > 0) {
                    packageBuilder.append(packageNameParts[0]);
                }

                for (int i = 1; i < packageNameParts.length; i++) {
                    if (!packageNameParts[i].equals("-")) {
                        packageBuilder.append(Character.toUpperCase(packageNameParts[i].charAt(0)));
                        packageBuilder.append(packageNameParts[i].substring(1));
                    }
                }

                packageName = packageBuilder.toString();
            }
            packageName = packageName.replaceAll("-", "");
            return new SwaggerEntry(SwaggerEntryType.PACKAGE_AND_URL, packageName, entry);
        }
    }

    public SwaggerEntryType type() {
        return this.type;
    }

    public String packageName() {
        return this.packageName;
    }

    public String managerName() {
        return this.managerName;
    }

    public String swaggerUrl() {
        return this.swaggerUrl;
    }

    public boolean isReadMe() {
        return this.swaggerUrl.endsWith("readme.md");
    }

    public Map<String, String> queryParams() {
        return this.queryParams;
    }

    private String processQueryParams(String swaggerUrl) {
        int index = swaggerUrl.indexOf("?");
        if(index > 1) {
            String[] queryParams = swaggerUrl.substring(index + 1).split("&");
            for(String queryParam : queryParams) {
                String[] parts = queryParam.split("=");
                this.queryParams.put(parts[0],
                        parts.length > 1 ? parts[1]: "");
            }
            swaggerUrl = swaggerUrl.substring(0, index);
        }

        return swaggerUrl;
    }
}
