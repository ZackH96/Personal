/** Java Message Encryptor
 * Version 1.0
 * Written by Zachary "Prov1996" Halladay
 */

package messageEncryptorVersion1;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class MessageEncryptor {
	
	private static final String ALGORITHM = "AES/GCM/NoPadding";
	private static final int TAG_LENGTH_BIT = 128; // Authentication tag length
	private static final int IV_LENGTH_BYTE = 12; // Recommended IV length for GCM
	
	// Step 1: Generate a secure 256-bit AES key
	public static SecretKey generate() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(256);;
		return keyGenerator.generateKey();
	}
	// Step 2: Encrypt the plaintext message
	public static String encrypt(String message, SecretKey key) throws Exception {
		//Generate a random Initialization Vector aka IV
		byte[] iv = new byte[IV_LENGTH_BYTE];
		SecureRandom random = new SecureRandom();
		random.nextBytes(iv);;
		
		// Initialize Cipher for message encryption
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
		cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
		
		// Encrypt the message bytes
		byte[] ciphertext = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
		
		// Prefix the ciphertext with the IV so it can be used during the decryption process
		ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + ciphertext.length);
		byteBuffer.put(iv);
		byteBuffer.put(ciphertext);
		
		// Convert the combined byte array to a Base64 string for safe transport/storage
		return Base64.getEncoder().encodeToString(byteBuffer.array());
		}
	
	// Step 3: Decrypt the encrypted message
	public static String decrypt(String encryptedMessage, SecretKey key) throws Exception {
		// Decode the Base64 string back into bytes
		byte[] encryptedBytes = Base64.getDecoder().decode(encryptedMessage);
		
		// Extract the IV from the beginning of the byte array
		ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedBytes);
		byte[] iv = new byte[IV_LENGTH_BYTE];
		byteBuffer.get(iv);
		
		// Extract remaining bytes as the actual ciphertext
		byte[] ciphertext = new byte[byteBuffer.remaining()];
		byteBuffer.get(ciphertext);
		
		// Initialize Cipher for message decryption
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
		cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
		
		// Decrypt the ciphertext and convert back to a String
		byte[] plaintextBytes = cipher.doFinal(ciphertext);
		return new String(plaintextBytes, StandardCharsets.UTF_8);
		}
	
	//Main method to test the program
    public static void main(String[] args) {
        try {
            String originalMessage = "Hackathon at my place tonight.";
            System.out.println("Original:  " + originalMessage);

            // Generate a symmetric key
            SecretKey secretKey = generate();

            // Encrypt
            String encryptedMessage = encrypt(originalMessage, secretKey);
            System.out.println("Encrypted: " + encryptedMessage);

            // Decrypt
            String decryptedMessage = decrypt(encryptedMessage, secretKey);
            System.out.println("Decrypted: " + decryptedMessage);

        } catch (Exception e) {
            System.err.println("Encryption/Decryption failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}