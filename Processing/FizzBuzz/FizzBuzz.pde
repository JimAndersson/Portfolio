void setup() 
{
	fizzBuzz();
}

void fizzBuzz()
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