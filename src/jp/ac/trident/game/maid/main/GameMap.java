/**
 * ゲーム名：うんちゃらかんちゃら
 * 
 * マップチップクラス
 */
package jp.ac.trident.game.maid.main;

import java.util.ArrayList;

import jp.ac.trident.game.maid.common.Collision;
import jp.ac.trident.game.maid.common.CommonData;
import jp.ac.trident.game.maid.common.Vector2D;
import jp.ac.trident.game.maid.main.Customer.PHASE;
import jp.ac.trident.game.maid.main.Food.FOOD_NAME;
import jp.ac.trident.game.maid.main.GameMain.TEX_NAME;
import jp.ac.trident.game.maid.main.ObjectData.OBJECT_NAME;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import com.example.maid.GameSurfaceView;

/**
 * マップクラス
 * 
 * @author matsuno
 * 
 */
public class GameMap {

	/*
	 * マップ制作方針 なんとなくつけているもの ・マップチップの大きさ（幅　高さ） ・マップのサイズ(縦○マス　横○マス) ・マップの座標
	 * ・マップの種類 ・リソース画像
	 */

	
	/* 定数宣言 */
	/**
	 * 店に入るか判定する、店の前の道路の座標
	 */
	public static final Vector2D FRONT_OF_SHOP_POS = new Vector2D(80, 60);
	/**
	 * お店の入口座標
	 */
	public static final Vector2D ENTRANCE_POS = new Vector2D(224, 152);

	/**
	 * マップのマス数（横）
	 */
	public static final int MAP_WIDTH = 11;

	/**
	 * マップのマス数（縦）
	 */
	public static final int MAP_HEIGHT = 10;

	/**
	 * 画面の幅
	 */
	private final int SCREEN_WIDTH = 800;

	/**
	 * 画面の高さ
	 */
	private final int SCREEN_HEIGHT = 480;

	/**
	 * チップの幅
	 */
	private final int MAP_CHIPSIZE_WIDTH = 64;

	/**
	 * チップの高さ
	 */
	private final int MAP_CHIPSIZE_HEIGHT = 32;

	/**
	 * マップオフセット オフセットは画面の大きさの半分
	 */
	private int map_Offset_x = SCREEN_WIDTH / 2;
	private int map_Offset_y = SCREEN_HEIGHT / 2;

	/**
	 * チップのオフセット
	 */
	private int chip_offset_x = MAP_CHIPSIZE_WIDTH / 2;
	private int chip_offset_y = MAP_CHIPSIZE_HEIGHT / 2;
	/**
	 * マップチップの画像読み込み幅
	 */
	private final int CHIP_RES_LENGTH = 4;
	/* ここまで定数宣言 */

	/* メンバ変数 */
	/**
	 * 生成してからの経過フレーム
	 */
	private int m_elapsedFrame;
	/**
	 * ひし形当たり判定用変数達
	 */
	private int map_cx;
	private int map_cy;
	private float gradient;
	private int col1, col2, col3, col4;
	private int target_squareY = 0, target_squareX = 2;
	private Vector2D select_pos;

	/**
	 * リソース画像 
	 * floor	: 床 
	 * object	: 物
	 * 
	 * food		: メイドが持つ料理品
	 */
	private Bitmap floor_img, wall_img, object_img, food_img;

	/**
	 * マップチップ 2次元配列の構造体
	 */
	/** 床の2次元配列 */
	// private FloorData floordata = new FloorData();
	private FloorData FloorChip[][] = new FloorData[MAP_HEIGHT][MAP_WIDTH];

	/** 壁の2次元配列構造体 */
	// private WallData WallChip[][] = new WallData[MAP_HEIGHT][MAP_WIDTH];

	/** オブジェクトの2次元配列 */
	// private ObjectData objectData = new ObjectData();
	// private ObjectData ObjectChip[][] = new
	// ObjectData[MAP_HEIGHT][MAP_WIDTH];
	private ObjectData ObjectChip[][] = {
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false),  new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), },
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CASHIER, 27, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 6, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), },
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 39, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 6, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), },
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 39, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), },
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 39, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 6, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), },
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 39, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 6, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), },
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 39, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), },
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 39, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 6, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), },
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 39, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 6, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), },
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_COOKING_TABLE, 15, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), },

	// テスト用3*3
	// {new ObjectData( 0, false), new ObjectData( 3, false), new ObjectData( 0,
	// false),},
	// {new ObjectData( 0, false), new ObjectData( 3, false), new ObjectData( 0,
	// false),},
	// {new ObjectData( 0, false), new ObjectData( 0, false), new ObjectData( 0,
	// false),},
	};
	/** 調理台のメニュー **/
	private CookingTableMenu m_cookingTableMenu;

	/** メイド */
	private Maid maid;
	private ArrayList<Customer> m_customerList;
	
	/** 配膳されている料理を格納するリスト */
	ArrayList<Food> m_foodList;

	/**
	 * コンストラクタ
	 * 
	 * @param img
	 *            : リソース画像
	 */
	public GameMap(Bitmap floorImg, Bitmap wallImg, Bitmap objectImg, Bitmap maidImg, Bitmap foodImg) {

		// 画像の初期化
		this.floor_img = floorImg;
		this.wall_img = wallImg;
		this.object_img = objectImg;
		this.food_img = foodImg;

		// 縦の配列 マップの高さ分回す 
		for (int y = 0; y < MAP_HEIGHT; y++) {
			// 横の配列 マップの横幅分回す
			for (int x = 0; x < MAP_WIDTH; x++) {
				FloorChip[y][x] = new FloorData();
				// ObjectChip[y][x] = new ObjectData();
			}
		}
		
		// メイドの生成
		maid = new Maid(maidImg);
		// 配膳時の料理を入れるリストの生成
		m_foodList = new ArrayList<Food>();
		
		// お客を入れるリストの生成
		m_customerList = new ArrayList<Customer>();
		
		Initialize();

		// ここで、マップチップ等の座標を変換している
		QuarterConvert();

		SetData();
		
		// 調理台の検索と、調理台の座標を元にメニューの生成
		m_cookingTableMenu = null;
		for (int y = 0; y < MAP_HEIGHT; y++) {
			// 横の配列 マップの横幅分回す
			for (int x = 0; x < MAP_WIDTH; x++) {
				if (ObjectChip[y][x].getM_objectName() == OBJECT_NAME.OBJECT_NAME_COOKING_TABLE) {
					m_cookingTableMenu = new CookingTableMenu(ObjectChip[y][x].GetPos());
				}
			}
		}

		// 選択の初期化
		select_pos = new Vector2D();
	}

	/**
	 * 初期化
	 */
	public void Initialize() {

		// 縦の配列 マップの高さ分回す
		for (int y = 0; y < MAP_HEIGHT; y++) {
			// 横の配列 マップの横幅分回す
			for (int x = 0; x < MAP_WIDTH; x++) {

				FloorChip[y][x].Initialize();
				// ObjectChip[i][j].Initialize();
			}
		}

		// メイドとお客の初期化
		maid.Initialize();
		for (int i=0; i<m_customerList.size(); i++) {
			m_customerList.get(i).Initialize();
		}
	}

	/**
	 * データを設定する
	 */
	public void SetData() {
		int count = 1;

		// 縦の配列 マップの高さ分回す
		for (int y = 0; y < MAP_HEIGHT; y++) {
			// 横の配列 マップの横幅分回す
			for (int x = 0; x < MAP_WIDTH; x++) {

				// 床部分
				// IDを設定
				FloorChip[y][x].SetId(count);
				count++;

				// チップ番号を設定
				if (y == 0 && x == 5 || y == 0 && x == 6) {
					FloorChip[y][x].SetChip_num(9);
				} else {
					FloorChip[y][x].SetChip_num(1);
				}

				// オブジェクトが置いてある場合
				if (!(ObjectChip[y][x].GetChip_num() == 0)) {
					// 進行不能の床と設定
					FloorChip[y][x].SetUsed_floor(true);
				}
			}
		}

		// メイド
		maid.SetFloorData(ObjectChip);
		maid.SetChip_num(0);
		maid.SetSquareXY(2, 0);
	}

	/**
	 * 更新
	 * 
	 * @param
	 */
	public void Update(float mouse_x, float mouse_y) {
		m_elapsedFrame++;

		// マップチップとの当たり判定を取り、target_squareY, target_squareX に、タッチしたチップ番号を入れている。
		// 縦の配列 マップの高さ分回す
		for (int y = 0; y < MAP_HEIGHT; y++) {
			// 横の配列 マップの横幅分回す
			for (int x = 0; x < MAP_WIDTH; x++) {
				MapChipCollision(y, x, mouse_x, mouse_y);
			}
		}
		
		// タッチしたマスがメイドの隣で、
		// タッチしたところがキッチンテーブルだった時、調理をさせる。
		// タッチしたところがテーブルだった時、料理を置く。
		if (maid.GetSquareX() == target_squareX && (maid.GetSquareY()-1 == target_squareY || maid.GetSquareY()+1 == target_squareY)
		||  maid.GetSquareY() == target_squareY && (maid.GetSquareX()-1 == target_squareX || maid.GetSquareX()+1 == target_squareX) ) {
			OBJECT_NAME objName = ObjectChip[target_squareY][target_squareX].getM_objectName();
			switch (objName) {
				case OBJECT_NAME_COOKING_TABLE:
					// メイドが料理を持っていたら、調理台メニューを閉じて、抜ける。
					if (maid.getM_food() != FOOD_NAME.FOOD_NAME_NONE) {
						m_cookingTableMenu.setOpen(false);
						break;
					}
					
					// 調理中じゃない場合、調理台メニューを開く
					FOOD_NAME cookingFood = m_cookingTableMenu.CheckCollide(new Vector2D(mouse_x, mouse_y));
					if (cookingFood == null) {
						m_cookingTableMenu.setOpen(true);
					}
					else {
						// 料理する品が決定していたら、料理
						m_cookingTableMenu.setOpen(false);
						maid.Cooking(cookingFood);
					}
					break;
					
				case OBJECT_NAME_TABLE:
					// メイドが料理を持っている場合のみ料理を置く。
					if (maid.getM_food() != FOOD_NAME.FOOD_NAME_NONE) {
						Food food = new Food(maid.getM_food(), target_squareX, target_squareY);
						m_foodList.add(food);
						maid.setM_food(Food.FOOD_NAME.FOOD_NAME_NONE);
					}
					break;
					
				default:
					break;
			}
		}
		if (ObjectChip[target_squareY][target_squareX].getM_objectName() != OBJECT_NAME.OBJECT_NAME_COOKING_TABLE) {
			// 調理台をタッチしていない場合は、メニューを消す
			m_cookingTableMenu.setOpen(false);
		}
		
		// 料理メニューDEBUG
		FOOD_NAME name = m_cookingTableMenu.CheckCollide(new Vector2D(mouse_x, mouse_y));
		if (name != null) Log.d("debug", name.toString());
		
		// メイドの更新、移動ルートの探索と、移動を行う
		maid.Update(target_squareY, target_squareX);
		// お客の更新
		for (int i=0; i<m_customerList.size(); i++) {
			m_customerList.get(i).Update();
		}
		
		// お客が席に座っており、目の前に料理がある場合、お客に食べる命令を出す。
		for (int i=0; i<m_customerList.size(); i++) {
			// 料理待ちか
			if (m_customerList.get(i).getM_phase() == PHASE.PHASE_WAITING) {
				// お客様の位置が、料理の目の前か
				for (int j=0; j<m_foodList.size(); j++) {
					Customer customer = m_customerList.get(i);
					Food food = m_foodList.get(j);
					if (customer.GetSquareX() == food.getM_x() && (customer.GetSquareY()-1 == food.getM_y() || customer.GetSquareY()+1 == food.getM_y())
					||  customer.GetSquareY() == food.getM_y() && (customer.GetSquareX()-1 == food.getM_x() || customer.GetSquareX()+1 == food.getM_x()) ) {
							customer.setM_phase(PHASE.PHASE_EATING);
							customer.setM_order(food);
					}
				}
			}
		}
		
		// お客リストを監視し、画面外の変な所に行ったお客は削除する
		Rect screenRect = new Rect(0-100, 0-100, 800+100, 480+100);
		for (int i=m_customerList.size()-1; i>=0; i--) {
			if (!Collision.pointRect(m_customerList.get(i).GetPos(), screenRect)) {
				m_customerList.remove(i);
			}
		}
		
		// 料理リストを監視して、食べられた料理は削除する
		for (int i=m_foodList.size()-1; i>=0; i--) {
			if (!m_foodList.get(i).isExist()) {
				m_foodList.remove(i);
			}
		}
		
		
		
		
		// 経過時間によって、お客を生成する
		if (m_elapsedFrame % (3*30) == 0) {
			TEX_NAME imgName = GameMain.rand.nextBoolean() ? TEX_NAME.MOHIKAN : TEX_NAME.MAID_02;
			Customer customer = new Customer(GameMain.imageMap.get(imgName));
			customer.Initialize();
			customer.SetFloorData(ObjectChip);
			m_customerList.add(customer);
		}
	}

	/**
	 * 描画
	 */
	public void Draw(GameSurfaceView sv) {

		// 縦の配列 マップの高さ分回す
		for (int y = 0; y < MAP_HEIGHT; y++) {
			// 横の配列 マップの横幅分回す
			for (int x = 0; x < MAP_WIDTH; x++) {

				// 床
				sv.DrawMapChip(
						floor_img,
						(int) FloorChip[y][x].GetPos().x,
						(int) FloorChip[y][x].GetPos().y,
						FloorData.FLOOR_RES_WIDTH
								* (FloorChip[y][x].GetChip_num() % CHIP_RES_LENGTH),
						FloorData.FLOOR_RES_HEIGHT
								* (FloorChip[y][x].GetChip_num() / CHIP_RES_LENGTH),
						MAP_CHIPSIZE_WIDTH, MAP_CHIPSIZE_HEIGHT * 2, false);
			}
		}

		// 選択されているマス
		sv.DrawMapChip(floor_img, (int) select_pos.x, (int) select_pos.y, 0, 0,
				MAP_CHIPSIZE_WIDTH, MAP_CHIPSIZE_HEIGHT, false);

		// 縦の配列 マップの高さ分回す
		for (int y = 0; y < MAP_HEIGHT; y++) {
			// 横の配列 マップの横幅分回す
			for (int x = 0; x < MAP_WIDTH; x++) {
				if (maid.GetSquareX() == x && maid.GetSquareY() == y) {
					// メイド
					sv.DrawImage(
							maid.getM_image(),
							(int) maid.GetPos().x + (Maid.MAID_RES_WIDTH / 2),
							(int) maid.GetPos().y - (Maid.MAID_RES_HEIGHT / 2),
							Maid.MAID_RES_WIDTH * (maid.GetChip_num() % Maid.MAID_ANIME_LENGTH),
							Maid.MAID_RES_HEIGHT * (maid.getM_direction() % 2 ),	// 上向きだけ1に変換
							Maid.MAID_RES_WIDTH, Maid.MAID_RES_HEIGHT,
							maid.isReverse);
					// メイドの所持料理(メイドが持ってる画像を準備出来たら、必要無くなるかも。)
					if (maid.getM_food() != Food.FOOD_NAME.FOOD_NAME_NONE) {
						int sx = 0;
						int sy = 0;
						switch (maid.getM_food()) {
							case FOOD_NAME_COFFEE:		sx = 0;	sy = 0;	break;
							case FOOD_NAME_CAKE:	sx = 1;	sy = 0;	break;
							case FOOD_NAME_TEA:			sx = 2; sy = 0; break;
							default:					sx = 0;	sy = 0;	break;
						}
						sv.DrawMapChip(
								food_img,
								(int)(maid.GetPos().x),
								(int)(maid.GetPos().y - Maid.MAID_RES_HEIGHT/4 - Food.FOOD_HEIGHT), // 料理の画像サイズ分引く
								sx * Food.FOOD_WIDTH,
								sy * Food.FOOD_HEIGHT,
								Food.FOOD_WIDTH,
								Food.FOOD_HEIGHT,
								false);
					}
					
				} // メイドの描画
				
				// お客様
				for (int i=0; i<m_customerList.size(); i++) {
					if (m_customerList.get(i).GetSquareX() == x && m_customerList.get(i).GetSquareY() == y) {
						sv.DrawImage(
								m_customerList.get(i).getM_image(),
								(int) m_customerList.get(i).GetPos().x + (Maid.MAID_RES_WIDTH / 2),
								(int) m_customerList.get(i).GetPos().y - (Maid.MAID_RES_HEIGHT / 2),
								Maid.MAID_RES_WIDTH * (m_customerList.get(i).GetChip_num() % Maid.MAID_ANIME_LENGTH),
								Maid.MAID_RES_HEIGHT * (m_customerList.get(i).getM_direction() % 2 ),	// 上向きだけ1に変換
								Maid.MAID_RES_WIDTH, Maid.MAID_RES_HEIGHT,
								m_customerList.get(i).isReverse);
						
						// 料理待ちの間、注文を頭上に表示する。
						if (m_customerList.get(i).getM_phase() == PHASE.PHASE_WAITING) {
							int sx = 0;
							int sy = 0;
							switch (m_customerList.get(i).getM_order().getM_foodName()) {
								case FOOD_NAME_COFFEE:		sx = 0;	sy = 0;	break;
								case FOOD_NAME_CAKE:		sx = 1;	sy = 0;	break;
								case FOOD_NAME_TEA:			sx = 2; sy = 0; break;
								default:					sx = 0;	sy = 0;	break;
							}
							sv.DrawImage(
									food_img,
									(int)(m_customerList.get(i).GetPos().x),
									(int)(m_customerList.get(i).GetPos().y - Human.MAID_RES_HEIGHT/4 - Food.FOOD_HEIGHT), // 料理の画像サイズ分引く
									sx * Food.FOOD_WIDTH,
									sy * Food.FOOD_HEIGHT,
									Food.FOOD_WIDTH,
									Food.FOOD_HEIGHT,
									false);
						}
					}
					
				}

				// オブジェクト
				sv.DrawMapChip(
						object_img,
						(int) ObjectChip[y][x].GetPos().x,
						(int) ObjectChip[y][x].GetPos().y
								- (ObjectData.OBJ_RES_HEIGHT / 2),
						ObjectData.OBJ_RES_WIDTH
								* (ObjectChip[y][x].GetChip_num() % ObjectData.OBJ_CHIP_RES_LENGTH),
						ObjectData.OBJ_RES_HEIGHT
								* (ObjectChip[y][x].GetChip_num() / ObjectData.OBJ_CHIP_RES_LENGTH),
						ObjectData.OBJ_RES_WIDTH, ObjectData.OBJ_RES_HEIGHT,
						ObjectChip[y][x].GetDirection());
				
				// 料理
				// 料理リスト内を検索して、指定座標に料理がある場合、描画する
				for (int i=0; i<m_foodList.size(); i++) {
					if (m_foodList.get(i).getM_x() == x
					&&  m_foodList.get(i).getM_y() == y) {
						int sx = 0;
						int sy = 0;
						switch (m_foodList.get(i).getM_foodName()) {
							case FOOD_NAME_COFFEE:		sx = 0;	sy = 0;	break;
							case FOOD_NAME_CAKE:	sx = 1;	sy = 0;	break;
							case FOOD_NAME_TEA:			sx = 2; sy = 0; break;
							default:					sx = 0;	sy = 0;	break;
						}
						sv.DrawMapChip(
								food_img,
								(int) ObjectChip[y][x].GetPos().x,
								(int) ObjectChip[y][x].GetPos().y - (Food.FOOD_HEIGHT / 2),
								sx * Food.FOOD_WIDTH,
								sy * Food.FOOD_HEIGHT,
								Food.FOOD_WIDTH,
								Food.FOOD_HEIGHT,
								false);
					}
				}
				
			}
		} // 床や、メイド、お客等の描画終わり
		
		// 調理台選択時、ポップアップの表示
		if (m_cookingTableMenu.isOpen()) {
			m_cookingTableMenu.Draw(sv);
		}
		
		// 
		DebugDraw(sv);
	}

	/**
	 * マップチップ座標をクォータービュー座標に変換
	 */
	private void QuarterConvert() {

		// 縦の配列 マップの高さ分回す
		for (int y = 0; y < MAP_HEIGHT; y++) {
			// 横の配列 マップの横幅分回す
			for (int x = 0; x < MAP_WIDTH; x++) {

				// マップチップ座標をクォータービュー座標に変換する
				// クォーターX = (チップの幅W/2) * (チップの座標X + チップの座標Y)
				// クォーターY = (チップの高さH/2) * ( チップの座標X - チップの座標Y)

				// 床
				FloorChip[y][x].SetPosX((MAP_CHIPSIZE_WIDTH / 2) * (y - x));
				FloorChip[y][x].SetPosY((MAP_CHIPSIZE_HEIGHT / 2) * (y + x));

				// オブジェクト
				ObjectChip[y][x].SetPosX((MAP_CHIPSIZE_WIDTH / 2) * (y - x));
				ObjectChip[y][x].SetPosY((MAP_CHIPSIZE_HEIGHT / 2) * (y + x));

				// マップ全体のオフセットを反映
				// 床
				FloorChip[y][x].SetPosX(FloorChip[y][x].GetPos().x
						+ map_Offset_x);
				FloorChip[y][x].SetPosY(FloorChip[y][x].GetPos().y
						+ map_Offset_y/3);

				// オブジェクト
				ObjectChip[y][x].SetPosX(ObjectChip[y][x].GetPos().x
						+ map_Offset_x);
				ObjectChip[y][x].SetPosY(ObjectChip[y][x].GetPos().y
						+ map_Offset_y/3);

				// チップ分のオフセットをする
				// 床
				FloorChip[y][x].SetPosX(FloorChip[y][x].GetPos().x - chip_offset_x/2);
				FloorChip[y][x].SetPosY(FloorChip[y][x].GetPos().y - chip_offset_y/2);
				FloorChip[y][x].SetCenterXY();

				// オブジェクト
				ObjectChip[y][x].SetPosX(ObjectChip[y][x].GetPos().x - chip_offset_x/2);
				ObjectChip[y][x].SetPosY(ObjectChip[y][x].GetPos().y - chip_offset_y/2);
			}
		}
	}

	/**
	 * マップチップの当たり判定 やり方 ：一次関数を使い当たり判定をとる 参考 ：URL.txt "ひし形当たり判定"
	 */
	public void MapChipCollision(int y, int x, float mouse_x, float mouse_y) {

		// マップチップの中心を算出
		map_cx = (int) FloorChip[y][x].GetPos().x + (MAP_CHIPSIZE_WIDTH / 2);
		map_cy = (int) FloorChip[y][x].GetPos().y + (MAP_CHIPSIZE_HEIGHT / 2);

		// 傾きを産出
		gradient = MAP_CHIPSIZE_HEIGHT / (float) MAP_CHIPSIZE_WIDTH;

		// int 辺1 = (int)( 傾き*( mx - ( cx - h)) + cy ); //辺1の数値
		// int 辺2 = (int)( 傾き*( mx - ( cx + h)) + cy ); //辺2の数値
		// int 辺3 = (int)(-傾き*( mx - ( cx + h)) + cy ); //辺3の数値
		// int 辺4 = (int)(-傾き*( mx - ( cx - h)) + cy ); //辺4の数値
		// 辺2→ ／＼ ←辺3
		// 辺4→ ＼／ ←辺1
		col1 = (int) (gradient * (mouse_x - (map_cx - MAP_CHIPSIZE_HEIGHT)) + map_cy);
		col2 = (int) (gradient * (mouse_x - (map_cx + MAP_CHIPSIZE_HEIGHT)) + map_cy);
		col3 = (int) (-gradient * (mouse_x - (map_cx + MAP_CHIPSIZE_HEIGHT)) + map_cy);
		col4 = (int) (-gradient * (mouse_x - (map_cx - MAP_CHIPSIZE_HEIGHT)) + map_cy);

		if (mouse_y <= col1 && mouse_y >= col2 && mouse_y <= col3
				&& mouse_y >= col4) {

			target_squareY = y;
			target_squareX = x;

			select_pos.x = FloorChip[y][x].GetPos().x;
			select_pos.y = FloorChip[y][x].GetPos().y;

			// maid.Move(debug_num1, debug_num2);
		}
	}

	/**
	 * デバック用
	 * 
	 * @param sv
	 */
	private void DebugDraw(GameSurfaceView sv) {
		// デバック表示
		/* ◆◆◆◆　Floor　◆◆◆◆◆ */

		sv.DrawText("お客の数(List)：" + m_customerList.size(), 400, 340, Color.WHITE);
		sv.DrawText("ID：" + FloorChip[target_squareY][target_squareX].GetId(),
				400, 340, Color.WHITE);
		sv.DrawText("DrawNumber：" + FloorChip[target_squareY][target_squareX].GetDrawNumber(),
				400, 360, Color.WHITE);
		sv.DrawText(
				"チップの種類："
						+ FloorChip[target_squareY][target_squareX]
								.GetChip_num(), 400, 380, Color.WHITE);
		sv.DrawText("(" + target_squareX + ","
				+ target_squareY + ")", 400, 400, Color.WHITE);
		sv.DrawText(
				"クォーターX："
						+ FloorChip[target_squareY][target_squareX].GetPos().x
						+ "　クォーターY："
						+ FloorChip[target_squareY][target_squareX].GetPos().y,
				400, 420, Color.WHITE);
		sv.DrawText(
				"床の上に物があるか："
						+ FloorChip[target_squareY][target_squareX]
								.GetUsed_floor(), 400, 440, Color.WHITE);
		sv.DrawText(
				"所持金："
						+ CommonData.GetInstance().GetPlayerData().money, 400, 460, Color.WHITE);
		/* ◆◆◆◆◆◆◆◆◆◆◆◆◆◆ */

		// /* ◆◆◆◆　Object　◆◆◆◆◆ */
		// sv.DrawText(
		// "オブジェクトの種類：" + ObjectChip[debug_num1][debug_num2].GetChip_num(),
		// 400, 280, Color.WHITE);
		// sv.DrawText("縦 HEIGHT Y：" + debug_num1 + "　横 WIDTH X:" + debug_num2,
		// 400, 400,
		// Color.WHITE);
		// sv.DrawText("クォーターX：" + ObjectChip[debug_num1][debug_num2].GetPos().x
		// + "　クォーターY：" + ObjectChip[debug_num1][debug_num2].GetPos().y,
		// 400, 320, Color.WHITE);

		/* ◆◆◆◆◆◆◆◆◆◆◆◆◆◆ */

		/* ◆◆◆◆　Maid　◆◆◆◆◆ */
		sv.DrawText("DebugText" + maid.GetDebugMessage(), 50, 300,
				Color.WHITE);
		sv.DrawText("targetX" + maid.GetTargetX(), 50, 320,
				Color.WHITE);
		sv.DrawText("targetY" + maid.GetTargetY(), 50, 340,
				Color.WHITE);
		sv.DrawText("Maid_PosX：" + (int) maid.GetPos().x + " Maid_PosY："
				+ (int) maid.GetPos().y, 50, 360, Color.WHITE);
		sv.DrawText("Maid_squareX：" + maid.GetSquareX() + " Maid_squareY："
				+ maid.GetSquareY(), 50, 380, Color.WHITE);
		sv.DrawText("Maid[maek_squareX]：" + maid.GetSquareX(), 50, 400,
				Color.WHITE);
		sv.DrawText("Maid[maek_squareY]：" + maid.GetSquareY(), 50, 420,
				Color.WHITE);
		// sv.DrawLine((int)maid.GetCenter().x, (int)maid.GetCenter().y,
		// (int)FloorChip[target_squareY][target_squareX].GetCenter().x,
		// (int)FloorChip[target_squareY][target_squareX].GetCenter().y);
		sv.DrawText("Maid_chip_num：" + maid.GetChip_num(), 50, 470, Color.WHITE);
		/* ◆◆◆◆◆◆◆◆◆◆◆◆◆ */
	}
}