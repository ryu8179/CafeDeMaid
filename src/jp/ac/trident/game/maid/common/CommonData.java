/**
 * 
 */
package jp.ac.trident.game.maid.common;

/**
 * シングルトン
 * シーンを跨いで利用するデータ群
 * @author ryu8179
 *
 */
public class CommonData {
	
	/* 定数 */
	/* ここまで定数 */
	
	/* メンバ変数 */
	/**
	 * シングルトンのインスタンス
	 */
	private static CommonData m_commonData;
	
	public int money;
	/* ここまでメンバ変数 */

	/**
	 * デフォルトコンストラクタ
	 */
	private CommonData() {
		money = 0;
	}
	
	public static CommonData GetInstance() {
		if (m_commonData == null) {
			m_commonData = new CommonData();
		}
		return m_commonData;
	}

}
