package com.craigke.picflic.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.craigke.picflic.model.UnsplashAPIResponse;

import java.util.List;

public class ImagePagerAdapter  extends FragmentPagerAdapter {

    private List<UnsplashAPIResponse> mPictures;

    public ImagePagerAdapter(FragmentManager fm, List<UnsplashAPIResponse> mPictures) {
        super(fm);
        this.mPictures = mPictures;
    }

    @Override
    public Fragment getItem(int i) {
        return FragmentImageDetail.newInstance(mPictures.get(i));
    }

    @Override
    public int getCount() {
        return mPictures.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mPictures.get(position).getUser().getFirstName();
    }
}
