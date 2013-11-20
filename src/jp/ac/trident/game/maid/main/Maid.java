package jp.ac.trident.game.maid.main;

import java.util.ArrayList;
import java.util.List;

public class Maid {

	/* ●●●メイドの管理方法●● */
	// 座標はマスを基準に取っていく（フロアのデータをもとに）　
	/* ●●●●●●●●●●●●●●●●● */

	/* 定数宣言 */

	/** ｷｬﾗｸﾀｰの現在の状態 0:待機 1:移動 2:その他 */
	private static final int MODE_NONE = 0, MODE_MOVE = 1;

	/** キャラクターのアニメーション枚数 */
	public static final int MAID_ANIME_LEMGTH = 3;

	/** 画像の幅 */
	public static final int MAID_RES_WIDTH = 32;

	/** 画像の高さ */
	public static final int MAID_RES_HEIGHT = 64;

	/** 移動法0:待機 */
	private static final int MOVE_WAITING = 0;
	/** 移動法1:左下 */
	private static final int MOVE_LEFTDOWN = 1;
	/** 移動法2:左上 */
	private static final int MOVE_LEFTUP = 7;
	/** 移動法3:右上 */
	private static final int MOVE_RIGHTUP = 9;
	/** 移動法4:右下 */
	private static final int MOVE_RIGHTDOWN = 3;

	/** アニメーションの切り替え時間 */
	private static final int ANIMATION_CHANGE_NUM = 10;

	/* ここまで定数宣言 */
	/**
	 * 床の情報
	 */
	private FloorData FloorChip[][] = new FloorData[GameMap.MAP_HEIGHT][GameMap.MAP_WIDTH];

	/**
	 * 番号
	 */
	// private int ID;

	/**
	 * メイドのイメージ番号
	 */
	private int chip_num;

	/**
	 * アニメーション描画時間
	 */
	public int animation_timer = 0;

	/**
	 * メイドのX,Y座標
	 */
	private Vector2D pos;

	/**
	 * メイドの中心座標
	 */
	private Vector2D center;

	/**
	 * メイドがどのマスにいるのか
	 */
	private int square_x, square_y;

	/**
	 * メイドの移動速度（ステータス的）
	 */
	private Vector2D vec;

	/**
	 * 移動ルートを入れ物
	 */
	// private int move_holder[] = new int[GameMap.MAP_WIDTH];
	// private int squareX_holder[] = new int[GameMap.MAP_WIDTH];
	// private int squareY_holder[] = new int[GameMap.MAP_WIDTH];

	/**
	 * マップチップの向き true:反転させる
	 */
	private boolean side_direction;

	/**
	 * 目的地X,Y
	 */
	private int targetX = 0, targetY = 0;

	/**
	 * 次に目標とするマスXY
	 */
	private int mark_squareX = 0, mark_squareY = 0;

	/**
	 * 次に目標とするマスについたかどうかのフラグ
	 */
	private boolean reach_flag = true;

	/**
	 * 実際の移動速度
	 */
	private Vector2D move_speed = new Vector2D();

	/**
	 * A*(エースター)用にいろいろ
	 */
	private A_Star a_star = new A_Star();
	private List<MapData> list = new ArrayList<MapData>();
	private boolean search_flag = true;
	private int root_counter = 0;

	/**
	 * よくわからんアニメーション用の変数
	 */
	private int animetion_start_num = 0;

	private String debug_message = "";

	/**
	 * デフォルトコンストラクタ
	 */
	public Maid() {
		pos = new Vector2D();
		center = new Vector2D();
		vec = new Vector2D();

		// 縦の配列 マップの高さ分回す
		for (int y = 0; y < GameMap.MAP_HEIGHT; y++) {
			// 横の配列 マップの横幅分回す
			for (int x = 0; x < GameMap.MAP_WIDTH; x++) {
				FloorChip[y][x] = new FloorData();
			}
		}

		Initialize();
	}

	/**
	 * 初期化
	 */
	public void Initialize() {
		// this.ID = 0;
		this.pos.x = 0;
		this.pos.y = 0;
		this.square_x = 0;
		this.square_y = 0;
		this.vec.x = 4.0f;
		this.vec.y = 4.0f;
		this.side_direction = false;
	}

	public void Update(int target_height, int target_width) {
		this.RootSerch(target_height, target_width);
		this.Move();
	}

	/**
	 * 床の情報の取得
	 */
	public void SetFloorData(FloorData[][] data) {
		this.FloorChip = data;
	}

	/**
	 * メイドのイメージ番号の取得
	 */
	public void SetChip_num(int chip_num) {
		this.chip_num = chip_num;
	}

	/**
	 * メイドのイメージ番号を返す
	 */
	public int GetChip_num() {
		return this.chip_num;
	}

	/**
	 * 座標の取得XY
	 */
	private void SetPosXY(float x, float y) {
		this.pos.x = x;
		this.pos.y = y;

		SetCenterXY(pos.x, pos.y);
	}

	/**
	 * 座標を返す
	 */
	public Vector2D GetPos() {
		return this.pos;
	}

	/**
	 * 座標の中心を取得
	 */
	private void SetCenterXY(float x, float y) {
		// 左上基準なので半径分足しこみ中心にする。　Yについては
		this.center.x = x + (MAID_RES_WIDTH / 2);
		this.center.y = y + ((MAID_RES_HEIGHT / 2) / 2);
	}

	/**
	 * 座標の中心を返す
	 */
	public Vector2D GetCenter() {
		return this.center;
	}

	/**
	 * 座標マスの取得XY
	 */
	public void SetSquareXY(int square_x, int square_y) {
		this.square_x = square_x;
		this.square_y = square_y;

		SetPosXY(FloorChip[square_y][square_x].GetPos().x,
				FloorChip[square_y][square_x].GetPos().y);
	}

	/**
	 * 座標マスを返すX
	 */
	public int GetSquareX() {
		return this.square_x;
	}

	/**
	 * 座標マスを返すY
	 */
	public int GetSquareY() {
		return this.square_y;
	}

	/**
	 * 向きを取得
	 */
	public void SetDirection(boolean side_direction) {
		this.side_direction = side_direction;
	}

	/**
	 * 向きを返す
	 */
	public boolean GetDirection() {
		return this.side_direction;
	}

	/**
	 * デバックメッセージを返す
	 */
	public String GetDebugMessage() {
		return debug_message;
	}

	/**
	 * 目的地への最短ルートを検索
	 */
	public void RootSerch(int target_height, int target_width) {
		if (search_flag) {
			this.targetY = target_height;
			this.targetX = target_width;

			try {
				if (targetX != square_x || targetY != square_y) {
					// イニシャライズしないとstampedがtrueのままでおかしくなる。
					// イニシャライズするタイミングをもっといい場所にしたい。
					a_star.Initialize();
					list = a_star.serch(FloorChip, this.GetSquareX(),
							this.GetSquareY(), targetX, targetY);
					search_flag = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 移動させる処理 実際に移動させる関数
	 */
	public void Move() {
		// もしメイドが目的地についていないなら
		if (this.square_x != targetX || this.square_y != targetY) {
			if (reach_flag == true) {

				mark_squareX = list.get(root_counter).x;
				mark_squareY = list.get(root_counter).y;

				// 目標とするマスよりも自身の居るマスの方が大きい場合
				if (mark_squareX - this.square_x == -1
						&& mark_squareY - this.square_y == 0) {
					// MOVE_LEFTDOWN;
					debug_message = "MOVE_LEFTDOWN";
					chip_num = 1;
				}

				if (mark_squareX - this.square_x == 1
						&& mark_squareY - this.square_y == 0) {
					// MOVE_RIGHTUP;
					debug_message = "MOVE_RIGHTUP";
					chip_num = 4;
				}
				
				if (mark_squareX - this.square_x == 0
						&& mark_squareY - this.square_y == -1) {
					// MOVE_LEFTUP;
					debug_message = "MOVE_LEFTUP";
					chip_num = 4;
				}
				
				if (mark_squareX - this.square_x == 0
						&& mark_squareY - this.square_y == 1) {
					// MOVE_RIGHTDOWN;
					debug_message = "MOVE_RIGHTDOWN";
					chip_num = 1;
				}

				animetion_start_num = chip_num;

				reach_flag = false;
			} else {
				if (this.square_x != mark_squareX
						|| this.square_y != mark_squareY) {
					// 移動速度の計算を行う
					SetMoveVec();

					// 現在の座標に移動速度を足しこむ
					pos.x += move_speed.x;
					pos.y += move_speed.y;

					// 移動アニメーションさせる
					Animation(MODE_MOVE);

					// 座標で隣接するマスに到着したか判定　こっちを使った方が後々便利だと思う
					// 経由マスに現在の座標が到達したら次の経由マスに移動　…現在floatで座標を取得しているので、重なったら処理に移るとすると時間がかかりすぎるため、判定を甘く見積もっている
					if ((((pos.x - 0.75f) <= FloorChip[mark_squareY][mark_squareX]
							.GetPos().x) && ((pos.x + 0.75f) >= FloorChip[mark_squareY][mark_squareX]
							.GetPos().x))
							&& ((pos.y - (0.75f / 2.0f)) <= FloorChip[mark_squareY][mark_squareX]
									.GetPos().y)
							&& (pos.y + 0.75f / 2.0f) >= FloorChip[mark_squareY][mark_squareX]
									.GetPos().y) {
						reach_flag = true;
						SetSquareXY(mark_squareX, mark_squareY);
						root_counter++;
					}
				}
			}
		} else {
			Animation(MODE_NONE);
			search_flag = true;
			root_counter = 0;
			list.clear();
		}
	}

	/**
	 * アニメーション関数 引数でどのアクションをしているか受け取り それに合ったアニメーションをさせたい
	 * 
	 * @param mode
	 *            ｷｬﾗｸﾀｰの状態
	 */
	private void Animation(int mode) {

		switch (mode) {

		// 何もしていない状態
		case MODE_NONE:

			chip_num = 0;
			animation_timer = 0;

			break;

		// 移動している
		case MODE_MOVE:

			// 速度によってｷｬﾗｸﾀｰの向きを変える 横
			if (move_speed.x <= 0.0f) {
				// 左に移動
				// 反転しない
				side_direction = false;
			} else {
				// 右に移動
				// 反転する
				side_direction = true;
			}

			// アニメーション切り替え時間に到達したか判定
			if (animation_timer > ANIMATION_CHANGE_NUM) {

				// アニメーション枚数を超えたら
				if (chip_num >= animetion_start_num + MAID_ANIME_LEMGTH) {
					chip_num = animetion_start_num;
				} else {
					chip_num++;
					animation_timer = 0;
				}
			} else {
				animation_timer++;
			}

			break;

		default:
			break;
		}
	}

	/**
	 * 移動速度を計算
	 */
	private void SetMoveVec() {
		// ピタゴラスの定理
		// length = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)
		float x1, x2, y1, y2, length;

		x1 = pos.x;
		x2 = FloorChip[mark_squareY][mark_squareX].GetPos().x;
		y1 = pos.y;
		y2 = FloorChip[mark_squareY][mark_squareX].GetPos().y;

		length = ((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1));

		length = (float) Math.sqrt(length);

		move_speed.x = ((x2 - x1) / length) * vec.x;
		move_speed.y = ((y2 - y1) / length) * vec.y;
	}
}