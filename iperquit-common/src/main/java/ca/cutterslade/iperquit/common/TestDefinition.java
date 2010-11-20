package ca.cutterslade.iperquit.common;

import com.google.common.collect.ImmutableMap;

public final class TestDefinition {

  private final String testClass;

  private final ImmutableMap<String, String> testOptions;

  public TestDefinition(String testClass, ImmutableMap<String, String> testOptions) {
    this.testClass = testClass;
    this.testOptions = testOptions;
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
