package uk.ac.ebi.pride.solr.indexes.pride;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SolrApiApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SolrApiApplication.class, args);
    }
}
