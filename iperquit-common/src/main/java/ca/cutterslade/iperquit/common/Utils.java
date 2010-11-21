package ca.cutterslade.iperquit.common;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

public final class Utils {

  private static final Logger log = LoggerFactory.getLogger(Utils.class);

  private static final String NULL_CHECK_FORMAT = "%s may not be null";

  private Utils() {
    throw new UnsupportedOperationException("Instantiation is not permitted");
  }

  public static void validateTestDefinition(TestDefinition definition, Test test) {
    Preconditions.checkArgument(null != definition, NULL_CHECK_FORMAT, "definition");
    Preconditions.checkArgument(null != test, NULL_CHECK_FORMAT, "test");
    Preconditions.checkArgument(test.getClass().getName().equals(definition.getTestClass()), "TestDefinition specifies class '%s' not '%s'", test.getClass().getName(), definition.getTestClass());
    Preconditions.checkArgument(definition.getTestOptions().keySet().containsAll(test.getRequiredOptions()), "TestDefinition is missing required options %s", Sets.difference(test.getRequiredOptions(), definition.getTestOptions().keySet()));
    if (test instanceof DefinitionValidatingTest) ((DefinitionValidatingTest) test).validateTestDefinition(definition);
  }

  public static double getAverageBytesPerSecond(Map<Long, Long> stats) {
    long minTime = Long.MAX_VALUE;
    long maxTime = Long.MIN_VALUE;
    long bytes = 0;
    for (Map.Entry<Long, Long> e : stats.entrySet()) {
      if (e.getKey() < minTime) minTime = e.getKey();
      if (e.getKey() > maxTime) maxTime = e.getKey();
      bytes += e.getValue();
    }
    long ms = maxTime - minTime;
    log.debug("Transfer {} bytes in {}ms", bytes, ms);
    return bytes / (ms / 1000d);
  }
}
