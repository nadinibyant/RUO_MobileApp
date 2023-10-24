package com.example.ruo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TherapyActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy1);

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

        TextView textMyTherapy = findViewById(R.id.textMyTherapy);
        SpannableString spannableString = new SpannableString(getString(R.string.My_Therapy));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(getApplicationContext(), TherapyActivity2.class);
                startActivity(intent);
            }

        };

        // supaya bisa ClickableSpan pada teks
        spannableString.setSpan(clickableSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        // Atur teks dalam TextView
        textMyTherapy.setText(spannableString);

        // Aktifin tautan untuk bisa diklik
        textMyTherapy.setMovementMethod(LinkMovementMethod.getInstance());

        RecyclerView recyclerView = findViewById(R.id.rv_listTherapy);
        MyAdapterTherapy adapterTherapy;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyListTherapyData[] therapyData = new MyListTherapyData[]{
                new MyListTherapyData(1,R.drawable.psikolog, "dr. Nadini Annisa, Sp.KJ", "Spesialis Kesehatan Jiwa", 5, "082283426568", "@dinibynt", R.drawable.like, R.drawable.unlike, 100, 5),
                new MyListTherapyData(2,R.drawable.psikolog, "dr. Darma Zidane, Sp.KJ", "Spesialis Kesehatan Jiwa", 7, "082286568", "@dzin16", R.drawable.like, R.drawable.unlike, 50, 1),
                new MyListTherapyData(3,R.drawable.psikolog, "dr. Amalia Sandi, Sp.KJ", "Spesialis Kesehatan Jiwa", 5, "082283426568", "@amalia", R.drawable.like, R.drawable.unlike, 120, 9),
                new MyListTherapyData(4,R.drawable.psikolog, "dr. Amalia Sandi, Sp.KJ", "Spesialis Kesehatan Jiwa", 5, "082283426568", "@amalia", R.drawable.like, R.drawable.unlike, 120, 9),
                new MyListTherapyData(5,R.drawable.psikolog, "dr. Amalia Sandi, Sp.KJ", "Spesialis Kesehatan Jiwa", 5, "082283426568", "@amalia", R.drawable.like, R.drawable.unlike, 120, 9),
                new MyListTherapyData(6,R.drawable.psikolog, "dr. Amalia Sandi, Sp.KJ", "Spesialis Kesehatan Jiwa", 5, "082283426568", "@amalia", R.drawable.like, R.drawable.unlike, 120, 9)

        };

        adapterTherapy = new MyAdapterTherapy(getApplicationContext(), therapyData);
        recyclerView.setAdapter(adapterTherapy);
    }
}