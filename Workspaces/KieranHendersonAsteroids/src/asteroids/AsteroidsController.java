/* AsteroidsController is the controller that is in charge of the game scene which means everything that happens after you hit play until you move to the death screen, it extends screens as it is one of the four other screen controllers */
/* Kieran Henderson */
/* 6/7/20 */

//My project is a recreation the classic arcade game asteroids from 1979, 
//there are some aspects of the game that may not be exactly how the original game worked as I have never played the original game but I did my best to include everything I found during research
//a more in depth description can be found at the bottom of the extras text file


package asteroids;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AsteroidsController extends Screens implements Initializable, ControlledScreen {
		
	//declaring all of my objects and variables 
	ArrayList<String> input; //variable containing player inputs
	
	ArrayList<Asteroid> asteroidList; //variable to hold the asteroids
	
	Player player; //player object
	 
	Player lifeDisplay; //the "player" object that I use to display the health the player has left 
	
	Enemy enemy; //the enemy object
	
	DisplayText displayScore; //the display score 
	
	ArrayList<Bullet> bulletList; //a list of all the players bullets
	
	ArrayList<Bullet> enemyBulletList; //a list of all the enemy bullets 
	
	int nextFreeLife = 10000; //at which score the player get an extra life
	
	/* variables used to track all the time related events in the game */
	long startTime; //time the program started 
	long deathTime = 0; //the time the player dies
	long time; //the current time
	long timeCompleted; //the time a level is completed
	int levelDelayTime = 2; //the time it takes for a new level to load 
	int deathDelayTime = 1; //the time it takes for the death screen to appear
	
	boolean reset = true; //variable to ensure the program only resets the variables once upon death so it is not constantly looping 
	
	//sound variables 
	double delay;
	double timeSinceSound;
	boolean lowBeat;
		
	
	@Override
	public void initialize(URL location, ResourceBundle resources) { //the method that is called when I start the program
		gc = gameCanvas.getGraphicsContext2D(); //set the gc
		delay = 1;
		lowBeat = true;
		
		reset(); //reset the game which will initialize all my variables 
		gameLoop(); //run the game loop
	}
	
	public void gameLoop() {
		
		new AnimationTimer() { //loop that loops once for every frame 
			
			@Override
			public void handle(long currentNanoTime) {	
				if(isGamePause() == false) { //if the game is not paused 
					if(gameScene != null) { //if the game scene is not null then I can check the input 
						checkInput();
					} else { //if it is null then I have to fix that and get teh scene
						getScene(stage);
					}
					
					time = (currentNanoTime / 1_000_000_000) - startTime; //update the game time 
					
					gc.setFill(Color.BLACK); 
					gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight()); //refresh the screen
					
					//update everything
					update();
					
					//draw everything
					draw();
					
					//check collision 				
					checkCollision();
					
					//check re-spawn area to make sure that the player wont die instantly on spawn and player has lives remaining and the death animation is finished for the last player
					if (player.dead == true && player.lifeTimer > player.respawnTime && Player.lives != 0 && player.safeToSpawn) {
						player = new Player(gc, gameCanvas, input, bulletList); //reset the player 
					}
					
					//check next level
					if(asteroidList.size() <= 0) { //if there are no asteroids on screen then call the next level
						 nextLevel();
					}

					//sounds
					//thrusters 
					if(input.contains("UP") && !player.isDead()) { //if the player is applying forward thrust then play the thrusters sound
						playSound("thrust.wav");
					}
					
					//back ground music 
					if (timeSinceSound + delay <= time) { //alternate between playing the low and high beat in the background with a 1 second delay
						if(lowBeat) {
							playSound("lowBeat.wav");
						} else {
							playSound("highBeat.wav");
						}
						lowBeat = !lowBeat;
						timeSinceSound = time;
					}					
				}
				
				else if (reset){ //if the game is paused and reset is true
					reset(); //call reset
				}
			}
		}.start();
	}
	
	private void reset() {
		reset = false; //reset is equal to false
		
		Asteroid.setStartAsteroids(4); //reset the starting number of asteroids
		Asteroid.setNumAsteroids(4); //reset the number of asteroids 
		asteroidList = new ArrayList<Asteroid>(); //asteroid array list that stores all the asteroid objects
		for (int i = 0; i < Asteroid.getStartAsteroids(); i++) { //creates a number of asteroid based of the number of asteroid variable in the asteroid class
			asteroidList.add(new Asteroid("big",gc,gameCanvas));
		}
		
		bulletList = new ArrayList<Bullet>(); //initialize the player bullet list
		
		enemyBulletList = new ArrayList<Bullet>(); //initialize the enemy bullet list
		
		input = new ArrayList<String>(); //initialize the array list to hold all the buttons that are being pressed 
		
		player = new Player(gc, gameCanvas, input, bulletList); //initialize the player
		Player.setLives(3); //reset the player lives 
		deathTime = 0;
		
		lifeDisplay = new Player(gc, gameCanvas); //initialize the life display player 
		
		enemy = new Enemy(gc,gameCanvas, enemyBulletList); //initialize the enemy 
		enemy.setActive(false); //set the enemy to inactive at the start 
		enemy.setMainMenu(false);
		
		displayScore = new DisplayText(gc, gameCanvas, 28); //initialize the display score 
						
		
		startTime = System.nanoTime()/1_000_000_000; //update the start time 
	}
	
	private void checkInput() {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>(){ //when a key is pressed 
			@Override
			public void handle(KeyEvent e) {

				String code = e.getCode().toString();
				if (!input.contains(code)) { //if the input array list does not contain the button that was pressed add it 
					input.add(code);
				} 
				if ("SPACE".equals(code) && player.dead == false) { //if the key pressed is space and the player isn't dead then shoot
					playSound("fire.wav");
					player.shoot();
				}
				if ("ALT".equals(code) && player.hyperspaceTimer > player.hyperspaceCooldown && player.dead == false) { //if the alt button is pushed and the player is alive and the hyperspace skill is off cool down then jump to hyper space
					player.hyperspace();
				}
			}
		});
		
		
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>(){ //when a key is released 
			@Override
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				if (input.contains(code)) { //if the key is in the input list then remove it 
					input.remove(code);
				}
			}
		});
	}

	private void checkCollision() { //method that checks the collision between all of the objects in the game 
		
		//player bullet collision with asteroids 
		for (int i = 0; i < bulletList.size(); i++) { //for each player bullet 
			Bullet b = bulletList.get(i); //Assign the bullet to a variable 
			
			for (int j = 0; j < asteroidList.size(); j++) { //for each asteroid 
				Asteroid a = asteroidList.get(j); //assign the asteroid to a variable 
				
				if(a.isActive() && a.collides(b.getX(), b.getY())) { //if the asteroid is active and it collides with the bullet
					
					SpaceObject.setScore(SpaceObject.getScore()+a.getScoreValue());; //add the value of the asteroid to the score
					
					bulletList.remove(i); //remove the bullet
					i--;
					
					breakAsteroid(a); //break the asteroid that got hit
					
					break;
				}
			}
		}
		
		//player bullet collision with player
			if(!player.isDead()) { //if the player isn't dead 
				
				for (int i = 0; i < bulletList.size(); i++) { //for each player bullet 
					Bullet b = bulletList.get(i); //Assign the bullet to a variable
					
					if(b.lifeTimer > 0.25 && player.collides(b.getX(), b.getY())) { //if the bullet has been alive long enough to have been shot by the player and it collides with the player 
						
						bulletList.remove(i);//remove the bullet
						i--;
						
						player.die();//player dies 
						
						break;
					}
				}
			}
	
		//player bullet collision with enemy
		if(enemy.isActive()) { //if the enemy is active
			
			for (int i = 0; i < bulletList.size(); i++) {//for each player bullet 
				Bullet b = bulletList.get(i); //Assign the bullet to a variable
				
				if(enemy.collides(b.getX(), b.getY())) { //if the enemy collides with a player bullet 
					
					SpaceObject.setScore(SpaceObject.getScore()+enemy.getScoreValue());; //add the value of the enemy to the score
					
					bulletList.remove(i); //remove the bullet
					i--;
					
					enemy.die(); //the enemy dies 
					
					break;
				}
			}
		}
		
		//enemy bullet collision with asteroids 
		for (int i = 0; i < enemyBulletList.size(); i++) { //for each enemy bullet
			Bullet b = enemyBulletList.get(i); //assign the bullet to a variable 
			
			for (int j = 0; j < asteroidList.size(); j++) { //for each asteroid 
				Asteroid a = asteroidList.get(j); //assign the asteroid to a variable 
				
				if(a.isActive() && a.collides(b.getX(), b.getY())) { // if the asteroid is active and collides with an enemy bullet 
					
					enemyBulletList.remove(i); //remove the bullet 
					i--;

					breakAsteroid(a); //break the asteroid 
					
					break;
				}
			}
		}
				
		//enemy bullet collision with player
		if(enemy.isActive()) { //if the enemy is active 
			for (int i = 0; i < enemyBulletList.size(); i++) { // for each bullet 
				Bullet b = enemyBulletList.get(i); //assign the bullet to a variable 
				
				if(player.collides(b.getX(), b.getY())) { //if the player collides with the bullet 
					
					enemyBulletList.remove(i); //remove the bullet 
					i--;
					
					player.die();//player dies
					
					break;
				}
			}
		}
		
		//ship collision with asteroids
		if(player.isDead() == false) { //if the player isn't dead
			for (int i = 0; i < asteroidList.size(); i++) { //for each asteroid 
				Asteroid a = asteroidList.get(i); //assign the asteroid to a variable 
				
				if (a.isActive() && a.hit(player)) { //if the asteroid is active and the player collides with it
					
					SpaceObject.setScore(SpaceObject.getScore()+a.getScoreValue());; //add the value of the asteroid to the score
					
					player.die(); //player dies 
					
					breakAsteroid(a); // break the asteroid 
				}
			}
		}
		
		//ship collision with enemy
		if(player.isDead() == false && enemy.isActive()) {//if the player ins't dead and the enemy is active 
			if (enemy.hit(player)) { //if the enemy hits the player 
				
				SpaceObject.setScore(SpaceObject.getScore()+enemy.getScoreValue());; //add the value of the enemy to the score
				
				player.die(); //enemy dies 
				enemy.die(); //player dies 
			}
		}
		
		
		//enemy collision with asteroids
		if (enemy.isActive()) {
			for (int i = 0; i < asteroidList.size(); i++) {
				Asteroid a = asteroidList.get(i);
				if (a.isActive() && a.hit(enemy)) {
					enemy.die();
//					Asteroid.numAsteroids--;
//					asteroidList.remove(i); 
//					i--;
					breakAsteroid(a);
				}
			}
		}
	}
	
	public void breakAsteroid(Asteroid a) {//the method called when an asteroid breaks
		a.explode(); //explode the asteroid 
				
		if(a.getSizeName() == "big") { //if the asteroids size is big then generate two medium asteroids on its position 
			playSound("bangLarge.wav");
			asteroidList.add(new Asteroid("med",gc,gameCanvas, a.getX(), a.getY()));
			asteroidList.add(new Asteroid("med",gc,gameCanvas, a.getX(), a.getY()));
		} 
		
		else if (a.getSizeName() == "med") { //if the asteroids size is medium then generate two small asteroids on its position 
			playSound("bangMedium.wav");
			asteroidList.add(new Asteroid("small",gc,gameCanvas, a.getX(), a.getY()));
			asteroidList.add(new Asteroid("small",gc,gameCanvas, a.getX(), a.getY()));
		} else {
			playSound("bangSmall.wav");
		}
		
		if(asteroidList.size() == 1) { //if the asteroid is the last one then set the completed time of the level to the current time 
			 timeCompleted = time;
		}
	}
	
	public void draw(){ //the method in charge of drawing everything on the screen
		
		player.draw(); //draw the player 
		
		lifeDisplay.draw(); //draw the life display
		
		enemy.draw(); //draw the enemy 
		
		for (int i = 0; i < bulletList.size(); i++) { //draw each bullet in the bullet list 
			bulletList.get(i).draw();
		}
		
		for (int i = 0; i < enemyBulletList.size(); i++) { //draw each bullet in the bullet list 
			enemyBulletList.get(i).draw();
		}
		
		for (int i = 0; i < Asteroid.getNumAsteroids(); i++) { //draw each asteroid in the asteroid list 
			asteroidList.get(i).draw();
		}

		
		displayScore.displayLeft(Integer.toString(SpaceObject.score), 20, 55); //draw the score 
		
	}
	
	public void update() {
		for (int i = 0; i < bulletList.size(); i++) { //update each player bullet in the player bullet list 
			bulletList.get(i).update();
			
			if(bulletList.get(i).isRemove()) {//if the bullet needs to be removed 
				bulletList.remove(i); //remove it 
				i--;
			}
		}
		
		for (int i = 0; i < enemyBulletList.size(); i++) { //update each enemy bullet in the enemy bullet list 
			enemyBulletList.get(i).update();
			
			if(enemyBulletList.get(i).isRemove()) {//if the bullet needs to be removed 
				enemyBulletList.remove(i);//remove it 
				i--;
			}
		}
		
		if(time > 1) { //adds a delay at the start of the game 
			for (int i = 0; i < Asteroid.getNumAsteroids(); i++) { //update each asteroid in the asteroid list 
				asteroidList.get(i).update();
	
				if(asteroidList.get(i).isRemove()) {  //if the asteroid needs to be removed 
					Asteroid.setNumAsteroids(Asteroid.getNumAsteroids()-1); //remove it
					asteroidList.remove(i);
					i--;
				}
			}
		}
		
		if (SpaceObject.getScore() >= nextFreeLife) { //if the score is at the point where you get a new life
			Player.setLives(Player.getLives()+1);;//add a life to the player 
			playSound("extraShip.wav");
			nextFreeLife+=10000; //update next free life
		}
		
		if(player.isDead()) { //if the player is dead 
			enemy.leaveScreen(); //have the enemy leave the screen
		}
		
		if(Player.getLives() == 0) { //if the player has no lives 
			if (deathTime == 0) {
				deathTime = time; //update the time of death
			} else if (deathTime + deathDelayTime < time) { //if the death time delay has passed then
				setGamePause(true);
				setDeathPause(false);
				Screens.setName("AAA");
				reset = true;
				controller.setScreen(Main.deathName, Main.deathFile); //change the screen to the death screen
			}
		}
		
		enemy.update(); //update the enemy
		
		if(player.isDead()) {
			checkSpawnArea(); //check the spawn area 
		}
		
		player.update(); //update the player 
		
		lifeDisplay.updateLives(); //update the life display 
		
		enemy.updateShotAngle(player); //update the shot angle between the enemy and the player 
	}
	
	public void checkSpawnArea(){ //method used to ensure the spawn location is clear for the player so the player doesn't die on spawn 
		//define a 250 x 250 square around the center of the screen
		double xMin = gameCanvas.getWidth()/2 - 125;
		double xMax = gameCanvas.getWidth()/2 + 125;
		double yMin = gameCanvas.getHeight()/2 - 125;
		double yMax = gameCanvas.getHeight()/2 + 125;
		
		//boolean to track if the area is clear 
		boolean clear = true;
		
		for (int i = 0; i < asteroidList.size(); i++) { //for each asteroid 
			if ((xMax > asteroidList.get(i).x && asteroidList.get(i).x > xMin) && (yMax > asteroidList.get(i).y && asteroidList.get(i).y > yMin)) { //if the asteroid is in the spawn area
				clear = false;
			}
		}
		
		for (int i = 0; i < enemyBulletList.size(); i++){ //for each enemy bullet  
			if ((xMax > enemyBulletList.get(i).x && enemyBulletList.get(i).x > xMin) && (yMax > enemyBulletList.get(i).y && enemyBulletList.get(i).y > yMin)) { //if the bullet is in the spawn area
				clear = false;
			}
		}
		
		for (int i = 0; i < bulletList.size(); i++){ //for each player bullet 
			if ((xMax > bulletList.get(i).x && bulletList.get(i).x > xMin) && (yMax > bulletList.get(i).y && bulletList.get(i).y > yMin)) { //if the bullet is in the spawn area
				clear = false;
			}
		}
		
		if ((xMax > enemy.x && enemy.x > xMin) && (yMax > enemy.y && enemy.y > yMin)) { //if the enemy is in the spawn area
			clear = false;
		}
		
		player.setSafeToSpawn(clear); //update the player safe to spawn variable 
		
		//Visual representation of the safe box
//		gc.strokeLine(xMin, yMin, xMax, yMin);
//		gc.strokeLine(xMin, yMin, xMin, yMax);
//		gc.strokeLine(xMin, yMax, xMax, yMax);
//		gc.strokeLine(xMax, yMax, xMax, yMin);

	}
	
	public void nextLevel() { //method to load the next level
		if (timeCompleted + levelDelayTime <= time) { //if the delay amount between levels has passed 
			Asteroid.setStartAsteroids(Asteroid.getStartAsteroids()+1);//increase the amount of asteroids at the start of the level by one 
			for (int i = 0; i < Asteroid.startAsteroids; i++) { //creates a number of asteroid based of the number of asteroid variable in the asteroid class
				asteroidList.add(new Asteroid("big",gc,gameCanvas)); 
				Asteroid.setNumAsteroids(Asteroid.numAsteroids+1); //populate the asteroid list 
			}
		}
	}
	
	private void playSound(String sound) {
		String file = "/sounds/" + sound;
		AudioClip soundEffect = new AudioClip(this.getClass().getResource(file).toString());
		soundEffect.play();
	}

	public void setScreenParent(ScreenController screenPage) {  //set the screen parent 
		controller = screenPage;
	}

	@Override
	public void getScene(Stage primaryStage) { //get the scene
		gameScene = primaryStage.getScene();
	}	
}