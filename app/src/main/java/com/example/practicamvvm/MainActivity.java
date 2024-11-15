package com.example.practicamvvm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Comprobar si no se ha cargado ya el fragment
        if (savedInstanceState == null) {
            // Añadir el fragmento MiHipotecaFragment al contenedor
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new MiHipotecaFragment())
                    .commit();
        }
    }
}
