package ada.osc.movielist.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ada.osc.movielist.R;
import ada.osc.movielist.view.movies.favorite.FavoriteMoviesFragment;
import ada.osc.movielist.view.movies.popular.PopularMoviesFragment;
import ada.osc.movielist.view.movies.toprated.TopratedMoviesFragment;
import ada.osc.movielist.view.movies.upcoming.UpcomingMoviesFragment;

/**
 * Created by avukelic on 20-Jun-18.
 */
public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    Context context;

    public CustomFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PopularMoviesFragment();
            case 1:
                return new TopratedMoviesFragment();
            case 2:
                return new UpcomingMoviesFragment();
            case 3:
                return new FavoriteMoviesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return context.getString(R.string.category_popular);
            case 1:
                return context.getString(R.string.category_toprated);
            case 2:
                return context.getString(R.string.category_upcoming);
            case 3:
                return context.getString(R.string.category_favorite);
            default:
                return null;
        }
    }
}
