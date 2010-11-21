package ca.cutterslade.iperquit.test.http.get;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.cutterslade.iperquit.common.TestDefinition;
import ca.cutterslade.iperquit.common.TransferTestResults;
import ca.cutterslade.iperquit.common.Utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RunWith(Parameterized.class)
public class VariousRequestsTest {

  private static final Logger log = LoggerFactory.getLogger(VariousRequestsTest.class);

  private static final ImmutableList<String[]> PARAMETERS;
  static {
    List<String> l = Lists.newArrayList();
    l.add("http://www.google.com/");
    l.add("http://repo2.maven.org/maven2/org/slf4j/slf4j-api/1.6.1/slf4j-api-1.6.1.jar");
    l.add("http://repo2.maven.org/maven2/org/slf4j/slf4j-api/1.6.1/slf4j-api-1.6.1-javadoc.jar");
    l.add("http://google-web-toolkit.googlecode.com/files/gwt-2.1.0.zip");
    ImmutableList.Builder<String[]> builder = ImmutableList.builder();
    for (String v : l) {
      builder.add(new String[] { v });
    }
    PARAMETERS = builder.build();
  }

  @Parameters
  public static Collection<String[]> getParameters() {
    return PARAMETERS;
  }

  private final String uri;

  public VariousRequestsTest(String uri) {
    this.uri = uri;
  }

  @Test
  public void test() {
    Map<String, String> opts = Maps.newHashMap();
    opts.put(HttpGetTest.URI_KEY, uri);
    TestDefinition td = new TestDefinition(HttpGetTest.class.getName(), opts);
    TransferTestResults results = new HttpGetTest().performTest(td);
    log.info("Average: {}B/s: {}", Utils.getAverageBytesPerSecond(results.getTransferStats()), results);
  }
}
