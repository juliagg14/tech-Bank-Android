package com.example.TechBank.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.TechBank.controller.ControllerBancoDados;
import com.example.TechBank.controller.Util;
import com.example.TechBank.databinding.ActivityCadastroBinding;


public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;
    private ControllerBancoDados controllerBancoDados;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        controllerBancoDados = new ControllerBancoDados(this);
        util = new Util();
        Intent intent = new Intent(CadastroActivity.this, MainActivity.class);

        binding.btnConfirmarCadastro.setOnClickListener(v -> {
            controllerBancoDados.open();

            String  nome = binding.hintRegisterNome.getText().toString().toUpperCase().trim();
            String email = binding.hintRegisterEmail.getText().toString().toUpperCase().trim();
            String saldo = binding.hintRegisterSaldo.getText().toString().trim();

            if(!nome.isEmpty() && !email.isEmpty() && !saldo.isEmpty() && util.isValidEmail(email) && !controllerBancoDados.isEmailInDatabase(email) ){

                double saldoDouble = Double.parseDouble(saldo);
                double chequeEspecial = saldoDouble * 4;

                try {
                    controllerBancoDados.insertData(nome, email, saldoDouble, chequeEspecial, chequeEspecial);
                    intent.putExtra("nome", nome);
                    intent.putExtra("email", email);
                    intent.putExtra("saldo", saldoDouble);
                    intent.putExtra("cheque", chequeEspecial);

                }catch (Exception e){
                    e.printStackTrace();
                } finally {
                    controllerBancoDados.close();
                    startActivity(intent);
                    finish();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Conta já existente", Toast.LENGTH_LONG).show();
            }



        });




    }
}