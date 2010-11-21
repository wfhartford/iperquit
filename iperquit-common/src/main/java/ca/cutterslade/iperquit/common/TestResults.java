package ca.cutterslade.iperquit.common;

public abstract class TestResults {

  private final TestDefinition test;

  private final Throwable throwable;

  TestResults(TestDefinition test) {
    this.test = test;
    this.throwable = null;
  }

  TestResults(TestDefinition test, Throwable throwable) {
    this.test = test;
    this.throwable = throwable;
  }

  /**
   * @return the test
   */
  public TestDefinition getTest() {
    return test;
  }

  /**
   * @return the throwable
   */
  public Throwable getThrowable() {
    return throwable;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((test == null) ? 0 : test.hashCode());
    result = prime * result + ((throwable == null) ? 0 : throwable.hashCode());
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
    if (throwable == null) {
      if (other.throwable != null) return false;
    }
    else if (!throwable.equals(other.throwable)) return false;
    return true;
  }

}
