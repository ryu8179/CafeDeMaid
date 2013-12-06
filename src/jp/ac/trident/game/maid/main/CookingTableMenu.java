/**
 * 
 */
package jp.ac.trident.game.maid.main;

import java.util.ArrayList;

import jp.ac.trident.game.maid.common.Collision;
import jp.ac.trident.game.maid.common.Vector2D;
import jp.ac.trident.game.maid.main.Food.FOOD_NAME;
import jp.ac.trident.game.maid.main.GameMain.TEX_NAME;

import android.R.bool;
import android.graphics.Rect;

import com.example.maid.GameSurfaceView;

/**
 * @author ryu8179
 * クッキングテーブルをタッチした時に出すポップアップだったり、料理だったり
 */
public class CookingTableMenu {
	////////////////////// 定数 //////////////////////
	/**
	 * 料理の横幅
	 */
	private final int FOOD_WIDTH = 32;
	/**
	 * 料理の縦幅
	 */
	private final int FOOD_HEIGHT = 32;
	////////////////////// ここまで定数 //////////////////////

	////////////////////// メンバ変数 //////////////////////
	/**
	 * メニューを開いているかどうか
	 */
	private boolean isOpen;
	
	/**
	 * 調理台の座標
	 */
	private Vector2D m_pos;
	
	/**
	 * １つ目の料理を表示する座標
	 */
	private Vector2D m_posOf1stFood;
	
	/**
	 * 調理可能な料理リスト
	 */
	private ArrayList<FOOD_NAME> m_foodList;
	////////////////////// ここまでメンバ変数 //////////////////////
	
	////////////////////// メソッド //////////////////////
	/**
	 * コンストラクタ
	 */
	public CookingTableMenu(Vector2D pos) {
		m_pos = new Vector2D();
		m_posOf1stFood = new Vector2D();
		m_foodList = new ArrayList<Food.FOOD_NAME>();
		
		this.Initialize(pos);
	}
	
	/**
	 * 初期化
	 */
	public void Initialize(Vector2D pos) {
		m_pos.set(pos);
		m_posOf1stFood.x = m_pos.x + ObjectData.OBJ_RES_WIDTH*0.5f - FOOD_WIDTH*1.5f;
		m_posOf1stFood.y = m_pos.y - (ObjectData.OBJ_RES_HEIGHT+FOOD_HEIGHT);
		
		this.AddFood(FOOD_NAME.FOOD_NAME_CAKE);
		this.AddFood(FOOD_NAME.FOOD_NAME_COFFEE);
	}
	
	/**
	 * 更新
	 */
	public void Update() {
		
	}
	
	/**
	 * 描画
	 * @param sv
	 */
	public void Draw(GameSurfaceView sv) {
		// 調理台選択時、吹き出しの表示
		sv.DrawImageScale(
				GameMain.imageMap.get(TEX_NAME.COOKING_TABLE_BALLOON),
				(int) m_pos.x - ObjectData.OBJ_RES_WIDTH,
				(int) m_pos.y - 96,
				0,
				0,
				256,
				128,
				0.75f,
				0.75f,
				false);
		// 料理リストの表示
		int chipNum = 0;
		for (int i=0; i<m_foodList.size(); i++) {
			switch (m_foodList.get(i)) {
				case FOOD_NAME_COFFEE:	chipNum = 0; break;
				case FOOD_NAME_CAKE:	chipNum = 1; break;
	
				default: break;
			}
			sv.DrawMapChip(
					GameMain.imageMap.get(TEX_NAME.FOOD),
					(int) (m_posOf1stFood.x + FOOD_WIDTH*(i%3)),
					(int) (m_posOf1stFood.y + FOOD_HEIGHT*(i/3)),
					16 + 64*(chipNum%4),
					16 + 64*(chipNum/4),
					32,
					32,
					false);
		}
	}
	
	/**
	 * 当たり判定チェック
	 * @param touchPos
	 * @return 料理名 or null
	 */
	public FOOD_NAME CheckCollide(Vector2D touchPos) {
		for (int i=0; i<m_foodList.size(); i++) {
			Rect rect = new Rect();
			rect.left = (int) (m_posOf1stFood.x + FOOD_WIDTH*(i%3));
			rect.top  = (int) (m_posOf1stFood.y + FOOD_HEIGHT*(i/3));
			rect.right = rect.left + FOOD_WIDTH;
			rect.bottom = rect.top + FOOD_HEIGHT;
			if (Collision.pointRect(touchPos, rect)) {
				return m_foodList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * 調理可能リストに、料理の追加
	 * @param food
	 */
	public void AddFood(FOOD_NAME food) {
		m_foodList.add(food);
	}
	
	/**
	 * 渡されてきた料理名が、調理可能リストにあったら、
	 * それを削除する
	 * @param food
	 */
	public void RemoveFood(FOOD_NAME food) {
		for (int i=m_foodList.size()-1; i>=0; i--) {
			if (m_foodList.get(i).equals(food)) {
				m_foodList.remove(i);
			}
		}
	}

	/**
	 * @return isOpen
	 */
	public boolean isOpen() {
		return isOpen;
	}

	/**
	 * @param isOpen 設定する isOpen
	 */
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	////////////////////// ここまでメソッド //////////////////////
	

}
