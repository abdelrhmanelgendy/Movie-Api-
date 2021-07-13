package com.example.movie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class secondActivity extends AppCompatActivity {

    ImageView poster;
    ImageView backdrop;

    TextView txtname;
    TextView txtrate;
    TextView txtoverview;
    TextView txtdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_content);

        initiate();
        Intent i = getIntent();

        String tittle = i.getExtras().getString("tittle");
        String date = i.getExtras().getString("date");
        String rate = i.getExtras().getString("rate");
        String overview = i.getExtras().getString("overview");
        String poster_path = i.getExtras().getString("poster_path");
        String back_drop = i.getExtras().getString("backdrop");



        txtname.setText(tittle);
txtrate.setText(rate);

        txtoverview.setText(overview);
        txtdate.setText(date);

        Picasso.get().load(poster_path).into(backdrop);
        Picasso.get().load(back_drop).into(poster);

    }

    void initiate(){
txtrate=findViewById(R.id.movie_intent_txtrate);
        poster=findViewById(R.id.movie_intent_image_poster);
        backdrop=findViewById(R.id.image_back_drop);

        txtname=findViewById(R.id.movie_intent_txtname);
        txtdate=findViewById(R.id.movie_intent_txtdate);

        txtoverview=findViewById(R.id.txtcontent);

    }




}
