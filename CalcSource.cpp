#include "Calc.h" //includes the header file

//Function definitions
static double add(double num1, double num2) { //addition
	return num1 + num2;
}

static double subtract(double num1, double num2) { //subtraction
	return num1 - num2;
}

static double multiply(double num1, double num2)
{
	return num1 * num2;
}

static double divide(double num1, double num2) { //division
	//error handling for by-zero division
	if (num2 != 0) {
		return num1 / num2;
	}
	else {
		return 0;
	}
}