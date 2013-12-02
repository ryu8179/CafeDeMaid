package jp.ac.trident.game.maid.common;

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

	// 値の指定
	public void set(Vector2D origin) {
		x = origin.x;
		y = origin.y;
	}

	// 値の指定
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 角度を取得する
	 * 
	 */
	public float getAngle() {

		// atan2() = 2点を結ぶ線の角度を求める。
		return (float) Math.atan2(y, x);
	}

	/**
	 * 大きさを取得する
	 * 
	 */
	public float getLength() {

		// sqrt() = 平方根を求める
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * ベクトルの大きさの上限設定
	 */
	public void setLengthCap(float maxLength) {

		float length = getLength();

		if (maxLength == 0) {

			return;
		}

		// maxLengthより大きければ大きさをmaxLengthにする
		if (length > maxLength) {

			float rate = length / maxLength;
			x /= rate;
			y /= rate;

		}
	}

	/**
	 * ベクトル方向に、引数のrate分だけ、ベクトルを加える
	 * 
	 * @param vec
	 *            ベクトル
	 * @param rate
	 *            比率
	 */
	public void add(Vector2D vec, float rate) {

		float width = vec.x - x;
		float height = vec.y - y;

		x += width * rate;
		y += height * rate;
	}
	
	// 減算
	public void sub(Vector2D v1) {
		this.x -= v1.x;
		this.y -= v1.y;
	}

	// スカラー倍
	public void scale(float scale) {
		this.x *= scale;
		this.y *= scale;
	}

	// ベクトルの長さの取得
	public float length() {
		return (float) Math.sqrt((double) ((x * x) + (y * y)));
	}

	// 正規化
	public void normalize() {
		float length = this.length();
		this.scale(1 / length);
	}

	// 値の比較
	@Override
	public boolean equals(Object o) {
		Vector2D v = (Vector2D) o;
		return v.x == x && v.y == y;
	}

	// public static メソッド(オブジェクトが無くても使用可能)
	// 加算
	public static Vector2D add(Vector2D v0, Vector2D v1) {
		return new Vector2D(v0.x+v1.x, v0.y+v1.y);
	}
	// 減算
	public static Vector2D sub(Vector2D v0, Vector2D v1) {
		return new Vector2D(v0.x-v1.x, v0.y-v1.y);
	}

}
