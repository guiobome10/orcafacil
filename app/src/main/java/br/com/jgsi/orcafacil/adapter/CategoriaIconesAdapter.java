package br.com.jgsi.orcafacil.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.model.Categoria;

/**
 * Created by guilherme.costa on 21/01/2016.
 */
public class CategoriaIconesAdapter extends BaseAdapter {

    private List<Categoria> categorias;
    private Activity activity;



    public CategoriaIconesAdapter(List<Categoria> categorias, Activity activity) {
        this.categorias = categorias;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Object getItem(int position) {
        return categorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categorias.get(position).getId() != null
                ? categorias.get(position).getId()
                : categorias.get(position).getImagem().getDrawableID() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = activity.getLayoutInflater().inflate(R.layout.categoria_icone_item, null);

        Categoria categoria = categorias.get(position);

        //Recuperar e popula os campos do xml.
        ImageView iconeImageView = (ImageView) item.findViewById(R.id.icone_categoria_receita);
        if(iconeImageView != null) iconeImageView.setImageBitmap(categoria.getImagem().getBitmap());

        TextView campoNome = (TextView) item.findViewById(R.id.icone_categoria_receita_nome);
        if(campoNome != null && categoria.getNome() != null){
            campoNome.setText(categoria.getNome());
        }

        return item;
    }
}
