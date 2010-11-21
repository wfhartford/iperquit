package ca.cutterslade.iperquit.common;

import java.net.URI;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;

public final class TestDefinition {

  private static final Function<String, Integer> INTEGER_CONVERSION = new Function<String, Integer>() {

    @Override
    public Integer apply(String input) {
      return Integer.valueOf(input);
    }
  };

  private static final Function<String, Long> LONG_CONVERSION = new Function<String, Long>() {

    @Override
    public Long apply(String input) {
      return Long.valueOf(input);
    }
  };

  private static final Function<String, URI> URI_CONVERSION = new Function<String, URI>() {

    @Override
    public URI apply(String input) {
      return URI.create(input);
    }
  };

  private final String testClass;

  private final ImmutableMap<String, String> testOptions;

  public TestDefinition(String testClass, Map<String, String> testOptions) {
    this.testClass = testClass;
    this.testOptions = ImmutableMap.copyOf(testOptions);
  }

  /**
   * @return the testClass
   */
  public String getTestClass() {
    return testClass;
  }

  /**
   * @return the testOptions
   */
  public ImmutableMap<String, String> getTestOptions() {
    return testOptions;
  }

  public <T> T getOption(String key, T dflt, Function<String, T> conversion) {
    String value = testOptions.get(key);
    return null == value ? dflt : conversion.apply(value);
  }

  public String getString(String key, String dflt) {
    return getOption(key, dflt, Functions.<String> identity());
  }

  public Integer getInteger(String key, Integer dflt) {
    return getOption(key, dflt, INTEGER_CONVERSION);
  }

  public Long getLong(String key, Long dflt) {
    return getOption(key, dflt, LONG_CONVERSION);
  }

  public URI getURI(String key, URI dflt) {
    return getOption(key, dflt, URI_CONVERSION);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((testClass == null) ? 0 : testClass.hashCode());
    result = prime * result + ((testOptions == null) ? 0 : testOptions.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    TestDefinition other = (TestDefinition) obj;
    if (testClass == null) {
      if (other.testClass != null) return false;
    }
    else if (!testClass.equals(other.testClass)) return false;
    if (testOptions == null) {
      if (other.testOptions != null) return false;
    }
    else if (!testOptions.equals(other.testOptions)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "TestDefinition [testClass=" + testClass + ", testOptions=" + testOptions + "]";
  }

}
