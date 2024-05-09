package jp.ac.gifu_u.task03_1_z3033034;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {

    private Button button01;
//    private Button button_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //レイアウトファイル(activity_main.xml)をビューにセット
        setContentView(R.layout.activity_main);
        //レイアウトファイル中のボタンを取得する
         button01 = (Button)findViewById(R.id.button01);
//         button_event = (Button)findViewById(R.id.button_event);

        //上で取得したボタンにクリックイベントの監視を設定
        //イベントの追加ボタンを押したら
        button01.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //AddEvent画面へ移動
                Intent intent = new Intent(getApplication(),AddEvent.class);
                startActivity(intent);

            }
        });

/*        //イベントボタンを押したら
        button_event.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //MemberList画面へ移動
                Intent intent = new Intent(getApplication(),MemberList.class);
                startActivity(intent);

            }
        });*/
    }
}