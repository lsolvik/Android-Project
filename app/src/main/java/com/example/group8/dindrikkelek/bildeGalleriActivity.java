package com.example.group8.dindrikkelek;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.GridView;

//activity for å vise alle bilder i bildebiblioteket
public class bildeGalleriActivity extends AppCompatActivity {
    private dbHandler mydbHandler;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilde_galleri);

        this.gridView = findViewById(R.id.activity_main_grid_view);
        this.mydbHandler = new dbHandler(this);
        this.gridView.setAdapter(new bildeAdapter(this, this.mydbHandler.readAllBilder(), false));
    }

    @Override
    protected void onResume() {
        super.onResume();

        ((CursorAdapter)gridView.getAdapter()).swapCursor(this.mydbHandler.readAllBilder());
    }
}
