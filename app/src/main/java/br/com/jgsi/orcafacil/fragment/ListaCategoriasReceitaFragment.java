package br.com.jgsi.orcafacil.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.activities.CategoriaReceitaActivity;

/**
 * Created by guilhermewesley on 25/01/2016.
 */
public class ListaCategoriasReceitaFragment extends Fragment {

    private CategoriaReceitaActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.categoria_lista, null);

        activity = (CategoriaReceitaActivity) getActivity();

        Button novaCategoria = (Button) layout.findViewById(R.id.btn_nova_categoria);

        novaCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.novaCategoriaReceita();
            }
        });

        return layout;
    }
}
