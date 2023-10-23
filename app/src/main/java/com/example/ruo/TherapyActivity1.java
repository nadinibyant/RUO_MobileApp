package com.example.ruo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TherapyActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy1);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        MenuItem homeItem = bottomNavigationView.getMenu().findItem(R.id.item_therapy);
        homeItem.setChecked(true);

        RecyclerView recyclerView = findViewById(R.id.rv_listTherapy);
        MyAdapterTherapy adapterTherapy;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyListTherapyData[] therapyData = new MyListTherapyData[]{
                new MyListTherapyData(R.drawable.psikolog, "dr. Nadini Annisa, Sp.KJ", "Spesialis Kesehatan Jiwa", 5, "082283426568", "@dinibynt", R.drawable.like, R.drawable.unlike, 100, 5),
                new MyListTherapyData(R.drawable.psikolog, "dr. Darma Zidane, Sp.KJ", "Spesialis Kesehatan Jiwa", 7, "082286568", "@dzin16", R.drawable.like, R.drawable.unlike, 50, 1),
                new MyListTherapyData(R.drawable.psikolog, "dr. Amalia Sandi, Sp.KJ", "Spesialis Kesehatan Jiwa", 5, "082283426568", "@amalia", R.drawable.like, R.drawable.unlike, 120, 9),
                new MyListTherapyData(R.drawable.psikolog, "dr. Amalia Sandi, Sp.KJ", "Spesialis Kesehatan Jiwa", 5, "082283426568", "@amalia", R.drawable.like, R.drawable.unlike, 120, 9),
                new MyListTherapyData(R.drawable.psikolog, "dr. Amalia Sandi, Sp.KJ", "Spesialis Kesehatan Jiwa", 5, "082283426568", "@amalia", R.drawable.like, R.drawable.unlike, 120, 9),
                new MyListTherapyData(R.drawable.psikolog, "dr. Amalia Sandi, Sp.KJ", "Spesialis Kesehatan Jiwa", 5, "082283426568", "@amalia", R.drawable.like, R.drawable.unlike, 120, 9)

        };

        adapterTherapy = new MyAdapterTherapy(getApplicationContext(), therapyData);
        recyclerView.setAdapter(adapterTherapy);
    }
}