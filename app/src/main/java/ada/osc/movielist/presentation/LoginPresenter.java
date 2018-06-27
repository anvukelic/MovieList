package ada.osc.movielist.presentation;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import ada.osc.movielist.R;
import ada.osc.movielist.network.GoogleLoginUtil;
import ada.osc.movielist.view.movies.login.LoginContract;

/**
 * Created by avukelic on 27-Jun-18.
 */
public class LoginPresenter implements LoginContract.Presenter {

    public LoginPresenter(Context context) {
        googleSignInClient = GoogleLoginUtil.configGoogleSignIn(context);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private static final String TAG = "LoginPresenter";
    private FirebaseAuth firebaseAuth;

    private GoogleSignInClient googleSignInClient;
    LoginContract.View view;

    @Override
    public void setView(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void checkUserOnStart() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        view.updateUi(currentUser);
    }

    @Override
    public void loginUser() {
        view.validateUserOnGoogle(googleSignInClient);
    }

    @Override
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct, Context context) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            view.updateUi(user);
                        } else {
                            view.updateUi(null);
                        }
                    }
                });
    }


}
