package com.anujpatel.restaurantfinder;

import org.scribe.builder.ServiceBuilder;
        import org.scribe.model.OAuthRequest;
        import org.scribe.model.Response;
        import org.scribe.model.Token;
        import org.scribe.model.Verb;
        import org.scribe.oauth.OAuthService;

public class YelpConnector {

    OAuthService service;
    Token accessToken;

    /**
     * Setup the Yelp API OAuth credentials.
     *
     * OAuth credentials are available from the developer site, under Manage API access (version 2 API).
     *
     * @param consumerKey Consumer key
     * @param consumerSecret Consumer secret
     * @param token Token
     * @param tokenSecret Token secret
     */
    public YelpConnector(String consumerKey, String consumerSecret, String token, String tokenSecret) {
        this.service = new ServiceBuilder().provider(YelpAPI.class).apiKey(consumerKey).apiSecret(consumerSecret).build();
        this.accessToken = new Token(token, tokenSecret);
    }

    /**
     * Search with term and location.
     *
     * @param keyword Search term
     * @param latitude Latitude
     * @param longitude Longitude
     * @return JSON string response
     */
//

    public String search(String keyword, double latitude, double longitude, int limit, int radius_filter) {
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
        request.addQuerystringParameter("term", keyword);
        request.addQuerystringParameter("ll", latitude + "," + longitude);
        request.addQuerystringParameter("limit", "" + limit);
        request.addQuerystringParameter("radius_filter", "" + radius_filter);
        this.service.signRequest(this.accessToken, request);
        Response response = request.send();
        return response.getBody();
    }

    public String searchSortDistance(String term, double latitude, double longitude, int sort ,int limit, int radius_filter) {
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
        request.addQuerystringParameter("term", term);
        request.addQuerystringParameter("ll", latitude + "," + longitude);
        request.addQuerystringParameter("sort", "" + sort);
        request.addQuerystringParameter("limit", "" + limit);
        request.addQuerystringParameter("radius_filter", "" + radius_filter);
        this.service.signRequest(this.accessToken, request);
        Response response = request.send();
        return response.getBody();
    }


}


