package com.graphql.graphqlplayground.service;

import com.graphql.graphqlplayground.models.ListingModel;

import java.util.List;

public interface ListingService {
    List<ListingModel> getListings();
}
