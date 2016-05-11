package br.com.jgsi.orcafacil.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Calendar;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.model.Categoria;
import br.com.jgsi.orcafacil.model.CategoriaDespesa;
import br.com.jgsi.orcafacil.model.CategoriaReceita;
import br.com.jgsi.orcafacil.model.Icone;

/**
 * Created by guilhermewesley on 25/01/2016.
 */
public class DummyObjects {

    public static CategoriaReceita getCategoriaReceita(){
        CategoriaReceita dummy = new CategoriaReceita();
        dummy.setId(-1L);
        dummy.setNome("Nova Categoria");
        dummy.setImagem(null);
        return dummy;
    }

    public static CategoriaDespesa getCategoriaDespesa() {
        CategoriaDespesa dummy = new CategoriaDespesa();
        dummy.setId(-1L);
        dummy.setNome("Nova Categoria");
        dummy.setImagem(null);
        return dummy;
    }

    public static String getDataAtualUltimoDiaMesFormatado(){
        Calendar data = Calendar.getInstance();
        data.set(Calendar.DAY_OF_MONTH, data.getActualMaximum(Calendar.DAY_OF_MONTH));
        return DateFormatter.formata(data);
    }
}
