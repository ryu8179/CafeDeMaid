package jp.ac.trident.game.maid.main;

import jp.ac.trident.game.maid.main.Food.FOOD_NAME;
import jp.ac.trident.game.maid.main.GameMain.TEX_NAME;
import android.graphics.Bitmap;

public class Maid extends Human {

	/* ●●●メイドの管理方法●● */
	// 座標はマスを基準に取っていく（フロアのデータをもとに）　
	/* ●●●●●●●●●●●●●●●●● */

	/* 定数宣言 */
	/** 調理時間(ミリ秒) */
	private final long COOKING_TIME = 2000;
	/* ここまで定数宣言 */
	
	/* ここからメンバ変数 */
	/**
	 * メイドが手に持っているもの
	 * 配膳等の時に使用する
	 */
	private FOOD_NAME m_food;
	
	/**
	 * 調理中かどうか
	 */
	private boolean isCooking;

	/**
	 * コンストラクタ
	 */
	public Maid(Bitmap image)  {
		super(image);
		Initialize();
	}

	/**
	 * 初期化
	 */
	public void Initialize() {
		super.Initialize();
		m_food = FOOD_NAME.FOOD_NAME_NONE;
		isCooking = false;
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
	 * 所持料理名を返す
	 * @return
	 */
	public FOOD_NAME getM_food() {
		return m_food;
	}

	/**
	 * 所持料理をセットする
	 * @param m_food
	 */
	public void setM_food(FOOD_NAME m_food) {
		this.m_food = m_food;
	}

	/**
	 * 移動させる処理 実際に移動させる関数
	 */
	public void Move(int target_height, int target_width) {
		super.Move(target_height, target_width);
		// 移動中は、調理フラグを下げる
		if (!reach_flag) {
			isCooking = false;
		}
	}
	
	/**
	 * 調理を行う。
	 */
	public void Cooking() {
		// 既に料理を持っていたらメソッドを抜ける。
		if (m_food != FOOD_NAME.FOOD_NAME_NONE) {
			return;
		}
		// 調理中のフラグが立っていたら調理を行う。
		if (isCooking) {
			Animation(MODE_MOVE);
			long currentTime = System.currentTimeMillis();
			// 調理終えたら
			if (currentTime - m_startTime >= COOKING_TIME) {
				m_food = FOOD_NAME.FOOD_NAME_COFFEE;
				m_image = GameMain.imageMap.get(TEX_NAME.MAID_01);
				isCooking = false;
			}
			return;
		}
		isCooking = true;
		m_image = GameMain.imageMap.get(TEX_NAME.MOHIKAN);
		m_startTime = System.currentTimeMillis();
	}
}