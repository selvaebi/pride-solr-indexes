package uk.ac.ebi.pride.solr.indexes.pride.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.core.Is;
import org.junit.Assume;
import org.junit.AssumptionViolatedException;
import org.junit.runners.model.Statement;
import org.junit.rules.TestRule;
import org.junit.runner.Description;

import java.io.IOException;

/**
 * @author ypriverol
 */
public class RequireSolrServerTest implements TestRule{

    private static final String PING_PATH = "/admin/info/system";

    private final String baseUrl;

    private RequireSolrServerTest(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public static RequireSolrServerTest onLocalhost() {
        return new RequireSolrServerTest("http://localhost:8983/solr");
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {

                checkServerRunning();
                base.evaluate();
            }
        };
    }

    private void checkServerRunning() {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            CloseableHttpResponse response = client.execute(new HttpGet(baseUrl + PING_PATH));
            if (response != null && response.getStatusLine() != null) {
                Assume.assumeThat(response.getStatusLine().getStatusCode(), Is.is(200));
            }
        } catch (IOException e) {
            throw new AssumptionViolatedException("SolrServer does not seem to be running", e);
        }
    }

}
