package org.automotive.utils;

import java.util.Objects;

public final class EnvVarUtils {

    public static String getStringOrDefault(String envVarName, String defReturn) {
        String envVarValue = System.getenv(envVarName);
        return Objects.isNull(envVarValue) ? defReturn : envVarValue;
    }

}
