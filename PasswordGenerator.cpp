//Secure Password Generator ver. 1.0
//Written by Zachary Halladay
//8/14/2026

#include <iostream>
#include <string>
#include <random>
#include <algorithm>

// Function generating a secure random password
std::string generatePassword(int length) {

	// Character pools for the password to draw from
	const std::string lower = "abcdefghijklmnopqrstuvwxyz";
	const std::string upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	const std::string digits = "0123456789";
	const std::string symbols = "!@#$%^&*()-_=+[]{}|;:,.<>?";

	//Combine all available characters
	std::string allChars = lower + upper + digits + symbols;

	//Initialize secure random number engine
	std::random_device rd;
	std::mt19937 generator(rd());

	//Distribution mapping to valid indices of the character pool
	std::uniform_int_distribution<size_t> distribution(0, allChars.size() - 1);

	std::string password = "";

	//Guarantee at least one character from each required pool for security
	password += lower[distribution(generator) % lower.size()];
	password += upper[distribution(generator) % upper.size()];
	password += digits[distribution(generator) % digits.size()];
	password += symbols[distribution(generator) % symbols.size()];

	//Fill the remaining length with random choices from the entire pool
	for (int i = 4; i < length; ++i) {
		password += allChars[distribution(generator)];
	}

	//Shuffle the characters so the guaranteed ones aren't always at the beginning
	std::shuffle(password.begin(), password.end(), generator);

	return password;
}

int main() {
	int length;

	std::cout << "=== Secure C++ Password Generator ===\n";
	std::cout << "    === by Zack Halladay ===\n";
	std::cout << "Enter desired password length (minimum 6): ";

	if (!(std::cin >> length) || length < 6) {
		std::cout << "Invalid input. Defaulting to length 12.\n";
		length = 12;
	}

	std::string securePassword = generatePassword(length);

	std::cout << "\nGenerated Password: " << securePassword << "\n";
	std::cout << "=====================================\n";

	return 0;
}