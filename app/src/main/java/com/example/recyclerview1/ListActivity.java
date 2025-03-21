package com.example.recyclerview1;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview1.adapter.StarAdapter;
import com.example.recyclerview1.beans.Star;
import com.example.recyclerview1.service.StarService;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private List<Star> stars;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;
    private StarService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Initialiser le service
        service = StarService.getInstance();

        // Ajouter des données de test
        if(service.findAll().isEmpty()){
            init();
        }


        // Configurer le RecyclerView
        recyclerView = findViewById(R.id.recyclerView); // Vérifiez l'ID correct !

        // Vérification pour éviter NullPointerException
        if (recyclerView != null) {
            stars = service.findAll();
            starAdapter = new StarAdapter(this, stars);
            recyclerView.setAdapter(starAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            Log.e("ListActivity", "RecyclerView not found!");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("ListActivity", "onCreateOptionsMenu called");
        setTitle("Stars");
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (starAdapter != null){
                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share){
            String txt = "Stars";
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Stars")
                    .setText(txt)
                    .startChooser();
        }
        return super.onOptionsItemSelected(item);
    }
    public void init(){
        service.create(new Star("İbrahim Çelikkol", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRL0mcxu0gjOkZBMgSdxw_ved8ESlGrUBXPWw&s", 5));
        service.create(new Star("Birce Akalay", "https://live.staticflickr.com/65535/51412087541_29736c3380_c.jpg", 5));
        service.create(new Star("Celine Dion", "https://cdn.britannica.com/91/231791-050-0845F502/Canadian-singer-Celine-Dion-2014.jpg", 3.5f));
        service.create(new Star("George Clooney", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXrabWTtRbaIVYBaJfKOcW6FsIPgG0yLscyg&s", 3));
        service.create(new Star("Michelle Rodriguez",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSPIHPkUjoVMzSegFbh8ps8k9QtgF9U118oSQ&s", 5));
        service.create(new Star("Selena Gomez", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSeFPkKdUuhKuaSANilCR2GOnJtaPIQCDx1Ng&s", 1));
        service.create(new Star("louise bouroin", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQBp5J6WJQgcHaBaFjJ1A9_L43DZlkpZjBLkw&s", 5));
        service.create(new Star("Justin Bieber", "https://upload.wikimedia.org/wikipedia/commons/d/da/Justin_Bieber_in_2015.jpg", 1));


    }
}