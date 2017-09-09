package com.kayzr.kayzrstaff;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.signIn)
    public void signIn()  {
        //todo encrypt password
        String encryptedPas = "" ;
        try{
             encryptedPas = sha256(mPassword.getText().toString()) ;
        }catch (Exception e){
            Log.e("Sha encrypt error", e.toString());
        }

        UserLoginTask task = new UserLoginTask(mUsername.getText().toString(),encryptedPas);
        task.doInBackground();
    }

    static String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA256");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
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
            Call<List<User>> call = caller.getUser(mUsername);
            call.enqueue(new Callback<List<User>>  () {
                @Override
                public void onResponse(Call<List<User>>   call, Response<List<User>>   response) {
                    currentUser = response.body().get(0);

                    Log.d("Backend Call", " call successful (get user)");
                    if(mPassword.equals(currentUser.getPassword())){
                        currentUser.setLoggedOn(true);
                        MainActivity.app.setCurrentUser(currentUser);
                        MainActivity.app.getCurrentUser().setPassword(mPassword);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<List<User>>   call, Throwable t) {
                    Log.e("Backend CAll", "call failed (get user) " + t.getMessage());
                }
            });

            return true;
        }
    }
}

