import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	public static boolean isMatch(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return  matcher.find();
	}
}
