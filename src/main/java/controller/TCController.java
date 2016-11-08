package controller;

import api.tumblr.PhotoPostsTumblrApi;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import utils.Downloader;
import utils.RequestBuilder;
import view.TCView;

import java.awt.event.ActionListener;
import java.io.File;


public class TCController {
    private final String APIKEY = "3htTI7QfsDOrfawHJDoheCeY4WfXZxptA5LCp33FuPqIBxiC20";
    private final int REQ_LIMIT = 50;
    private RequestBuilder request = null;
    private Downloader downloader = null;
    private String blog;
    private TCView view;
    private ActionListener actionListener;

    public TCController(TCView view) {
        this.request = new RequestBuilder();
        this.downloader = new Downloader();
        this.view = view;
        this.actionListener = null;
    }


    public void control() {
        actionListener = e -> {
            view.setProgressBar(0);
            if (view.getBlogText().getText().equals("")) {
                view.setTextIntoArea("Error: Please, fill in the text field.");
            } else {
                // Retrieve input data
                blog = view.getBlogText().getText();
                // Reset input data
                view.setTextToVoid();
                // Download all tumblr photo posts
                view.appendTextIntoArea("Connecting...\n\n");
                Thread thread = new Thread() {
                    public void run() {
                        init();
                    }
                };
                thread.start();
            }
        };
        view.getButton().addActionListener(actionListener);
    }


    private void init() {
        PhotoPostsTumblrApi api = new PhotoPostsTumblrApi(blog, APIKEY);
        retrieveAllPhotoPosts(api.getReqUrl());

        view.getButton().setEnabled(true);
    }


    /**
     * Method that retrieves all the photo posts from a tumblr blog using the Tumblr API
     * and saves them one by one into a folder
     * @param reqUrl - URL to make the HTTP request
     */
    private void retrieveAllPhotoPosts(String reqUrl) {
        final String strOffset = "&offset=";
        final String strLimit = "&limit=";

        JsonElement res = request.get(reqUrl);

        if (res == null) {
            view.appendTextIntoArea("Error: Tumblr blog not found.");
        } else {
            // Get number of total posts, number of requests to call and number of remainder posts
            int numTotalPosts = Integer.parseInt(res.getAsJsonObject().get("response").getAsJsonObject()
                    .get("total_posts").getAsString());

            if (numTotalPosts == 0) {
                view.appendTextIntoArea("This tumblr blog doesn't have any photo posts.");
            } else {
                int numCalls = numTotalPosts / REQ_LIMIT;
                int numRemainderPosts = numTotalPosts % REQ_LIMIT;

                // Create folder with blog name
                File folder = new File(blog);
                folder.mkdir();

                view.initProgressBar(0, numTotalPosts);

                view.appendTextIntoArea("Downloading photos...\n(This could take several minutes)\n\n");

                // Retrieve all posts and save all photos
                int offset = 0;
                for (int i = 0; i < numCalls; i++) {
                    retrieveLimitedPhotoPosts(reqUrl+strLimit+REQ_LIMIT+strOffset+offset);
                    offset += REQ_LIMIT;
                }
                retrieveLimitedPhotoPosts(reqUrl+strLimit+numRemainderPosts+strOffset+offset);

                view.appendTextIntoArea("\nPhotos saved succesfully!\n");
            }
        }
    }


    /**
     * Method that retrieves the maximum number of photo posts (which is limited by the Tumblr API)
     * and saves them into a folder
     * @param url - URL to make the HTTP response
     */
    private void retrieveLimitedPhotoPosts(String url) {
        JsonElement jsonResponse = request.get(url);
        if (jsonResponse == null) {
            view.appendTextIntoArea("\nAn error occurred. Please try again.");
            view.getButton().setEnabled(true);
        } else {
            JsonArray posts = jsonResponse.getAsJsonObject().get("response").getAsJsonObject().get("posts").getAsJsonArray();
            for (JsonElement post : posts) {
                JsonArray photos = post.getAsJsonObject().get("photos").getAsJsonArray();
                // Some posts have multiple photos
                for (JsonElement photo : photos) {
                    String p = photo.getAsJsonObject().get("alt_sizes").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();
                    savePhoto(p);
                }
                view.updateProgressBar();
            }
        }
    }


    /**
     * Method that, given a URL, downloads the photo and saves it in a folder
     * @param photoUrl - URL of the photo
     */
    private void savePhoto(String photoUrl) {
        String [] splitPhotoUrl = photoUrl.split("/");
        // Take the last part of the split
        String photoFileName = splitPhotoUrl[splitPhotoUrl.length-1];
        String ext = photoFileName.substring(photoFileName.length()-3, photoFileName.length());

        downloader.savePhotoFromUrl(photoUrl, blog + "/" + photoFileName, ext);
        view.appendTextIntoArea(photoFileName + "\n");
    }

}
