package edu.mnstate.jz1652ve.lab26;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

class Film {
    private String title, description, director;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDirector() {
        return director;
    }
}

interface JsonFilmAPI {
    @GET("films")
    Call<List<Film>> getFilms();
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text = findViewById(R.id.text);
        text.setMovementMethod(new ScrollingMovementMethod());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ghibliapi.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonFilmAPI jsonFilmAPI = retrofit.create(JsonFilmAPI.class);

        jsonFilmAPI.getFilms()
                .enqueue(new Callback<List<Film>>() {
                    @Override
                    public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {
                        text.setText("");
                        for(Film film : response.body()) {
                            String content = String.format("Title: %s\n\nDescription: %s\n\nDirector: %s\n\n\n********************\n\n", film.getTitle(), film.getDescription(), film.getDirector());
                            text.append(content);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Film>> call, Throwable t) {

                    }
                });
    }
}
