import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Main {
	public static void main(String[] args) {
		// System.out.println(generateRandomInt(4, 5));
	}
	// private static int generateRandomInt(int min, int max) {
		// Random rd = new Random();
		// return rd.nextInt((max-min)+1) + min;
	// }
	private static int generateRandomInt(int min, int max) {
		Random rd = new Random();
		return rd.nextInt((max-min)+1) + min;
	}

	private static String capitalize(String word) {
		String capitalizedWord = "";
		String[] words = word.split(" ");
		for (String str : words) {
			capitalizedWord += str.substring(0, 1).toUpperCase() + str.substring(1) + " ";
		}
		return capitalizedWord.trim();
	}
	private static String compactWord(String word, int maxLength) {
		if (word.length() > maxLength) {
			word = word.substring(0, maxLength-3);
			word = word + "...";
		}
		return word;
	} 
	private static String stringInto(String str, int inTo) {
		String initialString = str;
		for(int i = 0; i < inTo; i++) {
			str += initialString;
		}
		return str;
	}
}
