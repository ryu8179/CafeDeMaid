/*
 * ゲーム本体。
 */
package jp.ac.trident.game.maid.main;

import java.util.HashMap;
import java.util.Random;

import jp.ac.trident.game.maid.R;
import jp.ac.trident.game.maid.common.CommonData;
import jp.ac.trident.game.maid.common.Vector2D;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.example.maid.GameSurfaceView;
import com.example.maid.VirtualController;
import com.example.maid.ZooData;

/**
 * ゲーム本体のアクティビティ。
 *
 * @author minnna
 */
public class GameMain {
	/* ================== 定数宣言 ================== */
	/**
	 * テクスチャー用の列挙体
	 * @author ryu8179
	 *
	 */
	public enum TEX_NAME {
		NUMBER,
		FLOOR_CHIP,
		WALL,
		OBJECT,
		COOKING_TABLE_BALLOON,
		CUSTOMER_ORDER_BALLOON,
		MAID_01,
		MAID_02,
		MOHIKAN,
		FOOD,
		BG_OF_MONEY,
	}
	/* ================== ここまで定数宣言 ================== */

	/** ================== 変数宣言 ================== **/

	/**
	 * FPS。
	 */
	private int fps;

	/**
	 * 経過時間
	 */
	private long startTime = 0;

	/**
	 * 合計経過時間
	 */
	private float totalElapsedTime;


	/**
	 * 仮想コントローラ。
	 */
	private VirtualController virtualController;

	/**
	 * マウスを押したX,Y座標。
	 */
	private Vector2D touch_push;
	/**
	 * マウスを押している座標
	 */
	private Vector2D touch_now;

	/**
	 * マウスを離したX,Y座標
	 */
	private Vector2D touch_release;

	/**
	 * 乱数オブジェクト。
	 */
	public static Random rand = new Random();

	/**
	 * BGM。
	 */
	private MediaPlayer bgm;

	/**
	 * 効果音。
	 */
	private MediaPlayer se;

	/**
	 * コンテキスト。
	 */
	private Context context;
	
	/**
	 * 使用するイメージリソースをstaticで持ってしまう。
	 */
	public static HashMap<TEX_NAME, Bitmap> imageHashMap;

	/**
	 * 背景。
	 */
	private Bitmap bg;

	private ZooData zooData;

	private MenuButton[] menu;
	private String[] buttonName = {"TITLE","SUGOROKU","LAYOUT","SHOP","SET"};
	private String selectButton = "noSelect";
	private WindowManager wm;
	private Display disp;

	/**
	 * マップチップクラス
	 */
	private GameMap map;

	/**
	 * ベクトル
	 */
	private Vector2D vec;
	
	private Vector2D vec_holder;
	
	/**
	 * 選択した座標用
	 */
	private Vector2D select_touth;
	
	/**
	 * 背景の位置
	 */
	private int bg_x,bg_y;

	/** ============================================ **/

	/**
	 * コンストラクタ
	 */
	public GameMain(Context context) {
		this.context = context;

		// タッチ処理用コントローラを作成する。
		virtualController = new VirtualController();

		// タッチ座標用変数を作成
		touch_push = new Vector2D();
		touch_now = new Vector2D();
		touch_release = new Vector2D();
		wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		disp = wm.getDefaultDisplay();
		// 端末に保存されているユーザー名を取得する。
		menu = new MenuButton[buttonName.length];

		for(int i = 0;i < menu.length;i++){
			menu[i] = new MenuButton(10, 50 + (100 * i), 80,120 + (100 * i),buttonName[i]);
		}
		zooData = new ZooData(this.context);

		// BGMを読み込む。
		bgm = MediaPlayer.create(context, R.raw.bgm);

		// SEの読み込み
		se = MediaPlayer.create(context, R.raw.se);

		// リソースから背景画像を読み込む。
		bg = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
		//bg = Bitmap.createScaledBitmap(bg, disp.getWidth(), disp.getHeight(), true);

		// 経営部分で使用する画像の読み込みなど
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;
		
		// staticなMapに格納する
		imageHashMap = new HashMap<TEX_NAME, Bitmap>();
		imageHashMap.clear();
		
		Bitmap numberImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.number, options);
		Bitmap floorImg = BitmapFactory.decodeResource(context.getResources(),R.drawable.floor_chip_w64_h64_var3,options);
		Bitmap wallImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.wall, options);
		Bitmap objectImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.obj5, options);
		Bitmap cookingBalloonImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.cooking_table_balloon, options);
		Bitmap customerBalloonImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.customer_order_balloon, options);
		Bitmap maid01Img = BitmapFactory.decodeResource(context.getResources(), R.drawable.maid01, options);
		Bitmap maid02Img = BitmapFactory.decodeResource(context.getResources(), R.drawable.maid02, options);
		Bitmap mohikanImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.mohikan_edit, options);
		Bitmap foodImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.food, options);
		Bitmap bgOfMoney = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_of_money, options);

		// staticなMapに格納する
		imageHashMap.put(TEX_NAME.NUMBER, numberImg);
		imageHashMap.put(TEX_NAME.FLOOR_CHIP, floorImg);
		imageHashMap.put(TEX_NAME.WALL, wallImg);
		imageHashMap.put(TEX_NAME.OBJECT, objectImg);
		imageHashMap.put(TEX_NAME.COOKING_TABLE_BALLOON, cookingBalloonImg);
		imageHashMap.put(TEX_NAME.CUSTOMER_ORDER_BALLOON, customerBalloonImg);
		imageHashMap.put(TEX_NAME.MAID_01, maid01Img);
		imageHashMap.put(TEX_NAME.MAID_02, maid02Img);
		imageHashMap.put(TEX_NAME.MOHIKAN, mohikanImg);
		imageHashMap.put(TEX_NAME.FOOD, foodImg);
		imageHashMap.put(TEX_NAME.BG_OF_MONEY, bgOfMoney);
		
		// mapの作成
		map = new GameMap();

		// ベクトルの作成
		vec = new Vector2D();
		
		vec_holder = new Vector2D();
		
		select_touth = new Vector2D();
	}

	/**
	 * 0～maxの中からランダムな整数を得る。
	 *
	 * @param max
	 * @return 乱数値
	 */
	public int getRandom(int max) {
		return rand.nextInt(max);
	}

	/**
	 * 全サウンドを終了する（アプリ終了時にも呼ばれる）
	 */
	public void stopSound() {
		bgm.stop();
		se.stop();
	}

	/**
	 * ゲームを初期化する。
	 */
	public void initialize() {
		// BGMを再生する。
		// bgm.start();

		context = null;

		touch_push.Init();
		touch_now.Init();
		touch_release.Init();

		vec.Init();
		
		vec_holder.Init();

		totalElapsedTime = 0;
		
		bg_x = -175;
		bg_y = -180;
		
		select_touth.Init();
	}

	/**
	 * フレーム毎に座標などを更新する。
	 */
	public void update(float elapsedTime) {
		updateGame(elapsedTime);
	}

	/**
	 * フレーム毎に描画する。
	 *
	 * @param sv
	 *            サーフェイスビュー
	 */
	public void draw(GameSurfaceView sv) {
		drawGame(sv);
	}

	/**
	 * ゲームの状態を更新する。
	 */
	void updateGame(float elapsedTime) {

		if (elapsedTime != 0.0f) {
			// デバック用　現在意味をなしていない GameActivityに問題あり
			// 合計経過時間
			totalElapsedTime += elapsedTime;
		}
		
		// タッチされた瞬間
		if(VirtualController.isTouchTrigger(0)){
			select_touth.x = VirtualController.getTouchX(0);
			select_touth.y = VirtualController.getTouchY(0);
		}
		
		// 画面にタッチしてる？
		if (VirtualController.isTouch(0)) {
			// FPSの表示座標をタッチ位置に更新
			if (VirtualController.isTouchTrigger(0)) {
				touch_push.x = VirtualController.getTouchX(0);
				touch_push.y = VirtualController.getTouchY(0);

				// タッチ座標を入れる
				for(int i = 0;i < menu.length;i++){
					if(menu[i].TouchButton((int)touch_push.x, (int)touch_push.y)){
						selectButton = menu[i].getName();
					}
				}
			}
			
			// 指を離した時の座標を入れる
			// タッチされている座標を入れる
			touch_now.x = VirtualController.getTouchX(0);
			touch_now.y = VirtualController.getTouchY(0);
			Log.d("debug", "touchPos = "+touch_now.x+", "+touch_now.y);

			vec.x = touch_now.x - touch_push.x;
			vec.y = touch_now.y - touch_push.y;
			
//			if(Math.abs(vec.x) >= 10.0f || Math.abs(vec.y) >= 10.0f)
//			{
//				GameSurfaceView.SetVecXY(vec.x, vec.y);
//			}
			
			// 効果音を再生する。
			se.start();
		} else {
			// 指を離した時の座標を入れる
			touch_release.x = VirtualController.getTouchX(0);
			touch_release.y = VirtualController.getTouchY(0);
		}
		
		map.Update(select_touth.x, select_touth.y);
		
		vec_holder.x = vec.x;
		vec_holder.y = vec.y;
	}

	/**
	 * ゲームシーンを描画する。
	 *
	 * @param sv
	 *            サーフェイスビュー
	 */
	void drawGame(GameSurfaceView sv) {
		
		// 背景を表示する。
		sv.DrawImage(bg, bg_x, bg_y);
		//sv.DrawImage(bg, bg_x + (int)vec.x, bg_y + (int)vec.y);

		map.Draw(sv);
		
		// 所持金等のUIを表示する。
		DrawUI(sv);
		
	//**************************************************************************************//
		// テキストを表示する。
		sv.DrawText("bg_x:" + bg_x, 90, 40, Color.BLACK);
		sv.DrawText("bg_y:" + bg_y, 90, 60, Color.BLACK);
		sv.DrawText("vec.x:" + vec.x, 290, 40, Color.BLACK);
		sv.DrawText("vec.y:" + vec.y, 290, 60, Color.BLACK);
		//sv.DrawText("FPS:" + fps, 90, 40, Color.BLACK);
		//sv.DrawText("x:" + touch_push.x + " y:" + touch_push.y, 200, 40,Color.WHITE);
		//sv.DrawText("x2:" + touch_release.x + " y2:" + touch_release.y, 200,60, Color.WHITE);
		//sv.DrawText("vec_X:" + vec.x + " vec_y:" + vec.y, 200, 80, Color.WHITE);


		// テキストを表示する。
//		sv.DrawText("Name:" + zooData.getName(), 90, 20, Color.BLACK);
//		sv.DrawText("Rank:", 300, 20, Color.WHITE);
//		for(int i = 0;i < zooData.getRank();i++){
//			sv.DrawText("★", 250 + (20 * i), 20, Color.WHITE);
//		}
//		sv.DrawText("Money:" + "￥" + zooData.getMoney(), 500, 20, Color.WHITE);
//		sv.DrawText("x:" + touch_push.x, 500, 100, Color.WHITE);
//		sv.DrawText("y:" + touch_push.y, 700, 100, Color.WHITE);
		//sv.DrawText(selectButton, 800, 20, Color.WHITE);
		// メニューボタン
		//for(int i = 0;i < menu.length;i++){
		//	sv.DrawRect(menu[i].getX(), menu[i].getY(), menu[i].getW(),menu[i].getH(),Color.CYAN);
		//}
		//sv.DrawRect((int)touch_push.x, (int)touch_push.y, (int)touch_push.x + 10,(int)touch_push.y + 10,Color.CYAN);
		
	//**************************************************************************************//
	}

	/**
	 * FPSを設定する。
	 *	指摘
	 * @param fps
	 *            FPS
	 */
	public void setFps(int fps) {
		this.fps = fps;
	}
	
	/**
	 * 所持金等のUIを表示する
	 * @param sv
	 */
	private void DrawUI(GameSurfaceView sv) {
		sv.DrawImage(GameMain.imageHashMap.get(TEX_NAME.BG_OF_MONEY), (int)(800-(250*1.0f)), 0, 0, 0, 250, 100, 1.0f, 0.75f, false, 255);
		sv.DrawImage(GameMain.imageHashMap.get(TEX_NAME.OBJECT), 790-64, 0,0, 576, 64, 64, false);
		DrawNumber(sv, CommonData.GetInstance().GetPlayerData().money, new Vector2D(790-64, 24));
	}
	
	/**
	 * スプライトで数字を表示。
	 * @param sv
	 * @param num
	 * @param rightPos	数字なので右寄せで描画をする、右端座標
	 */
	private void DrawNumber(GameSurfaceView sv, int num, Vector2D rightPos) {
		// 桁数を計算
		int digit = 0;
		int tmp = num;
		while (tmp > 0) {
			tmp /= 10;
			digit++;
		}
		
		// 桁数分だけ、右から表示
		for (int i=0; i<digit; i++) {
			tmp = num % 10;
			sv.DrawImage(GameMain.imageHashMap.get(TEX_NAME.NUMBER), (int)(rightPos.x-32*(i+1)), (int)(rightPos.y), tmp*32, 0, 32, 32, false);
			num /= 10;
		}
	}
}
