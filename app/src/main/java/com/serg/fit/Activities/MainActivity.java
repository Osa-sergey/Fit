package com.serg.fit.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.serg.fit.R;

import DB.DBHelper;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView headerFullName;
    private TextView headerEdit;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        headerFullName = navigationView.getHeaderView(0).findViewById(R.id.header_name);
        headerEdit = navigationView.getHeaderView(0).findViewById(R.id.header_edit);
        setSharedPref();
        pref=getApplicationContext().getSharedPreferences("Pref",MODE_PRIVATE);
        headerFullName.setText(pref.getString("authorizedFullName",""));
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        headerEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //устанавливаем обработчики навигации
        NavigationUI.setupActionBarWithNavController(this,navController,drawer);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        NavigationUI.setupWithNavController(navigationView,navController);

        //TODO изменить название БД
        //Ининциализируем БД
        DBHelper.getInstance(getApplicationContext(),"Test");
    }

    /**
     * подгрузить из бд нужные данные по текущему пользователю
     */
    private void setSharedPref() {
        //TODO написать
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController,drawer)
                || super.onSupportNavigateUp();
    }

}
