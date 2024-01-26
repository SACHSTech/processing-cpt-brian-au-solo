import processing.core.PApplet;
import processing.core.PImage;

/**
 * Slicing fruit game
 * You must slice fruits and avoid slicing bombs to score points.
 */
public class Sketch extends PApplet {

  // Images 
  PImage imgStartTitle;
  PImage imgBackground;
  PImage imgMisses;
  PImage imgGameOver;
  PImage imgKatana;
  PImage imgKatanaSlice;
  PImage imgFruit1;
  PImage imgFruit2;
  PImage imgBomb;

  // Game screen, animation variables
  boolean blnStartTitle = true;
  boolean blnGameOver = false;
  boolean blnKatanaSlice = false;

  // more variables
  int intScore = 0;
  int intFruitSpeed = 3;
  int intFruitSpeed2 = 4;
  int intLives = 3;
  int katanaSliceFrame = -1;

  // Arrays to store fruit, bomb positions and their states
  float[] fltFruitY = new float[5];
  float[] fltFruitX = new float[5];
  float[] fltFruitY2 = new float[5];
  float[] fltFruitX2 = new float[5];
  float[] fltBombX = new float[3];
  float[] fltBombY = new float[3];
  boolean[] blnFruitHide = new boolean[5];
  boolean[] blnFruitHide2 = new boolean[5];
  boolean[] blnBombHide = new boolean[3];

  /**
   * screen size
   */
  public void settings() {
    size(1000, 800);
  }

  /**
   * Load images, initialize arrays, initialize game.
   */
  public void setup() {
    // Load images and resize them
    imgStartTitle = loadImage("StartTitle.jpg");
    imgStartTitle.resize(1000, 800);

    imgGameOver = loadImage("GameOver.jpg");
    imgGameOver.resize(1000, 800);

    imgBackground = loadImage("background.jpg");
    imgBackground.resize(1000, 800);

    imgKatana = loadImage("katana.png");
    imgKatana.resize(120, 120);
    cursor(imgKatana);

    imgKatanaSlice = loadImage("katanaslice.png");
    imgKatanaSlice.resize(80, 80);

    imgFruit1 = loadImage("apple.png");
    imgFruit1.resize(80, 80);

    imgFruit2 = loadImage("watermelon.png");
    imgFruit2.resize(80, 80);

    imgBomb = loadImage("bomb.png");
    imgBomb.resize(80, 80);

    imgMisses = loadImage("heart.png");

    // Initialize fruit, bomb positions and their states
    initializeFruitPositions();
  }

  /**
   * Game screen
   */
  public void draw() {
    background(255);

    if (blnStartTitle) {
      image(imgStartTitle, 0, 0);
    } else if (blnGameOver) {
      image(imgGameOver, 0, 0);
      displayScore();
    } else {
      image(imgBackground, 0, 0);
      drawGameElements();
    }
  }

  /**
   * player score
   */
  public void displayScore() {
    textSize(32);
    fill(255, 0, 0);
    textAlign(CENTER, CENTER);
    textFont(createFont("Arial Bold", 40));
    text("Your score: " + intScore, width / 2, height / 2 - 120);
    text("Click to Play Again", width / 2, height / 2);
  }

  /**
   * Game elements such as fruits, bombs, lives, and katana animation
   */
  public void drawGameElements() {
    drawAndMoveFruits();
    drawLives();
    drawAndMoveBombs();
    playKatanaSliceAnimation();
  }

  /**
   * Draws, moves the fruits on the screen
   */
  public void drawAndMoveFruits() {
    for (int i = 0; i < fltFruitX.length; i++) {
      if (!blnFruitHide[i]) {
        image(imgFruit1, fltFruitX[i], fltFruitY[i], 80, 80);
      }
      fltFruitY[i] += intFruitSpeed;
  
      // Reset position and lose life when fruit goes below the screen
      if (fltFruitY[i] > height && !blnFruitHide[i]) {
        intLives--;
        resetFruit(i);
        blnFruitHide[i] = false;
      }
  
      if (intLives != 0 && checkFruitCollision(fltFruitX[i], fltFruitY[i], 25)) {
        blnFruitHide[i] = true;
        intScore += 10;
      }
    }
  
    for (int i = 0; i < fltFruitX2.length; i++) {
      if (!blnFruitHide2[i]) {
        image(imgFruit2, fltFruitX2[i], fltFruitY2[i], 80, 80);
      }
      fltFruitY2[i] += intFruitSpeed2;
  
      // Reset position and lose life when fruit goes below the screen
      if (fltFruitY2[i] > height && !blnFruitHide2[i]) {
        intLives--;
        resetFruit2(i);
        blnFruitHide2[i] = false;
      }
  
      if (intLives != 0 && checkFruitCollision(fltFruitX2[i], fltFruitY2[i], 25)) {
        blnFruitHide2[i] = true;
        intScore += 20;
      }
    }
  
    decreaseLivesOnMiss();
  
    // Check if lives are zero and show game over
    if (intLives == 0) {
      blnStartTitle = false;
      blnGameOver = true;
    }
  }

  /**
   * Checks for collision between the katana and fruits
   * @param fruitX fruit X-coordinate
   * @param fruitY fruit Y-coordinate
   * @param collisionRadius radius collision
   * @return True if collision is detected, false otherwise
   */
  public boolean checkFruitCollision(float fruitX, float fruitY, int collisionRadius) {
    return dist(fruitX, fruitY, mouseX, mouseY) <= collisionRadius;
  }

  /**
   * Resets apple 
   * @param index apple reset
   */
  public void resetFruit(int index) {
    blnFruitHide[index] = false;
    fltFruitY[index] = random(-200);
    fltFruitX[index] = random(width);
  }

  /**
   * Resets watermelon 
   * @param index watermelon reset
   */
  public void resetFruit2(int index) {
    blnFruitHide2[index] = false;
    fltFruitY2[index] = random(-100);
    fltFruitX2[index] = random(width);
  }

  /**
 * Subtracts player lives if fruit goes below screen
 */
public void decreaseLivesOnMiss() {
    for (int i = 0; i < fltFruitY.length; i++) {
    if (!blnFruitHide[i] && fltFruitY[i] > height) {
      intLives--;
      resetFruit(i);
      blnFruitHide[i] = false;
    }
  }

  for (int i = 0; i < fltFruitY2.length; i++) {
    if (!blnFruitHide2[i] && fltFruitY2[i] > height) {
      intLives--;
      resetFruit2(i);
      blnFruitHide2[i] = false;
    }
  }

  if (intLives == 0) {
    blnStartTitle = false;
    blnGameOver = true;
  }
}

/**
 * Check if any fruit is below screen
 * @return True if any fruit is below screen, false otherwise.
 */
public boolean anyFruitBelowHeight() {
  for (int i = 0; i < fltFruitY.length; i++) {
    if (!blnFruitHide[i] && fltFruitY[i] >= height) {
      return true;
    }
  }
  for (int i = 0; i < fltFruitY2.length; i++) {
    if (!blnFruitHide2[i] && fltFruitY2[i] >= height) {
      return true;
    }
  }
  return false;
}

  /**
   * Draws heart lives
   */
  public void drawLives() {
    for (int i = intLives; i > 0; i--) {
      image(imgMisses, 750 + (3 - i) * 30, 20, 40, 40);
    }
  }

  /**
   * Moves the bomb images on the screen
   */
  public void drawAndMoveBombs() {
    for (int i = 0; i < fltBombX.length; i++) {
      if (!blnBombHide[i]) {
        image(imgBomb, fltBombX[i], fltBombY[i], 80, 80);
      }
      fltBombY[i] += intFruitSpeed;

      if (intLives != 0 && checkFruitCollision(fltBombX[i], fltBombY[i], 20)) {
        blnStartTitle = false;
        blnGameOver = true;
      }

      if (fltBombY[i] > height) {
        resetBomb(i);
      }
    }
  }

  /**
   * Bomb reset
   * @param index Bomb reset
   */
  public void resetBomb(int index) {
    fltBombY[index] = random(-80);
    fltBombX[index] = random(width);
  }

  /**
   * Katana slice animation
   */
  public void playKatanaSliceAnimation() {
    if (blnKatanaSlice) {
      image(imgKatanaSlice, mouseX - 40, mouseY - 40, 80, 80);
      katanaSliceFrame++;

      if (katanaSliceFrame > 10) {
        katanaSliceFrame = -1;
        blnKatanaSlice = false;
      }
    }
  }

  /**
   * Mouse click
   * Katana slice animation, check for fruit collisions
   */
  public void mouseClicked() {
    if (!blnGameOver && !blnStartTitle && katanaSliceFrame == -1) {
      blnKatanaSlice = true;
      katanaSliceFrame = 0;
      checkAndHideFruits();
    }
  }

  /**
   * Checks fruit collisions during katana slice animation and hides fruits
   */
  public void checkAndHideFruits() {
    for (int i = 0; i < fltFruitX.length; i++) {
      if (!blnFruitHide[i] && checkFruitCollision(fltFruitX[i], fltFruitY[i], 20)) {
        // Reset the fruit position
        resetFruit(i);
        blnFruitHide[i] = false;  // Set hide state to false
      }
    }
  
    for (int i = 0; i < fltFruitX2.length; i++) {
      if (!blnFruitHide2[i] && checkFruitCollision(fltFruitX2[i], fltFruitY2[i], 20)) {
        // Reset the fruit position
        resetFruit2(i);
        blnFruitHide2[i] = false;  // Set hide state to false
      }
    }
  }

  /**
   * Mouse pressed
   * Resets game when it is over or at start title screen
   */
  public void mousePressed() {
    if (blnStartTitle || blnGameOver) {
      blnStartTitle = false;
      blnGameOver = false;
      intScore = 0;
      intLives = 3;
      initializeFruitPositions();
    }
  }

  /**
   * Initializes fruit and bomb position at start or end of game
   */
  public void initializeFruitPositions() {
    for (int i = 0; i < fltFruitX.length; i++) {
      fltFruitX[i] = random(width);
      fltFruitY[i] = random(-400);
      blnFruitHide[i] = false;
    }

    for (int i = 0; i < fltFruitX2.length; i++) {
      fltFruitX2[i] = random(width);
      fltFruitY2[i] = random(-300);
      blnFruitHide2[i] = false;
    }

    for (int i = 0; i < fltBombX.length; i++) {
      fltBombX[i] = random(width);
      fltBombY[i] = random(-100);
      blnBombHide[i] = false;
    }
  }
}