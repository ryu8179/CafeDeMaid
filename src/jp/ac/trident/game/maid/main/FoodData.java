/**
 * 
 */
package jp.ac.trident.game.maid.main;

import jp.ac.trident.game.maid.main.Food.FOOD_NAME;

/**
 * 料理のデータを持つ。
 * 料理名、重さ、調理時間、値段
 * @author ryu8179
 *
 */
public class FoodData {
	
	public FOOD_NAME name;
	public int weight;
	public int baseCookingTime;
	public int price;

	/**
	 * 
	 */
	public FoodData() {
		this.name = FOOD_NAME.FOOD_NAME_NONE;
		this.weight = 0;
		this.baseCookingTime = 0;
		this.price = 0;
	}
	/**
	 * 
	 * @param name
	 * @param weight
	 * @param baseCookingTime
	 * @param price
	 */
	public FoodData(FOOD_NAME name, int weight, int baseCookingTime, int price) {
		this.name = name;
		this.weight = weight;
		this.baseCookingTime = baseCookingTime;
		this.price = price;
	}
	
	/**
	 * 仮データ
	 */
	public final static FoodData foodData[] = {
		new FoodData(FOOD_NAME.FOOD_NAME_COFFEE, 5, 2, 300 ),
		new FoodData(FOOD_NAME.FOOD_NAME_CAKE, 10, 5, 600 ),
	};

}
