#include <iostream> //includes input/output functions
#include "Calc.h" //includes header file
#include "CalcSource.cpp"

int main() {
	double num1, num2;
	char op;

	std::cout << "Enter operator: ";
	std::cin >> op;

	std::cout << "Enter two numbers: ";
	std::cin >> num1 >> num2;

	double result;

	switch (op) {
	case '+':
		result = add(num1, num2);
		std::cout << num1 << " + " << num2 << " = " << result;
		break;

	case '-':
		result = subtract(num1, num2);
		std::cout << num1 << " - " << num2 << " = " << result;
		break;

	case '*':
		result = multiply(num1, num2);
		std::cout << num1 << " * " << num2 << " = " << result;
		break;

	case '/':
		result = divide(num1, num2);
		if (num2 != 0) {
			std::cout << num1 << " / " << num2 << " = " << result;
		}
		else {
			std::cout << "Error: Cannot divide by zero!";
		}
		break;
	default: 
		std::cout << "Error: Unrecognized operator!";
	}

	std::cout << std::endl;
	return 0;
}