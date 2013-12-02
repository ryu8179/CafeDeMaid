/*
 * ゲーム本体。
 */
package jp.ac.trident.game.maid.game;

import java.util.Random;

import jp.ac.trident.game.maid.R;
import jp.ac.trident.game.maid.common.Vector2D;
import jp.ac.trident.game.maid.main.GameMap;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.example.maid.GameSurfaceView;
import com.example.maid.VirtualController;

/**
 * ゲーム本体のアクティビティ。
 *
 * @author minnna
 */
public class GameMain {

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
	private Random r = new Random();

	/**
	 * BGM。
	 */
	//private MediaPlayer bgm;

	/**
	 * 効果音。
	 */
	//private MediaPlayer se;
	/**
	 * コンテキスト。
	 */
	private Context context;

	/**
	 * 背景。
	 */
	private Bitmap bg;

//	private ZooData zooData;

	//private MenuButton[] menu;
	//private String[] buttonName = {"TITLE","SUGOROKU","LAYOUT","SHOP","SET"};
	//private String selectButton = "noSelect";
	//private WindowManager wm;
	//private Display disp;

	/**
	 * ビットマップ用画像
	 */
	private Bitmap floorImg, objectImg;
	/**
	 * マップチップクラス
	 */
	private GameMap map;

	/**
	 * ベクトル
	 */
	private Vector2D vec;
	

	
	
	
	//*******************************************追加した部分*********************************//
	//変数のみ
//	private boolean titleflag;
	private boolean playflag;
	private boolean tableflag;
	
	private Maid maid;
	private Bitmap maidImg;
	
	private Table table;
	private Bitmap tableImg;
	private Bitmap floorclothImg;
	
//	private Makiwari makiwari;
//	private boolean makiwariflag;
//	private Bitmap onoImg;
//	
//	private Cook cook;
//	private boolean cookflag;
//	private Bitmap food1Img;
//	private Bitmap food2Img;
//	private Bitmap food3Img;
//	private Bitmap food4Img;
//	private Bitmap food5Img;
//	private Bitmap bowlImg;
	
	private Game game;
	private boolean gameflag;
//	private int number;
//	private Bitmap gooImg;
//	private Bitmap cyokiImg;
//	private Bitmap parImg;
//	
//	private boolean maidflag;
//	
//	private Guest guest;
//	private Bitmap guestImg;
//	
//	private Buttle buttle;
//	private boolean buttleflag;
	//*******************************************追加した部分*********************************//
	
	
	
	
	
	
	
	/** ============================================ **/

	/**
	 * コンストラクタ
	 */
	public GameMain(Context context) {
		this.context = context;

		// タッチ処理用コントローラを作成する。
		//virtualController = new VirtualController();

		// タッチ座標用変数を作成
		touch_push = new Vector2D();
		touch_now = new Vector2D();
		touch_release = new Vector2D();
		//wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		//disp = wm.getDefaultDisplay();
		// 端末に保存されているユーザー名を取得する。
//		menu = new MenuButton[buttonName.length];
//
//		for(int i = 0;i < menu.length;i++){
//			menu[i] = new MenuButton(10, 50 + (100 * i), 80,120 + (100 * i),buttonName[i]);
//		}
//		zooData = new ZooData(this.context);

//		dbAdapter = new DBAdapter(this.context);
//		dbAdapter.saveData("abcdefg",0,0,0);
//		dbAdapter.loadData();
		// タッチ処理用コントローラを作成する。
		//virtualController = new VirtualController();

		// BGMを読み込む。
		//bgm = MediaPlayer.create(context, R.raw.bgm);

		// SEの読み込み
		//se = MediaPlayer.create(context, R.raw.se);

		// リソースから背景画像を読み込む。
//		bg = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
		//bg = Bitmap.createScaledBitmap(bg, disp.getWidth(), disp.getHeight(), true);

//		floorImg = BitmapFactory.decodeResource(context.getResources(),
//				R.drawable.chip,options);
		
//		objectImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.abc, options);
		
		

//		map = new GameMap(floorImg, objectImg);
		
		// ランダムの作成
		r = new Random();

		// ベクトルの作成
		vec = new Vector2D();
		
		
		
		
		
		//*******************************************追加した部分*********************************//
		//画像読み込みや初期化
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;
		maidImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.maid, options);
		tableImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.table, options);
		floorclothImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.floorcloth, options);
		table = new Table(tableImg,floorclothImg);
//		titleflag = false;
		playflag = true;
		tableflag = false;
		maid = new Maid(maidImg);
//		onoImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.ono, options);
//		makiwari = new Makiwari(onoImg);
//		makiwariflag = false;
//		food1Img = BitmapFactory.decodeResource(context.getResources(), R.drawable.food01, options);
//		food2Img = BitmapFactory.decodeResource(context.getResources(), R.drawable.food02, options);
//		food3Img = BitmapFactory.decodeResource(context.getResources(), R.drawable.food03, options);
//		food4Img = BitmapFactory.decodeResource(context.getResources(), R.drawable.food04, options);
//		food5Img = BitmapFactory.decodeResource(context.getResources(), R.drawable.food05, options);
//		bowlImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.bowl, options);
//		cook = new Cook(food1Img,food2Img,food3Img,food4Img,bowlImg,food5Img);
//		cookflag = false;
//		gooImg = BitmapFactory.decodeResource(context.getResources(),
//				R.drawable.goo,options);
//		cyokiImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.cyoki, options);
//		parImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.par, options);
//		game = new Game(gooImg,cyokiImg,parImg);
//		gameflag = false;
//		number = 0;
//		maidflag = false;
//		guestImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.guest, options);
//		guest = new Guest(guestImg);
//		buttle = new Buttle();
//		buttleflag = false;
		//*******************************************追加した部分*********************************//
		
		
		
		
		
		
		
		
	}

	/**
	 * 0～maxの中からランダムな整数を得る。
	 *
	 * @param max
	 * @return 乱数値
	 */
	public int getRandom(int max) {
		return r.nextInt(max);
	}

	/**
	 * 全サウンドを終了する（アプリ終了時にも呼ばれる）
	 */
	public void stopSound() {
//		bgm.stop();
//		se.stop();
	}

	/**
	 * ゲームを初期化する。
	 */
	void initialize() {
		// BGMを再生する。
		// bgm.start();

		context = null;

		touch_push.Init();
		touch_now.Init();
		touch_release.Init();

		vec.Init();

		totalElapsedTime = 0;

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
		
		
		
		
		
		
		
		//*******************************************追加した部分*********************************//
		//タッチしたときにシーン移動
		if(playflag == true)
		{
			// 画面にタッチしてる？
			if (VirtualController.isTouch(0)) {
				// FPSの表示座標をタッチ位置に更新
				if (VirtualController.isTouchTrigger(0)) {
					touch_push.x = VirtualController.getTouchX(0);
					touch_push.y = VirtualController.getTouchY(0);
	
					// タッチ座標を入れる
//					for(int i = 0;i < menu.length;i++){
//						if(menu[i].TouchButton((int)touch_push.x, (int)touch_push.y)){
//							selectButton = menu[i].getName();
//						}
//					}
				}
				// 指を離した時の座標を入れる
				// タッチされている座標を入れる
				touch_now.x = VirtualController.getTouchX(0);
				touch_now.y = VirtualController.getTouchY(0);
	
				vec.x = touch_now.x - touch_push.x;
				vec.y = touch_now.y - touch_push.y;
				// 効果音を再生する。
				//se.start();
			} else {
				// 指を離した時の座標を入れる
				touch_release.x = VirtualController.getTouchX(0);
				touch_release.y = VirtualController.getTouchY(0);
	
				vec.x = touch_release.x - touch_push.x;
				vec.y = touch_release.y - touch_push.y;
			}
		}
	/*	if(titleflag == true)
		{
			if(touch_push.x > 600 && touch_push.x < 680)
			{
				if(touch_push.y > 0 && touch_push.y < 50)
				{
					titleflag = false;
					playflag = true;
				}
			}
		}*/
		if(playflag == true)
		{
			if(touch_push.x > 10 && touch_push.x < 70)
			{
				if(touch_push.y > 50 && touch_push.y < 100)
				{
					playflag = false;
					tableflag = true;
					table.Initialize();
					gameflag = false;
					game.Initialize();
					maid.Initialize();
				}
			}
//			if(touch_push.x > 10 && touch_push.x < 70)
//			{
//				if(touch_push.y > 150 && touch_push.y < 200)
//				{
//					playflag = false;
//					makiwariflag = true;
//					makiwari.Initialize();
//					gameflag = false;
//					game.Initialize();
//					maid.Initialize();
//				}
//			}
//			if(touch_push.x > 10 && touch_push.x < 70)
//			{
//				if(touch_push.y > 250 && touch_push.y < 300)
//				{
//					playflag = false;
//					cookflag = true;
//					cook.Initialize();
//					gameflag = false;
//					game.Initialize();
//					maid.Initialize();
//				}
//			}
//			if(touch_push.x > 10 && touch_push.x < 70)
//			{
//				if(touch_push.y > 350 && touch_push.y < 400)
//				{
//					playflag = false;
//					maidflag = true;
//					maid.Initialize();
//					gameflag = false;
//					game.Initialize();
//					maid.Initialize();
//					maid.On_StatusScene();
//				}
//			}
//			if(touch_push.x > 450 && touch_push.x < 470)
//			{
//				if(touch_push.y > 140 && touch_push.y < 170)
//				{
//					if(gameflag == false && buttleflag == false)
//					{
//						number = getRandom(2);
//						guest.Update();
//						if(number == 0)
//						{
//							gameflag = true;
//						}
//						else
//						{
//							buttleflag = true;
//						}
//					}
//				}
//			}
		}
//		if(tableflag == true)
		{
			if(table.Return_Time() == 60)
			{
				if (VirtualController.isTouchTrigger(0))
				{
					touch_push.x = VirtualController.getTouchX(0);
					touch_push.y = VirtualController.getTouchY(0);
				}
			}
			else
			{
				if (VirtualController.isTouch(0))
				{
					touch_push.x = VirtualController.getTouchX(0);
					touch_push.y = VirtualController.getTouchY(0);
				}
			}
			table.Update(touch_push.x,touch_push.y,maid);
			if(table.Return_PauseFlag() == true)
			{
				if(touch_push.x > 550 && touch_push.x < 640)
				{
					if(touch_push.y > 390 && touch_push.y < 450)
					{
						tableflag = false;
						playflag = true;
					}
				}
			}
		}
//		if(makiwariflag == true)
//		{
//			if (VirtualController.isTouchTrigger(0))
//			{
//				touch_push.x = VirtualController.getTouchX(0);
//				touch_push.y = VirtualController.getTouchY(0);
//			}
//			makiwari.Update(touch_push.x,touch_push.y,maid);
//			if(makiwari.Return_PauseFlag() == true)
//			{
//				if(touch_push.x > 550 && touch_push.x < 640)
//				{
//					if(touch_push.y > 390 && touch_push.y < 450)
//					{
//						makiwariflag = false;
//						playflag = true;
//					}
//				}
//			}
//		}
//		if(cookflag == true)
//		{
//			if (VirtualController.isTouchTrigger(0))
//			{
//				touch_push.x = VirtualController.getTouchX(0);
//				touch_push.y = VirtualController.getTouchY(0);
//			}
//			cook.Update(touch_push.x,touch_push.y,maid);
//			if(cook.Return_PauseFlag() == true)
//			{
//				if(touch_push.x > 550 && touch_push.x < 640)
//				{
//					if(touch_push.y > 390 && touch_push.y < 450)
//					{
//						cookflag = false;
//						playflag = true;
//					}
//				}
//			}
//		}	
//		if(playflag == true)
//		{
////			map.Update(touch_now.x, touch_now.y);
//		}
//		if(maidflag == true)
//		{
//			if (VirtualController.isTouchTrigger(0))
//			{
//				touch_push.x = VirtualController.getTouchX(0);
//				touch_push.y = VirtualController.getTouchY(0);
//			}
//			maid.Update(touch_push.x,touch_push.y);
//			if(maid.Return_PauseFlag() == true)
//			{
//				if(touch_push.x > 550 && touch_push.x < 640)
//				{
//					if(touch_push.y > 390 && touch_push.y < 450)
//					{
//						maidflag = false;
//						playflag = true;
//					}
//				}
//			}
//		}
//		if(gameflag == true)
//		{
//			game.Update(touch_push.x, touch_push.y);
//			if(game.Return_Timer() < 0)
//			{
//				gameflag = false;
//				guest.Initialize();
//				number = 0;
//				game.Initialize();
//				buttle.Initialize();
//			}
//		}
//		if(buttleflag == true)
//		{
//			touch_push.x = 0;
//			buttle.Update();
//			game.MoneyUp(buttle.Return_Timer(),maid.Return_Power(),guest.Return_Power());
//			game.MoneyDown(buttle.Return_Timer(),maid.Return_Power(),guest.Return_Power());
//			if(buttle.Return_Timer() < 0)
//			{
//				buttleflag = false;
//				guest.Initialize();
//				number = 0;
//				game.Initialize();
//				buttle.Initialize();
//			}
//		}
		//*******************************************追加した部分*********************************//
		
		
		
		
		
		
		
		
	}
	
	/**
	 * ゲームシーンを描画する。
	 *
	 * @param sv
	 *            サーフェイスビュー
	 */
	void drawGame(GameSurfaceView sv) {
		
		
		
		
		
		
		//*******************************************追加した部分*********************************//
		//シーン毎の描画
		sv.Clear(Color.WHITE);
		/*if(titleflag == true)
		{
			sv.DrawText2("タイトル", 200, 300, Color.BLACK);
		}
*/
		if(playflag == true)
		{
//			map.draw(sv);
			// テキストを表示する。
			//sv.DrawText("Name:" + zooData.getName(), 10, 20, Color.BLACK);
			//sv.DrawText("Rank:", 200, 20, Color.BLACK);
			/*for(int i = 0;i < zooData.getRank();i++){
				sv.DrawText("★", 250 + (20 * i), 20, Color.BLACK);
			}*/
			//game.Draw_Money(sv);
			//sv.DrawText("Money:" + "￥" + game.Return_Money(), 500, 20, Color.BLACK);
//			sv.DrawText(selectButton, 800, 20, Color.BLACK);
			// メニューボタン
//			for(int i = 0;i < menu.length;i++){
//				sv.DrawRect(menu[i].getX(), menu[i].getY(), menu[i].getW(),menu[i].getH(),Color.CYAN);
//			}
//			sv.DrawRect((int)touch_push.x, (int)touch_push.y, (int)touch_push.x + 10,(int)touch_push.y + 10,Color.CYAN);
//			sv.DrawText2("プレイ", 200, 300, Color.BLACK);
			
			//sv.DrawText2("テ", 20, 100, Color.BLACK);
			//sv.DrawText2("マ", 20, 200, Color.BLACK);
			//sv.DrawText2("リ", 20, 300, Color.BLACK);
			//sv.DrawText2("メ", 20, 400, Color.BLACK);
			
			maid.DrawStatusScene(sv);
			//guest.Draw(sv);
			
		}	
		//if(tableflag == true)
		{
			table.Draw(sv,touch_push.x,touch_push.y);
			
		}
//		if(makiwariflag == true)
//		{
//			makiwari.Draw(sv);
//		}
//		if(cookflag == true)
//		{
//			cook.Draw(sv);
//		}
//		if(gameflag == true)
//		{
//			game.Draw(sv);
//		}
//		if(maidflag == true)
//		{
//			maid.Draw(sv);
//		}
//		if(buttleflag == true)
//		{
//			buttle.Draw(sv,maid.Return_HP(),maid.Return_Power(),guest.Return_HP(),guest.Return_Power());
//		}
		//*******************************************追加した部分*********************************//
		
		
		
		
		
		
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
}