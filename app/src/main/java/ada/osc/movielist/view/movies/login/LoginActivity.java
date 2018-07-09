package ada.osc.movielist.view.movies.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import ada.osc.movielist.Consts;
import ada.osc.movielist.R;
import ada.osc.movielist.presentation.LoginPresenter;
import ada.osc.movielist.utils.SharedPrefUtil;
import ada.osc.movielist.view.MainActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;

    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this);
        presenter.setView(this);
    }

    @OnClick(R.id.signInButton)
    public void signInUser() {
        presenter.loginUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefUtil.getStringFromSharedPref(this, Consts.SP_NAME, Consts.EMAIL_PREF).length()>0) {
            presenter.checkUserOnStart();
        }
    }

    @Override
    public void validateUserOnGoogle(GoogleSignInClient googleSignInClient) {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                presenter.firebaseAuthWithGoogle(account, this);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                updateUi(null);
            }
        }
    }


    @Override
    public void updateUi(FirebaseUser user) {
        if (user != null) {
            SharedPrefUtil.saveToSharedPref(this, Consts.SP_NAME, Consts.USER_ID_PREF, user.getUid());
            SharedPrefUtil.saveToSharedPref(this, Consts.SP_NAME, Consts.EMAIL_PREF, user.getEmail());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Google sign in failed", Toast.LENGTH_SHORT).show();
        }
    }


}
