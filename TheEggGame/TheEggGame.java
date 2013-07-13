import ucigame.*;

public class TheEggGame extends Ucigame{

	/*
	 * ALFRED WONG
	 * ID:60579424
	 */
	final int MAX_CHICKENS = 10;
	final int GOAL_FRAMERATE = 100;

	boolean usePixelPerfect = true;
	Sprite display;

	Sprite borders;

	Sprite farmerBasket;
	//Sprite farmerKnife;
	//boolean knife = false; // keeps track of the state of farmer
	//int killAllow = 0; // needs to be 5 to allow knife to activate cannot stack

	Sprite egg;

	// statistics purposes
	//int chickensKilled = 0;
	//int drumsticksEaten = 0;
	int eggsCollected; 
	int highScoreEggsCollected;
	
	Image eggImage = getImage("images/egg.png",255,0,0);

	Chicken[] chickensRight = new Chicken[MAX_CHICKENS];

	public void setup(){
		window.size(640, 480);
		window.title("The Egg Game");
		framerate(GOAL_FRAMERATE);
		window.showFPS();

		canvas.background(0,175,0);

		borders = makeSprite(getImage("images/borders.png",255,0,0));
		
		farmerBasket = makeSprite(getImage("images/farmerBasket.png", 255, 0, 0));
		//farmerKnife = makeSprite(getImage("images/farmerKnife.png", 255, 0, 0));
		//farmerBasket.pin(farmerKnife, 0, 0);

		farmerBasket.position(32, 96);

		egg = new Egg(eggImage);

		Image chickenRightImage = getImage("images/chickenRight.png", 0, 0, 0);
		for (int i=0; i<MAX_CHICKENS; i++)
			chickensRight[i] = new Chicken(chickenRightImage);

		//reset statistics
		//chickensKilled = 0;
		//drumsticksEaten = 0;
		eggsCollected = 0;
		highScoreEggsCollected = 0;
		
		//reset situation
		//knife = false;
		//killAllow = 0;
		
		startScene("NewGame");
	}

	public void drawNewGame()
	{
		if(eggsCollected > highScoreEggsCollected)
			highScoreEggsCollected = eggsCollected;
		canvas.clear();
		borders.draw();
		
		borders.font("Arial", BOLD, 20, 255, 255, 255);
		borders.putText("Eggs Collected: " + eggsCollected + "                                   High Score: " + highScoreEggsCollected, 32, 64);
		
		borders.putText("Stay away from the angry chickens as you steal their eggs!", 40, 120);
		borders.putText("Use Arrow Keys to move", 100, 150);
		borders.putText("To start a new game press 'N'", 100, 180);
		
		farmerBasket = makeSprite(getImage("images/farmerBasket.png", 255, 0, 0));
		//farmerKnife = makeSprite(getImage("images/farmerKnife.png", 255, 0, 0));
		//farmerBasket.pin(farmerKnife, 0, 0);

		farmerBasket.position(32, 96);

		egg = new Egg(eggImage);

		Image chickenRightImage = getImage("images/chickenRight.png", 0, 0, 0);
		for (int i=0; i<MAX_CHICKENS; i++)
			chickensRight[i] = new Chicken(chickenRightImage);

		if (keyboard.isDown(keyboard.N)){
			eggsCollected = 0;
			startScene("Game");
		}
	}
	public void drawGame() {
		canvas.clear();
		borders.draw();
		
		borders.font("Arial", BOLD, 20, 255, 255, 255);
		borders.putText("Eggs Collected: " + eggsCollected + "                         High Score: " + highScoreEggsCollected, 32, 64);
		
		farmerBasket.stopIfCollidesWith(borders,PIXELPERFECT);

		
		//farmerBasket = makeSprite(getImage("images/egg.png",255,0,0));
		//farmerBasket.draw();
		
		for (int i=0; i<MAX_CHICKENS; i++)
		{
			farmerBasket.checkIfCollidesWith(chickensRight[i],PIXELPERFECT);
			if(farmerBasket.collided())
			{
				startScene("NewGame");
			}
		}
		
		farmerBasket.checkIfCollidesWith(egg,PIXELPERFECT);
		if(farmerBasket.collided())
		{
			eggsCollected++;
			egg = new Egg(eggImage);
			for(int i=0; i<MAX_CHICKENS; i++)
				chickensRight[i].incrementMovement();
		}
		
		for (int i=0; i<MAX_CHICKENS; i++)
			chickensRight[i].move();

		for (int i=0; i<MAX_CHICKENS; i++)
		{
			chickensRight[i].bounceIfCollidesWith(borders, PIXELPERFECT);
			for (int j=i+1; j<MAX_CHICKENS; j++)
			{
				chickensRight[i].bounceIfCollidesWith(chickensRight[j], PIXELPERFECT);
			}
		}

		for (int i=0; i<MAX_CHICKENS; i++)
			chickensRight[i].draw();

		farmerBasket.draw();
		egg.draw();
	}

	class Chicken extends Sprite
	{
		Chicken(Image image)
		{
			super(image);
			position(random(96, (canvas.width()-32) - width()),
					random(160, (canvas.height()-32) - height()));
			motion(0.1,0.1);
		}

		public void incrementMovement()
		{
			double x = eggsCollected*0.1;
			motion(random(-x,x),random(-x,x),ADD);
		}
	}

	class Egg extends Sprite
	{
		Egg(Image image)
		{
			super(image);
			position(random(96, (canvas.width()-32) - width()),
					random(160, (canvas.height()-32) - height()));
		}
	}

	public void onKeyPress()
	{
		if (keyboard.isDown(keyboard.UP))
		{
			farmerBasket.nextY(farmerBasket.y() - 2);
			farmerBasket.nextX(farmerBasket.x());
		}
		if (keyboard.isDown(keyboard.DOWN))
		{
			farmerBasket.nextY(farmerBasket.y() + 2);
			farmerBasket.nextX(farmerBasket.x());
		}
		if (keyboard.isDown(keyboard.LEFT))
		{
			farmerBasket.nextX(farmerBasket.x() - 2);
			farmerBasket.nextY(farmerBasket.y());
		}
		if (keyboard.isDown(keyboard.RIGHT))
		{
			farmerBasket.nextX(farmerBasket.x() + 2);
			farmerBasket.nextY(farmerBasket.y());
		}
	}
}
