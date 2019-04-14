import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class FizzBuzz extends PApplet {


public void setup() 
{
	fizzBuzz();
}

public void fizzBuzz()
{
	for (int i = 1; i < 101; ++i) 
	{
		String s = "";

		if (i % 3 == 0) s = "Fizz";
		if (i % 5 == 0) s = s + "Buzz";

		if (s.length() > 0) println(s);
		else println(i);
	}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "FizzBuzz" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
