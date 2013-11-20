package jp.ac.trident.game.maid.main;

public class ObjectData {
	
	/* 定数宣言 */
	
	/** 画像の幅 */
	public static final int OBJ_RES_WIDTH = 64;

	/** 画像の高さ */
	public static final int OBJ_RES_HEIGHT = 64;
	
	/** 画像のリソース幅 */
	public static final int OBJ_CHIP_RES_LENGTH = 3;
	
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
	 * オブジェクトの中心座標
	 */
	private Vector2D center;

	/**
	 * オブジェクトがどのマスにいるのか
	 */
	private int square_x, square_y;

	/**
	 * マップチップの向き
	 * true:反転させる
	 */
	private boolean direction;

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
	 * 引数付きコンストラクタ
	 */
	public ObjectData(int chip_num, boolean direction) {
		this.pos.x = 0;
		this.pos.y = 0;
		this.chip_num = chip_num;
		this.direction = direction;
		this.used_flag = false;
	}

	/**
	 * 初期化
	 */
	public void Initialize() {
		this.pos.x = 0;
		this.pos.y = 0;
		this.chip_num = 0;
		this.direction = false;
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
	public void SetDirection(boolean direction) {
		this.direction = direction;
	}

	/**
	 * 向きを返す
	 */
	public boolean GetDirection() {
		return this.direction;
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
}