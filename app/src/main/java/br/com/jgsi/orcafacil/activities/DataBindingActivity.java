package br.com.jgsi.orcafacil.activities;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.widget.TextView;

import br.com.jgsi.orcafacil.R;
import br.com.jgsi.orcafacil.databinding.DatabindingBinding;
import br.com.jgsi.orcafacil.helper.MyDataBindingHelper;

/**
 * Created by guilherme.costa on 11/05/2016.
 */
public class DataBindingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.databinding);

        // Line 1
        TextView unbound = (TextView)
                findViewById(R.id.textViewUnBound);
        // Line 2
        MyDataBindingHelper myDataBindingHelper =
                MyDataBindingHelper.get("Unbound");

        // Line 3
        unbound.setText(myDataBindingHelper.getMessage());

        DatabindingBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.databinding);
        dataBinding.setMydatabindinghelper(new MyDataBindingHelper("Bounded"));
    }
}
