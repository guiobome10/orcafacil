package br.com.jgsi.orcafacil.util;

import android.text.Selection;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by guilhermewesley on 25/01/2016.
 */
public class DateTimeMask implements View.OnKeyListener {

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        EditText et = (EditText) v;

        if(event.getAction() == KeyEvent.ACTION_UP && keyCode != KeyEvent.KEYCODE_DEL){
            int length = et.getText().toString().length();

            switch (length){
                case 2:
                    et.setTextKeepState(et.getText()+ "/");
                    break;
                case 5:
                    et.setTextKeepState(et.getText() + "/");
                    break;
            }
        }

        if(et.getText().toString().length() < 7) Selection.setSelection(et.getText(), et.getText().toString().length());

        return false;
    }
}
