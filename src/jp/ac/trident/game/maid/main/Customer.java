/**
 * 
 */
package jp.ac.trident.game.maid.main;

import jp.ac.trident.game.maid.common.Collision;
import jp.ac.trident.game.maid.common.Vector2D;
import jp.ac.trident.game.maid.main.ObjectData.OBJECT_NAME;
import android.graphics.Bitmap;

/**
 * @author ryu8179
 *
 */
public class Customer extends Human {
	
	/* メンバ変数 */
	
	/**
	 * 入店するかどうか判定を行ったか。
	 */
	private boolean isCheckEnter;
	
	/**
	 * 店内にいるか
	 * 描画方法に関わってくる。
	 */
	private boolean isInShop;
	
	/**
	 * 目の前のマスじゃなく、最後の座標
	 */
	private int target_height;
	private int target_width;
	/* ここまでメンバ変数 */

	/* メソッド */
	/**
	 * コンストラクタ
	 */
	public Customer(Bitmap image) {
		// Humanクラスのコンストラクタ呼び出し
		super(image);
		
		// 初期化
		Initialize();
	}

	/**
	 * 初期化
	 */
	public void Initialize() {
		// HumanクラスのInitialize()呼び出し
		super.Initialize();
		// Customerのみの変数の初期化
		isCheckEnter = false;
		isInShop = false;
		target_height = 0;	// 店内に入るまで意味なし
		target_width = 0;	// 店内に入るまで意味なし
		target.x= 80;
		target.y = 60;
		// 店外に設置するための初期化
		InitializePos();
	}
	
	/**
	 * 初期位置に設置(店外)
	 */
	private void InitializePos() {
		
		// どちらに向いて歩くかをランダムで決定。
		if (GameMain.rand.nextBoolean()) {
			pos.x = -40;
			pos.y = 120;
			m_direction = DIRECTION_RIGHTUP;
			isReverse = true;
		} else {
			pos.x = 200;
			pos.y = 0;
			m_direction = DIRECTION_LEFTDOWN;
			isReverse = false;
		}
		vel = Vector2D.sub(target, pos);
		vel.normalize();
		vel.scale(4);
	}
	

	/**
	 * 更新
	 */
	public void Update() {
		m_elapsedFrame++;
		
		// 店内にいるか
		if (isInShop) {
			//  目標地点に向かいます。
			super.Update(target_height, target_width);
			
			// 目の前が目標の椅子だったら、座らせるような処理をする
			if (square_x == target_width && (square_y-1 == target_height || square_y+1 == target_height)
			||  square_y == target_height && (square_x-1 == target_width || square_x+1 == target_width) ) {
				SetSquareXY(target_width, target_height);
				list.clear();
			}
			
		} else {
			// 道路を歩く、店に入るまで。
			this.pos.x += vel.x;
			this.pos.y += vel.y;
			Animation(MODE_MOVE);
			
			// 店に入るかどうかの判定
			if (!isCheckEnter) {
				CheckEnter();
			}
			
			// 店内の入り口に来たら、マスでの移動モードに切り替える
			if (Collision.pointCircle(pos, GameMap.ENTRANCE_POS, 5)) {
				super.Initialize();
				SetSquareXY(5, 0);
				isInShop = true;
				
				// 空いている座席を探し、そこに向かわせる。
				Vector2D objPos = SearchOfUnusedObject(OBJECT_NAME.OBJECT_NAME_CHAIR);
				if (objPos != null) {
					ObjectChip[(int)objPos.y][(int)objPos.x].SetUsed_flag(true);
					target_height = (int)objPos.y;
					target_width = (int)objPos.x;
				}
			}
		}
	}
	/**
	 * 更新
	 * 
	 * @param target_height
	 * @param target_width
	 */
	public void Update(int target_height, int target_width) {
		super.Update(target_height, target_width);
	}
	
	/**
	 * 入店するかどうかのチェック。
	 * isCheckEnter によって、１度しか行わない。
	 */
	private void CheckEnter() {
		// 店の前の判定ポジションにいるかどうか
		if (!Collision.pointCircle(pos, GameMap.FRONT_OF_SHOP_POS, 20)) {
			return;
		}
		// 75%の確率で入店させる。
		if (GameMain.rand.nextInt(100) <= 75) {
			target.set(GameMap.ENTRANCE_POS);
			m_direction = DIRECTION_RIGHTDOWN;
			isReverse = true;
			vel = Vector2D.sub(target, pos);
			vel.normalize();
			vel.scale(4);
		}
		// 入店するかどうかの判定を行った。
		isCheckEnter = true;
	}
	
	/**
	 * 使われていないオブジェクトの、マップ上での座標を返す
	 * @param objName	椅子とか、机とか。
	 */
	private Vector2D SearchOfUnusedObject(OBJECT_NAME objName) {
		// 空いているオブジェクトを探す
		for (int y = GameMap.MAP_HEIGHT-1; y >= 0; y--) {
			// 横の配列 マップの横幅分回す
			for (int x = GameMap.MAP_WIDTH-1; x >= 0; x--) {
				// 使用されていたら次のマスへ
				if (ObjectChip[y][x].GetUsed_flag()) {
					continue;
				}
				// 引数で渡ってきたオブジェクト名と同じか
				if (ObjectChip[y][x].getM_objectName() == objName) {
					return new Vector2D(x, y);
				}
			}
		}
		return null;
	}
	/* ここまでメソッド */

}
