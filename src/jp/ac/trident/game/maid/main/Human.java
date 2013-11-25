/**
 * 
 */
package jp.ac.trident.game.maid.main;

import java.util.ArrayList;
import java.util.List;

/**
 * メイド、お客様に派生するスーパークラス
 * 移動などの共通部分を持つ
 * 
 * @author ryu8179
 *
 */
public abstract class Human {

	/* ●●●人間の管理方法●● */
	// 座標はマスを基準に取っていく（フロアのデータをもとに）　
	/* ●●●●●●●●●●●●●●●●● */

	
	/* 定数宣言 */

	/** ｷｬﾗｸﾀｰの現在の状態 0:待機 1:移動 2:その他 */
	protected static final int MODE_NONE = 0, MODE_MOVE = 1;

	/** キャラクターのアニメーション枚数 */
	public static final int MAID_ANIME_LENGTH = 3;

	/** 画像の幅 */
	public static final int MAID_RES_WIDTH = 32;
	/** 画像の高さ */
	public static final int MAID_RES_HEIGHT = 64;
	
	/**
	 * 移動状態の列挙型
	 * @author ryu8179
	 *
	 */
	public static final int DIRECTION_NONE = -1;
	public static final int DIRECTION_LEFTDOWN = 0;
	public static final int DIRECTION_LEFTUP = 1;
	public static final int DIRECTION_RIGHTDOWN = 2;
	public static final int DIRECTION_RIGHTUP = 3;
	public static final int DIRECTION_COUNT = 4;

	/** アニメーションの切り替え時間 */
	protected static final int ANIMATION_CHANGE_FRAME = 10;
	/** スプライトシートでのアニメーション順番 */
	protected static final int[] ANIMATION_INDEX = {0,1,0,2};
	
	/* ここまで定数宣言 */
	
	/* ここからメンバ変数 */
	/**
	 * 経過フレーム
	 */
	protected int m_elapsedFrame;
	
	/**
	 * 床の情報
	 */
	protected FloorData FloorChip[][] = new FloorData[GameMap.MAP_HEIGHT][GameMap.MAP_WIDTH];

	/**
	 * スプライトシートの、何番目かのイメージ番号
	 */
	protected int chip_num;

	/**
	 * メイドのX,Y座標
	 */
	protected Vector2D pos;

	/**
	 * メイドの中心座標
	 */
	protected Vector2D center;

	/**
	 * メイドがどのマスにいるのか
	 */
	protected int square_x, square_y;

	/**
	 * メイドの移動速度（ステータス的）
	 */
	protected Vector2D vec;
	
	/**
	 * メイドの向き
	 * 右向きの場合、描画を反転させる必要がある。
	 */
	protected int m_direction;

	/**
	 * 目的地X,Y
	 */
	protected int targetX = 0, targetY = 0;

	/**
	 * 次に目標とするマスXY
	 */
	protected int mark_squareX = 0, mark_squareY = 0;

	/**
	 * 次に目標とするマスについたかどうかのフラグ
	 */
	protected boolean reach_flag = true;

	/**
	 * 実際の移動速度
	 */
	protected Vector2D move_speed = new Vector2D();

	/**
	 * A*(エースター)用にいろいろ
	 */
	protected A_Star a_star = new A_Star();
	protected List<MapData> list = new ArrayList<MapData>();
	protected boolean search_flag = true;
	protected int root_counter = 0;

	/**
	 * よくわからんアニメーション用の変数
	 */
	protected int animetion_start_num = 0;

	protected String debug_message = "";
	/* ここまでメンバ変数 */
	
	/* ここからメソッド */
	/**
	 * デフォルトコンストラクタ
	 */
	public Human() {
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
		this.m_elapsedFrame = 0;
		this.pos.x = 0;
		this.pos.y = 0;
		this.square_x = 0;
		this.square_y = 0;
		this.vec.x = 4.0f;
		this.vec.y = 4.0f;
		this.m_direction = DIRECTION_LEFTDOWN;
	}

	/**
	 * 更新
	 * 
	 * @param target_height
	 * @param target_width
	 */
	public void Update(int target_height, int target_width) {;
		m_elapsedFrame++;
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
	protected void SetPosXY(float x, float y) {
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
	protected void SetCenterXY(float x, float y) {
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

	public int GetTargetX() {
		return this.targetX;
	}

	public int GetTargetY() {
		return this.targetY;
	}

	/**
	 * 向きを返す
	 * @return
	 */
	public int getM_direction() {
		return m_direction;
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
		// もし人が目的地についていないなら
		if (list.size() == 0) {
			search_flag = true;
			root_counter = 0;
			list.clear();
			return;
		} else {
			targetX = list.get(list.size() - 1).x;
			targetY = list.get(list.size() - 1).y;
		}

		if (this.square_x != targetX || this.square_y != targetY) {
			if (reach_flag == true) {

				mark_squareX = list.get(root_counter).x;
				mark_squareY = list.get(root_counter).y;

				// 目標とするマスよりも自身の居るマスの方が大きい場合
				if (mark_squareX - this.square_x == -1
						&& mark_squareY - this.square_y == 0) {
					// MOVE_RIGHTUP;
					m_direction = DIRECTION_RIGHTUP;
				}

				if (mark_squareX - this.square_x == 1
						&& mark_squareY - this.square_y == 0) {
					// MOVE_LEFTDOWN;
					m_direction = DIRECTION_LEFTDOWN;
				}

				if (mark_squareX - this.square_x == 0
						&& mark_squareY - this.square_y == -1) {
					// MOVE_LEFTUP;
					m_direction = DIRECTION_LEFTUP;
				}

				if (mark_squareX - this.square_x == 0
						&& mark_squareY - this.square_y == 1) {
					// MOVE_RIGHTDOWN;
					m_direction = DIRECTION_RIGHTDOWN;
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
	protected void Animation(int mode) {

		switch (mode) {

		// 何もしていない状態
		case MODE_NONE:

			chip_num = 0;

			break;

		// 移動している
		case MODE_MOVE:

//			// 速度によってｷｬﾗｸﾀｰの向きを変える 横
//			if (move_speed.x <= 0.0f) {
//				// 左に移動
//				// 反転しない
//				side_direction = false;
//			} else {
//				// 右に移動
//				// 反転する
//				side_direction = true;
//			}
			
			// アニメーションさせる
			chip_num = ANIMATION_INDEX[m_elapsedFrame / 10 % ANIMATION_INDEX.length];

			break;

		default:
			break;
		}
	}

	/**
	 * 移動速度を計算
	 */
	protected void SetMoveVec() {
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
