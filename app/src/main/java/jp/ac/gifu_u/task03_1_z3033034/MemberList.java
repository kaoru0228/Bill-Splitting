package jp.ac.gifu_u.task03_1_z3033034;

import static java.lang.Math.abs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MemberList extends Activity {

    private Button button_add_member,button_return,button_result;
    private EditText editText_paymentA,editText_paymentB,editText_paymentC;
    Integer pre_resultA,pre_resultB,pre_resultC,resultA,resultB,resultC;
    Integer n,l;  //nは人数
    List<Integer> k = new ArrayList<Integer>();  //重みをつけるためのリスト
    List<Integer> pre_result = new ArrayList<Integer>();  //各メンバーが支払うべき金額のリスト
    List<Integer> result = new ArrayList<Integer>();  //最終的に支払う金額のリスト
    List<String> name = new ArrayList<String>();  //名前（メンバー）のリスト

/*    String a;Integer t;
    ArrayList<charinfo> name_money = new ArrayList<>();
    charinfo b = new charinfo(t,a);*/

    List<Integer> payer = new ArrayList<Integer>();  //最期にお金を支払う人を保存
    List<Integer> payer_money = new ArrayList<Integer>();  //その支払額

    List<Integer> receiver = new ArrayList<Integer>();  //最後にお金を受け取る人を保存
    List<Integer> receiver_money = new ArrayList<Integer>();  //その受け取り額


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity_subレイアウトをセット
        setContentView(R.layout.activity_member);

        button_add_member = findViewById(R.id.button_add_member);
        button_return = findViewById(R.id.button_return);
        button_result = findViewById(R.id.button_result);
        editText_paymentA = findViewById(R.id.editTextNumber_A);
        editText_paymentB = findViewById(R.id.editTextNumber_B);
        editText_paymentC = findViewById(R.id.editTextNumber_C);


        n = 3;  //人数を3人に固定
        l=0;
        k.add(1);k.add(1);k.add(1);  //初期設定は全て１
        pre_result.add(0);pre_result.add(0);pre_result.add(0);  //初期値は0
        result.add(0);result.add(0);result.add(0);  //初期値は0
        name.add("a");name.add("a");name.add("a");  //初期値はa

/*        name_money.add(b);
        if(name_money.get().SearchName()){
            name_money.get().money=
        }*/

        k.set(0,1);k.set(1,1);k.set(2,1);  //A,B,Cの重みは順に1:1:1
        name.set(0,"Aさん");name.set(1,"Bさん");name.set(2,"Cさん");  //名前の設定

        //決算ボタンを押したら
        button_result.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //金額の取得  取得する際にedittextがNULLだとエラーする
                String payment_A = editText_paymentA.getText().toString();
                String payment_B = editText_paymentB.getText().toString();
                String payment_C = editText_paymentC.getText().toString();
                Integer paymentA = Integer.parseInt(payment_A);
                Integer paymentB = Integer.parseInt(payment_B);
                Integer paymentC = Integer.parseInt(payment_C);

                Integer payment_sum = paymentA+paymentB+paymentC;
                pre_result.set(0,payment_sum * k.get(0)/(k.get(0)+k.get(1)+k.get(2)) - paymentA);
                pre_result.set(1,payment_sum * k.get(1)/(k.get(0)+k.get(1)+k.get(2)) - paymentB);
                pre_result.set(2,payment_sum * k.get(2)/(k.get(0)+k.get(1)+k.get(2)) - paymentC);

                result.set(0,Math.abs(payment_sum * k.get(0)/(k.get(0)+k.get(1)+k.get(2)) - paymentA));
                result.set(1,Math.abs(payment_sum * k.get(1)/(k.get(0)+k.get(1)+k.get(2)) - paymentB));
                result.set(2,Math.abs(payment_sum * k.get(2)/(k.get(0)+k.get(1)+k.get(2)) - paymentC));

//                for(Integer i:pre_result){  //拡張for  for(変数名:配列やリスト名)
//                    showToast(String.valueOf(i));
//                }

                //resultが負の場合は清算時にお金が返ってきて、正の場合は誰かに支払う。
                for(Integer pre_result1:pre_result){
                    if(pre_result1>0) {
                        payer.add(l);  //最後に支払いを行う人を保持（nameリストの何番目かで）
//                        showToast(name.get(l) + "の支払い合計は" + String.valueOf(pre_result1) + " 円です。");
                    }
                    else if(pre_result1==0 | pre_result1<0){
                        receiver.add(l);  //最期にお金を受け取る人を保持（nameリストの何番目かで）
                        pre_result.set(l,Math.abs(pre_result1));
//                        showToast(name.get(l) + "の受け取り金額の合計は" + String.valueOf(pre_result1) + " 円です。");
                    }
                    l++;  //name.get(l)で何番目の人かを保存
                }
                l=0;

//                result = pre_result;  //中身はすべて正
                Integer sum=n+1;
                while(sum>n){  //pre_resultの合計がになったら終了（切り捨ての１円を考慮して人数円までは誤差とする。）
                    for(Integer payer_x:payer){
                        while(result.get(payer_x) != 0) {  //支払いを行う人について、一人ずつ処理していく。
                            for (Integer receiver_x : receiver) {
                                if (result.get(payer_x) <= result.get(receiver_x)) {  //payerの支払金額がreceiverの受け取り金額より小さいなら
                                    Log.d("zzzzz",name.get(payer_x) + "が" + name.get(receiver_x) + "に" + String.valueOf(result.get(payer_x)) + "円支払い");
                                    result.set(receiver_x,result.get(receiver_x) - result.get(payer_x));  //resultを更新
                                    result.set(payer_x,0);  //全額をreceiverに支払い
                                }
                                else if(result.get(receiver_x) !=0 && result.get(payer_x) > result.get(receiver_x)){  //payerの支払金額がreceiverの受け取り金額より大きいなら
                                    Log.d("zzzzz",name.get(payer_x) + "が" + name.get(receiver_x) + "に" + String.valueOf(result.get(receiver_x)) + "円支払い");
                                    result.set(payer_x,result.get(payer_x) - result.get(receiver_x));  //差額分をreceiverに支払い
                                    result.set(receiver_x,0);  //resultを更新
                                }
                            }
                        }
                    }
                    sum=0;
                    for(Integer result_check:result){
                        sum += Math.abs(result_check);
                    }
                }
                Log.d("zzzzz","最終清算誤差は" + String.valueOf(sum));

                for(Integer test:payer){
                    Log.d("zzzzz","支払う人は" + name.get(test));
                }
                for(Integer test1:receiver){
                    Log.d("zzzzz","もらうひとは" + name.get(test1));
                }


/*                for(int i=0;i<3;i++){
                    showToast(name.get(i) +"pre_resultは" + String.valueOf(pre_result.get(i)) + "resultは" + String.valueOf(result.get(i)));
                }*/

  /*              for(Integer resultt:result) {
                    showToast("resultの中身は" + String.valueOf(result.get(l)));
                    l++;
                }
                l=0;*/




/*                //Toastで表示
                for(Integer resultt:pre_result) {
                    showToast(name.get(l) + "の支払い合計は" + String.valueOf(resultt) + " 円です。");
                    l++;
                }
                l=0;*/
            }
        });






        //メンバーを追加ボタンを押したら
        button_add_member.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //AddMember画面へ移動
                Intent intent = new Intent(getApplication(),AddMember.class);
                startActivity(intent);
            }
        });
        //戻るボタンを押したら
        button_return.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //MainActivity画面へ移動
                Intent intent = new Intent(getApplication(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showToast(String string) {
        Toast t = Toast.makeText(this, string, Toast.LENGTH_SHORT);
        t.show();
    }
}

/*class charinfo{
    Integer money;
    String name;
    charinfo(Integer money,String name){
        this.money=money;
        this.name=name;
    };
    Boolean SearchName(String name){
      if(this.name==name)return true;
      return false;
    }

}*/