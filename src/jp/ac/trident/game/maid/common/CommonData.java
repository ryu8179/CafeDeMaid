/**
 * 
 */
package jp.ac.trident.game.maid.common;


/**
 * シングルトンクラス
 * シーンをまたいで利用するデータ群
 * @author ryu8179
 *
 */
public class CommonData {
	
	/* データ構造体 */
	public class PlayerData {
		public int money;
		public int str;
		public int speed;
		public int maid;
	};
	public class OptionData {
		;
	};
	
	/* 定数 */
	/* ここまで定数 */
	
	/**
	 * シングルトンのインスタンス
	 */
	private static CommonData instance = new CommonData();

	/* メンバ変数 */
	/**
	 * プレイヤーのデータ
	 */
	private PlayerData playerData;
	/**
	 * オプションデータ
	 */
	private OptionData optionData;
	/* ここまでメンバ変数 */

	/* メソッド */
	/**
	 * デフォルトコンストラクタ
	 */
	private CommonData() {
		playerData = new PlayerData();
		optionData = new OptionData();
		
		// 端末に保存されているデータのロード
		LoadData();
	}
	
	/**
	 * 端末に保存されているデータのロードを行う。　予定
	 */
	public void LoadData() {
		playerData.money = 1000;
		playerData.str = 5;
		playerData.speed = 10;
		playerData.maid = 15;
	}
	
	/**
	 * 端末にデータを保存する。　予定
	 */
	public void SaveData() {
	}
	
	/**
	 * インスタンスの取得
	 * @return
	 */
	public static CommonData GetInstance() {
		return instance;
	}
	
	/**
	 * プレイヤーデータの取得
	 * @return
	 */
	public PlayerData GetPlayerData() {
		return playerData;
	}
	
	/**
	 * オプションデータの取得
	 * @return
	 */
	public OptionData GetOptionData() {
		return optionData;
	}
	/* ここまでメソッド */

}
