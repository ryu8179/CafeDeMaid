package jp.ac.trident.game.maid.game;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.maid.GameSurfaceView;

public class Game
{
	/**
	 * グー
	 */
	private Bitmap goo;
	
	/**
	 * チョキ
	 */
	private Bitmap cyoki;
	
	/**
	 * パー
	 */
	private Bitmap par;
	
	/**
	 * グーフラグ
	 */
	private boolean gooflag;
	
	/**
	 * チョキフラグ
	 */
	private boolean cyokiflag;
	
	/**
	 * パーフラグ
	 */
	private boolean parflag;
	
	/**
	 * ランダム
	 */
    private Random rnd;
    
    /**
     * 数値
     */
    private int number;
    
    /**
     * 時間
     */
    private int timer;
    
    /**
     * お金
     */
    private int money;
    
    /**
     * お金フラグ
     */
    private boolean moneyflag;
	
	/**
	 * コンストラクタ
	 * @param gooImg
	 * @param cyokiImg
	 * @param parImg
	 */
	public Game(Bitmap gooImg,Bitmap cyokiImg,Bitmap parImg)
	{
		goo = gooImg;
		cyoki = cyokiImg;
		par = parImg;
		gooflag = false;
		cyokiflag = false;
		parflag = false;
		rnd = new Random();
		number = 0;
		timer = 50;
		money = 2000;
		moneyflag = false;
	}
	
	/**
	 * 初期化
	 */
	public void Initialize()
	{
		gooflag = false;
		cyokiflag = false;
		parflag = false;
		rnd = new Random();
		number = 0;
		timer = 50;
		moneyflag = false;
	}
	
	/**
	 * 更新
	 * @param mouse_x
	 * @param mouse_y
	 */
	public void Update(float mouse_x,float mouse_y)
	{
		if(gooflag == false && cyokiflag == false && parflag == false)
		{
			if(mouse_x > 310 && mouse_x < 370)
			{
				if(mouse_y > 310 && mouse_y < 360)
				{
					number = rnd.nextInt(3);
					gooflag = true;
				}
			}
			if(mouse_x > 460 && mouse_x < 540)
			{
				if(mouse_y > 310 && mouse_y < 360)
				{
					number = rnd.nextInt(3);
					cyokiflag = true;
				}
			}
			if(mouse_x > 620 && mouse_x < 680)
			{
				if(mouse_y > 310 && mouse_y < 360)
				{
					number = rnd.nextInt(3);
					parflag = true;
				}
			}
		}
		if(gooflag == true || cyokiflag == true || parflag == true)
		{
			timer --;
		}
	}
		
	/**
	 * 描画
	 * @param sv
	 */
	public void Draw(GameSurfaceView sv)
	{
//		if(gooflag == false && cyokiflag == false && parflag == false)
//		{
//			sv.DrawImage(goo,300,100);
//			sv.DrawImage(cyoki,450,100);
//			sv.DrawImage(par,600,100);
//			
//			sv.DrawImage(goo,300,300);
//			sv.DrawImage(cyoki,450,300);
//			sv.DrawImage(par,600,300);
//		}
//		if(gooflag == true)
//		{
//			timer --;
//			sv.DrawImage(goo,460,300);
//			if(number == 0)
//			{
//				sv.DrawImage(goo,460,100);
//				//sv.DrawText2("あいこ",610,250,Color.BLACK);
//			}
//			else if(number == 1)
//			{
//				sv.DrawImage(cyoki,450,100);
//				//sv.DrawText2("かち",610,250,Color.BLACK);
//				if(moneyflag == false)
//				{
//					money += 100;
//					moneyflag = true;
//				}
//			}
//			else
//			{
//				sv.DrawImage(par,450,100);
//				sv.DrawText2("まけ",610,250,Color.BLACK);
//				if(moneyflag == false)
//				{
//					moneyflag = true;
//				}
//			}
//		}
//		if(cyokiflag == true)
//		{
//			timer --;
//			sv.DrawImage(cyoki,450,300);
//			if(number == 0)
//			{
//				sv.DrawImage(goo,460,100);
//				sv.DrawText2("まけ",610,250,Color.BLACK);
//				if(moneyflag == false)
//				{
//					moneyflag = true;
//				}
//			}
//			else if(number == 1)
//			{
//				sv.DrawImage(cyoki,450,100);
//				sv.DrawText2("あいこ",610,250,Color.BLACK);
//			}
//			else
//			{
//				sv.DrawImage(par,450,100);
//				sv.DrawText2("かち",610,250,Color.BLACK);
//				if(moneyflag == false)
//				{
//					money += 100;
//					moneyflag = true;
//				}
//			}
//		}
//		if(parflag == true)
//		{
//			timer --;
//			sv.DrawImage(par,450,300);
//			if(number == 0)
//			{
//				sv.DrawImage(goo,460,100);
//				sv.DrawText2("かち",610,250,Color.BLACK);
//				if(moneyflag == false)
//				{
//					money += 100;
//					moneyflag = true;
//				}
//			}
//			else if(number == 1)
//			{
//				sv.DrawImage(cyoki,450,100);
//				sv.DrawText2("まけ",610,250,Color.BLACK);
//				if(moneyflag == false)
//				{
//					moneyflag = true;
//				}
//			}
//			else
//			{
//				sv.DrawImage(par,450,100);
//				sv.DrawText2("あいこ",610,250,Color.BLACK);
//			}
//		}
	}
	
	/**
	 * 時間を返す
	 */
	public int Return_Timer()
	{
		return timer;
	}
	
	/**
	 * お金を返す
	 */
	public int Return_Money()
	{
		return money;
	}
	
	/**
	 * ランクを表示
	 * @param sv
	 */
	public void Draw_Money(GameSurfaceView sv)
	{
		if(money >= 0 && money <= 1000)
		{
			sv.DrawText("★",250, 20, Color.BLACK);
		}
		else if(money >= 1100 && money <= 2000)
		{
			sv.DrawText("★★",250, 20, Color.BLACK);
		}
		else if(money >= 2100 && money <= 3000)
		{
			sv.DrawText("★★★",250, 20, Color.BLACK);
		}
		else if(money >= 3100 && money <= 4000)
		{
			sv.DrawText("★★★★",250, 20, Color.BLACK);
		}
		else
		{
			sv.DrawText("★★★★★",250, 20, Color.BLACK);
		}
	}
	
	/**
	 * お金を加算
	 */
	public void MoneyUp(int buttle_timer,int maid_power,int guest_power)
	{
		if(maid_power > guest_power)
		{
			if(moneyflag ==false)
			{
				if(buttle_timer < 20)
				{
					money += 100;
					moneyflag = true;
				}
			}
		}
	}
	
	/**
	 * お金を減算
	 */
	public void MoneyDown(int buttle_timer,int maid_power,int guest_power)
	{
		if(maid_power > guest_power)
		{
			if(moneyflag ==false)
			{
				if(buttle_timer < 20)
				{
					money -= 100;
					moneyflag = true;
				}
			}
		}
	}
}
