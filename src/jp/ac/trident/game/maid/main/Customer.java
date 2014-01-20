/**
 * 
 */
package jp.ac.trident.game.maid.main;

import java.util.ArrayList;
import java.util.Collections;

import jp.ac.trident.game.maid.common.Collision;
import jp.ac.trident.game.maid.common.CommonData;
import jp.ac.trident.game.maid.common.Vector2D;
import jp.ac.trident.game.maid.main.Food.FOOD_NAME;
import jp.ac.trident.game.maid.main.ObjectData.OBJECT_NAME;
import android.graphics.Bitmap;

/**
 * @author ryu8179
 *
 */
public class Customer extends Human {
	
	private class ObjectSquareData {
		public ObjectData objectData;
		public int x;
		public int y;
	}
	
	/* 定数 */
	/**
	 * お客様の現在の行動状況
	 * @author ryu8179
	 *
	 */
	public enum PHASE {
		PHASE_MOVING_ROAD,
		PHASE_MOVING_SHOP,
		PHASE_WAITING,
		PHASE_EATING,
		
		PHASE_COUNT,
		PHASE_NONE,
	}
	
	/**
	 * 食事時間(ミリ秒)
	 */
	private static final long EATING_TIME = 3000;
	/* ここまで定数 */

	
	/* メンバ変数 */
	
	/**
	 * お客の現在の行動状況
	 */
	private PHASE m_phase;
	
	/**
	 * 入店するかどうか判定を行ったか。
	 */
	private boolean isCheckEnter;
	
	/**
	 * 注文の品
	 */
	private Food m_orderFood;
	
	/**
	 * 食事中か
	 */
	private boolean isEating;
	
	/**
	 * 椅子のリスト
	 */
	private ArrayList<ObjectSquareData> m_chairList;
	
	/**
	 * イライラ率
	 * 1.0fになったら帰らす！
	 */
	private float m_irritatedRate;
	
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
		
		// Customerのみの変数の初期化
		m_phase = PHASE.PHASE_MOVING_ROAD;
		isCheckEnter = false;
		m_orderFood = new Food();
		isEating = false;
		m_chairList = new ArrayList<Customer.ObjectSquareData>();
		m_irritatedRate = 0;
		target_height = 0;	// 店内に入るまで意味なし
		target_width = 0;	// 店内に入るまで意味なし
		
		// 店外に設置するための初期化
		InitializePos();
	}
	
	/**
	 * 初期位置に設置(店外)
	 */
	private void InitializePos() {
		// 向かう先を決定
		target.x= 80;
		target.y = 60;
		
		// どちらに向いて歩くかをランダムで決定。
		if (GameMain.rand.nextBoolean()) {
			pos.x = -40;
			pos.y = 120;
			m_direction = CommonData.DIRECTION_RIGHTUP;
			isReverse = true;
		} else {
			pos.x = 200;
			pos.y = 0;
			m_direction = CommonData.DIRECTION_LEFTDOWN;
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
		
		// 状況によっての行動分岐
		switch (m_phase) {
		
			// 店内の移動
			case PHASE_MOVING_SHOP:
				//  目標地点に向かいます。
				super.Update(target_height, target_width);
				
				// 食事前かどうか
				if (m_orderFood.isExist()) {
					// 目の前が目標の椅子だったら、座らせ、注文を決定する。
					if (square_x == target_width && (square_y-1 == target_height || square_y+1 == target_height)
					||  square_y == target_height && (square_x-1 == target_width || square_x+1 == target_width) ) {
						// 椅子と同じ座標に設置
						SetSquareXY(target_width, target_height);
						// 椅子と同じ向きに固定
						m_direction = ObjectChip[target_height][target_width].getM_direction();
						if (m_direction == CommonData.DIRECTION_LEFTDOWN || m_direction == CommonData.DIRECTION_LEFTUP) {
							isReverse = false;
						} else {
							isReverse = true;
						}
						// 移動リストのクリア
						list.clear();
						// 注文の品を決定
						int rNum = GameMain.rand.nextInt(100);
						if (rNum < 30) {
							m_orderFood.setM_foodData(FoodData.foodData[0]);
						} else if (rNum < 60) {
							m_orderFood.setM_foodData(FoodData.foodData[1]);
						} else if (rNum < 80) {
							m_orderFood.setM_foodData(FoodData.foodData[2]);
						} else if (rNum < 100) {
							m_orderFood.setM_foodData(FoodData.foodData[3]);
						} else {
							m_orderFood.setM_foodData(FoodData.foodData[0]);
						}
						m_phase = PHASE.PHASE_WAITING;
					}
				} else {
					// 食事を終え、入り口についたら外にいかせる。
					if (Collision.pointCircle(pos, GameMap.ENTRANCE_POS, 1)) {
						m_direction = CommonData.DIRECTION_LEFTUP;
						isReverse = false;
						m_phase = PHASE.PHASE_MOVING_ROAD;
						vel.x = -4.0f;
						vel.y = -4.0f;
					}
				}
				
				
				break;
				
				
			// 料理待ち
			case PHASE_WAITING:
				// イライラ率を増加
				m_irritatedRate += 0.001f;
				if (m_irritatedRate >= 1.0f) {
					LeaveStore();
				}
				break;
				
				
			// 食事中
			case PHASE_EATING:
				Eating();
				break;
				
		
			// 店外での移動
			case PHASE_MOVING_ROAD:
				// 道路を歩く、店に入るまで。
				this.pos.x += vel.x;
				this.pos.y += vel.y;
				Animation(MODE_MOVE);
				
				// 店に入るかどうかの判定
				if (!isCheckEnter) {
					CheckEnter();
				}
				
				// 店内の入り口に来たら、マスでの移動モードに切り替える
				if (Collision.pointCircle(pos, GameMap.ENTRANCE_POS, 5)) {
					super.Initialize();
					SetSquareXY(5, 0);
					m_phase = PHASE.PHASE_MOVING_SHOP;
					
					// 席リストに全部の席を格納し、リストをシャッフルする。
					AddChairList();
					Collections.shuffle(m_chairList);
					// 空いている座席を探し、その席の座標を保存。空いている席が無かったら帰ってもらう
					Vector2D objPos = null;
					for (int i=0; i<m_chairList.size(); i++) {
						if (!m_chairList.get(i).objectData.GetUsed_flag()) {
							objPos = new Vector2D(m_chairList.get(i).x, m_chairList.get(i).y);
						}
					}
					if (objPos == null) {
						m_orderFood.setExist(false);
						break;
					}
					// 席を使用していることにする。
					ObjectChip[(int)objPos.y][(int)objPos.x].SetUsed_flag(true);
					target_height = (int)objPos.y;
					target_width = (int)objPos.x;
				}
				break;
				
			// その他の場合無視する。
			case PHASE_NONE:
			default:
				break;
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
	
	/**
	 * 入店するかどうかのチェック。
	 * isCheckEnter によって、１度しか行わない。
	 */
	private void CheckEnter() {
		// 店の前の判定ポジションにいるかどうか
		if (!Collision.pointCircle(pos, GameMap.FRONT_OF_SHOP_POS, 20)) {
			return;
		}
		// 75%の確率で入店させる。
		if (GameMain.rand.nextInt(100) <= 75) {
			target.set(GameMap.ENTRANCE_POS);
			m_direction = CommonData.DIRECTION_RIGHTDOWN;
			isReverse = true;
			vel = Vector2D.sub(target, pos);
			vel.normalize();
			vel.scale(4);
		}
		// 入店するかどうかの判定を行った。
		isCheckEnter = true;
	}
	
	/**
	 * 椅子をリストに格納
	 */
	private void AddChairList() {
		// 椅子を探す
		for (int y = GameMap.MAP_HEIGHT-1; y >= 0; y--) {
			// 横の配列 マップの横幅分回す
			for (int x = GameMap.MAP_WIDTH-1; x >= 0; x--) {
				// 引数で渡ってきたオブジェクト名と同じか
				if (ObjectChip[y][x].getM_objectName() == OBJECT_NAME.OBJECT_NAME_CHAIR) {
					ObjectSquareData object = new ObjectSquareData();
					object.objectData = ObjectChip[y][x];
					object.x = x;
					object.y = y;
					m_chairList.add(object);
				}
			}
		}
	}
	
	/**
	 * 料理を食べる。
	 */
	public void Eating() {
		// 注文の料理が食べられていたら、メソッドを抜ける
		if (!m_orderFood.isExist()) {
			return;
		}
		// 食事中のフラグが立っていたら食事を行う。
		if (isEating) {
			Animation(MODE_MOVE);
			long currentTime = System.currentTimeMillis();
			// 食べ終えたら
			if (currentTime - m_startTime >= EATING_TIME) {
				// 料理代金を徴収する
				CommonData.GetInstance().GetPlayerData().money += m_orderFood.getM_foodData().price;
				CommonData.GetInstance().SaveData();
				// 店を出るための処理
				LeaveStore();
			}
			return;
		}
		isEating = true;
		//m_image = GameMain.imageHashMap.get(TEX_NAME.MOHIKAN);
		m_startTime = System.currentTimeMillis();
	}
	
	/**
	 * 席を立ち店を出るときに必要となる処理
	 */
	private void LeaveStore() {
		// 注文料理を無効化する
		m_orderFood.setExist(false);
		// 自分が座っていた席を空ける
		ObjectChip[this.GetSquareY()][this.GetSquareX()].SetUsed_flag(false);
		//// アニメーションを終えて、テクスチャを戻す？
		//m_image = GameMain.imageHashMap.get(TEX_NAME.MAID_01);
		// 食事中のフラグを下ろす
		isEating = false;
		// 食事を終えたので、入り口に向かわせる
		target_height = 0;
		target_width = 5;
		m_phase = PHASE.PHASE_MOVING_SHOP;
	}

	/**
	 * @return m_orderFood
	 */
	public Food getM_order() {
		return m_orderFood;
	}

	/**
	 * @param m_orderFood 設定する m_orderFood
	 */
	public void setM_order(Food m_orderFood) {
		this.m_orderFood = m_orderFood;
	}

	/**
	 * @return m_phase
	 */
	public PHASE getM_phase() {
		return m_phase;
	}

	/**
	 * @param m_phase 設定する m_phase
	 */
	public void setM_phase(PHASE m_phase) {
		this.m_phase = m_phase;
	}

	/**
	 * @return m_irritatedRate
	 */
	public float getM_irritatedRate() {
		return m_irritatedRate;
	}

	/**
	 * @param m_irritatedRate 設定する m_irritatedRate
	 */
	public void setM_irritatedRate(float m_irritatedRate) {
		this.m_irritatedRate = m_irritatedRate;
	}
	
	/* ここまでメソッド */

}
