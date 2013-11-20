package jp.ac.trident.game.maid.main;

public class Vector2D {

	/** ==================メンバ変数の定義================== **/
	public float x; // x座標
	public float y; // y座標

	/** ================================================ **/

	/**
	 * Vectorクラスのコンストラクタ
	 * 
	 */
	public Vector2D() {
		Init();
	}

	/**
	 * 引数付きコンストラクタ
	 * 
	 * @param x
	 * @param y
	 */
	public Vector2D(float x, float y) {

		this.x = x;
		this.y = y;
	}
	
	/**
	 * 初期化
	 */
	public void Init() {
		// 初期化
		x = 0;
		y = 0;
	}

	/**
	 * Xの値を返す
	 * 
	 * @return x : X要素
	 */
	public float getX() {
		return x;
	}

	/**
	 * Yの値を返す
	 * 
	 * @
	 */
	public float getY() {
		return y;
	}

	/**
	 * Xに値を設定する
	 * 
	 * @param x
	 *            :
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Yに値を設定する
	 * 
	 * @param y
	 *            :
	 */
	public void setY(float y) {
		this.y = y;
	}

}
