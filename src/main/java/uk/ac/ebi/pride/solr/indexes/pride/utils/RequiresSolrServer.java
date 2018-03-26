/*
 * Copyright 2014-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ebi.pride.solr.indexes.pride.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * @author ypriverol
 */
public class RequiresSolrServer {

	private static final String PING_PATH = "/admin/info/system";

	private final String baseUrl;

	private RequiresSolrServer(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * Returns a Solr Server in localhost with, the process create the Solr Service and
	 * checks that the server is running properly.
	 *
	 * @return RequiresSolrServer
	 */
	public static RequiresSolrServer onLocalhost(){
		RequiresSolrServer solrServer = new RequiresSolrServer("http://localhost:8983/solr");
		try {
			solrServer.checkServerRunning();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return solrServer;
	}


	/**
	 * Checking the Solr Server in localhost and check that the status of returns in 200.
	 *
	 * @throws IOException
	 */
	private void checkServerRunning() throws IOException {

		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			CloseableHttpResponse response = client.execute(new HttpGet(baseUrl + PING_PATH));
			if (response != null && response.getStatusLine() != null && response.getStatusLine().getStatusCode() != 200) {
				throw new IOException("The SolrServer in localhost does not seem to be running");
			}
		} catch (IOException e) {
			throw new IOException("The SolrServer in localhost does not seem to be running", e);
		}
	}

}
