package com.graphql.graphqlplayground.service;

import com.graphql.graphqlplayground.models.ListingModel;
import com.netflix.graphql.dgs.InputArgument;

import java.io.IOException;
import java.util.List;

public interface ListingService {
    List<ListingModel> featuredListingsRequest() throws IOException;
    ListingModel listingRequest(@InputArgument String id);
}
