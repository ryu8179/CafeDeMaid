package jp.ac.trident.game.maid.main;

import jp.ac.trident.game.maid.common.CommonData;
import jp.ac.trident.game.maid.common.Vector2D;

public class ObjectData {
	
	/* 定数宣言 */
	
	/** 画像の幅 */
	public static final int OBJ_RES_WIDTH = 64;

	/** 画像の高さ */
	public static final int OBJ_RES_HEIGHT = 64;
	
	/** 画像のリソース幅 */
	public static final int OBJ_CHIP_RES_LENGTH = 3;
	
	/**
	 * オブジェクトの種類
	 * @author ryu8179
	 *
	 */
	public enum OBJECT_NAME {
		OBJECT_NAME_NONE,
		OBJECT_NAME_CHAIR,
		OBJECT_NAME_TABLE,
		OBJECT_NAME_COOKING_TABLE,
		OBJECT_NAME_CASHIER,
	}
	
	/* ここまで定数宣言 */
	
	/**
	 * オブジェクトのX,Y座標
	 */
	private Vector2D pos = new Vector2D();

	/**
	 * オブジェクトのチップ番号
	 */
	private int chip_num;
	
	/**
	 * オブジェクトの種類
	 */
	private OBJECT_NAME m_objectName;

	/**
	 * 画像を反転させるか。
	 * true:反転させる
	 */
	private boolean isReverse;
	
	/**
	 * オブジェクトの向き
	 * 右向きの場合、描画を反転させる必要がある。
	 * 椅子以外はあまり必要無いかも。
	 * DIRECTION_***** で指定お願いします。
	 */
	private int m_direction;

	/**
	 * オブジェクトが使用されているかのフラグ
	 */
	private boolean used_flag;
	
	/**
	 * 何番目に描画するのか
	 */
	private int draw_number;

	/**
	 * デフォルトコンストラクタ
	 */
	public ObjectData() {
		Initialize();
	}
	
	/**
	 * コンストラクタ
	 * @param objectName
	 * @param chip_num
	 * @param isReverse
	 */
	public ObjectData(OBJECT_NAME objectName, int chip_num, boolean isReverse) {
		this.pos.x = 0;
		this.pos.y = 0;
		this.chip_num = chip_num;
		this.m_objectName = OBJECT_NAME.OBJECT_NAME_NONE;
		this.isReverse = isReverse;
		this.m_direction = CommonData.DIRECTION_NONE;
		this.used_flag = false;
		this.m_objectName = objectName;
	}
	/**
	 * 方向指定コンストラクタ
	 * @param objectName
	 * @param chip_num
	 * @param isReverse
	 * @param direction
	 */
	public ObjectData(OBJECT_NAME objectName, int chip_num, boolean isReverse, int direction) {
		this(objectName, chip_num, isReverse);
		m_direction = direction;
	}

	/**
	 * 初期化
	 */
	public void Initialize() {
		this.pos.x = 0;
		this.pos.y = 0;
		this.chip_num = 0;
		this.isReverse = false;
		this.used_flag = false;
	}

	/**
	 * IDの取得
	 */
	//public void SetId(int id) {
	//	this.ID = id;
	//}

	/**
	 * IDを返す
	 */
	//public int GetId() {
	//	return this.ID;
	//}

	/**
	 * 座標の取得XY
	 */
	public void SetPosXY(float pos_x, float pos_y) {
		this.pos.x = pos_x;
		this.pos.y = pos_y;
	}

	/**
	 * 座標を取得X
	 */
	public void SetPosX(float pos_x) {
		this.pos.x = pos_x;
	}

	/**
	 * 座標を取得Y
	 */
	public void SetPosY(float pos_y) {
		this.pos.y = pos_y;
	}

//	/**
//	 * 座標を返すX
//	 */
//	public int GetPosX() {
//		return this.pos_x;
//	}
//
//	/**
//	 * 座標を返すY
//	 */
//	public int GetPosY() {
//		return this.pos_y;
//	}
	
	/**
	 * 座標を返すX
	 */
	public Vector2D GetPos() {
		return this.pos;
	}

	/**
	 * 床のチップ番号の取得
	 */
	public void SetChip_num(int chip_num) {
		this.chip_num = chip_num;
	}

	/**
	 * 床のチップ番号を返す
	 */
	public int GetChip_num() {
		return this.chip_num;
	}

	/**
	 * 向きを取得
	 */
	public void SetReverse(boolean isReverse) {
		this.isReverse = isReverse;
	}

	/**
	 * 向きを返す
	 */
	public boolean IsReverse() {
		return this.isReverse;
	}

	/**
	 * @return m_direction
	 */
	public int getM_direction() {
		return m_direction;
	}

	/**
	 * @param m_direction 設定する m_direction
	 */
	public void setM_direction(int m_direction) {
		this.m_direction = m_direction;
	}

	/**
	 * 使われているかのフラグを取得
	 */
	public void SetUsed_flag(boolean used_flag) {
		this.used_flag = used_flag;
	}

	/**
	 * 使用されているかのフラグを返す
	 */
	public boolean GetUsed_flag() {
		return this.used_flag;
	}
	
	/**
	 * 描画順を設定
	 */
	public void SetDrawNumber(int draw_number) {
		this.draw_number = draw_number;
	}

	/**
	 * 描画順を返す
	 */
	public int GetDrawNumber() {
		return this.draw_number;
	}

	/**
	 * オブジェクトの名前を返す
	 * @return
	 */
	public OBJECT_NAME getM_objectName() {
		return m_objectName;
	}
}