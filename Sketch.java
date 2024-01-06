import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	
	// Declare images
  PImage imgStartTitle;
  PImage imgGameOver;
  PImage imgBackground;
  PImage imgMisses;

  // Fruit contact and player navigation
  boolean blnFruitHit = false;
  boolean wPressed = false;
  boolean aPressed = false;
  boolean sPressed = false;
  boolean dPressed = false;

  // Boolean for screens
  boolean blnStartTitle;
  boolean blnGameOver;

  // Player lives and score
  int intScore = 0;
  int intMisses = 0;

  public void settings() {
	// screen size
  size(600, 600);
}

  public void setup() {
    
    // Enter images
    imgStartTitle = loadImage("StartTitle.jpg");
    imgGameOver = loadImage("");

  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
	  
	// sample code, delete this stuff
    stroke(128);
    line(150, 25, 270, 350);  

    stroke(255);
    line(50, 125, 70, 50);  
  }
  
  // define other methods down here.
}