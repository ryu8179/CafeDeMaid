package jp.ac.trident.game.maid.status;

import jp.ac.trident.game.maid.R;
import jp.ac.trident.game.maid.main.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StatusActivity extends Activity {
	
	/**
	 * 作成時の処理。
	 *
	 * @param savedInstanceState
	 *            保存されていたインスタンス
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
        
        //btn_gameを取得
        Button btn_return = (Button)findViewById(R.id.button_return);
        btn_return.setOnClickListener(new OnClickListener() {
        	// 押されたら
            public void onClick(View v) {
            	// アクティビティを終了させる事により、一つ前のアクティビティへ戻る事が出来る。
            	finish();
            }
        });
    }
    
	/**
	 * 終了時の処理。
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	/**
	 * 一時停止時の処理。
	 */
	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * 再開時の処理。
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	/**
	 * 開始時の処理。
	 */
	@Override
	protected void onStart() {
		super.onStart();
	}

	/**
	 * アプリ復帰時の処理。
	 */
	@Override
	protected void onResume() {
		super.onResume();
	}

	/**
	 * アプリ中断時の処理。
	 */
	@Override
	protected void onStop() {
		super.onStop();
	}
}