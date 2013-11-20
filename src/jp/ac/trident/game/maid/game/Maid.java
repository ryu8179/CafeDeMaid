package jp.ac.trident.game.maid.game;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.maid.GameSurfaceView;

public class Maid
{
	/**
	 * メイドの画像
	 */
	private Bitmap maid;

	/**
	 * HP
	 */
	private int hp;

	/**
	 * 力
	 */
	private int power;
	
	/**
	 * 知力
	 */
	public int intellectualpower; 
	
	/**
	 * 素早さ
	 */
	private int speed;
	
	/**
	 * ポーズフラグ
	 */
	private boolean pauseflag;
	
	/**
	 * ポーズのX座標
	 */
	private int pause_x[];
	
	/**
	 * ポーズのY座標
	 */
	private int pause_y[];
	
	/**
	 * 選択のX座標
	 */
	private int select_x[];
	
	/**
	 * 選択のY座標
	 */
	private int select_y[];
	
	/**
	 * ステータスアップフラグ
	 */
	public boolean statusupflag;
	
	/**
	 * ステータスシーン
	 */
	private boolean statusscene;

	/**
	 * コンストラクタ
	 * @param maidImg
	 */
	public Maid(Bitmap maidImg)
	{
		maid = maidImg;
		hp = 100;
		power = 10;
		intellectualpower = 3;
		speed = 5;
		pauseflag = false;
		pause_x = new int[10];
		pause_x[0] = 500;
		pause_x[1] = pause_x[0]+340;
		pause_y = new int[10];
		pause_y[0] = 300;
		pause_y[1] = pause_y[0]+200;
		select_x = new int[10];
		select_x[0] = 540;
		select_x[1] = select_x[0]+100;
		select_x[2] = 700;
		select_x[3] = select_x[2]+100;
		select_y = new int[10];
		select_y[0] = 380;
		select_y[1] = select_y[0]+80;
		select_y[2] = 380;
		select_y[3] = select_y[2]+80;
		statusupflag = false;
		statusscene = false;
	}
	
	/**
	 * 初期化
	 */
	public void Initialize()
	{
		pauseflag = false;
		statusupflag = false;
		statusscene = false;
	}
	
	/**
	 * 更新
	 * @param mouse_x
	 * @param mouse_y
	 */
	public void Update(float mouse_x, float mouse_y)
	{
		if(pauseflag == false)
		{
			if(mouse_x > 0 && mouse_x < 130)
			{
				if(mouse_y > 0 && mouse_y < 50)
				{
					pauseflag = true;
				}
			}
		}
		else
		{
			if(mouse_x > 710 && mouse_x < 800)
			{
				if(mouse_y > 390 && mouse_y < 450)
				{
					pauseflag = false;
				}
			}
		}
	}
	
	
	/**
	 * 知力更新
	 * @param rankA
	 * @param rankB
	 * @param rankC
	 * @param rankD
	 */
	public void UpdateIntellectualPower(boolean rankA,boolean rankB,boolean rankC,boolean rankD)
	{
		if(statusupflag == false)
		{
			if(rankA == true)
			{
				intellectualpower += 5;
			}
			else if(rankB == true)
			{
				intellectualpower += 3;
			}
			else if(rankC == true)
			{
				intellectualpower += 1;
			}
			else if(rankD == true)
			{
			}
			statusupflag = true;
		}
	}
	
	/**
	 * 力更新
	 * @param rankA
	 * @param rankB
	 * @param rankC
	 * @param rankD
	 */
	public void UpdatePower(boolean rankA,boolean rankB,boolean rankC,boolean rankD)
	{
		if(statusupflag == false)
		{
			if(rankA == true)
			{
				power += 5;
			}
			else if(rankB == true)
			{
				power += 3;
			}
			else if(rankC == true)
			{
				power += 1;
			}
			else if(rankD == true)
			{
			}
			statusupflag = true;
		}
	}
	
	/**
	 * 素早さ更新
	 * @param rankA
	 * @param rankB
	 * @param rankC
	 * @param rankD
	 */
	public void UpdateSpeed(boolean rankA,boolean rankB,boolean rankC,boolean rankD)
	{
		if(statusupflag == false)
		{
			if(rankA == true)
			{
				speed += 5;
			}
			else if(rankB == true)
			{
				speed += 3;
			}
			else if(rankC == true)
			{
				speed += 1;
			}
			else if(rankD == true)
			{
			}
			statusupflag = true;
		}
	}
	
	/**
	 * 描画
	 * @param sv
	 */
	public void Draw(GameSurfaceView sv)
	{
		if(pauseflag == false)
		{
//			sv.DrawText2("MENU", 0, 50, Color.BLACK);
//			sv.DrawImage(maid,520,200);
//			sv.DrawText2("HP"+hp, 200, 200, Color.BLACK);
//			sv.DrawText2("力"+power, 200, 250, Color.BLACK);
//			sv.DrawText2("知力"+intellectualpower, 200, 300, Color.BLACK);
//			sv.DrawText2("素早さ"+speed, 200, 350, Color.BLACK);
		}
		else
		{
//			sv.DrawText2("MENU", 0, 50, Color.BLACK);
//			sv.DrawImage(maid,520,200);
//			sv.DrawText2("HP"+hp, 200, 200, Color.BLACK);
//			sv.DrawText2("力"+power, 200, 250, Color.BLACK);
//			sv.DrawText2("知力"+intellectualpower, 200, 300, Color.BLACK);
//			sv.DrawText2("素早さ"+speed, 200, 350, Color.BLACK);
			sv.DrawRect(pause_x[0],pause_y[0],pause_x[1],pause_y[1],Color.rgb(255,255,204));
			sv.DrawText("ゲームを中断しますか？", 560, 340, Color.BLACK);
			sv.DrawRect(select_x[0],select_y[0],select_x[1],select_y[1],Color.GRAY);
			sv.DrawRect(select_x[2],select_y[2],select_x[3],select_y[3],Color.GRAY);
			sv.DrawText("YES", select_x[0]+30, select_y[0]+50, Color.RED);
			sv.DrawText("NO", select_x[2]+35, select_y[2]+50, Color.RED);
		}
	}
	

	/**
	 * ステータスシーン描画
	 * @param sv
	 */
	public void DrawStatusScene(GameSurfaceView sv)
	{
		sv.DrawImage(maid,600,200);
	}
	
	/**
	 * ポーズフラグを返す
	 */
	public boolean Return_PauseFlag()
	{
		return pauseflag;
	}
	
	/**
	 * パワーを上げる
	 * @param number
	 */
	public void PowerUp(int number)
	{
		power += number;
	}
	
	/**
	 * 知力を上げる
	 * @param number
	 */
	public void IntellectualPowerUp(int number)
	{
		intellectualpower += number;
	}
	
	/**
	 * 素早さを上げる
	 * @param number
	 */
	public void SpeedUp(int number)
	{
		speed += number;
	}
	
	/**
	 * ステータスシーンを点灯
	 */
	public void On_StatusScene()
	{
		statusscene = true;
	}
	
	/**
	 * HPを返す
	 */
	public int Return_HP()
	{
		return hp;
	}
	
	/**
	 * 力を返す
	 */
	public int Return_Power()
	{
		return power;
	}
}