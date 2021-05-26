package com.ddm.tde2;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri uriImagem = data.getData();
            String caminhoImagem = uriImagem.toString();
            Intent i = new Intent(this, ImagemActivity.class);
            i.putExtra("CAMINHO_IMAGEM", caminhoImagem);
            startActivity(i);
        }
    }

    public void handleBtnCamera(View view) {
        Intent i = new Intent(this, CameraActivity.class);
        startActivity(i);
    }

    public void handleBtnGaleria(View view) {
        abrirGaleria();
    }
}