package net.elvanwinanda.movieapps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieData extends AppCompatActivity {
    TextView tv_ttl_detail, tv_rd, tv_ovw_detail;
    ImageView img_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_movies);
        tv_ttl_detail = findViewById(R.id.tv_ttl_detail);
        tv_rd = findViewById(R.id.tv_rd);
        tv_ovw_detail = findViewById(R.id.tv_ovw_detail);
        img_detail = findViewById(R.id.img_detail);
        getData();
    }

    private void getData(){

        String imgPath = "http://image.tmdb.org/t/p/w780"+getIntent().getStringExtra("backdrop");
        Glide.with(this)
                .load(imgPath)
                .into(img_detail);
        tv_ttl_detail.setText(getIntent().getStringExtra("title"));
        tv_rd.setText(getIntent().getStringExtra("release_date"));
        tv_ovw_detail.setText(getIntent().getStringExtra("overview"));
    }
}
