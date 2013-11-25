/**
 * ゲーム名：うんちゃらかんちゃら
 * 
 * マップチップクラス
 */
package jp.ac.trident.game.maid.main;

import java.util.ArrayList;

import jp.ac.trident.game.maid.main.ObjectData.OBJECT_NAME;
import android.graphics.Bitmap;
import android.graphics.Color;

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
	private int SCREEN_WIDTH = 800;

	/**
	 * 画面の高さ
	 */
	private int SCREEN_HEIGHT = 480;

	/**
	 * チップの幅
	 */
	private int MAP_CHIPSIZE_WIDTH = 64;

	/**
	 * チップの高さ
	 */
	private int MAP_CHIPSIZE_HEIGHT = 32;

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
	private int CHIP_RES_LENGTH = 4;

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
	 * wall		: 壁 
	 * object	: 物
	 * maid		: メイド画像
	 * 
	 * food		: メイドが持つ料理品
	 */
	private Bitmap floor_img, wall_img, object_img, maid_img, food_img;

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
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 27, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 6, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), },
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 39, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 6, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), },
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 39, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), },
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 39, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 6, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), },
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 39, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 6, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), },
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 39, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), },
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 39, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 6, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), },
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 39, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 6, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 1, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_CHAIR, 9, false), },
			{ new ObjectData(OBJECT_NAME.OBJECT_NAME_COOKING_TABLE, 15, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_TABLE, 39, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), new ObjectData(OBJECT_NAME.OBJECT_NAME_NONE, 0, false), },

	// テスト用3*3
	// {new ObjectData( 0, false), new ObjectData( 3, false), new ObjectData( 0,
	// false),},
	// {new ObjectData( 0, false), new ObjectData( 3, false), new ObjectData( 0,
	// false),},
	// {new ObjectData( 0, false), new ObjectData( 0, false), new ObjectData( 0,
	// false),},
	};

	/** メイド */
	private Maid maid;
	
	/** 配膳されている料理を格納するリスト */
	ArrayList<Food> m_foodList;

	// あほみたいなやり方
	// 描画順を正しく行いたいけどアルゴリズム思いつかないんで、今はこれで
	private int draw_num[][] = { { 0, 2, 5, 9, 14, 20, 27, 35, 44, 54, 64, },
			{ 1, 4, 8, 13, 19, 26, 34, 43, 53, 63, 73, },
			{ 3, 7, 12, 18, 25, 33, 42, 52, 62, 72, 81, },
			{ 6, 11, 17, 24, 32, 41, 51, 61, 71, 80, 88, },
			{ 10, 16, 23, 31, 40, 50, 60, 70, 79, 87, 94, },
			{ 15, 22, 30, 39, 49, 59, 69, 78, 86, 93, 99, },
			{ 21, 29, 38, 48, 58, 68, 77, 85, 92, 98, 103, },
			{ 28, 37, 47, 57, 67, 76, 84, 91, 97, 102, 106, },
			{ 36, 46, 56, 66, 75, 83, 90, 96, 101, 105, 108, },
			{ 45, 55, 65, 74, 82, 89, 95, 100, 104, 107, 109, }, };

	// private int time = 0;

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
		this.maid_img = maidImg;
		this.food_img = foodImg;

		// 縦の配列 マップの高さ分回す
		for (int y = 0; y < MAP_HEIGHT; y++) {
			// 横の配列 マップの横幅分回す
			for (int x = 0; x < MAP_WIDTH; x++) {
				FloorChip[y][x] = new FloorData();
				// ObjectChip[i][j] = new ObjectData();
			}
		}

		maid = new Maid();
		
		m_foodList = new ArrayList<Food>();

		Initialize();

		QuarterConvert();

		SetData();

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

		maid.Initialize();
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
		maid.SetFloorData(FloorChip);
		maid.SetChip_num(0);
		maid.SetSquareXY(2, 0);
	}

	/**
	 * 更新
	 * 
	 * @param
	 */
	public void Update(float mouse_x, float mouse_y) {

		// マップチップとの当たり判定を取り、target_squareY, target_squareX に、タッチしたチップ番号を入れている。
		// 縦の配列 マップの高さ分回す
		for (int y = 0; y < MAP_HEIGHT; y++) {
			// 横の配列 マップの横幅分回す
			for (int x = 0; x < MAP_WIDTH; x++) {
				MapChipCollision(y, x, mouse_x, mouse_y);
			}
		}
		
		// タッチしたマスがメイドの隣で、
		// タッチしたところがキッチンテーブルだった時、料理を持たせる。
		// タッチしたところがテーブルだった時、料理を置く。
		if (maid.GetSquareX() == target_squareX && (maid.GetSquareY()-1 == target_squareY || maid.GetSquareY()+1 == target_squareY)
		||  maid.GetSquareY() == target_squareY && (maid.GetSquareX()-1 == target_squareX || maid.GetSquareX()+1 == target_squareX) ) {
			OBJECT_NAME objName = ObjectChip[target_squareY][target_squareX].getM_objectName();
			switch (objName) {
				case OBJECT_NAME_COOKING_TABLE:
					maid.Cooking();
					break;
				case OBJECT_NAME_TABLE:
					// メイドが料理を持っていない場合のみ料理を持たせる
					if (maid.getM_food() != Food.FOOD_NAME.FOOD_NAME_NONE) {
						Food food = new Food(maid.getM_food(), target_squareX, target_squareY);
						m_foodList.add(food);
						maid.setM_food(Food.FOOD_NAME.FOOD_NAME_NONE);
					}
					break;
				default:
					break;
			}
		}
		
		// メイドの更新、移動ルートの探索と、移動を行う
		maid.Update(target_squareY, target_squareX);

		// time++;

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
		sv.DrawImage(floor_img, (int) select_pos.x, (int) select_pos.y, 0, 0,
				MAP_CHIPSIZE_WIDTH, MAP_CHIPSIZE_HEIGHT, false);

		// 縦の配列 マップの高さ分回す
		for (int y = 0; y < MAP_HEIGHT; y++) {
			// 横の配列 マップの横幅分回す
			for (int x = 0; x < MAP_WIDTH; x++) {
				if (maid.GetSquareX() == x && maid.GetSquareY() == y) {
					boolean reverseFlag;
					switch (maid.getM_direction()) {
						case Maid.DIRECTION_RIGHTDOWN:
						case Maid.DIRECTION_RIGHTUP:
							reverseFlag = true;
							break;
						case Maid.DIRECTION_LEFTDOWN:
						case Maid.DIRECTION_LEFTUP:
						default:
							reverseFlag = false;
							break;
					}
					// メイド
					sv.DrawImage(
							maid_img,
							(int) maid.GetPos().x + (Maid.MAID_RES_WIDTH / 2),
							(int) maid.GetPos().y - (Maid.MAID_RES_HEIGHT / 2),
							Maid.MAID_RES_WIDTH * (maid.GetChip_num() % Maid.MAID_ANIME_LENGTH),
							Maid.MAID_RES_HEIGHT * (maid.getM_direction() % 2 ),	// 上向きだけ1に変換
							Maid.MAID_RES_WIDTH, Maid.MAID_RES_HEIGHT,
							reverseFlag);
					
					// メイドの所持料理(メイドが持ってる画像を準備出来たら、必要無くなるかも。)
					if (maid.getM_food() != Food.FOOD_NAME.FOOD_NAME_NONE) {
						int sx = 0;
						int sy = 0;
						switch (maid.getM_food()) {
							case FOOD_NAME_TEA:			sx = 1; sy = 2; break;
							case FOOD_NAME_COFFEE:		sx = 2;	sy = 2;	break;
							case FOOD_NAME_RICE_OMELET:	sx = 3;	sy = 0;	break;
							default:					sx = 0;	sy = 0;	break;
						}
						sv.DrawImage(
								food_img,
								(int) maid.GetPos().x + (Maid.MAID_RES_WIDTH / 2),
								(int) maid.GetPos().y - (Maid.MAID_RES_HEIGHT / 2) - Food.FOOD_HEIGHT, // 料理の画像サイズ分引く
								sx * Food.FOOD_WIDTH,
								sy * Food.FOOD_HEIGHT,
								Food.FOOD_WIDTH,
								Food.FOOD_HEIGHT,
								false);
					}
					
				}

				// オブジェクト
				sv.DrawImage(
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
				
				// 料理リスト内を検索して、指定座標に料理がある場合、描画する
				for (int i=0; i<m_foodList.size(); i++) {
					if (m_foodList.get(i).getM_x() == x
					&&  m_foodList.get(i).getM_y() == y) {
						int sx = 0;
						int sy = 0;
						switch (m_foodList.get(i).getM_foodName()) {
							case FOOD_NAME_TEA:			sx = 1; sy = 2; break;
							case FOOD_NAME_COFFEE:		sx = 2;	sy = 2;	break;
							case FOOD_NAME_RICE_OMELET:	sx = 3;	sy = 0;	break;
							default:					sx = 0;	sy = 0;	break;
						}
						sv.DrawImage(
								food_img,
								(int) ObjectChip[y][x].GetPos().x,
								(int) ObjectChip[y][x].GetPos().y - (ObjectData.OBJ_RES_HEIGHT *1/4),
								sx * Food.FOOD_WIDTH,
								sy * Food.FOOD_HEIGHT,
								Food.FOOD_WIDTH,
								Food.FOOD_HEIGHT,
								false);
					}
				}
				
			}
		}
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

		sv.DrawText("ID：" + FloorChip[target_squareY][target_squareX].GetId(),
				400, 360, Color.WHITE);
		sv.DrawText(
				"チップの種類："
						+ FloorChip[target_squareY][target_squareX]
								.GetChip_num(), 400, 380, Color.WHITE);
		sv.DrawText("縦 HEIGHT Y：" + target_squareY + "　横 WIDTH X:"
				+ target_squareX, 400, 400, Color.WHITE);
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
				"所持料理："
						+ maid.getM_food(), 400, 460, Color.WHITE);
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