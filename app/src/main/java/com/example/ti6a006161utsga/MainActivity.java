package com.example.ti6a006161utsga;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ti6a006161utsga.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new LokasiSaatIni());
        binding.title.setText(getString(R.string.text1));
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.lokasisaatini:
                    replaceFragment(new LokasiSaatIni());
                    binding.title.setText(getString(R.string.text1));
                    break;

                case R.id.pencarianlokasi:
                    replaceFragment(new PencarianLokasi());
                    binding.title.setText(getString(R.string.text2));
                    break;

                case R.id.titiklokasisekarang:
                    replaceFragment(new TitikLokasi());
                    binding.title.setText(getString(R.string.text3));
                    break;
            }

            return true;
        });
    }

    private void replaceFragment (Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}