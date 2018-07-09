package ada.osc.movielist.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ada.osc.movielist.App;
import ada.osc.movielist.Consts;
import ada.osc.movielist.R;
import ada.osc.movielist.interaction.ApiInteractorImpl;
import ada.osc.movielist.model.RequestToken;
import ada.osc.movielist.model.Session;
import ada.osc.movielist.presentation.MainPresenter;
import ada.osc.movielist.utils.SharedPrefUtil;
import ada.osc.movielist.view.movies.login.LoginActivity;
import ada.osc.movielist.view.movies.searched.SearchedMovies;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.tablayout_main)
    TabLayout tabLayout;
    @BindView(R.id.viewPager_main)
    ViewPager viewPager;

    SearchView searchView;

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initUi();
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initUi() {
        CustomFragmentPagerAdapter adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        presenter = new MainPresenter(this);
        presenter.setView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_log_out:
                presenter.signOutUser(this);
                return true;
            case R.id.action_search:
                searchView = (SearchView) item.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Intent intent = new Intent(MainActivity.this, SearchedMovies.class);
                        intent.putExtra("query", query);
                        startActivity(intent);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void goToLogin() {
        SharedPrefUtil.clearLoginPrefs(this, Consts.SP_NAME);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
