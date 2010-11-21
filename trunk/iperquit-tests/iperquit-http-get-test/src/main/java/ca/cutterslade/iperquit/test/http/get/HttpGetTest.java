package ca.cutterslade.iperquit.test.http.get;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.cutterslade.iperquit.common.DefinitionValidatingTest;
import ca.cutterslade.iperquit.common.TestDefinition;
import ca.cutterslade.iperquit.common.TransferTestResults;
import ca.cutterslade.iperquit.common.Utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

public class HttpGetTest implements DefinitionValidatingTest {

  private static final Logger log = LoggerFactory.getLogger(HttpGetTest.class);

  private static final class Handler implements ResponseHandler<TransferTestResults> {

    private final TestDefinition test;

    private final TransferTestResults.Builder builder;

    Handler(TestDefinition test) {
      this.test = test;
      this.builder = TransferTestResults.builder(test);
    }

    @Override
    public TransferTestResults handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
      builder.start();
      InputStream s = response.getEntity().getContent();
      byte[] b = new byte[getChunkSize(test)];
      for (int bread = s.read(b); -1 != bread; bread = s.read(b)) {
        builder.transfered(bread);
      }
      return builder.build();
    }

    public TransferTestResults throwable(Throwable throwable) {
      return builder.build(throwable);
    }

  }

  private static final String FQN = HttpGetTest.class.getName();

  public static final String URI_KEY = FQN + ".uri";

  public static final String CONNECT_TIMEOUT_KEY = FQN + ".connectTimeout";

  public static final Long DFLT_CONNECT_TIMEOUT = Long.valueOf(TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES));

  public static final String READ_TIMEOUT_KEY = FQN + ".readTimeout";

  public static final Long DFLT_READ_TIMEOUT = Long.valueOf(TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES));

  public static final String CHUNK_SIZE_KEY = FQN + ".chunkSize";

  public static final Integer DFLT_CHUNK_SIZE = Integer.valueOf(1024 * 16);

  private static final ImmutableSet<String> REQUIRED = ImmutableSet.of(URI_KEY);

  private static final ImmutableSet<String> AVAILABLE = ImmutableSet.<String> builder().addAll(REQUIRED).add(CONNECT_TIMEOUT_KEY, READ_TIMEOUT_KEY).build();

  @Override
  public TransferTestResults performTest(TestDefinition definition) {
    Utils.validateTestDefinition(definition, this);
    DefaultHttpClient client = new DefaultHttpClient();
    Handler handler = new Handler(definition);
    try {
      HttpGet get = new HttpGet(getUri(definition));
      return client.execute(get, handler);
    }
    catch (Exception e) {
      return handler.throwable(e);
    }
    finally {
      client.getConnectionManager().shutdown();
    }
  }

  @Override
  public void validateTestDefinition(TestDefinition definition) {
    getUri(definition);
    greaterThanZero(getConnectTimeout(definition), CONNECT_TIMEOUT_KEY);
    greaterThanZero(getReadTimeout(definition), READ_TIMEOUT_KEY);
    greaterThanZero(getChunkSize(definition), CHUNK_SIZE_KEY);
  }

  private URI getUri(TestDefinition definition) {
    return definition.getURI(URI_KEY, null);
  }

  private static long getConnectTimeout(TestDefinition definition) {
    return definition.getLong(CONNECT_TIMEOUT_KEY, DFLT_CONNECT_TIMEOUT).longValue();
  }

  private static long getReadTimeout(TestDefinition definition) {
    return definition.getLong(READ_TIMEOUT_KEY, DFLT_READ_TIMEOUT).longValue();
  }

  private static int getChunkSize(TestDefinition definition) {
    return definition.getInteger(CHUNK_SIZE_KEY, DFLT_CHUNK_SIZE);
  }

  private static void greaterThanZero(long number, String name) {
    Preconditions.checkArgument(number > 0, "%s must be greater than zero: %d", name, number);
  }

  @Override
  public ImmutableSet<String> getRequiredOptions() {
    return REQUIRED;
  }

  @Override
  public ImmutableSet<String> getAvailableOptions() {
    return AVAILABLE;
  }

}
