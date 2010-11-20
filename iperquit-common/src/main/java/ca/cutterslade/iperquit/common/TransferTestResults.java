package ca.cutterslade.iperquit.common;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public final class TransferTestResults extends TestResults {

  public TransferTestResults(TestDefinition test, Map<Long, Long> transferStats) {
    super(test);
    this.transferStats = ImmutableMap.copyOf(transferStats);
  }

  private final ImmutableMap<Long, Long> transferStats;

  /**
   * @return the transferStats
   */
  public ImmutableMap<Long, Long> getTransferStats() {
    return transferStats;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((transferStats == null) ? 0 : transferStats.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (getClass() != obj.getClass()) return false;
    TransferTestResults other = (TransferTestResults) obj;
    if (transferStats == null) {
      if (other.transferStats != null) return false;
    }
    else if (!transferStats.equals(other.transferStats)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "TransferTestResults [test=" + getTest() + ", transferStats=" + transferStats + "]";
  }

}
