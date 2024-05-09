package jp.ac.gifu_u.task03_1_z3033034;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Member extends Activity {

    private Button button_add_payment;
    private Button button_return2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity_subレイアウトをセット
        setContentView(R.layout.activity_member_individual);

        button_add_payment = findViewById(R.id.button_add_payment);
        button_return2 = findViewById(R.id.button_return2);

        //支払いを追加ボタンを押したら
        button_add_payment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //AddPayment画面へ移動
                Intent intent = new Intent(getApplication(),AddPayment.class);
                startActivity(intent);
            }
        });
        //戻るボタンを押したら
        button_return2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //MemberList画面へ移動
                Intent intent = new Intent(getApplication(),MemberList.class);
                startActivity(intent);
            }
        });
    }
}