package blue.jimma;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import blue.jimma.backend.myApi.model.User;


public class Main extends Activity {

    Fragment login = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, login)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void takePic(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        final EditText test = (EditText) findViewById(R.id.editText);
        String username = test.getText().toString();


        new LoginAsyncTask() {
            protected void onPostExecute(User u) {
                if (u != null) {
                    StaticUser.user = u;
                    setContentView(R.layout.activity_feed);
                }
            }
        }.execute(new Pair<Context, String>(this, username));


    }
}
