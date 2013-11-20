package jp.ac.trident.game.maid.main;

public class WallData {

	/**
	 * 壁のX,Y座標
	 */
	private int pos_x, pos_y;

	/**
	 * チップの番号
	 */
	private int chip_num;

	/**
	 * チップの正確な種類 椅子:1 机:2
	 */
	// private int chip_brand;

	/**
	 * マップチップの向き
	 * true:反転させる
	 */
	private boolean direction;

	/**
	 * コンストラクタ
	 */
	public WallData() {
		Initialize();
	}

	/**
	 * 初期化
	 */
	public void Initialize() {
		this.pos_x = 0;
		this.pos_y = 0;
		this.chip_num = 6;
		this.direction = false;
	}

	/**
	 * 座標を設定X
	 */
	public void SetPosX(int pos_x) {
		this.pos_x = pos_x;
	}

	/**
	 * 座標を設定Y
	 */
	public void SetPosY(int pos_y) {
		this.pos_y = pos_y;
	}

	/**
	 * 座標を返すX
	 */
	public int GetPosX() {
		return this.pos_x;
	}

	/**
	 * 座標を返すY
	 */
	public int GetPosY() {
		return this.pos_y;
	}

	/**
	 * チップの種類を取得
	 */
	public void SetChip_num(int chip_num) {
		this.chip_num = chip_num;
	}

	/**
	 * チップの種類を返す
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
}