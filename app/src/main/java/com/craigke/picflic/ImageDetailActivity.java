package com.craigke.picflic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.craigke.picflic.adapter.ImagePagerAdapter;
import com.craigke.picflic.model.UnsplashAPIResponse;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageDetailActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private ImagePagerAdapter pagerAdapter;
    List<UnsplashAPIResponse> mPictures = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);

        mPictures = Parcels.unwrap(getIntent().getParcelableExtra("pictures"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        pagerAdapter = new ImagePagerAdapter(getSupportFragmentManager(), mPictures);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(startingPosition);

    }
}

