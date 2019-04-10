package br.com.andrewesteves.travelling;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.andrewesteves.travelling.modelos.basicas.Foto;
import br.com.andrewesteves.travelling.modelos.repositorios.FotoDados;
import br.com.andrewesteves.travelling.modelos.repositorios.ViagemDados;

public class FotosAdicionarActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1;
    private static final int MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    String imagemCaminho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos_adicionar);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        Log.d("DEPURAR", Integer.toString(permissionCheck));

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permissão concedida com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(this, "Opsss, a permissão foi negada!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            try{
                Bitmap fotoCaptura = (Bitmap) data.getExtras().get("data");

                ViagemDados viagemDados = new ViagemDados(getApplicationContext());
                Foto foto = new Foto();
                foto.setViagem(viagemDados.unico(1));

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                fotoCaptura.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                foto.setImagem(stream.toByteArray());
                foto.setCaminho(this.salvarImagem(fotoCaptura));

                FotoDados fotoDados = new FotoDados(getApplicationContext());
                fotoDados.inserir(foto);

                Toast.makeText(getApplicationContext(), "Foto tirada com sucesso",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, FotosActivity.class);
                startActivity(intent);
            }catch(Exception e) {
                Log.d("DEBUGAR", e.getMessage());
                Toast.makeText(getApplicationContext(), "Não foi possível tirar com sucesso", Toast.LENGTH_LONG).show();
            }
        }
    }

    private File adicionarImagem() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imagemNome = "JPEG_" + timeStamp + "_";
        File diretorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagem = File.createTempFile(imagemNome,".jpg", diretorio);
        imagemCaminho = imagem.getAbsolutePath();
        return imagem;
    }

    private String salvarImagem(Bitmap imagem) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imagemNome = timeStamp + ".jpg";
        String root = Environment.getExternalStorageDirectory().toString() + "/travelling_imagens";
        File diretorio = new File(root);
        if(!diretorio.exists()) {
            diretorio.mkdir();
        }
        File minhaImagem = new File(diretorio, imagemNome);
        if(minhaImagem.exists()) {
            minhaImagem.delete();
        }
        try{
            FileOutputStream out = new FileOutputStream(minhaImagem);
            imagem.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        }catch(Exception e) {
            Log.d("DEBUGAR", e.getMessage());
        }
        return root + imagemNome + ".jpg";
    }
}
