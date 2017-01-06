package unefa.app.david.com.unefaapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import unefa.app.david.com.unefaapp.Custom.Dialogs;
import unefa.app.david.com.unefaapp.R;

public class NewUserActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, View.OnClickListener{

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;

    GoogleSignInAccount acct;

    Dialogs dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        dialog = new Dialogs(this);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.button2:
                Intent mainIntent = new Intent().setClass(NewUserActivity.this, TypoUserActivity.class);
                startActivity(mainIntent);
                break;
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            acct = result.getSignInAccount();

            //   mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private String getUserEmail(){
        SharedPreferences prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE);

        String email = prefs.getString("email", "");
        return email;
    }

    private void SavePreferences(String email, String name, String image, String typoUser, String career){
        SharedPreferences sharedPreferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("name", name);
        editor.putString("image", image);
        editor.putString("typoUser", typoUser);
        editor.putString("career", career);
        editor.commit();
    }

    private boolean userIsNew(String email){

        JsonObject json = new JsonObject();
        json.addProperty("email", email);

        Ion.with(getApplicationContext())
                .load("http://192.168.0.109:8000/usertwo/")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Toast.makeText(getApplicationContext(), result.get("email").getAsString(), Toast.LENGTH_LONG).show();
                    }
                });

        return true;
    }

    private void updateUI(boolean signedIn) {
        dialog.dismiss();
        if (signedIn) {

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);

            userIsNew(acct.getEmail());

            /*if (getUserEmail().equals("") && userIsNew(acct.getEmail())){

                dialog.show();

                dialog.image.setImageDrawable(getResources().getDrawable(R.drawable.students));

                dialog.title.setText("Escoja tipo de usuario");
                dialog.description.setVisibility(View.GONE);
                dialog.layoutProgress.setVisibility(View.GONE);

                dialog.spinnerTypoUser.setVisibility(View.VISIBLE);

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(NewUserActivity.this,
                        R.array.typo_user_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dialog.spinnerTypoUser.setAdapter(adapter);

                dialog.layoutButtons.setVisibility(View.VISIBLE);
                dialog.layoutButtonNegative.setVisibility(View.GONE);
                dialog.layoutAux.setVisibility(View.GONE);
                dialog.positiveText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        SavePreferences(acct.getEmail(), acct.getDisplayName(),
                                acct.getPhotoUrl().toString(),
                                dialog.spinnerTypoUser.getSelectedItem().toString(),
                                dialog.spinnerCareer.getSelectedItem().toString());
                        Intent mainIntent = new Intent().setClass(NewUserActivity.this, TypoUserActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                });
            }else {
                SavePreferences(acct.getEmail(), acct.getDisplayName(),
                        acct.getPhotoUrl().toString(),
                        "",
                        "");
                Intent mainIntent = new Intent().setClass(NewUserActivity.this, TypoUserActivity.class);
                startActivity(mainIntent);
            }*/

            //  findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            //  mStatusTextView.setText(R.string.signed_out);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);


            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        dialog.show();

        dialog.image.setImageDrawable(getResources().getDrawable(R.drawable.students));

        dialog.title.setText("Iniciando Sesion");
        dialog.description.setVisibility(View.GONE);
        dialog.layoutProgress.setVisibility(View.VISIBLE);
        dialog.progressText.setText("Cargando!!!");

        /*final TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Start the next activity
                //dialog.dismiss();


                //

                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, RC_SIGN_IN);*/

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
            Intent mainIntent = new Intent().setClass(NewUserActivity.this, TypoUserActivity.class);
            startActivity(mainIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
        updateUI(false);
    }
}
