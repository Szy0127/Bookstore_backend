package com.web.bookstore.bookstore_backend.config;


import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrConfig {


    @Value("${solr.url}")
    private String url;

    @Value("${solr.collection")
    public String collection;
    @Bean
    public SolrClient solrClient() {
//        final String solrUrl = "http://localhost:8983/solr";
        return new HttpSolrClient.Builder(url)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
    }
}
