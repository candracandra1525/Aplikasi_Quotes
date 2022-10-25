package com.mdp.aplikasiquotes.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mdp.aplikasiquotes.API.APIRequestData;
import com.mdp.aplikasiquotes.API.RetroServer;
import com.mdp.aplikasiquotes.Adapter.AdapterQuote;
import com.mdp.aplikasiquotes.Model.QuoteModel;
import com.mdp.aplikasiquotes.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvQuote;
    private ProgressBar pbQuote;
    private List<QuoteModel> listQuote;
    private AdapterQuote adapterQuote;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvQuote = findViewById(R.id.rv_quote);
        pbQuote = findViewById(R.id.pb_quote);

        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        rvQuote.setLayoutManager(linearLayoutManager);
        retrieveQuote();
    }

    private void retrieveQuote()
    {
        pbQuote.setVisibility(View.VISIBLE);

        APIRequestData ardData = RetroServer.connectRetrofit().create(APIRequestData.class);

        Call<List<QuoteModel>> retrieveProcess = ardData.ardGet();

        retrieveProcess.enqueue(new Callback<List<QuoteModel>>() {
            @Override
            public void onResponse(Call<List<QuoteModel>> call, Response<List<QuoteModel>> response) {
                listQuote = response.body();
                adapterQuote = new AdapterQuote(listQuote);
                rvQuote.setAdapter(adapterQuote);

                pbQuote.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<QuoteModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal menghubungi server" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("CEK", t.getMessage());
                pbQuote.setVisibility(View.INVISIBLE);

            }
        });


    }

}