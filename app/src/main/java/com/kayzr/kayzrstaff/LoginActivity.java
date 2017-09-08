package com.kayzr.kayzrstaff;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.kayzr.kayzrstaff.domain.User;
import com.kayzr.kayzrstaff.network.Calls;
import com.kayzr.kayzrstaff.network.Config;

import butterknife.BindView;
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

    public User currentUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    @OnClick(R.id.email_sign_in_button)
    public void signIn(){
        //todo encrypt pasword
        UserLoginTask task = new UserLoginTask(mUsername.getText().toString(),mPassword.getText().toString());
        task.doInBackground();

    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            Calls caller = Config.getRetrofit().create(Calls.class);
            Call<User> call = caller.getUser(mUsername);
            call.enqueue(new Callback<User> () {
                @Override
                public void onResponse(Call<User>  call, Response<User>  response) {
                    currentUser = response.body();
                    Log.d("Backend Call", " call successful (get user)");
                    if(mPassword.equals(currentUser.getPassword())){
                        currentUser.setLoggedOn(true);
                        MainActivity.app.setCurrentUser(currentUser);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<User>  call, Throwable t) {
                    Log.e("Backend CAll", "call failed (get user) " + t.getMessage());
                }
            });

            return true;
        }
    }
}

