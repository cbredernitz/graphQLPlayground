package com.graphql.graphqlplayground.datafetchers;

import com.graphql.graphqlplayground.models.ListingModel;
import com.graphql.graphqlplayground.service.ListingService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@DgsComponent
public class ListingDataFetcher {

    @Autowired
    private ListingService listingService;

    @DgsQuery
    public List<ListingModel> featuredListings() throws IOException {
        return listingService.featuredListingsRequest();
    }

    @DgsQuery
    public ListingModel listing(@InputArgument String id) {
        return listingService.listingRequest(id);
    }

}
