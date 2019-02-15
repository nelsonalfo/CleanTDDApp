package com.nelsonalfo.cleantddapp.presentation.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nelsonalfo.cleantddapp.R;
import com.nelsonalfo.cleantddapp.presentation.models.MovieResumeViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by nelso on 27/12/2017.
 */

class MoviesViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.movie_poster)
    ImageView moviePoster;
    @BindView(R.id.move_title)
    TextView moveTitle;
    @BindView(R.id.move_description)
    TextView moveDescription;


    public MoviesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(MovieResumeViewModel movieResume) {
        String posterImageUrl = movieResume.posterImageUrl;
        Picasso.get().load(posterImageUrl).into(moviePoster);

        moveTitle.setText(movieResume.title);
        moveDescription.setText(movieResume.overview);
    }
}
