package com.example.TechBank.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.TechBank.controller.ControllerBancoDados;
import com.example.TechBank.databinding.ActivityTransferirBinding;


public class TransferirActivity extends AppCompatActivity {

    private ActivityTransferirBinding binding;
    private ControllerBancoDados controllerBancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransferirBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        controllerBancoDados = new ControllerBancoDados(this);

        controllerBancoDados.open();

        Intent intent = getIntent();
        String emailUser = intent.getStringExtra("email_trans");
        Double saldoUser = controllerBancoDados.getSaldoByTitular(emailUser);
        Double chequeUser = controllerBancoDados.getChequeByTitular(emailUser);

        binding.confirmarTransferencia.setOnClickListener(v -> {

            String destinatarioEmail = binding.transUserEmail.getText().toString().toUpperCase();
            Double destinatarioSaldo = controllerBancoDados.getSaldoByTitular(destinatarioEmail);
            String valorUser = binding.transUserValor.getText().toString();

            if(controllerBancoDados.isEmailInDatabase(destinatarioEmail) && saldoUser > 0){
                try {

                    Double saldoUserNew = saldoUser - Double.parseDouble(valorUser);
                    Double saldoDestinatarioNew = destinatarioSaldo + Double.parseDouble(valorUser);

                    controllerBancoDados.updateSaldo(destinatarioEmail, saldoDestinatarioNew);
                    controllerBancoDados.updateSaldo(emailUser, saldoUserNew);

                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    controllerBancoDados.close();
                    binding.transUserValor.setText("");
                    binding.transUserEmail.setText("");

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setMessage("Sucesso!");
                    builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alerta = builder.create();
                    alerta.show();

                }
            }else if(controllerBancoDados.isEmailInDatabase(destinatarioEmail) && saldoUser <= 0 && chequeUser > 0){
                try{
                    Double saldoUserNew = saldoUser - Double.parseDouble(valorUser);
                    Double chequeUserNew = chequeUser - Double.parseDouble(valorUser);
                    Double saldoDestinatarioNew = destinatarioSaldo + Double.parseDouble(valorUser);

                    controllerBancoDados.updateSaldo(destinatarioEmail, saldoDestinatarioNew);
                    controllerBancoDados.updateCheque(emailUser, chequeUserNew);
                    controllerBancoDados.updateSaldo(emailUser, saldoUserNew);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    controllerBancoDados.close();
                    binding.transUserValor.setText("");
                    binding.transUserEmail.setText("");

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setMessage("Sucesso");
                    builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alerta = builder.create();
                    alerta.show();
                }

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("Saldo insuficiente");
                builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alerta = builder.create();
                alerta.show();
            }


        });

        binding.cancelarTransferencia.setOnClickListener(v -> {
            finish();

        });

    }



}