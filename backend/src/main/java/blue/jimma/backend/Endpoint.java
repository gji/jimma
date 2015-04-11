/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package blue.jimma.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

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

/**
 * An endpoint class we are exposing
 */
@Api(name = "myApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.jimma.blue", ownerName = "backend.jimma.blue", packagePath = ""))
public class Endpoint {

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

                BufferedImage b = ImageIO.read(new URL(imgUr));
                Post p = new Post();
                p.comment = comment;
                p.image = b;
                p.u = test;
                posts.put(p);
            }

        } catch (Exception e) {
        }

    }

    @ApiMethod(name = "getUser")
    public User getUser(@Named("name") String name) throws Exception {
        for(User u: users) {
            if(u.getUsername().equals(name)) {
                return u;
            } else {
                throw new Exception("User:" + name);
            }
        }
        return null;
    }

    @ApiMethod(name = "getPosts")
    public ArrayList<Post> getPosts() {
        return posts;
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
