/**
 * 
 */
package jp.ac.trident.game.maid.main;

import java.text.BreakIterator;

import jp.ac.trident.game.maid.common.Collision;
import jp.ac.trident.game.maid.common.Vector2D;
import android.graphics.Bitmap;

/**
 * @author ryu8179
 *
 */
public class Customer extends Human {
	
	/* メンバ変数 */
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
		InitializePos();
	}
	
	/**
	 * 初期位置に設置(店外)
	 */
	private void InitializePos() {
		isInShop = false;
		target_height = 0;	// 店内に入るまで意味なし
		target_width = 0;	// 店内に入るまで意味なし
		target.x= 80;
		target.y = 60;
		
		// どちらに向いて歩くかをランダムで決定。
		if (GameMain.rand.nextBoolean()) {
			pos.x = 0;
			pos.y = 80;
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
		// 店内と店外で移動方法が異なる
		if (isInShop) {
			// 目標に向かって
			super.Update(target_height, target_width);
			
		} else {
			// 道路を歩く、店に入るまで。
			this.pos.x += vel.x;
			this.pos.y += vel.y;
			Animation(MODE_MOVE);
			// 店の前らへんに来たら、入るかどうかの判定、ターゲット座標の変更
			if (Collision.pointCircle(pos, GameMap.FRONT_OF_SHOP_POS, 20)) {
				target.set(GameMap.ENTRANCE_POS);
				m_direction = DIRECTION_RIGHTDOWN;
				isReverse = true;
				vel = Vector2D.sub(target, pos);
				vel.normalize();
				vel.scale(4);
			}
			if (Collision.pointCircle(pos, GameMap.ENTRANCE_POS, 5)) {
				super.Initialize();
				SetSquareXY(5, 0);
				isInShop = true;
				// 空いている座席に移動先を定める。
				// 縦の配列 マップの高さ分回す
				for (int y = 0; y < GameMap.MAP_HEIGHT; y++) {
					boolean isSearchEnd = false;
					// 横の配列 マップの横幅分回す
					for (int x = 0; x < GameMap.MAP_WIDTH; x++) {
						if (FloorChip[y][x].GetUsed_floor()) {
							continue;
						}
						if (!FloorChip[y][x].GetUsed_floor()) {
							FloorChip[y][x].SetUsed_floor(true);
							target_height = y;
							target_width = x;
							isSearchEnd = true;
							break;
						}
					}
					if (isSearchEnd) break;
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
	/* ここまでメソッド */

}
