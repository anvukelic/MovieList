package ada.osc.movielist.view;

import android.content.Context;

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
