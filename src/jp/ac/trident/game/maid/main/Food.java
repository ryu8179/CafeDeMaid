package jp.ac.trident.game.maid.main;

/**
 * 料理データ
 * @author ryu8179
 *
 */
class Food {
	// ********************** 定数宣言 ********************** 
	/**
	 * 料理名
	 */
	public enum FOOD_NAME {
		FOOD_NAME_COFFEE,
		FOOD_NAME_TEA,
		FOOD_NAME_CAKE,
		FOOD_NAME_RICE_OMELET,
		FOOD_NAME_COUNT,
		
		FOOD_NAME_NONE,
	}
	/**
	 * 料理の画像情報　サイズ等
	 */
	public static final int FOODTX_WIDTH = 128;
	public static final int FOODTX_HEIGHT = 128;
	public static final int FOOD_WIDTH = 64;
	public static final int FOOD_HEIGHT = 64;
	// ********************** ここまで定数宣言 ********************** 

	// ********************** メンバ変数 ********************** 
	/**
	 * 料理データ
	 */
	private FoodData m_foodData;
	
	/**
	 * マップチップによる x と y 座標
	 */
	private int m_x;
	private int m_y;
	
	/**
	 * 食べられる前か。
	 */
	private boolean isExist;
	
	// ********************** ここまでメンバ変数 ********************** 

	// ********************** メソッド ********************** 
	/**
	 * コンストラクタ
	 * @return
	 */
	public Food() {
		this(new FoodData(), 0, 0);
	}
	public Food(FoodData foodData, int x, int y) {
		m_foodData = foodData;
		m_x = x;
		m_y = y;
		isExist = true;
	}

	// getter と setter
	public int getM_x() {
		return m_x;
	}
	public void setM_x(int m_x) {
		this.m_x = m_x;
	}
	public int getM_y() {
		return m_y;
	}
	public void setM_y(int m_y) {
		this.m_y = m_y;
	}
	public FoodData getM_foodData() {
		return m_foodData;
	}
	public void setM_foodData(FoodData foodData) {
		this.m_foodData = foodData;
	}
	/**
	 * @return isExist
	 */
	public boolean isExist() {
		return isExist;
	}
	/**
	 * @param isExist 設定する isExist
	 */
	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}
	
	// ********************** ここまでメソッド ********************** 
}