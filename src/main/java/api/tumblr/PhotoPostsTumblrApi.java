package api.tumblr;


/**
 * A class that represents the Tumblr API to retrieve all the photo posts from a tumblr blog
 */
public class PhotoPostsTumblrApi {
    private final String BASE_URL = "https://api.tumblr.com/v2/blog/%s.tumblr.com/posts/photo?api_key=%s&format=json";
    private String reqUrl;

    public PhotoPostsTumblrApi(String blogName, String apiKey) {
        this.reqUrl = String.format(BASE_URL, blogName, apiKey);
    }

    public String getReqUrl() {
        return this.reqUrl;
    }
}
