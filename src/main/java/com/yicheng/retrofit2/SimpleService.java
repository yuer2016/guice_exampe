package com.yicheng.retrofit2;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

/**
 * Created by yuer on 2017/3/3.
 */
public class SimpleService {
    @SneakyThrows
    public static void main(String[] args) {
        final String baseApi = "https://api.github.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHub gitHub = retrofit.create(GitHub.class);
        Call<List<Contributor>> call = gitHub.contributors("square", "retrofit");
        List<Contributor> body = call.execute().body();
        body.forEach(System.out::println);
    }
}
