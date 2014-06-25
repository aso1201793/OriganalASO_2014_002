package com.example.originalaso_2014_002;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity implements View.OnClickListener  {
	
	SQLiteDatabase sdb = null;
	MySQLiteOpenHelper helper =null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		
	}
	@Override
	protected void onResume(){
		//TODO 自動生成されたメソッド・スタブ
		super.onResume();
		
		//登録ボタン変数にリスナーを登録する
		Button btnENTRY = (Button)findViewById(R.id.button2);
		btnENTRY.setOnClickListener(this);
		
		//メンテボタン変数にリスナーを登録する
		Button btnMAITE = (Button)findViewById(R.id.button1);
		btnMAITE.setOnClickListener(this);
		
		//一言チェックボタン変数にリスナーを登録する
		Button btnCHECK =(Button)findViewById(R.id.button3);
		btnCHECK.setOnClickListener(this);
		
		//クラスのフィールド変数がNULLなら、データベース全開オープン
		if(sdb == null){
			helper = new MySQLiteOpenHelper(getApplicationContext());
		}
		try{
			sdb = helper.getWritableDatabase();
			
		}catch(SQLiteException e){
			//異常終了
			return;
		}
	}
	@Override
	public void onClick(View v){
		//TODO 自動生成されたメソッド・スタブ
		
		//生成して代入用のIntentインスタンス変数を用意
		Intent intent = null;
		switch(v.getId()){ //どのボタンが押されたか判定
		case R.id.button2: //登録ボタンが押された
			//エディットテキストからの入力内容を取り出す
			EditText etv = (EditText)findViewById(R.id.edt);
			String inputMsg = etv.getText().toString();
			
			//inputMsgがnullでない、かつ、空でない場合のみ、if文内を実行
			if(inputMsg!=null && !inputMsg.isEmpty()){
				//MySQLiteOpenHelperのインサートメソッドを呼び出し
				helper.insertHitokoto(sdb,inputMsg);
			}
			//入力欄をクリア
			etv.setText("");
			break;
		case R.id.button1: //メンテボタンが押された
		
			//インテントのインスタンス生成
		intent = new Intent(MainActivity.this,MaintenanceActivity.class);
		//次画面のアクティビティ起動
		startActivity(intent);
		case R.id.button3: //一言チェックボタンが押された
		
			//MySQLiteOpenHelperのセレクト一言メソッドを呼び出して一言をランダムに取得
			String strHitokoto = helper.selectRandomHitokoto(sdb);
			
			//インテントのインスタンス生成
			intent = new Intent(MainActivity.this,HitokotoActivity.class);
			//インテントに一言を混入
			intent.putExtra("hitokoto",strHitokoto);
			
			//次画面のアクティビティ起動
			startActivity(intent);
			break;
		}
	}

	private int v() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

}
