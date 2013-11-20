package jp.ac.trident.game.maid.main;

import jp.ac.trident.game.maid.R;
import jp.ac.trident.game.maid.game.GameActivity;
import jp.ac.trident.game.maid.status.StatusActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.maid.GameSurfaceView;
import com.example.maid.VirtualController;

public class MainActivity extends Activity implements Runnable {
	
	/**
	 * 仮想のスクリーンサイズ インターフェイスの配置などはこの数字を想定して配置する
	 */
	public static final int SYSTEM_BAR_SIZE = 0;

	/**
	 * 画面の幅。
	 */
	public static final int SCREEN_WIDTH = 800;

	/**
	 * 画面の高さ。
	 */
	public static final int SCREEN_HEIGHT = 480;

	/**
	 * フレームレート。
	 */
	public static final int FRAME_RATE = 60;

	/**
	 * スクリーンサイズ。
	 */
	public Rect screenRect;

	/**
	 * キャンバス。
	 */
	protected Canvas mainCanvas;

	/**
	 * メインスレッド。
	 */
	private Thread thread;

	/**
	 * メインループ用フラグ｡
	 */
	private static boolean loopFlag;

	/**
	 * trueの時空ループ｡
	 */
	private boolean stopFlag;

	/**
	 * フレームレイアウト｡
	 */
	private RelativeLayout relativeLayout ;

	/**
	 * サーフェイスのホルダーを保存｡
	 */
	// private SurfaceHolder surfaceHolder;

	/**
	 * サーファイスビュー｡
	 */
	private GameSurfaceView surfaceView;

	/**
	 * ゲーム本体。
	 */
	private GameMain gameMain;
	/**
	 * ゲーム本体。
	 */
	//private SugorokuMain sugorokuMain;

	/**
	 * コンテキスト。
	 */
	// private Context context;
  
	
	/**
	 * 作成時の処理。
	 *
	 * @param savedInstanceState
	 *            保存されていたインスタンス
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 変数初期化
 		loopFlag = true;

 		// 画面の矩形範囲を取得(本体全体)
 		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
 		Display display = windowManager.getDefaultDisplay();
 		// 実画面
 		screenRect = new Rect(0, 0, display.getWidth(), display.getHeight());
 		// 仮想ゲーム画面
 		Rect gameScreen = new Rect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);


 		// 描画レイヤーを作成して、初期化する。 作成順に注意すること。
 		surfaceView = new GameSurfaceView(this, screenRect, gameScreen);

 		// レイアウトを作成する。
 		relativeLayout = new RelativeLayout(this);

 		// レイヤーを登録する。
 		relativeLayout.addView(surfaceView, new LinearLayout.LayoutParams(
 				LinearLayout.LayoutParams.WRAP_CONTENT,
 				LinearLayout.LayoutParams.WRAP_CONTENT));
 		
 		
 		 //btn_statusを取得
 		ImageButton btn_status = new ImageButton(this);
 		btn_status.setImageResource(R.drawable.icon_status);
 		
 		RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
 												LinearLayout.LayoutParams.WRAP_CONTENT,
 												LinearLayout.LayoutParams.WRAP_CONTENT);
 		// マージンをピクセル単位で指定
 		params1.setMargins(10, screenRect.bottom - 110, 0, 0);
 		
 		// ボタンの処理
        btn_status.setOnClickListener(new OnClickListener() {
        	// 押されたら
            public void onClick(View v) {
            	Intent intent = new Intent(MainActivity.this,StatusActivity.class);
            	intent.setAction(Intent.ACTION_VIEW);
            	startActivity(intent);
            }
        }); 
         
        //btn_gameを取得
 		ImageButton btn_game = new ImageButton(this);
 		btn_game.setImageResource(R.drawable.icon_game);
 		
 		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
		// マージンをピクセル単位で指定
		params2.setMargins(screenRect.right - 110, screenRect.bottom - 110, 0, 0);
 		
		// ボタンの処理
        btn_game.setOnClickListener(new OnClickListener() {
        	// 押されたら
            public void onClick(View v) {
            	Intent intent = new Intent(MainActivity.this,GameActivity.class);
            	intent.setAction(Intent.ACTION_VIEW);
            	startActivity(intent);
            }
        });

 		relativeLayout.addView(btn_status, params1);
 		
 		relativeLayout.addView(btn_game, params2);

 		// ビューを設定する。
 		setContentView(relativeLayout);

 		gameMain = new GameMain(this);

 		//sugorokuMain = new SugorokuMain(this);

 		// スレッドを作成して無限ループする。
 		thread = new Thread(this);
 		thread.start();
        
    }
    
    /**
	 * 終了時の処理。
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();

		// サウンドを停止する。
		gameMain.stopSound();

		// スレッドを削除する。
		thread = null;
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

		setContentView(relativeLayout);

		// 停止中にする。
		stopFlag = false;
	}

	/**
	 * 開始時の処理。
	 */
	@Override
	protected void onStart() {
		super.onStart();

		// 停止中を解除する。
		stopFlag = false;
	}

	/**
	 * アプリ復帰時の処理。
	 */
	@Override
	protected void onResume() {
		// ビューを設定する。
		setContentView(relativeLayout);
		super.onResume();
	}

	/**
	 * アプリ中断時の処理。
	 */
	@Override
	protected void onStop() {
		super.onStop();

		// 停止中を解除する。
		stopFlag = true;
	}

	/**
	 * スレッドで実行する更新処理。
	 */
	public void run() {
		// フレーム管理FPS
		long currentTime = 0;
		long prevTime = System.nanoTime(); // ?
		long prevTimeFPS = 0;
		long frameRate = (1000000000L / FRAME_RATE);
		int fpsCount = 0;
		float elapsedTime = 0;


		// ゲームを初期化する。
		gameMain.initialize();

		try {
			while (loopFlag) {
				// 現在時刻を取得する。
				currentTime = System.nanoTime();

				// FPSを更新する。
				if (currentTime - prevTimeFPS > 1000000000L) {
					prevTimeFPS = currentTime;
					gameMain.setFps(fpsCount);
					fpsCount = 0;
				}

				if (!stopFlag) {
					// 前回から一定時間たっていたら更新する。
					if (currentTime - prevTime > frameRate) {
						prevTime = currentTime;
						fpsCount++;
						elapsedTime = (currentTime - prevTime) / 1000000000.0f;

						// 入力を更新する。
						VirtualController.update();

						// アプリケーションの更新および描画する。
						gameMain.update(elapsedTime);
						if (surfaceView.Begin()) {
							gameMain.draw(surfaceView);
							surfaceView.End();
						}
						// 実画面に描画する
						surfaceView.drawRealScreen();
					}
				}
			}

			// 終了する。
			appFinish();

		} catch (Exception e) {
			// 何もしない。
			e.printStackTrace();
		}
	}

	/**
	 * 画面タッチ時のイベントを処理する。
	 *
	 * @param event
	 *            イベント
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// イベントをコントローラに丸投げする。
		VirtualController.onEvent(event, screenRect.right, screenRect.bottom);
		return super.onTouchEvent(event);
	}

	/**
	 * 終了時の処理。
	 */
	public static void exit() {
		// ゲームループを抜けるフラグを立てる。
		loopFlag = false;
	}

	@Override
	public void finish(){
		loopFlag = false; // メインループの終了条件
	}

	public void appFinish()
	{
		// 本来のフィニッシュを呼ぶ
		super.finish();
	}
}
