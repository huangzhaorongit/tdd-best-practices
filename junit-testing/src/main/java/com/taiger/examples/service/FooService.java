package com.taiger.examples.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taiger.examples.dao.FooDao;

@Service
public class FooService {

	@Autowired
	private FooDao fooDao;

	private static final String ELASTICSEARCH_HOST = "localhost";
	private static final int ELASTICSEARCH_PORT = 9300;

	private Client client;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@SuppressWarnings("resource")
	@PostConstruct
	public void init() {
		client = new TransportClient()
				.addTransportAddress(new InetSocketTransportAddress(ELASTICSEARCH_HOST, ELASTICSEARCH_PORT));

	}

	public String generateMessage() {
		return fooDao.getMessage();
	}

	public List<String> search() {
		List<String> sourceList = new ArrayList<String>();
		SearchResponse response = client.prepareSearch("taiger").setTypes("message")
				.setQuery(QueryBuilders.termQuery("user", "taiger")).setFrom(0).setSize(10).execute().actionGet();
		if (response != null && response.getHits() != null) {
			for (SearchHit hits : response.getHits()) {
				sourceList.add(hits.getSourceAsString());
			}
		}
		return sourceList;
	}

}
