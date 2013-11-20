package jp.ac.trident.game.maid.game;

import jp.ac.trident.game.maid.game.Maid;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.maid.GameSurfaceView;

public class Table
{
	/**
	 * テーブルの画像
	 */
	private Bitmap table;
	
	/**
	 * 雑巾の画像
	 */
	private Bitmap floorcloth; 
	
	/**
	 * 時間
	 */
	private int time;
	
	/**
	 * ヒットフラグ
	 */
	private boolean hitflag[];
	
	/**
	 * ランクフラグ
	 */
	public boolean rankflag;
	
	/**
	 * 汚れのX座標
	 */
	private int dirt_x[];
	
	/**
	 * 汚れのY座標
	 */
	private int dirt_y[];
	
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
	 * 掃除カウント
	 */
	private int cleaningcount;
	
	/**
	 * カウント
	 */
	private int count;
	
	/**
	 * Aランクフラグ
	 */
	public boolean rankflag_A;
	
	/**
	 * Bランクフラグ
	 */
	private boolean rankflag_B;
	
	/**
	 * Cランクフラグ
	 */
	private boolean rankflag_C;
	
	/**
	 * Dランクフラグ
	 */
	private boolean rankflag_D;
	
	
	/**
	 * コンストラクタ
	 * @param tableImg
	 * @param floorclothImg
	 */
	public Table(Bitmap tableImg,Bitmap floorclothImg)
	{
		table = tableImg;
		floorcloth = floorclothImg;
		hitflag = new boolean[10];
		dirt_x = new int[10];
		dirt_x[0] = 220;
		dirt_x[1] = dirt_x[0]+40;
		dirt_x[2] = 320;
		dirt_x[3] = dirt_x[2]+60;
		dirt_x[4] = 220;
		dirt_x[5] = dirt_x[4]+40;
		dirt_x[6] = 420;
		dirt_x[7] = dirt_x[6]+40;
		dirt_x[8] = 400;
		dirt_x[9] = dirt_x[8]+60;
		dirt_y = new int[10];
		dirt_y[0] = 120;
		dirt_y[1] = dirt_y[0]+60;
		dirt_y[2] = 140;
		dirt_y[3] = dirt_y[2]+60;
		dirt_y[4] = 200;
		dirt_y[5] = dirt_y[4]+60;
		dirt_y[6] = 200;
		dirt_y[7] = dirt_y[6]+60;
		dirt_y[8] = 250;
		dirt_y[9] = dirt_y[8]+60;
		time = 10;
		for(int i=0; i<10; i++)
		{
			hitflag[i] = false;
		}
		rankflag = false;
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
		cleaningcount = 0;
		count = 0;
		rankflag_A = false;
		rankflag_B = false;
		rankflag_C = false;
		rankflag_D = false;
	}
	
	/**
	 * 初期化
	 */
	public void Initialize()
	{
		time = 10;
		for(int i=0; i<10; i++)
		{
			hitflag[i] = false;
		}
		rankflag = false;
		pauseflag = false;
		cleaningcount = 0;
		count = 0;
		rankflag_A = false;
		rankflag_B = false;
		rankflag_C = false;
		rankflag_D = false;
	}
	
	/**
	 * 更新
	 * @param mouse_x
	 * @param mouse_y
	 * @param maid
	 */
	public void Update(float mouse_x,float mouse_y,Maid maid)
	{
		if(pauseflag == false)
		{
			if(rankflag == false)
			{
				count ++;
				if(count%30== 0)
				{
					time --;
				}
				UpdateDirt(mouse_x,mouse_y);
			}
			if(mouse_x > 0 && mouse_x < 130)
			{
				if(mouse_y > 0 && mouse_y < 50)
				{
					pauseflag = true;
				}
			}
			if(time == 0)
			{
				rankflag = true;
			}
			if(cleaningcount == 5)
			{
				rankflag = true;
			}
			if(rankflag == true)
			{
				if(cleaningcount == 5)
				{
					rankflag_A = true;
				}
				else if(cleaningcount == 4)
				{
					rankflag_B = true;
				}
				else if(cleaningcount == 3)
				{
					rankflag_B = true;
				}
				else if(cleaningcount == 2)
				{
					rankflag_C = true;
				}
				else if(cleaningcount == 1)
				{
					rankflag_D = true;
				}
				else
				{
					rankflag_D = true;
				}
				maid.UpdateSpeed(rankflag_A,rankflag_B,rankflag_C,rankflag_D);
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
	 * 描画
	 * @param sv
	 * @param mouse_x
	 * @param mouse_y
	 */
	public void Draw(GameSurfaceView sv,float mouse_x,float mouse_y)
	{
		if(pauseflag == false)
		{
			//sv.DrawText2("MENU", 0, 50, Color.BLACK);
			//sv.DrawText2(""+time, 640, 140, Color.BLACK);
			sv.DrawImage(table,200,100);
//			sv.DrawImage(table,480,260);
			DrawDirt(sv);
			sv.DrawImage(floorcloth,(int)mouse_x,(int)mouse_y);
			if(rankflag == true)
			{
				if(cleaningcount == 5)
				{
					//sv.DrawText2("A", 650, 240, Color.RED);
				}
				else if(cleaningcount == 4)
				{
					//sv.DrawText2("B", 650, 240, Color.GREEN);
				}
				else if(cleaningcount == 3)
				{
					//sv.DrawText2("B", 650, 240, Color.GREEN);
				}
				else if(cleaningcount == 2)
				{
					//sv.DrawText2("C", 650, 240, Color.YELLOW);
				}
				else if(cleaningcount == 1)
				{
					//sv.DrawText2("D", 650, 240, Color.BLUE);
				}
				else
				{
					//sv.DrawText2("D", 650, 240, Color.BLUE);
				}
			}
		}
		else
		{
			//sv.DrawText2("MENU", 0, 50, Color.BLACK);
			//sv.DrawText2(""+time, 640, 140, Color.BLACK);
			sv.DrawImage(table,480,260);
			DrawDirt(sv);
			sv.DrawImage(floorcloth,(int)mouse_x,(int)mouse_y);
			if(rankflag == true)
			{
				if(cleaningcount == 5)
				{
					//sv.DrawText2("A", 650, 240, Color.RED);
				}
				else if(cleaningcount == 4)
				{
					//sv.DrawText2("B", 650, 240, Color.GREEN);
				}
				else if(cleaningcount == 3)
				{
					//sv.DrawText2("B", 650, 240, Color.GREEN);
				}
				else if(cleaningcount == 2)
				{
					//sv.DrawText2("C", 650, 240, Color.YELLOW);
				}
				else if(cleaningcount == 1)
				{
					//sv.DrawText2("D", 650, 240, Color.BLUE);
				}
				else
				{
					//sv.DrawText2("D", 650, 240, Color.BLUE);
				}
			}
			sv.DrawRect(pause_x[0],pause_y[0],pause_x[1],pause_y[1],Color.rgb(255,255,204));
			sv.DrawText("ゲームを中断しますか？", 560, 340, Color.BLACK);
			sv.DrawRect(select_x[0],select_y[0],select_x[1],select_y[1],Color.GRAY);
			sv.DrawRect(select_x[2],select_y[2],select_x[3],select_y[3],Color.GRAY);
			sv.DrawText("YES", select_x[0]+30, select_y[0]+50, Color.RED);
			sv.DrawText("NO", select_x[2]+35, select_y[2]+50, Color.RED);
		}
	}
	
	/**
	 * 時間を返す
	 */
	public int Return_Time()
	{
		return time;
	}
	
	/**
	 * ポーズフラグを返す
	 */
	public boolean Return_PauseFlag()
	{
		return pauseflag;
	}
	
	/**
	 * 当たり判定
	 * @param mouse_x
	 * @param mouse_y
	 * @param number1
	 * @param number2
	 * @param number3
	 * @param number4
	 * @return
	 */
	public boolean Hit(float mouse_x,float mouse_y,float number1,float number2,float number3,float number4)
	{
		if(mouse_x > number1 && mouse_x < number2)
		{
			if(mouse_y > number3 && mouse_y < number4)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 汚れ更新
	 * @param mouse_x
	 * @param mouse_y
	 */
	public void UpdateDirt(float mouse_x,float mouse_y)
	{
		if(Hit(mouse_x,mouse_y,dirt_x[0],dirt_x[1],dirt_y[0],dirt_y[1]))
		{
			if(hitflag[0] == false)
			{
				cleaningcount ++;
			}
			hitflag[0] = true;
		}
		if(Hit(mouse_x,mouse_y,dirt_x[2],dirt_x[3],dirt_y[2],dirt_y[3]))
		{
			if(hitflag[1] == false)
			{
				cleaningcount ++;
			}
			hitflag[1] = true;
		}
		if(Hit(mouse_x,mouse_y,dirt_x[4],dirt_x[5],dirt_y[4],dirt_y[5]))
		{
			if(hitflag[2] == false)
			{
				cleaningcount ++;
			}
			hitflag[2] = true;
		}
		if(Hit(mouse_x,mouse_y,dirt_x[6],dirt_x[7],dirt_y[6],dirt_y[7]))
		{
			if(hitflag[3] == false)
			{
				cleaningcount ++;
			}
			hitflag[3] = true;
		}
		if(Hit(mouse_x,mouse_y,dirt_x[8],dirt_x[9],dirt_y[8],dirt_y[9]))
		{
			if(hitflag[4] == false)
			{
				cleaningcount ++;
			}
			hitflag[4] = true;
		}
		if(hitflag[0] == true && hitflag[1] == true && hitflag[2] == true && hitflag[3] == true && hitflag[4] == true)
		{
			rankflag = true;
		}
	}
	
	/**
	 * 汚れ描画
	 * @param sv
	 */
	public void DrawDirt(GameSurfaceView sv)
	{
		if(hitflag[0] == false)
		{
			sv.DrawRect(dirt_x[0],dirt_y[0],dirt_x[1],dirt_y[1],Color.BLACK);
		}
		if(hitflag[1] == false)
		{
			sv.DrawRect(dirt_x[2],dirt_y[2],dirt_x[3],dirt_y[3],Color.BLACK);
		}
		if(hitflag[2] == false)
		{
			sv.DrawRect(dirt_x[4],dirt_y[4],dirt_x[5],dirt_y[5],Color.BLACK);
		}
		if(hitflag[3] == false)
		{
			sv.DrawRect(dirt_x[6],dirt_y[6],dirt_x[7],dirt_y[7],Color.BLACK);
		}
		if(hitflag[4] == false)
		{
			sv.DrawRect(dirt_x[8],dirt_y[8],dirt_x[9],dirt_y[9],Color.BLACK);
		}
	}
}
