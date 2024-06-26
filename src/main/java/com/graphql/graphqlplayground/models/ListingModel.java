package com.graphql.graphqlplayground.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.graphql.graphqlplayground.codegen.types.Listing;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListingModel extends Listing {
}
