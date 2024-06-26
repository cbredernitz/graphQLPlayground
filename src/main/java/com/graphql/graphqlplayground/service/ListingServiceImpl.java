package com.graphql.graphqlplayground.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphql.graphqlplayground.models.ListingModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ListingServiceImpl implements ListingService {
    private final String LISTING_API_URL = "https://rt-airlock-services-listing.herokuapp.com/";
    private final RestClient client = RestClient.builder().baseUrl(LISTING_API_URL).build();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<ListingModel> featuredListingsRequest() throws IOException {
        try {
            JsonNode response = client
                    .get()
                    .uri("/featured-listings")
                    .retrieve()
                    .body(JsonNode.class);


            if (response != null) {
                return mapper.readValue(response.traverse(), new TypeReference<List<ListingModel>>() {});
            }
        } catch (IOException e) {
            log.error("There was an exception with the response from featuredListingsRequest", e);
        }
        return List.of();
    }
}
