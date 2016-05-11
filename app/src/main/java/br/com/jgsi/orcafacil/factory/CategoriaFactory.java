package br.com.jgsi.orcafacil.factory;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.model.Categoria;
import br.com.jgsi.orcafacil.model.CategoriaDespesa;
import br.com.jgsi.orcafacil.model.CategoriaReceita;
import br.com.jgsi.orcafacil.model.Icone;

/**
 * Created by guilherme.costa on 27/01/2016.
 */
public class CategoriaFactory {

    public static List<Categoria> createCategoriaReceitaIconesPadrao(Resources resources) {
        List<Categoria> categorias = new ArrayList<Categoria>();
        categorias.add(new CategoriaReceita(null, criaIcone(resources, R.drawable.aluguel)));
        categorias.add(new CategoriaReceita(null, criaIcone(resources, R.drawable.salario)));
        categorias.add(new CategoriaReceita(null, criaIcone(resources, R.drawable.rendimento)));
        categorias.add(new CategoriaReceita(null, criaIcone(resources, R.drawable.transferencia)));
        categorias.add(new CategoriaReceita(null, criaIcone(resources, R.drawable.hora_extra)));
        categorias.add(new CategoriaReceita(null, criaIcone(resources, R.drawable.porquinho)));

        return categorias;
    }

    @NonNull
    public static Icone criaIcone(Resources resources, int resourceID) {
        return new Icone(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, resourceID), 70, 70, true), resourceID);
    }

    public static List<Categoria> createCategoriaDespesaIconesPadrao(Resources resources) {
        List<Categoria> categorias = new ArrayList<Categoria>();
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.academia)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.acougue)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.agua)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.aluguel)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.baby)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.carro)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.cartao)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.energia)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.farmacia)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.frutaria)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.hotel)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.jogos)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.lanche)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.mercado)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.panificadora)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.pets)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.presente)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.restaurante)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.roupas)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.salao)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.telefone)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.tv)));
        categorias.add(new CategoriaDespesa(null, criaIcone(resources, R.drawable.viajem)));
        return categorias;
    }
}
