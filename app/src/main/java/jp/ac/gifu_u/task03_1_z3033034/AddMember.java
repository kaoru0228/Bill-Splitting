package jp.ac.gifu_u.task03_1_z3033034;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddMember extends Activity {
    private Button button_cancel2,button_add_member;
    private EditText editText_member;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmember);


        button_cancel2 = findViewById(R.id.button_cancel2);
        button_add_member = findViewById(R.id.button_add_member);
        editText_member = findViewById(R.id.editTextTextPersonName2);

        //追加ボタンを押したら
        button_add_member.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //メンバーの名前を取得
                String event = editText_member.getText().toString();
                finish();
            }
        });

        //キャンセルボタンを押したら
        button_cancel2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
}
