package com.ddm.tde2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_CODE = 2001;
    private static final int CAMERA_INTENT_CODE = 3001;

    String caminhoDaImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestCameraPermission();
        } else {
            sendCameraIntent();
        }
    }

    // Inicia o processo de verificação de permissão de acesso à Câmera
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestCameraPermission() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{ Manifest.permission.CAMERA }, CAMERA_PERMISSION_CODE);
            } else {
                sendCameraIntent();
            }
        } else {
            Toast.makeText(CameraActivity.this, "No camera available", Toast.LENGTH_LONG).show();
        }
    }

    // Abre a câmera
    public void sendCameraIntent(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, true);

        if (intent.resolveActivity(getPackageManager()) != null) {
            // Gera um nome único baseado na data e hora pro arquivo da imagem
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String nomeDaImagem = "pic_" + timeStamp;

            // Pega o diretório de arquivos externos
            File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File arquivoDaImagem = null;

            // Cria um arquivo temporário para a imagem
            try {
                arquivoDaImagem = File.createTempFile(nomeDaImagem, ".jpg", dir);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (arquivoDaImagem != null) {
                // Pega o caminho da imagem.
                caminhoDaImagem = arquivoDaImagem.getAbsolutePath();

                Uri photoUri = FileProvider.getUriForFile(
                        CameraActivity.this,
                        "com.ddm.photolike.fileprovider",
                        arquivoDaImagem
                );

                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, CAMERA_INTENT_CODE);
            }
        }
    }

    // Verifica se o aplicativo foi concedido a permissão de acesso à Câmera
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendCameraIntent();
            } else {
                Toast.makeText(CameraActivity.this, "Camera Permission Denied",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verifica se houve sucesso na obtenção da imagem
        if (requestCode == CAMERA_INTENT_CODE) {
            if (resultCode == RESULT_OK) {
                File arquivo = new File(caminhoDaImagem);

                // Verifica se o arquivo existe
                if (arquivo.exists()) {
                    // Coloca a imagem na tela pela ImageView
                    //fotoPerfil.setImageURI(Uri.fromFile(file));
                }
            } else {
                Toast.makeText(CameraActivity.this,
                        "Problem getting the image from the camera app",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}