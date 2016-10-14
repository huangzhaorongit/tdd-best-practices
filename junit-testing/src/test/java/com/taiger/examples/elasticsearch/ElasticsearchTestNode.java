package com.taiger.examples.elasticsearch;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.junit.rules.ExternalResource;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackages = "com.taiger.examples")
public class ElasticsearchTestNode extends ExternalResource {

	private Node node;
	private Path dataDirectory;

	@Override
	public void before() throws Throwable {
		try {

			dataDirectory = Files.createTempDirectory("es-test", new FileAttribute[] {});

		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}

		node = NodeBuilder.nodeBuilder().local(true).clusterName("isearch-testing-node")
				.settings(ImmutableSettings.settingsBuilder().put("http.enabled", false).put("gateway.type", "local")
						.put("index.number_of_shards", "1").put("index.number_of_replicas", "0")
						.put("discovery.zen.ping.multicast.enabled", false)
						.put("path.data", new File(dataDirectory.toString(), "data").getAbsolutePath())
						.put("path.logs", new File(dataDirectory.toString(), "logs").getAbsolutePath())
						.put("path.work", new File(dataDirectory.toString(), "work").getAbsolutePath()))
				.node();

		indexSearch();
	}

	@Override
	public void after() {
		getClient().admin().indices().prepareDelete("_all").get();
		node.stop();
		node.close();
		try {
			FileUtils.deleteDirectory(dataDirectory.toFile());
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
	}

	public Client getClient() {
		return node.client();
	}

	private void indexSearch() throws Exception {

		// Get mapping file
		// File mapping = new
		// File(this.getClass().getResource("mapping.json").getFile());

		// Get settings file
		// File settings = new
		// File(this.getClass().getResource("settings.json").getFile());

		if (getClient().admin().indices().prepareExists("taiger").execute().actionGet().isExists()) {
			assertTrue(deleteIndex("taiger"));
		}

		CreateIndexRequestBuilder createIndexRequestBuilder = getClient().admin().indices().prepareCreate("taiger");

		// Create Settings
		// String settingsJson = new
		// String(Files.readAllBytes(settings.toPath()));

		// Create Mappings
		// String mappingJson = new
		// String(Files.readAllBytes(mapping.toPath()));

		// createIndexRequestBuilder.setSettings(settingsJson);

		// createIndexRequestBuilder.addMapping("message", mappingJson);

		CreateIndexResponse indexResponse = createIndexRequestBuilder.execute().actionGet();

		assertNotNull("IndexResponse is null", indexResponse);

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		
		String json = "{" + "\"user\":\"taiger\"," + "\"postDate\":\"" + dateFormat.format(date) + "\","
				+ "\"message\":\"trying out Elasticsearch\"" + "}";
		IndexResponse response = getClient().prepareIndex("taiger", "message").setSource(json).execute().actionGet();
		assertNotNull("The item can not be created in elasticsearch", response);
		assertNotNull(response.getId());

		refreshIndex("taiger");

	}

	private boolean deleteIndex(String indexName) {

		DeleteIndexRequest request = new DeleteIndexRequest(indexName);

		DeleteIndexResponse response = getClient().admin().indices().delete(request).actionGet();
		assertTrue(response.isAcknowledged());
		return response.isAcknowledged();

	}

	public void refreshIndex(String index) {
		getClient().admin().indices().refresh(new RefreshRequest(index)).actionGet();
	}

}
