package com.ddm.tde2;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ImagemActivity extends AppCompatActivity {
    ImageView imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagem);

        imagem = findViewById(R.id.imagem_galeria);

        // Transforma para preto e branco
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        imagem.setColorFilter(new ColorMatrixColorFilter(matrix));

        String caminhoImagem = getIntent().getStringExtra("CAMINHO_IMAGEM");
        Uri imagemUri = Uri.parse(caminhoImagem);

        // Coloca a imagem na tela pela ImageView
        imagem.setImageURI(imagemUri);
    }
}