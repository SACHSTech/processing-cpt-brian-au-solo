import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	
	// Declare images
  PImage imgStartTitle;
  PImage imgBackground;
  PImage imgMisses;
  PImage imgGameOver;
  PImage imgKatana;
  PImage imgKatanaSlice;
  PImage imgFruit1;
  PImage imgFruit2;

  // Boolean for screens
  boolean blnStartTitle;
  boolean blnGameOver;
  boolean blnKatanaSlice;

  // Player lives and score
  int intScore = 0;

  // Storing fruits into arrays and if they can be seen or not
  float[] fltFruitY = new float[40];
  float[] fltFruitX = new float[40];
  float[] fltFruitY2 = new float[40];
  float[] fltFruitX2 = new float[40];
  boolean [] blnFruitHide = new boolean[40];

   // Fruit collision detection, fruit speed, spawn location, player lives
   int intFruit = 0;
   int intFruitSpeed = 3;
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

    imgMisses = loadImage("heart.png");

    // Boolean for which picture is shown
    blnStartTitle = true; 
    blnGameOver = false;
    blnKatanaSlice = false;

    // Initialize fruit positions
    for (int i = 0; i < fltFruitX.length; i++) {
      fltFruitX[i] = random(width);
      fltFruitY[i] = -50 - random(400);
      fltFruitX2[i] = random(width);
      fltFruitY2[i] = -50 - random(400);
      blnFruitHide[i] = false;
   }
  }

  public void draw() {
    image(imgStartTitle, 0, 0);
    
    if (!blnGameOver && !blnStartTitle) {
      image(imgBackground, 0, 0);
        // Draw and move fruits
        for (int i = 0; i < fltFruitX.length; i++) {
            if (!blnFruitHide[i]) {
                // Draw the fruits
                if (intFruit % 2 == 0) {
                    image(imgFruit1, fltFruitX[i], fltFruitY[i], 50, 50);
                } else {
                    image(imgFruit2, fltFruitX2[i], fltFruitY2[i], 50, 50);
                }

                // Move the fruit down
                fltFruitY[i] += intFruitSpeed;

                // Check for collision with katana
                if (dist(fltFruitX[i], fltFruitY[i], mouseX, mouseY) < 30) {
                    blnFruitHide[i] = true;  // Hide the fruit
                    intScore += 10;  // Increase the score
                    // Reset the fruit position
                    fltFruitY[i] = -50 - random(400);
                    fltFruitX[i] = random(width);
                }

                // Check for misses
                if (fltFruitY[i] > height) {
                    blnFruitHide[i] = true;  // Hide the fruit
                    intLives--;  // Increase the misses
                    if (intLives == 0) {
                        blnGameOver = true;  // Game over if 3 misses
                    }
                }
            }
        }
    }
    
    // Display lives
    for (int i = intLives; i > 0; i--) {
      image(imgMisses, 650 + (3 - i) * 30, 20, 40, 40);
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
      image(imgKatanaSlice, mouseX, mouseY, 55, 75);
      katanaSliceFrame++;

      // Hide katana slice after a certain number of frames
      if (katanaSliceFrame > 10) {
        katanaSliceFrame = -1;
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
    image(imgKatanaSlice, mouseX, mouseY, 55, 75);
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
