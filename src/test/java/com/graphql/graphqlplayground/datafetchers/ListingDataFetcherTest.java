package com.graphql.graphqlplayground.datafetchers;


import com.graphql.graphqlplayground.codegen.types.Amenity;
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

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = {DgsAutoConfiguration.class, ListingDataFetcher.class})
class ListingDataFetcherTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @MockBean
    ListingService listingService;

    @BeforeEach
    public void before() throws IOException {
        Amenity amenity = new Amenity();
        amenity.setId("am-1");
        amenity.setCategory("In Unit Washer and Dryer");
        amenity.setName("Gorlock the washing unit");

        ListingModel model0 = new ListingModel();
        model0.setId("1");
        model0.setTitle("Beach house on the edge of the Laertes meteor");
        model0.setCostPerNight(360.00);
        model0.setClosedForBookings(false);
        model0.setNumOfBeds(3);
        model0.setAmenities(List.of(amenity));

        ListingModel model1 = new ListingModel();
        model1.setId("2");
        model1.setTitle("Mountain house on the edge of the Moon");
        model1.setCostPerNight(130.00);
        model1.setClosedForBookings(true);
        model1.setNumOfBeds(4);
        model1.setAmenities(List.of(amenity));
        Mockito.when(listingService.featuredListingsRequest()).thenAnswer(invocation -> List.of(model0, model1));

        ListingModel model2 = new ListingModel();
        model2.setId("3");
        model2.setTitle("Lake front property on the crater of Azul");
        model2.setCostPerNight(965.00);
        model2.setClosedForBookings(false);
        model2.setNumOfBeds(2);
        model2.setAmenities(List.of(amenity));
        Mockito.when(listingService.listingRequest( "3")).thenAnswer(invocation -> model2);
    }

    @Test
    void givenGetFeaturedListings_whenQueried_ThenReturnStubTitle() {
        List<String> listingModels = dgsQueryExecutor.executeAndExtractJsonPath(
                " { featuredListings { title }}",
                "data.featuredListings[*].title");

        assertThat(listingModels).contains("Beach house on the edge of the Laertes meteor");
        assertThat(listingModels).hasSize(2);
    }

    @Test
    void givenGetListing_whenQueried_ThenReturnStubTitle() {
        Map<String, Object> variables = Collections.singletonMap("listingId", "3");
        String listingTitles = dgsQueryExecutor.executeAndExtractJsonPath(
                "query Listing($listingId: ID!) { listing(id: $listingId) { title amenities { name category }}}",
                "data.listing.title",
                variables);

        assertThat(listingTitles).isEqualTo("Lake front property on the crater of Azul");
    }

    @Test
    void givenGetAmenities_whenQueried_ThenReturnStubTitle() {
        Map<String, Object> variables = Collections.singletonMap("listingId", "3");
        String listingTitles = dgsQueryExecutor.executeAndExtractJsonPath(
                "query Listing($listingId: ID!) { listing(id: $listingId) { title amenities { name category }}}",
                "data.listing.title",
                variables);

        assertThat(listingTitles).isEqualTo("Lake front property on the crater of Azul");
    }
}