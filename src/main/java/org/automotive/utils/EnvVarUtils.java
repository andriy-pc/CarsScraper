package org.automotive.utils;

import static java.util.Objects.isNull;

import java.util.function.Supplier;

public final class EnvVarUtils {

  public static String getStringOrDefault(String envVarName, String defReturn) {
    String envVarValue = System.getenv(envVarName);
    return isNull(envVarValue) ? defReturn : envVarValue;
  }

  public static String getStringOrException(
      String envVarName, Supplier<? extends RuntimeException> exceptionSupplier) {
    String envVarValue = System.getenv(envVarName);
    if (isNull(envVarValue)) {
      throw exceptionSupplier.get();
    }
    return envVarValue;
  }
}
