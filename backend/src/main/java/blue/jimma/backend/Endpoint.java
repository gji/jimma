/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package blue.jimma.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.ArrayList;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(name = "myApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.jimma.blue", ownerName = "backend.jimma.blue", packagePath = ""))
public class Endpoint {

    static ArrayList<User> users = new ArrayList<User>();

    {
        users.add(new User("test"));


    }

    @ApiMethod(name = "getUser")
    public User getUser(@Named("name") String name) {
        for(User u: users) {
            if(u.username.equals(name)) {
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
