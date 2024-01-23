import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {

  PImage imgStartTitle;
  PImage imgBackground;
  PImage imgMisses;
  PImage imgGameOver;
  PImage imgKatana;
  PImage imgKatanaSlice;
  PImage imgFruit1;
  PImage imgFruit2;
  PImage imgBomb;

  // Boolean for screens
  boolean blnStartTitle;
  boolean blnGameOver;
  boolean blnKatanaSlice;

  // Player score
  int intScore = 0;

  // Storing fruits into arrays and if they can be seen or not
  float[] fltFruitY = new float[40];
  float[] fltFruitX = new float[40];
  float[] fltFruitY2 = new float[40];
  float[] fltFruitX2 = new float[40];
  float[] fltBombX = new float[5];
  float[] fltBombY = new float[5];
  boolean [] blnFruitHide = new boolean[40];
  boolean [] blnFruitHide2 = new boolean[40];
  boolean [] blnBombHide = new boolean[5];

   // Fruit collision detection, fruit speed, spawn location, player lives
   int intFruit = 0;
   int intFruitSpeed = 5;
   int intLives = 3;

   // Indicates the katana slice is not active
   int katanaSliceFrame = -1; 


  public void settings() {
	// screen size
  size(1000, 800);
}

  public void setup() {

    // Enter images
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

    // Boolean for which picture is shown
    blnStartTitle = true; 
    blnGameOver = false;
    blnKatanaSlice = false;

    // Initialize fruit positions
    for (int i = 0; i < fltFruitX.length; i++) {
      fltFruitX[i] = random(width);
      fltFruitY[i] = -10 - random(800);
      blnFruitHide[i] = false;
   }

    for (int i = 0; i < fltFruitX.length; i++) {
      fltFruitX2[i] = random(width);
      fltFruitY2[i] = -20 - random(800);
      blnFruitHide2[i] = false;
    }

    for (int i = 0; i < fltBombX.length; i++) {
      fltBombX[i] = random(width);
      fltBombY[i] = -80 - random(800);
      blnBombHide[i] = false;
    }
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    image(imgStartTitle, 0, 0);
    
    if (!blnGameOver && !blnStartTitle) {
      image(imgBackground, 0, 0);

    // Draw and move apples, watermelons, and bombs
    for (int i = 0; i < fltFruitX.length; i++) {
      if (!blnFruitHide[i]) {
        image(imgFruit1, fltFruitX[i], fltFruitY[i], 80, 80);
      }
      fltFruitY[i] += intFruitSpeed;

      if (!blnFruitHide2[i]) {
        image(imgFruit2, fltFruitX2[i], fltFruitY2[i], 80, 80);
      }
      fltFruitY2[i] += intFruitSpeed;

      if (!blnBombHide[i]) {
        image(imgBomb, fltBombX[i], fltBombY[i], 80, 80);
      }
      fltBombY[i] += intFruitSpeed;

      // Apple collision with katana, hide fruit, increase score
      if (dist(fltFruitX[i], fltFruitY[i], mouseX, mouseY) < 30) {
        blnFruitHide[i] = true;
        intScore += 10;
      }

      // Watermelon collision with katana, hide fruit, increase score
      if (dist(fltFruitX2[i], fltFruitY2[i], mouseX, mouseY) < 30) {
        blnFruitHide2[i] = true;
        intScore += 10;
      }

      // Bomb collision with katana, display game over
      if (dist(fltBombX[i], fltBombY[i], mouseX, mouseY) < 30) {
        blnGameOver = true;
      }
      
      

      if (blnFruitHide[i] || blnFruitHide2[i] || blnBombHide[i]) {
        fltFruitY[i] = -50 - random(800);
        fltFruitX[i] = random(width);
        fltFruitY2[i] = -30 - random(800);
        fltFruitX2[i] = random(width);
        fltBombY[i] = -80 - random(800);
        fltBombX[i] = random(width);
      }

      // Check for misses
      if (fltFruitY[i] > height || fltFruitY2[i] > height || fltBombY[i] > height) {
        fltFruitY[i] = -50 - random(800);
        fltFruitX[i] = random(width);
        fltFruitY2[i] = -30 - random(800);
        fltFruitX2[i] = random(width);
        fltBombY[i] = -80 - random(800);
        fltBombX[i] = random(width);
        intLives--; // Increase the misses
        if (intLives == 0) {
          blnGameOver = true; // Game over if 3 misses
        }
      }
    }

    // Display lives
    for (int i = intLives; i > 0; i--) {
      image(imgMisses, 700 + (3 - i) * 30, 20, 40, 40);
    }

    // Display game over message
    if (blnGameOver) {
      image(imgGameOver, 0, 0);
      textSize(32);
      fill(255, 0, 0);
      textAlign(CENTER, CENTER);
      textFont(createFont("arial bold", 40));
      text("Your score: " + intScore, width / 2 - 10, height / 2 - 30);
    }

    // Draw katana slice animation if active
    if (katanaSliceFrame >= 0) {
      image(imgKatanaSlice, mouseX, mouseY, 155, 175);
      katanaSliceFrame++;

      // Hide katana slice after a certain number of frames
      if (katanaSliceFrame > 10) {
        katanaSliceFrame = -1;
      }
    }
  }
}

public void mouseClicked() {
    if (!blnGameOver && !blnStartTitle && katanaSliceFrame == -1) {
        // Katana slice animation
        blnKatanaSlice = true;
        katanaSliceFrame = 0;
    }
}

  public void playKatanaSliceAnimation() {
    imageMode(CENTER);
    image(imgKatanaSlice, mouseX, mouseY);
    katanaSliceFrame++;

    if(katanaSliceFrame > 10) {
      katanaSliceFrame = -1;
      blnKatanaSlice = false;
    }
  }

public void mousePressed() {
  blnStartTitle = false;
  } 

  public void mouseReleased() {
  blnStartTitle = false;
  } 

  public void mouseDragged() {
  }
}
