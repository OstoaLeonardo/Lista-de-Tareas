package com.example.listadetareas;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MenuTopBar extends AppCompatActivity {

    public MenuItem itemTema;

    public void setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void setupToolbarMain() {
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar, menu);
        itemTema = menu.findItem(R.id.itemTema);

        int currentTheme = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentTheme == Configuration.UI_MODE_NIGHT_YES) {
            itemTema.setTitle(R.string.tema_claro_item_menu);
        } else {
            itemTema.setTitle(R.string.tema_oscuro_item_menu);
        }

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemTema:
                int currentTheme = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if (currentTheme == Configuration.UI_MODE_NIGHT_YES) {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                recreate();
                return true;
            case R.id.itemGitHub:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/OstoaLeonardo/Lista-de-Tareas")));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
