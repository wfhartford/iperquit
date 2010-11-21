package ca.cutterslade.iperquit.common;

import com.google.common.collect.ImmutableSet;

public interface Test {

  ImmutableSet<String> getAvailableOptions();

  ImmutableSet<String> getRequiredOptions();

  TestResults performTest(TestDefinition definition);
}
