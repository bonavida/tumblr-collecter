package controller;

import api.tumblr.PhotoPostsTumblrApi;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import utils.Downloader;
import utils.RequestBuilder;

import java.io.File;


public class TCController {
    private final String APIKEY = "3htTI7QfsDOrfawHJDoheCeY4WfXZxptA5LCp33FuPqIBxiC20";
    private final int REQ_LIMIT = 50;
    private RequestBuilder request = null;
    private Downloader downloader = null;
    private String blog;

    public TCController() {
        request = new RequestBuilder();
        downloader = new Downloader();
        blog = "thekidsfromkibble";
    }


    public void start() {
        PhotoPostsTumblrApi api = new PhotoPostsTumblrApi(blog, APIKEY);
        retrieveAllPhotoPosts(api.getReqUrl());
    }


    private void retrieveAllPhotoPosts(String reqUrl) {
        final String strOffset = "&offset=";
        final String strLimit = "&limit=";

        // Get number of total posts, number of requests to call and number of remainder posts
        int numTotalPosts = Integer.parseInt(request.get(reqUrl).getAsJsonObject().get("response").getAsJsonObject()
                .get("total_posts").getAsString());
        int numCalls = numTotalPosts / REQ_LIMIT;
        int numRemainderPosts = numTotalPosts % REQ_LIMIT;

        // Create folder with blog name
        File folder = new File(blog);
        folder.mkdir();

        // Retrieve all posts and save all photos
        int offset = 0;
        for (int i = 0; i < numCalls; i++) {
            retrieveLimitedPhotoPosts(reqUrl+strLimit+REQ_LIMIT+strOffset+offset);
            offset += REQ_LIMIT;
        }
        retrieveLimitedPhotoPosts(reqUrl+strLimit+numRemainderPosts+strOffset+offset);
    }


    private void retrieveLimitedPhotoPosts(String url) {
        JsonElement jsonResponse = request.get(url);
        JsonArray posts = jsonResponse.getAsJsonObject().get("response").getAsJsonObject().get("posts").getAsJsonArray();
        for (JsonElement post : posts) {
            JsonArray photos = post.getAsJsonObject().get("photos").getAsJsonArray();
            // Some posts have multiple photos
            for (JsonElement photo : photos) {
                String p = photo.getAsJsonObject().get("alt_sizes").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();
                System.out.println(p);
                downloader.savePhotoFromUrl(blog, p);
            }
        }
    }

}
