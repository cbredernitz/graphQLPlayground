package com.graphql.graphqlplayground.datafetchers;

import com.graphql.graphqlplayground.models.ListingModel;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

import java.util.List;

@DgsComponent
public class ListingDataFetcher {

    @DgsQuery
    public List<ListingModel> featuredListings() {
        ListingModel meteorListing = new ListingModel();
        meteorListing.setId("1");
        meteorListing.setTitle("Beach house on the edge of the Laertes meteor");
        meteorListing.setCostPerNight(360.00);
        meteorListing.setClosedForBookings(false);
        meteorListing.setNumOfBeds(3);

        return List.of(meteorListing);
    }
}
