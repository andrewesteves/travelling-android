package br.com.andrewesteves.travelling.modelos.repositorios;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.andrewesteves.travelling.modelos.basicas.Lugar;

public class LugarJson {
    private String url = "https://maps.googleapis.com/maps/api/place/nearbysearch";

    public LugarJson(String coordenadas) {
        this.url += "/json?location="+ coordenadas +"&type=restaurant&radius=300&key=AIzaSyBhwApIdG04jx9O6NNPOdg3f6E5FcPU4GE";
    }

    public HttpURLConnection conectar() throws IOException {
        URL urlReq = new URL(this.url);
        HttpURLConnection conexao = (HttpURLConnection) urlReq.openConnection();
        conexao.setRequestMethod("GET");
        conexao.setReadTimeout(15000);
        conexao.setConnectTimeout(15000);
        conexao.setDoInput(true);
        conexao.setDoOutput(false);
        conexao.connect();
        return conexao;
    }

    public List<Lugar> listar() {
        try {
            HttpURLConnection conexao = this.conectar();
            if(conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = conexao.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(is));
                return this.formatarJson(json);
            }
        }catch (Exception e) {
            Log.d("DEPUPAR", e.getMessage());
        }
        return null;
    }

    public List<Lugar> formatarJson(JSONObject json) throws JSONException {
        List<Lugar> listaLugares = new ArrayList<Lugar>();
        String lugarTitulo;
        String lugarEndereco;
        JSONArray jsonResultados = json.getJSONArray("results");

        for(int i = 0; i < jsonResultados.length(); i++) {
            JSONObject jsonLugares = jsonResultados.getJSONObject(i);
            Lugar lugar = new Lugar(jsonLugares.getString("name"), jsonLugares.getString("vicinity"));
            listaLugares.add(lugar);
        }

        return listaLugares;
    }

    private static String bytesParaString(InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
        int bytesLidos;
        while ((bytesLidos = is.read(buffer)) != -1) {
            bufferzao.write(buffer, 0, bytesLidos);
        }
        return new String(bufferzao.toByteArray(), "UTF-8");
    }
}
