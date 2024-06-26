package com.graphql.graphqlplayground.datafetchers;


import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = {DgsAutoConfiguration.class, ListingDataFetcher.class})
class ListingDataFetcherTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    void givenGetListing_whenQueried_ThenReturnStubTitle() {
        List<String> listingModels = dgsQueryExecutor.executeAndExtractJsonPath(
                " { featuredListings { title }}",
                "data.featuredListings[*].title");

        assertThat(listingModels).contains("Beach house on the edge of the Laertes meteor");
    }
}