package com.craigke.picflic.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.craigke.picflic.R;
import com.craigke.picflic.model.UnsplashAPIResponse;
import com.squareup.picasso.Picasso;

public class FirebaseImageView extends RecyclerView.ViewHolder {

    View mView;
    Context mContext;

    public ImageView mImageView;
    public ImageView mProfileImage;

    public FirebaseImageViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindPicture(UnsplashAPIResponse picture){
        mImageView = mView.findViewById(R.id.img_id);
        mProfileImage = mView.findViewById(R.id.profile_image);
        TextView mDescription = mView.findViewById(R.id.img_description_id);

        Picasso.get().load(picture.getUrls().getSmall()).into(mImageView);
        Picasso.get().load(picture.getUser().getProfileImage().getLarge()).into(mProfileImage);
        mDescription.setText("Photo by " + picture.getUser().getUserFullName());
    }
}
