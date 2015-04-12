/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package blue.jimma.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.inject.Named;

import sun.misc.IOUtils;

/**
 * An endpoint class we are exposing
 */
@Api(name = "myApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.jimma.blue", ownerName = "backend.jimma.blue", packagePath = ""))
public class Endpoint {

    private static final Logger log = Logger.getLogger(Endpoint.class.getName());

    static ArrayList<User> users = new ArrayList<User>();
    static ArrayList<Post> posts = new ArrayList<Post>();

    {
        User test = new User("test");
        users.add(test);

        try {
            InputStream is = new URL("http://www.reddit.com/r/pics/hot.json").openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            String jsonText = sb.toString();

            JSONObject json = new JSONObject(jsonText);

            JSONArray posts = json.getJSONObject("data").getJSONArray("children");
            for(int i = 0; i < posts.length(); i++) {
                String imgUr = posts.getJSONObject(i).getJSONObject("data").getString("url");
                String comment = posts.getJSONObject(i).getJSONObject("data").getString("title");
                URL url = new URL(imgUr);

                Post p = new Post();
                p.comment = comment;
                p.u = test;
                posts.put(p);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream ims = null;
                try {
                    ims = url.openStream();
                    byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
                    int n;

                    while ( (n = ims.read(byteChunk)) > 0 ) {
                        baos.write(byteChunk, 0, n);
                    }
                    p.image = baos.toByteArray();
                }
                catch (IOException e) {
                    System.err.printf ("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
                    e.printStackTrace ();
                    // Perform any other exception handling that's appropriate.
                }
                finally {
                    if (is != null) { is.close(); }
                }
            }

        }
        catch (Exception e) {
        }

    }

    @ApiMethod(name = "getComments")
    public ArrayList<Comment> getComments(Post post) {
        return post.comments;
    }

    @ApiMethod(name = "getPosts")
    public ArrayList<Post> getPosts() {
        return posts;
    }

    @ApiMethod(name = "addPost")
    public void addPost(Post post) {
        posts.add(post);
    }

    @ApiMethod(name = "getUser")
    public User getUser(@Named("name") String name) throws Exception {
        log.info("here!!!");
        log.info(Integer.toString(users.size()));
        for(User u: users) {
            if(u.getUsername().equals(name)) {
                return u;
            }
        }
        return null;
    }


    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }

}
