package ada.osc.movielist.view.movies.login;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by avukelic on 27-Jun-18.
 */
public interface LoginContract {

    interface View{
        void updateUi(FirebaseUser user);
        void validateUserOnGoogle(GoogleSignInClient googleSignInClient);
    }

    interface Presenter{
        void setView(LoginContract.View view);
        void loginUser();
        void firebaseAuthWithGoogle(GoogleSignInAccount acct, Context context);
        void checkUserOnStart();
    }
}
