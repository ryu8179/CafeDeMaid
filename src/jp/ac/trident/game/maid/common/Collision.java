/*
 *
 * Copyright (C) ${year} Ryugo Abe. All rights reserved.
 */
package jp.ac.trident.game.maid.common;

import android.graphics.Rect;

public class Collision {

	// 2次元座標系当たり判定
	/**
	 * 点と矩形の当たり判定
	 *
	 * @param v
	 *            点の座標ベクトル
	 * @param r
	 *            矩形の情報
	 *
	 * @return 当たっていればtrueを返す
	 */
	public static boolean pointRect(Vector2D v, Rect r) {
		if (v.x >= r.left && v.x <= r.right) {
			if (v.y >= r.top && v.y <= r.bottom) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 点と円の当たり判定
	 *
	 * @param v
	 *            点の座標ベクトル
	 * @param vc
	 *            円の中心座標ベクトル
	 * @param rc
	 *            円の半径
	 *
	 * @return 当たっていればtrueを返す
	 */
	public static boolean pointCircle(Vector2D v, Vector2D vc, float rc) {
		Vector2D vector = new Vector2D(v.x, v.y);
		vector.sub(vc);

		if (vector.length() <= rc) {
			return true;
		}
		return false;
	}

	/**
	 * 円と円のあたり判定
	 *
	 * @param v1
	 *            物体1の座標ベクトル
	 * @param r1
	 *            物体1の半径
	 * @param v2
	 *            物体2の座標ベクトル
	 * @param r2
	 *            物体2の半径
	 * @return
	 */
	public static boolean circleCircle(Vector2D v1, float r1, Vector2D v2,
			float r2) {
		if ((r1 + r2) * (r1 + r2) >= (v2.x - v1.x) * (v2.x - v1.x)
				+ (v2.y - v1.y) * (v2.y - v1.y)) {
			return true;
		}
		return false;
	}

}
