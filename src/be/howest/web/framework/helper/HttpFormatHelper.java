package be.howest.web.framework.helper;

public class HttpFormatHelper {

	/**
	 * Convert newlines to <br/> tags. 
	 * @param input the input string. 
	 * @return output string with converted newlines.
	 */
	public static String newLineToBr(String input) {
		return input.replaceAll("\n", "<br/>");
	}
}
