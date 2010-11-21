package ca.cutterslade.iperquit.common;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;

public final class TransferTestResults extends TestResults {

  public static final class Builder {

    private static final Long START_TIME_KEY = Long.MIN_VALUE;

    private static final Long STOP_TIME_KEY = Long.MIN_VALUE + 1;

    private static final AtomicLong DUMBY = new AtomicLong();

    private static final ImmutableSet<Long> FAKE_KEYS = ImmutableSet.of(START_TIME_KEY, STOP_TIME_KEY);

    private final TestDefinition test;

    private final ConcurrentMap<Long, AtomicLong> stats = new MapMaker().makeComputingMap(new Function<Long, AtomicLong>() {

      @Override
      public AtomicLong apply(Long input) {
        return new AtomicLong();
      }
    });

    Builder(TestDefinition test) {
      this.test = test;
    }

    public Builder start() {
      Preconditions.checkState(null == stats.putIfAbsent(START_TIME_KEY, DUMBY), "start() method has already been called");
      stats.put(System.currentTimeMillis(), DUMBY);
      return this;
    }

    public void transfered(long bytes) {
      checkStarted();
      checkNotDone();
      stats.get(System.currentTimeMillis()).addAndGet(bytes);
    }

    public TransferTestResults build() {
      done(true);
      return new TransferTestResults(test, cleanStats());
    }

    public TransferTestResults build(Throwable throwable) {
      done(false);
      return new TransferTestResults(test, cleanStats(), throwable);
    }

    Map<Long, Long> cleanStats() {
      ImmutableMap.Builder<Long, Long> b = ImmutableMap.builder();
      for (Map.Entry<Long, AtomicLong> e : Maps.filterKeys(stats, Predicates.not(Predicates.in(FAKE_KEYS))).entrySet()) {
        b.put(e.getKey(), e.getValue().get());
      }
      return b.build();
    }

    void done(boolean mustHaveStarted) {
      if (mustHaveStarted) checkStarted();
      Preconditions.checkState(null == stats.putIfAbsent(STOP_TIME_KEY, DUMBY), "build() method has already been called");
    }

    void checkStarted() {
      Preconditions.checkState(!stats.isEmpty(), "start() method has not been called");
    }

    void checkNotDone() {
      Preconditions.checkState(!stats.containsKey(STOP_TIME_KEY), "build() method has already been called");
    }
  }

  private final ImmutableMap<Long, Long> transferStats;

  TransferTestResults(TestDefinition test, Map<Long, Long> transferStats) {
    super(test);
    this.transferStats = ImmutableMap.copyOf(transferStats);
  }

  TransferTestResults(TestDefinition test, Map<Long, Long> transferStats, Throwable throwable) {
    super(test, throwable);
    this.transferStats = ImmutableMap.copyOf(transferStats);
  }

  public static Builder builder(TestDefinition test) {
    return new Builder(test);
  }

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
