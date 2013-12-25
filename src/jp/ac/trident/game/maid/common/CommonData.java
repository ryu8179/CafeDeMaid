/**
 * 
 */
package jp.ac.trident.game.maid.common;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;



/**
 * シングルトンクラス
 * シーンをまたいで利用するデータ群
 * @author ryu8179
 * 
 * 使用例：
 *	CommonData.GetInstance().GetPlayerData().speed;
 *	と書けば、シーンを跨いで使用できるプレイヤーのスピード値を取得できます。
 * 
 *	CommonData.GetInstance().SaveData();
 *	と書けば、Android端末にデータを保存します。
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
	/**
	 * 方向を示す(移動方向や、オブジェクトの向きに利用)
	 * @author ryu8179
	 *
	 */
	public static final int DIRECTION_NONE = -1;
	public static final int DIRECTION_LEFTDOWN = 0;
	public static final int DIRECTION_LEFTUP = 1;
	public static final int DIRECTION_RIGHTDOWN = 2;
	public static final int DIRECTION_RIGHTUP = 3;
	public static final int DIRECTION_COUNT = 4;
	/* ここまで定数 */
	
	/**
	 * シングルトンのインスタンス
	 */
	private static CommonData instance = new CommonData();

	/* メンバ変数 */
	private SharedPreferences sharedPre;
	
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
	}
	
	/**
	 * 端末に保存されているデータのロードを行う。　予定
	 */
	public void LoadData() {
		playerData.money = sharedPre.getInt("money", 1000);
		playerData.str = sharedPre.getInt("str", 5);
		playerData.speed = sharedPre.getInt("speed", 10);
		playerData.maid = sharedPre.getInt("maid", 15);
	}
	
	/**
	 * 端末にデータを保存する。　予定
	 */
	public void SaveData() {
		Editor editor = sharedPre.edit();
		editor.putInt("money", playerData.money);
		editor.putInt("str", playerData.str);
		editor.putInt("speed", playerData.speed);
		editor.putInt("maid", playerData.maid);
		editor.commit();
	}
	
	/**
	 * インスタンスの取得
	 * @return
	 */
	public static CommonData GetInstance() {
		return instance;
	}
	
	/**
	 * SharedPreferencesへの道をつないでおく
	 * @param sharedPre
	 */
	public void SetSharedPreferences(SharedPreferences sharedPre) {
		this.sharedPre = sharedPre;
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
