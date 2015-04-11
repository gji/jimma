package blue.jimma;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import blue.jimma.backend.myApi.MyApi;
import blue.jimma.backend.myApi.model.User;

/**
 * Created by gji on 4/11/15.
 */
class LoginAsyncTask extends AsyncTask<Pair<Context, String>, Void, User> {
    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected User doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://ivory-lotus-91218.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0].first;
        String name = params[0].second;

        try {
            return myApiService.getUser(name).execute();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(User u) {
        if(u != null) {
            Toast.makeText(context, u.getId().toString(), Toast.LENGTH_LONG).show();
        }
    }
}
