package com.example.ruo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TherapyActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy2);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        MenuItem therapyItem = bottomNavigationView.getMenu().findItem(R.id.item_therapy);
        therapyItem.setChecked(true);

        therapyItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(getApplicationContext(), TherapyActivity1.class);
                startActivity(intent);
                return false;
            }
        });

        MenuItem homeItem = bottomNavigationView.getMenu().findItem(R.id.item_home);
        homeItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity1.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                return false;
            }
        });

        RecyclerView recyclerView = findViewById(R.id.rv_listTherapyIndividu);
        MyAdapterIndividuTherapy myAdapterIndividuTherapy;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyListIndividuTherapyData[] myListIndividuTherapyData = new MyListIndividuTherapyData[]{
               new MyListIndividuTherapyData(1, R.drawable.baseline_mode_edit_24, R.drawable.baseline_delete_24, R.drawable.psikolog, "dr Nadini Annisa", "Spesialis Kedokteran Jiwa", 7, "082283426568", "@dinibynt",R.drawable.like, R.drawable.unlike, 50, 8),
                new MyListIndividuTherapyData(2, R.drawable.baseline_mode_edit_24, R.drawable.baseline_delete_24, R.drawable.psikolog, "dr Annisa Byant", "Spesialis Kedokteran Jiwa", 10, "086545678543", "@byantt",R.drawable.like, R.drawable.unlike, 100, 5),
                new MyListIndividuTherapyData(5, R.drawable.baseline_mode_edit_24, R.drawable.baseline_delete_24, R.drawable.psikolog, "dr Nadini Annisa", "Spesialis Kedokteran Jiwa", 7, "082283426568", "@dinibynt",R.drawable.like, R.drawable.unlike, 50, 8),
                new MyListIndividuTherapyData(11, R.drawable.baseline_mode_edit_24, R.drawable.baseline_delete_24, R.drawable.psikolog, "dr Annisa Byant", "Spesialis Kedokteran Jiwa", 10, "086545678543", "@byantt",R.drawable.like, R.drawable.unlike, 100, 5),
                new MyListIndividuTherapyData(10, R.drawable.baseline_mode_edit_24, R.drawable.baseline_delete_24, R.drawable.psikolog, "dr Nadini Annisa", "Spesialis Kedokteran Jiwa", 7, "082283426568", "@dinibynt",R.drawable.like, R.drawable.unlike, 50, 8),
                new MyListIndividuTherapyData(20, R.drawable.baseline_mode_edit_24, R.drawable.baseline_delete_24, R.drawable.psikolog, "dr Annisa Byant", "Spesialis Kedokteran Jiwa", 10, "086545678543", "@byantt",R.drawable.like, R.drawable.unlike, 100, 5),
                new MyListIndividuTherapyData(9, R.drawable.baseline_mode_edit_24, R.drawable.baseline_delete_24, R.drawable.psikolog, "dr Nadini Annisa", "Spesialis Kedokteran Jiwa", 7, "082283426568", "@dinibynt",R.drawable.like, R.drawable.unlike, 50, 8),

        };

        myAdapterIndividuTherapy = new MyAdapterIndividuTherapy(getApplicationContext(), myListIndividuTherapyData);
        recyclerView.setAdapter(myAdapterIndividuTherapy);

        ImageView btnToAddTherapy = findViewById(R.id.btnToAddTherapy);
        btnToAddTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TherapyActivityAdd.class);
                startActivity(intent);
            }
        });

    }
}