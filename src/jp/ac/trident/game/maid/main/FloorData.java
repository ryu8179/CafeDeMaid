package jp.ac.trident.game.maid.main;

import jp.ac.trident.game.maid.common.Vector2D;

public class FloorData {
	
	/* 定数宣言 */
	/** 画像の幅 */
	public static final int FLOOR_RES_WIDTH = 64;
	
	/** 画像の高さ */
	public static final int FLOOR_RES_HEIGHT = 32;
	
	/** 画像のリソース幅 */
	public static final int FLOOR_CHIP_RES_LENGTH = 4;

	
	/* ここまで定数宣言 */
	/**
	 * 番号
	 */
	private int ID;

	/**
	 * 床のX,Y座標
	 */
	private Vector2D pos;

	/**
	 * 床の中心座標
	 */
	private Vector2D center;
	
	/**
	 * クォータビューでのどの座標にいるか
	 */
	protected int square_x, square_y;

	/** 
	 * 床のチップ番号 
	 */
	private int chip_num;
	
	/**
	 * 床の上に物があるかのフラグ true: ある false: ない
	 */
	private boolean used_floor;
	
	/**
	 * 何番目に描画するのか
	 */
	private int draw_number;

	/**
	 * デフォルトコンストラクタ
	 */
	public FloorData() {
		pos = new Vector2D();
		center = new Vector2D();

		Initialize();
	}

	/**
	 * 初期化
	 */
	public void Initialize() {
		this.ID = 0;
		this.pos.x = 0;
		this.pos.y = 0;
		this.chip_num = 0;
		this.used_floor = false;
		this.draw_number = 0;
	}

	/**
	 * IDの取得
	 */
	public void SetId(int id) {
		this.ID = id;
	}

	/**
	 * IDを返す
	 */
	public int GetId() {
		return this.ID;
	}

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

	/**
	 * 座標を返す
	 */
	public Vector2D GetPos() {
		return this.pos;
	}
	
	/**
	 * 中心座標の取得
	 */
	public void SetCenterXY() {
		this.center.x = pos.x + (FLOOR_RES_WIDTH/2);
		this.center.y = pos.y + (FLOOR_RES_HEIGHT/2);
	}

	/**
	 * 中心座標を返す
	 */
	public Vector2D GetCenter() {
		return this.center;
	}
	
	/**
	 * 座標マスの取得XY
	 */
	public void SetSquareXY(int square_x, int square_y) {
		this.square_x = square_x;
		this.square_y = square_y;
	}
	
	/**
	 * 座標マスを返すX
	 */
	public int GetSquareX() {
		return this.square_x;
	}

	/**
	 * 座標マスを返すY
	 */
	public int GetSquareY() {
		return this.square_y;
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
	 * 使用されているかのフラグの取得
	 */
	public void SetUsed_floor(boolean used_floor) {
		this.used_floor = used_floor;
	}

	/**
	 * 使用されているかのフラグを返す
	 */
	public boolean GetUsed_floor() {
		return this.used_floor;
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