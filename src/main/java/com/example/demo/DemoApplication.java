package com.example.demo;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		validatePassword();
	
    }

	public static void validatePassword() {
		// Membuka objek Scanner untuk input pengguna
		Scanner scanner = new Scanner(System.in);
		
		// Input password
		System.out.print("Masukkan password: ");
		
		// Scanner akan cek inputan user per karakter dalam inputan password user
		String password = scanner.nextLine();
		
		// Menutup Scanner
		scanner.close();

		// Memvalidasi password
		if (isValidPassword(password)) {
			// Jika password bernilai true
			System.out.println("Password valid.");
		} else {
			// Jika password bernilai false
			System.out.println("Password tidak valid. Password harus memiliki minimal 8 karakter, mengandung huruf besar, huruf kecil, dan angka.");
		}
	}

    public static boolean isValidPassword(String password) {
		// cek panjang password
        if (password.length() < 8) {
            return false;
        }

		// huruf kapital
        boolean hasUpper = false;
		// huruf kecil
        boolean hasLower = false;
		// angka
        boolean hasDigit = false;

		// for loop untuk cek 
        for (char c : password.toCharArray()) {
			// cek jika c punya huruf kapital
            if (Character.isUpperCase(c)) {
                hasUpper = true;
			// cek jika c punya huruf kecil
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
			// cek jika c punya angka
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }

			// cek apa bila c punya 3 syarat password
            if (hasUpper && hasLower && hasDigit) {
				// balikin true jika sudah sesuai
                return true;
            }
        }

		// false jika password tidak sesuai
        return false;
    }
}


