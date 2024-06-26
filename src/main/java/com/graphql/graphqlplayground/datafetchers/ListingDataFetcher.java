package com.graphql.graphqlplayground.datafetchers;

import com.graphql.graphqlplayground.codegen.types.Amenity;
import com.graphql.graphqlplayground.models.ListingModel;
import com.graphql.graphqlplayground.service.ListingService;
import com.netflix.graphql.dgs.*;
import graphql.execution.DataFetcherResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@DgsComponent
public class ListingDataFetcher {

    @Autowired
    private ListingService listingService;

    @DgsQuery
    public DataFetcherResult<List<ListingModel>> featuredListings() throws IOException {
        List<ListingModel> listings = listingService.featuredListingsRequest();
        return DataFetcherResult.<List<ListingModel>>newResult()
                .data(listings)
                .localContext(Map.of("hasAmenityData", false))
                .build();
    }

    @DgsQuery
    public DataFetcherResult<ListingModel> listing(@InputArgument String id) {
        ListingModel listing = listingService.listingRequest(id);
        return DataFetcherResult.<ListingModel>newResult()
                .data(listing)
                .localContext(Map.of("hasAmenityData", true))
                .build();
    }

    @DgsData(parentType="Listing")
    public List<Amenity> amenities(DgsDataFetchingEnvironment dfe) throws IOException {
        ListingModel listing = dfe.getSource();
        String id = listing.getId();
        Map<String, Boolean> localContext = dfe.getLocalContext();

        if (localContext.get("hasAmenityData")) {
            return listing.getAmenities();
        }

        return listingService.amenitiesRequest(id);
    }
}
