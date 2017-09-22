package com.kayzr.kayzrstaff;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kayzr.kayzrstaff.domain.User;
import com.kayzr.kayzrstaff.network.Calls;
import com.kayzr.kayzrstaff.network.Config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.LoginUsername) EditText mUsername;
    @BindView(R.id.Loginpassword) EditText mPassword;
    @BindView(R.id.signIn) Button signin ;
    public User currentUser ;
    public static boolean leave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //If the user wanted to remember the username and pas then fill it already in for him
        if(MainActivity.app.getCurrentUser().getRememberUsernameAndPass()){
            mUsername.setText(MainActivity.app.getCurrentUser().getUsername());
            mPassword.setText(MainActivity.app.getCurrentUser().getPassword());
        }

    }

    @OnClick(R.id.signIn)
    public void signIn()  {

        //encrypt the password
        String encryptedPas = "" ;
        try{
             encryptedPas = sha256(mPassword.getText().toString()) ;
        }catch (Exception e){
            Log.e("Sha encrypt error", e.toString());
        }

        //if the user wanted to be remembered then we dont need to send the encypted password to the backend
        if(MainActivity.app.getCurrentUser().getRememberUsernameAndPass()){
            // check if the user that wanted to be rememeberd is the same then the user that wants to login
            if(mUsername.getText().toString().equals(MainActivity.app.getCurrentUser().getUsername())){
                // if its the same then we can send our already encrypted saved password to the task
                UserLoginTask task = new UserLoginTask(mUsername.getText().toString(), mPassword.getText().toString());
                task.doInBackground();
            } else {
                // if its another user we need to send the new encrypted password and username to the task
                UserLoginTask task = new UserLoginTask(mUsername.getText().toString(), encryptedPas);
                task.doInBackground();
            }
        } else {
            //if the user did not wanted to be rememeber the we have to send the username and the new encrypted password to the task
            UserLoginTask task = new UserLoginTask(mUsername.getText().toString(), encryptedPas);
            task.doInBackground();
        }
    }

    @Override
    public void onBackPressed() {
        // press the back button twice to leave the app
        Log.d("LoginActivity", "onBackPressed Called");
        if(leave){
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "if you want to exit press Back again!",Toast.LENGTH_LONG).show();
            leave = true ;
        }
    }

    private static String sha256(String input) throws NoSuchAlgorithmException {
        //encrypt the input string with sha256 algoritm and return it
        MessageDigest mDigest = MessageDigest.getInstance("SHA256");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

     //Represents an asynchronous login task used to authenticate the user.

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // we make the call to get the userinfo and pas from the backend with the username
            Calls caller = Config.getRetrofit().create(Calls.class);
            Call<List<User>> call = caller.getUser(mUsername);
            call.enqueue(new Callback<List<User>>  () {
                @Override
                public void onResponse(Call<List<User>>   call, Response<List<User>>   response) {

                        try {
                            //Normaal verloop: de user uit backend halen
                            currentUser = response.body().get(0);
                            Log.d("Backend Call", " call successful (get user)");

                            //kijken of het paswoord gelijk is
                            if (mPassword.equals(currentUser.getPassword())) {
                                // we kijken of er nog geen user was die al eens ingelogd had of uit de database gehaald is
                                if(MainActivity.app.getCurrentUser() == null ){
                                    // als dit niet zo is dan zetten we de nieuwe user als de current user van de app
                                    currentUser.setLoggedOn(true);
                                    MainActivity.app.setCurrentUser(currentUser);
                                    MainActivity.app.getCurrentUser().setPassword(mPassword);
                                    MainActivity.app.getCurrentUser().setLoggedOn(true);
                                }else {
                                    // als we al een user hadden die al eens had ingelogd moeten we controleren of dit dezelfde user is
                                    if(currentUser.getUsername().equals(MainActivity.app.getCurrentUser().getUsername())){
                                        // als dit zo is dan zetten we de user logged in
                                        MainActivity.app.getCurrentUser().setLoggedOn(true);
                                    } else{
                                        // als de user verschillend is dan moeten we de nieuwe user veranderen met de current user
                                        currentUser.setLoggedOn(true);
                                        MainActivity.app.setCurrentUser(currentUser);
                                        MainActivity.app.getCurrentUser().setPassword(mPassword);
                                        MainActivity.app.getCurrentUser().setLoggedOn(true);
                                    }
                                }
                                // de login activity is afgelopen en de user is ingelogd deze activity mag afgesloten worden
                                finish();

                            } else {
                                // user is niet ingelogd toon een popup
                                Toast.makeText(getApplicationContext(), "Wrong password or username", Toast.LENGTH_LONG).show();
                            }
                        } catch (NullPointerException npe ){
                            // mocht de backend niet werken of de username is verkeerd dan krijgt men een nullpointer
                            // dit was dan de makkelijkste manier om deze op te vangen
                            Toast.makeText(getApplicationContext(),"There is no user like that are the service is currently down! " +
                                    "please report this to Mafken",Toast.LENGTH_LONG).show();
                        }
                }

                @Override
                public void onFailure(Call<List<User>>   call, Throwable t) {
                    //als er een error is in de call
                    Log.e("Backend CAll", "call failed (get user) " + t.getMessage());
                }
            });

            return true;
        }
    }
}

