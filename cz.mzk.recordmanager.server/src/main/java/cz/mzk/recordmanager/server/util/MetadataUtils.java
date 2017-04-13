package cz.mzk.recordmanager.server.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.validator.routines.ISBNValidator;

import cz.mzk.recordmanager.server.ClasspathResourceProvider;
import cz.mzk.recordmanager.server.model.ShortTitle;
import cz.mzk.recordmanager.server.model.Title;

public class MetadataUtils {

	private static final ISBNValidator isbnValidator = ISBNValidator.getInstance(true);

	private static final List<String> similarity_words = new BufferedReader(new InputStreamReader(
			new ClasspathResourceProvider().getResource("/mapping/similarity_words.map"), StandardCharsets.UTF_8)) 
			.lines().collect(Collectors.toCollection(ArrayList::new));

	public static final Pattern NUMBER_PATTERN = Pattern.compile("\\d");
	public static final List<Pattern> PATTERNS = similarity_words.stream()
			.map(w -> Pattern.compile("\\b" + w + "\\b", Pattern.CASE_INSENSITIVE)).collect(Collectors.toList());

	public static boolean hasTrailingPunctuation(final String input) {
		return input.matches(".*(([:;,=\\(\\[])|(\\s\\.))$");
	}

	public static String normalize(final String input) {
		return input == null ? null : Normalizer
				.normalize(input, Normalizer.Form.NFD)
				.replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
				.replaceAll("\\W", "")
				.toLowerCase();
	}

	public static String normalizeAndShorten(final String input, int length) {
		String normalized = normalize(input);
		if (normalized == null) {
			return null;
		}
		return normalized.substring(0, Math.min(length, normalized.length()));
	}

	public static Long toISBN13(String isbn) {
		String isbn13 = isbnValidator.validate(isbn);
		return (isbn13 == null)? null: Long.valueOf(isbn13);
	}
	
	public static boolean similarityEnabled(Title title){
		return similarityEnabled(title.getTitleStr());
	}
	
	public static boolean similarityEnabled(ShortTitle shortTitle) {
		return similarityEnabled(shortTitle.getShortTitleStr());
	}
	
	protected static boolean similarityEnabled(String title) {
		if (NUMBER_PATTERN.matcher(title).find()) return false;
		for (Pattern pattern: PATTERNS) {
			if (pattern.matcher(title).find()) {
				return false;
			}
		}
		return true;
	}

}
