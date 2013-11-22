package jp.ac.trident.game.maid.main;

import java.util.ArrayList;
import java.util.List;

public class A_Star {

	/**
	 * スタート地点X,Y
	 */
	private int start_x, start_y;

	/**
	 * ゴール地点X,Y
	 */
	private int goal_x, goal_y;

	/**
	 * オープンしている(到達可能性のある)ブロックのリスト
	 */
	private List<MapData> openList = new ArrayList<MapData>();

	/**
	 * マップデータ
	 */
	private MapData[][] map = new MapData[GameMap.MAP_HEIGHT][GameMap.MAP_WIDTH];

	/**
	 * コンストラクタ
	 */
	public A_Star() {

		// A*マップ作成
		for (int y = 0; y < GameMap.MAP_HEIGHT; y++) {
			for (int x = 0; x < GameMap.MAP_WIDTH; x++) {
				map[y][x] = new MapData();
			}
		}

		// 初期化
		Initialize();
	}

	/**
	 * 初期化
	 */
	public void Initialize() {
		this.start_x = 0;
		this.start_y = 0;
		this.goal_x = 0;
		this.goal_x = 0;

		this.openList.clear();

		for (int y = 0; y < GameMap.MAP_HEIGHT; y++) {
			for (int x = 0; x < GameMap.MAP_WIDTH; x++) {
				map[y][x].Initialize();
			}
		}
	}

	/**
	 * 最短経路を検索し返す
	 * 
	 * @param floorData
	 * @param start_x
	 * @param start_y
	 * @param goal_x
	 * @param goal_y
	 * @throws Exception
	 */
	public List<MapData> serch(FloorData[][] floorData, int start_x,
			int start_y, int goal_x, int goal_y) throws Exception {

		for (int y = 0; y < GameMap.MAP_HEIGHT; y++) {
			for (int x = 0; x < GameMap.MAP_WIDTH; x++) {
				if (floorData[y][x].GetUsed_floor()) {
					// 実際のマップの障害物がある地点を割り出し、仮想マップにも障害物を置く
					map[y][x].barrier = true;
				}
				map[y][x].x = x;
				map[y][x].y = y;
			}
		}

		// スタート地点を格納
		this.start_x = start_x;
		this.start_y = start_y;
		map[this.start_y][this.start_x].start = true;

		// ゴール地点を格納
		this.goal_x = goal_x;
		this.goal_y = goal_y;
		map[this.goal_y][this.goal_x].goal = true;

		// 各ブロックのゴールまでの最短移動距離を算出・格納
		for (int y = 0; y < GameMap.MAP_HEIGHT; y++) {
			for (int x = 0; x < GameMap.MAP_WIDTH; x++) {
				map[y][x].expected = Math.abs(this.goal_x - x)
						+ Math.abs(goal_y - y);
			}
		}

		// スタート地点をオープン
		map[this.start_y][this.start_x].cost = 0;
		openList.add(map[this.start_y][this.start_x]);

		// 探索開始
		while (true) {
			// スタートからゴールまでの期待値が最小のものから順次探索する
			MapData now = getShortestOpenBlock();
			now.stamped = true;

			// 現在地点がゴールに隣接していたら探索終了
			if (now.x + 1 == this.goal_x && now.y == this.goal_y) {
				map[this.goal_y][this.goal_x].parent = now;
				break;
			} else if (now.x == this.goal_x && now.y + 1 == this.goal_y) {
				map[this.goal_y][this.goal_x].parent = now;
				break;
			} else if (now.x - 1 == this.goal_x && now.y == this.goal_y) {
				map[this.goal_y][this.goal_x].parent = now;
				break;
			} else if (now.x == this.goal_x && now.y - 1 == this.goal_y) {
				map[this.goal_y][this.goal_x].parent = now;
				break;
			}

			// 現在地点の四方のうち、進めるブロックをオープンリストに追加
			if (now.x + 1 < GameMap.MAP_WIDTH && !map[now.y][now.x + 1].barrier
					&& !map[now.y][now.x + 1].stamped) {
				map[now.y][now.x + 1].parent = now;
				map[now.y][now.x + 1].cost = now.cost + 1;
				openList.add(map[now.y][now.x + 1]);
			}
			if (now.x - 1 >= 0 && !map[now.y][now.x - 1].barrier
					&& !map[now.y][now.x - 1].stamped) {
				map[now.y][now.x - 1].parent = now;
				map[now.y][now.x - 1].cost = now.cost + 1;
				openList.add(map[now.y][now.x - 1]);
			}
			if (now.y + 1 < GameMap.MAP_HEIGHT
					&& !map[now.y + 1][now.x].barrier
					&& !map[now.y + 1][now.x].stamped) {
				map[now.y + 1][now.x].parent = now;
				map[now.y + 1][now.x].cost = now.cost + 1;
				openList.add(map[now.y + 1][now.x]);
			}
			if (now.y - 1 >= 0 && !map[now.y - 1][now.x].barrier
					&& !map[now.y - 1][now.x].stamped) {
				map[now.y - 1][now.x].parent = now;
				map[now.y - 1][now.x].cost = now.cost + 1;
				openList.add(map[now.y - 1][now.x]);
			}
		}

		// ゴールからスタートまでの道のりをマーキング
		List<MapData> list = new ArrayList<MapData>();
		// ゴール地点が障害物でないなら、ゴール地点まで向かう
		if(!map[this.goal_y][this.goal_x].barrier){
			list.add(0, map[this.goal_y][this.goal_x]);
			map[this.goal_y][this.goal_x].correct = true;
		}

		MapData parent = map[this.goal_y][this.goal_x].parent;
		while (true) {
			if (parent.start == false) {
				list.add(0, parent);
				parent.correct = true;
				parent = parent.parent;
			} else {
				break;
			}
		}

		return list;
	}

	private MapData getShortestOpenBlock() throws Exception {
		// 目的地へ辿り着く道がなければExceptionを投げる
		if (openList.size() == 0) {
			throw new Exception("目的地へ辿り着くルートはありません");
		}

		MapData shortest = null;
		int index = 0;

		for (int i = 0; i < openList.size(); i++) {
			if (shortest == null
					|| shortest.getPoint() >= openList.get(i).getPoint()) {
				shortest = openList.get(i);
				index = i;
			}
		}

		return openList.remove(index);
	}
}

/**
 * マップ情報
 */
class MapData {
	// マップX,Y座標
	int x, y;

	// スタート地点かどうか
	boolean start = false;

	// ゴール地点かどうか
	boolean goal = false;

	// 障害物かどうか
	boolean barrier = false;

	// 正解の軌道か否か
	boolean correct = false;

	// 探索済みかどうか
	boolean stamped = false;

	// スタート地点からの移動距離
	int cost;

	// ゴールまでの最短移動距離
	int expected;

	// スタートからゴールまでの最小期待値
	int getPoint() {
		return cost + expected;
	}

	// 親ブロック
	MapData parent = null;

	/**
	 * 初期化
	 */
	void Initialize() {
		this.x = 0;
		this.y = 0;
		this.start = false;
		this.goal = false;
		this.barrier = false;
		this.stamped = false;
		this.cost = 0;
		this.expected = 0;
		this.parent = null;
	}
}
