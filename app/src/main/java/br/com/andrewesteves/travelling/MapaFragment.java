package br.com.andrewesteves.travelling;


import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import br.com.andrewesteves.travelling.modelos.basicas.Roteiro;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mMapView;
    private View mView;
    private Roteiro roteiro;

    public MapaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null) {
            this.roteiro = bundle.getParcelable("roteiro");
        }

        mView = inflater.inflate(R.layout.fragment_mapa, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.map);
        if(mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try{
            String endereco = this.roteiro.getLocal();
            LatLng localizacao = this.localizacaoEndereco(getContext(), endereco);

            MapsInitializer.initialize(getContext());
            mMap = googleMap;
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.addMarker(new MarkerOptions().position(localizacao).title(endereco).snippet(this.roteiro.getViagem().getTitulo()));
            CameraPosition estatua = CameraPosition.builder().target(localizacao).zoom(16).bearing(0).tilt(45).build();
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(estatua));
        }catch(Exception e) {
            Toast.makeText(getContext(), "O endereço informado não é válido", Toast.LENGTH_LONG).show();
        }
    }

    public LatLng localizacaoEndereco(Context context, String endereco) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> enderecos;
        LatLng resLatLng = null;

        try{
            enderecos = geocoder.getFromLocationName(endereco, 5);
            if(enderecos == null) {
                throw new Exception("Retorno nulo");
            }
            if(enderecos.size() == 0) {
                throw new Exception("Sem resultados");
            }

            Address localizacao = enderecos.get(0);

            resLatLng = new LatLng(localizacao.getLatitude(), localizacao.getLongitude());

        }catch(Exception e) {
            Log.d("DEPURAR", e.getMessage());
        }

        return resLatLng;
    }
}
