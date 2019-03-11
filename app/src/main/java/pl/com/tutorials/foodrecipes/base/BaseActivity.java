package pl.com.tutorials.foodrecipes.base;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import pl.com.tutorials.foodrecipes.R;

/***
 * abstract, bo inaczej mam warningi zeby dodac do manifesta te activity. Plus jak dajemy abstact
 * to ta activity nie bedzie mogla byc instantiated, wiec dobrze, o to chodzi. Chcemy tylko ja rozszerzac
 */
public abstract class BaseActivity extends AppCompatActivity {

    public ProgressBar progressBar;

    @Override
    public void setContentView(int layoutResID) {
        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
        progressBar = constraintLayout.findViewById(R.id.progress_bar);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(constraintLayout);
    }

    public void showProgressBar(boolean visible){
        progressBar.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

}
