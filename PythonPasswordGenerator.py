import random
import string

def generate_password():
    length = int(input("Enter password legnth: "))
    characters = string.ascii_letters + string.digits + string.punctuation
    password = ' '.join(random.choice(characters) for _ in range(length))

    print(f"Generated Password: {password}")
generate_password()