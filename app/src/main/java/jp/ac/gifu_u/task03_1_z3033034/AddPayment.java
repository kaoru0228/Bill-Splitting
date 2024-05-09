package jp.ac.gifu_u.task03_1_z3033034;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPayment extends Activity {

    private EditText editText_title,editText_payment,editText_date2;
    private Button button_cancel3,button_add_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity_subレイアウトをセット
        setContentView(R.layout.activity_event);

        button_cancel3 = findViewById(R.id.button_cancel3);
        button_add_payment = findViewById(R.id.button_addpayment);
        editText_title = findViewById(R.id.editText_Title);
        editText_payment = findViewById(R.id.editText_Payment);
        editText_date2 = findViewById(R.id.editText_Date2);

        //追加ボタンを押したら
        button_add_payment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //タイトル、金額、日付の取得
                String title = editText_title.getText().toString();
                String payment = editText_payment.getText().toString();
                String date = editText_date2.getText().toString();
                finish();
            }
        });

        //キャンセルボタンを押したら
        button_cancel3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
}
