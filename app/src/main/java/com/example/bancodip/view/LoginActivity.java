package com.example.bancodip.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bancodip.controller.ControllerBancoDados;
import com.example.bancodip.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private ControllerBancoDados controllerBancoDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intentRegister = new Intent(LoginActivity.this, CadastroActivity.class);
        Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);

        controllerBancoDados = new ControllerBancoDados(this);

        binding.btnCadastrar.setOnClickListener(v -> {
            startActivity(intentRegister);
        });

        binding.btnLogar.setOnClickListener(v -> {
            controllerBancoDados.open();

            String nome = binding.hintLoginNome.getText().toString().trim().toUpperCase();
            String email = binding.hintLoginEmail.getText().toString().trim().toUpperCase();


            if (controllerBancoDados.isNomeInDatabase(nome) && controllerBancoDados.isEmailInDatabase(email)){

                try {
                    intentMain.putExtra("nome", nome);
                    intentMain.putExtra("email", email);

                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    controllerBancoDados.close();
                    startActivity(intentMain);
                    finish();
                }



            }else {
                Toast.makeText(getApplicationContext(), "Não foi possivel se conectar", Toast.LENGTH_LONG).show();
            }

        });



    }
}