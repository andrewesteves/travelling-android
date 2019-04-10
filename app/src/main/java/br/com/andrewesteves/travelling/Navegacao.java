package br.com.andrewesteves.travelling;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

public class Navegacao {
    public static void iniciar(final Context context, final BottomNavigationView bottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.menu_inicio:
                                intent = new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                context.startActivity(intent);
                                break;
                            case R.id.menu_viagens:
                                intent = new Intent(context, ViagensListarActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                context.startActivity(intent);
                                break;
                            case R.id.menu_fotos:
                                intent = new Intent(context, FotosActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                context.startActivity(intent);
                                break;
                        }
                        return true;
                    }
                }
        );
    }
}
