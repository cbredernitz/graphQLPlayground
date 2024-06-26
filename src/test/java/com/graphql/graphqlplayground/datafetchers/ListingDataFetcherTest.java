package com.graphql.graphqlplayground.datafetchers;


import com.graphql.graphqlplayground.models.ListingModel;
import com.graphql.graphqlplayground.service.ListingService;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = {DgsAutoConfiguration.class, ListingDataFetcher.class})
class ListingDataFetcherTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @MockBean
    ListingService listingService;

    @BeforeEach
    public void before() {
        ListingModel model = new ListingModel();
        model.setId("1");
        model.setTitle("Beach house on the edge of the Laertes meteor");
        model.setCostPerNight(360.00);
        model.setClosedForBookings(false);
        model.setNumOfBeds(3);
        Mockito.when(listingService.getListings()).thenAnswer(invocation -> List.of(model));
    }

    @Test
    void givenGetListing_whenQueried_ThenReturnStubTitle() {
        List<String> listingModels = dgsQueryExecutor.executeAndExtractJsonPath(
                " { featuredListings { title }}",
                "data.featuredListings[*].title");

        assertThat(listingModels).contains("Beach house on the edge of the Laertes meteor");
    }
}