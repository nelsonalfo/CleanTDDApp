package com.nelsonalfo.cleantddapp.data.api;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.nelsonalfo.cleantddapp.data.api.Constants.API_BASE_URL;


/**
 * Created by nelso on 26/12/2017.
 */

public class ApiServiceGenerator {
    private final Retrofit retrofit;

    public ApiServiceGenerator(Context context) {
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor);

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        retrofit = builder.client(httpClient.build()).build();
    }

    public TheMovieDbRestApi createClient() {
        return retrofit.create(TheMovieDbRestApi.class);
    }
}