package ada.osc.movielist.presentation;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import ada.osc.movielist.network.GoogleLoginUtil;
import ada.osc.movielist.view.MainContract;

/**
 * Created by avukelic on 27-Jun-18.
 */
public class MainPresenter implements MainContract.Presenter {


    public MainPresenter(Context context) {
        googleSignInClient = GoogleLoginUtil.configGoogleSignIn(context);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private MainContract.View view;

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void signOutUser(Context context) {
        // Firebase sign out
        firebaseAuth.signOut();

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener((Activity) context,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        view.goToLogin();
                    }
                });
    }

}
