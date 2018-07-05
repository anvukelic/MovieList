package ada.osc.movielist.view;

import android.content.Context;

import java.util.List;

import ada.osc.movielist.model.Movie;

/**
 * Created by avukelic on 27-Jun-18.
 */
public interface MainContract {
    interface View{
        void goToLogin();
    }
    interface Presenter{
        void setView(MainContract.View view);
        void signOutUser(Context context);
    }
}
