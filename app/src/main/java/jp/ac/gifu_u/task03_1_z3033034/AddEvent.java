package jp.ac.gifu_u.task03_1_z3033034;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddEvent extends Activity {

    private Button button_cancel,button_addPayment,buttonCalculate;
    private EditText personName,addPayment,addEventDetail;
    private ScrollView scrollView,scrollViewResult;
    private LinearLayout linearLayout_vert,linearLayout_vert2,linearLayout_result1,linearLayout_result2;
    Integer count,n;  //イベント数をカウントする変数、参加者の人数
    Integer l,s,t,count0;  //万能変数l,s,tと、清算回数のカウントcount0

    List<String> nameList;  //参加者（メンバー）のリスト
    Set<String> set;
    List<String> nameEvList = new ArrayList<String>();  //イベントにおける名前（メンバー）のリスト
    List<Integer> paymentList = new ArrayList<Integer>();  //追加金額を保存するリスト
//    List<Integer> paymentSum = new ArrayList<Integer>();  //メンバーそれぞれの支払い合計
//    List<Integer> paymentPreResult = new ArrayList<Integer>();  //各メンバーが支払うべき金額のリスト
//    List<Integer> payer = new ArrayList<Integer>();  //最期にお金を支払う人の配列番号（nameListにおける）
//    List<Integer> receiver = new ArrayList<Integer>();  //最期にお金を払う人の配列番号


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity_subレイアウトをセット
        setContentView(R.layout.activity_event);

        button_addPayment = findViewById(R.id.button_addPayment);
        //button_addMember.setBackgroundColor(Color.MAGENTA);
        button_cancel = findViewById(R.id.button2);
        buttonCalculate = findViewById(R.id.button_calculate);
        personName = findViewById(R.id.editTextPersonName);
        addPayment = findViewById(R.id.editText_addPayment);
        addEventDetail = findViewById(R.id.editText_addEventDetail);

        scrollView = findViewById(R.id.scrollView);
        scrollView.setBackgroundColor(Color.LTGRAY);
        scrollViewResult = findViewById(R.id.scrollView_result);
        scrollViewResult.setBackgroundColor(Color.LTGRAY);
        linearLayout_vert = findViewById(R.id.linearLayout_vert);
        linearLayout_vert2 = findViewById(R.id.linearLayout_vert2);
        count=0;count0=0;

        linearLayout_result1 = new LinearLayout(this);  //清算結果をverticalな１の中にhorizontalな２を入れ子にすることで実現
        linearLayout_result1.setOrientation(LinearLayout.VERTICAL);
        linearLayout_result2 = new LinearLayout(this);  //これは間違いで、正しくは清算結果（誰が誰に支払いをしたか）毎に適宜LinearLayoutを作成し代入する。
        linearLayout_result2.setOrientation(LinearLayout.HORIZONTAL);

        //支払いの追加ボタンを押したら
        button_addPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //イベント名、支払者の氏名、支払額にいずれかが未入力ならエラー文を表示
                if(personName.getText().toString().equals("")|addEventDetail.getText().toString().equals("")|addPayment.getText().toString().equals("")){
                    if(addEventDetail.getText().toString().equals("")) showToast("イベント名を入力してください");
                    if(personName.getText().toString().equals("")) showToast("支払者の名前を入力してください");
                    if(addPayment.getText().toString().equals("")) showToast("支払額を入力してください");
                }
                else {
                    if (count == 0) {
                        drawPartition(linearLayout_vert);
                    }

                    //リストに、名前と追加金額を追加
                    nameEvList.add(personName.getText().toString());
                    paymentList.add(Integer.parseInt(addPayment.getText().toString()));

                    //参加者リストを作成（これまでにない名前ならリストに追加）
                    set = new HashSet<String>(nameEvList);
                    nameList = new ArrayList(set);
                    //                for (String name:nameList){
                    //                    Log.d("zzz",name);
                    //                }

                    //参加者リストを作成（これまでにない名前ならリストに追加）
                    //                for(int i=0;i<nameEvList.size();i++){
                    //                    for(int j=i+1;j<nameEvList.size();j++){
                    //                        if(nameEvList.get(i).equals(nameEvList.get(j))){
                    //                            Log.d("zzzzza","if");
                    //                            check=1;
                    //                            break;
                    //                        }
                    //                    }
                    //                    if(check==1) break;
                    //                }
                    //                if(check==0) nameList.add(personName.getText().toString());
                    //                for(String name1:nameList) {
                    //                    Log.d("zzzzza",name1);
                    //                }
                    //                check=0;  //初期化


                    make_event(linearLayout_vert, count, personName.getText().toString(), Integer.parseInt(addPayment.getText().toString()), addEventDetail.getText().toString());
                    drawPartition(linearLayout_vert);
                    count++;
                    //showToast(String.valueOf(count) + "個目のイベントの追加");
                    /*for(String name5:nameList){
                        //showToast(name5);
                    }*/
                }
            }

        });

        //清算を押したら、清算結果をscrollView_resultに追加する。
        buttonCalculate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                n = nameList.size();
                l=0;s=0;t=0;
                Integer paySum = 0;  //全員の支払合計

                List<Integer> paymentSum = new ArrayList<Integer>();  //メンバーそれぞれの支払い合計
                List<Integer> paymentPreResult = new ArrayList<Integer>();  //各メンバーが支払うべき金額のリスト
                List<Integer> payer = new ArrayList<Integer>();  //最期にお金を支払う人の配列番号（nameListにおける）
                List<Integer> receiver = new ArrayList<Integer>();  //最期にお金を払う人の配列番号

                addText(linearLayout_vert2,String.valueOf(count0+1) + "回目の清算");

    /*            if(count0==0){  //最初に押したときのみaddする。
                    for(int j=0;j<n;j++){
                        paymentSum.add(0);  //paymentSumの初期値を設定
                        paymentPreResult.add(0);  //paymentPreResultの初期値を設定
                    }
                }
                else if(count0>0){
                    for(int j=0;j<n;j++){
                        paymentSum.set(j,0);  //初期化
                        paymentPreResult.set(j,0);
                    }
                }*/

                for(int j=0;j<n;j++){
                    paymentSum.add(0);  //paymentSumの初期値を設定
                    paymentPreResult.add(0);  //paymentPreResultの初期値を設定
                }

                for(String name:nameList){  //paymentSumに各々の支払金額を保存
                    for(String name1:nameEvList){
                        if(name.equals(name1)){
                            paymentSum.set(l,paymentSum.get(l) + paymentList.get(s));
                        }
                        s++;
                    }
                    s=0;
                    l++;
                }
                for(Integer pay:paymentSum){ paySum += pay; }  //全員の支払いの合計を計算

                l=0;s=0;
                for(Integer pay:paymentSum){  //各々の支払うべき金額を計算（正なら支払い、負なら受け取り）
                    paymentPreResult.set(l,paySum/n - pay);
                    if (paymentPreResult.get(l) > 0) {
                        payer.add(l);
                    }
                    else if (paymentPreResult.get(l) <= 0) {
                        receiver.add(l);
                        paymentPreResult.set(l, Math.abs(paymentPreResult.get(l)));
                    }
                    /*if(count0==0){  //payer,receiverについて、初めて清算を押されたときはadd
                        if (paymentPreResult.get(l) > 0) {
                            payer.add(l);
                        }
                        else if (paymentPreResult.get(l) <= 0) {
                            receiver.add(l);
                            paymentPreResult.set(l, Math.abs(paymentPreResult.get(l)));
                        }
                    }
                    else if(count0>0) {  //2回目以降に清算を押されたときはset
                        if (paymentPreResult.get(l) > 0) {
                            payer.set(s, l);
                            s++;
                        }
                        else if (paymentPreResult.get(l) <= 0) {
                            receiver.set(t, l);
                            t++;
                            paymentPreResult.set(l, Math.abs(paymentPreResult.get(l)));
                        }
                    }*/
                    l++;
                }
                Integer checker = n+1;
                while(checker>n){
                    for(Integer payer_x:payer){
                        while(paymentPreResult.get(payer_x) != 0) {  //支払いを行う人について、一人ずつ処理していく。
                            for (Integer receiver_x : receiver) {
                                if (paymentPreResult.get(payer_x) <= paymentPreResult.get(receiver_x)) {  //payerの支払金額がreceiverの受け取り金額より小さいなら
                                    Log.d("zzzzz",nameList.get(payer_x) + "が" + nameList.get(receiver_x) + "に" + String.valueOf(paymentPreResult.get(payer_x)) + "円支払い");
                                    addText(linearLayout_vert2,nameList.get(payer_x) + "が" + nameList.get(receiver_x) + "に" + String.valueOf(paymentPreResult.get(payer_x)) + "円支払い");
                                    paymentPreResult.set(receiver_x,paymentPreResult.get(receiver_x) - paymentPreResult.get(payer_x));  //resultを更新
                                    paymentPreResult.set(payer_x,0);  //全額をreceiverに支払い
                                }
                                else if(paymentPreResult.get(receiver_x) > 0 && paymentPreResult.get(payer_x) > paymentPreResult.get(receiver_x)){  //payerの支払金額がreceiverの受け取り金額より大きいなら
                                    Log.d("zzzzz",nameList.get(payer_x) + "が" + nameList.get(receiver_x) + "に" + String.valueOf(paymentPreResult.get(receiver_x)) + "円支払い");
                                    addText(linearLayout_vert2,nameList.get(payer_x) + "が" + nameList.get(receiver_x) + "に" + String.valueOf(paymentPreResult.get(receiver_x)) + "円支払い");
                                    paymentPreResult.set(payer_x,paymentPreResult.get(payer_x) - paymentPreResult.get(receiver_x));  //差額分をreceiverに支払い
                                    paymentPreResult.set(receiver_x,0);  //resultを更新
                                }
                            }
                        }
                    }
                    checker=0;
                    for(Integer result_check:paymentPreResult){
                        checker += Math.abs(result_check);
                        //Log.d("zzzzz","PreResultは"+String.valueOf(result_check));
                    }
                    Log.d("zzzzz","清算誤差は"+String.valueOf(checker));

                    //for(Integer test:payer) Log.d("zzzzz","支払う人は"+String.valueOf(test));
                    //for(Integer test:receiver) Log.d("zzzzz","受け取るは"+String.valueOf(test));


                }
//                linearLayout_vert2.addView(linearLayout_result1);

            l=0;
            for(Integer check:paymentSum){
                Log.d("zzzzz",nameList.get(l)+"さん"+String.valueOf(check)+"円");
                l++;
            }

            addText(linearLayout_vert2,"");

            l=0;
            for(Integer paymentSum_last:paymentSum){
                addText(linearLayout_vert2,nameList.get(l)+"さんの支払額は"+String.valueOf(paymentSum_last)+"円");
                l++;
            }
            addText(linearLayout_vert2,"");
            count0++;
            }
        });

        //キャンセルボタンを押したら
        button_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplication(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //メンバーを作成する関数。メンバーを追加ボタンを押したときに呼び出す。
    void make_event(LinearLayout layout,Integer count,String person_name,Integer payment,String eventDetail){
        LinearLayout member_info = new LinearLayout(this);
        member_info.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout member_info2 = new LinearLayout(this);
        member_info2.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout member_info3 = new LinearLayout(this);
        member_info3.setOrientation(LinearLayout.VERTICAL);
        member_info3.setBackgroundColor(Color.LTGRAY);

        Integer size=20;  //文字のサイズ

        TextView name1 = new EditText(this);  //メンバーの名前
        name1.setTextSize(size);
        name1.setText(person_name);
        //name1.setBackgroundColor(Color.GRAY);

        TextView san = new TextView(this);
        san.setTextSize(size);
        san.setText("さん　　　");

        /*EditText weight = new EditText(this);
        weight.setInputType(InputType.TYPE_CLASS_NUMBER);
        weight.setTextSize(size);
        weight.setWidth(200);
        weight.setBackgroundColor(Color.GRAY);*/

        /*TextView kingaku = new TextView(this);
        kingaku.setTextSize(size);
        kingaku.setText("追加金額");*/

        TextView payment1 = new TextView(this);  //その人の支払合計金額
        payment1.setTextSize(size);
        payment1.setText(String.valueOf(payment));
        //payment1.setWidth(200);
        //payment1.setBackgroundColor(Color.GRAY);

        TextView en = new TextView(this);
        en.setTextSize(size);
        en.setText("円　支払い");

        TextView event_detail = new EditText(this);  //支払いイベントの詳細
        event_detail.setTextSize(size);
        event_detail.setText(eventDetail);
        //event_detail.setWidth(200);
        //event_detail.setBackgroundColor(Color.GRAY);

        /*Button button_addPayment = new Button(this);
        button_addPayment.setText("金額の追加");
        button_addPayment.setBackgroundColor(Color.GRAY);*/

        member_info.addView(event_detail);
//        member_info.addView(weight);

        member_info2.addView(name1);
        member_info2.addView(san);
        //member_info2.addView(kingaku);
        member_info2.addView(payment1);
        member_info2.addView(en);
        //member_info2.addView(button_addPayment);

        member_info3.addView(member_info);
        member_info3.addView(member_info2);

        layout.addView(member_info3);
    }

    void drawPartition(LinearLayout layout){  //仕切りを描画する関数
        LinearLayout linearLayout_partition = new LinearLayout(this);
        linearLayout_partition.setOrientation(LinearLayout.HORIZONTAL);
        TextView partition = new TextView(this);
        partition.setWidth(1400);
        partition.setHeight(15);
        partition.setText("");
        partition.setBackgroundColor(Color.GRAY);
        linearLayout_partition.addView(partition);
        layout.addView(linearLayout_partition);
    }

    void addText(LinearLayout layout,String str) {  //LinearLayoutと文字列を与えたら、文字列をTextViewとしてLinearLayoutに追加する関数
        TextView text = new TextView(this);
        text.setTextSize(20);
        text.setText(str);
        layout.addView(text);
    }

    public void showToast(String string) {
        Toast t = Toast.makeText(this, string, Toast.LENGTH_SHORT);
        t.show();
    }
}
