package ca.cutterslade.iperquit.common;

public abstract class TestResults {

  private final TestDefinition test;

  TestResults(TestDefinition test) {
    this.test = test;
  }

  /**
   * @return the test
   */
  public TestDefinition getTest() {
    return test;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((test == null) ? 0 : test.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    TestResults other = (TestResults) obj;
    if (test == null) {
      if (other.test != null) return false;
    }
    else if (!test.equals(other.test)) return false;
    return true;
  }

}
