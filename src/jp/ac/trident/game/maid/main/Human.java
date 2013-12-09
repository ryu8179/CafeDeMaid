/**
 * 
 */
package jp.ac.trident.game.maid.main;

import java.util.ArrayList;
import java.util.List;

import jp.ac.trident.game.maid.common.CommonData;
import jp.ac.trident.game.maid.common.Vector2D;
import android.graphics.Bitmap;

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

	/** アニメーションの切り替え時間 */
	protected static final int ANIMATION_CHANGE_FRAME = 10;
	/** スプライトシートでのアニメーション順番 */
	protected static final int[] ANIMATION_INDEX = {0,1,0,2};
	
	/* ここまで定数宣言 */
	
	/* ここからメンバ変数 */
	/**
	 * 生成してからの経過フレーム
	 */
	protected int m_elapsedFrame;
	
	/**
	 * 床の上にあるオブジェクトの情報
	 */
	protected ObjectData ObjectChip[][] = new ObjectData[GameMap.MAP_HEIGHT][GameMap.MAP_WIDTH];

	/**
	 * 画像データ
	 */
	protected Bitmap m_image;
	
	/**
	 * スプライトシートの、何番目かのイメージ番号
	 */
	protected int chip_num;
	
	/**
	 * 描画時に反転させるか
	 * 右向きの場合、trueにする必要がある。
	 */
	protected boolean isReverse;

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
	protected Vector2D vel;
	
	/**
	 * メイドの向き
	 * 右向きの場合、描画を反転させる必要がある。
	 * DIRECTION_***** で指定お願いします。
	 */
	protected int m_direction;

	/**
	 * 目的地X,Y
	 * 使用時にintにキャストしたりする
	 */
	protected Vector2D target = new Vector2D();

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
	 * 調理/食事開始時のタイム
	 */
	protected long m_startTime = 0;

	protected String debug_message = "";
	/* ここまでメンバ変数 */
	
	/* ここからメソッド */
	/**
	 * デフォルトコンストラクタ
	 */
	public Human(Bitmap image) {
		m_image = image;
		pos = new Vector2D();
		center = new Vector2D();
		vel = new Vector2D();

		// 縦の配列 マップの高さ分回す
		for (int y = 0; y < GameMap.MAP_HEIGHT; y++) {
			// 横の配列 マップの横幅分回す
			for (int x = 0; x < GameMap.MAP_WIDTH; x++) {
				ObjectChip[y][x] = new ObjectData();
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
		this.chip_num = 0;
		this.isReverse = false;
		this.pos.x = 0;
		this.pos.y = 0;
		this.square_x = 0;
		this.square_y = 0;
		this.vel.x = 4.0f;
		this.vel.y = 4.0f;
		this.m_direction = CommonData.DIRECTION_LEFTDOWN;
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
		this.Move(target_height, target_width);
	}

	/**
	 * オブジェクト情報のセット
	 */
	public void SetFloorData(ObjectData[][] data) {
		this.ObjectChip = data;
	}

	/**
	 * @return m_image
	 */
	public Bitmap getM_image() {
		return m_image;
	}

	/**
	 * @param m_image 設定する m_image
	 */
	public void setM_image(Bitmap m_image) {
		this.m_image = m_image;
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
	 * @return isReverse
	 */
	public boolean isReverse() {
		return isReverse;
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

		SetPosXY(ObjectChip[square_y][square_x].GetPos().x,
				ObjectChip[square_y][square_x].GetPos().y);
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
		return (int)(this.target.x);
	}

	public int GetTargetY() {
		return (int)(this.target.y);
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
			this.target.y = target_height;
			this.target.x = target_width;

			try {
				if ((int)(target.x) != square_x || (int)(target.y) != square_y) {
					// イニシャライズしないとstampedがtrueのままでおかしくなる。
					// イニシャライズするタイミングをもっといい場所にしたい。
					a_star.Initialize();
					list = a_star.serch(ObjectChip, this.GetSquareX(),
							this.GetSquareY(), (int)(target.x), (int)(target.y));
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
	public void Move(int target_height, int target_width) {
		// 目的地について、リストがクリアされていたら
		if (list.size() == 0) {
			search_flag = true;
			root_counter = 0;
			list.clear();
			return;
		} else {
			target.x = list.get(list.size() - 1).x;
			target.y = list.get(list.size() - 1).y;
		}

		// もし現在地が目的地でないなら
		if (this.square_x != (int)(target.x) || this.square_y != (int)(target.y)) {
			// 次のマスに着いたか
			if (reach_flag == true) {
				if(target_width != (int)(target.x) || target_height != (int)(target.y)){
					search_flag = true;
					root_counter = 0;
					list.clear();
					RootSerch(target_height, target_width);
				}
				// 
				try {
					mark_squareX = list.get(root_counter).x;
					mark_squareY = list.get(root_counter).y;
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
					return;
				}

				// 目標とするマスよりも自身の居るマスの方が大きい場合
				if (mark_squareX - this.square_x == -1
						&& mark_squareY - this.square_y == 0) {
					// MOVE_RIGHTUP;
					m_direction = CommonData.DIRECTION_RIGHTUP;
					isReverse = true;
				}

				if (mark_squareX - this.square_x == 1
						&& mark_squareY - this.square_y == 0) {
					// MOVE_LEFTDOWN;
					m_direction = CommonData.DIRECTION_LEFTDOWN;
					isReverse = false;
				}

				if (mark_squareX - this.square_x == 0
						&& mark_squareY - this.square_y == -1) {
					// MOVE_LEFTUP;
					m_direction = CommonData.DIRECTION_LEFTUP;
					isReverse = false;
				}

				if (mark_squareX - this.square_x == 0
						&& mark_squareY - this.square_y == 1) {
					// MOVE_RIGHTDOWN;
					m_direction = CommonData.DIRECTION_RIGHTDOWN;
					isReverse = true;
				}

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
					if ((((pos.x - 0.75f) <= ObjectChip[mark_squareY][mark_squareX]
							.GetPos().x) && ((pos.x + 0.75f) >= ObjectChip[mark_squareY][mark_squareX]
							.GetPos().x))
							&& ((pos.y - (0.75f / 2.0f)) <= ObjectChip[mark_squareY][mark_squareX]
									.GetPos().y)
							&& (pos.y + 0.75f / 2.0f) >= ObjectChip[mark_squareY][mark_squareX]
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
		x2 = ObjectChip[mark_squareY][mark_squareX].GetPos().x;
		y1 = pos.y;
		y2 = ObjectChip[mark_squareY][mark_squareX].GetPos().y;

		length = ((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1));

		length = (float) Math.sqrt(length);

		move_speed.x = ((x2 - x1) / length) * vel.x;
		move_speed.y = ((y2 - y1) / length) * vel.y;
	}
	
	

}
