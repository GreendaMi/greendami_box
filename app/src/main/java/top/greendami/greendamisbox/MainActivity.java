package top.greendami.greendamisbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import top.greendami.greendamisbox.UI.GM_EditText;

public class MainActivity extends AppCompatActivity {

    Button yes,no,loading;
    GM_EditText gm_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gm_editText = (GM_EditText)findViewById(R.id.edit);
        yes = (Button)findViewById(R.id.button);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gm_editText.startYes();
            }
        });
        no = (Button)findViewById(R.id.button2);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gm_editText.startNo();
            }
        });
        loading = (Button)findViewById(R.id.button3);
        loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gm_editText.startLoading();
            }
        });
    }
}
