package com.greboreda.poker.parser;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Card;
import com.greboreda.poker.card.Suit;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.greboreda.poker.card.Suit.CLUBS;
import static com.greboreda.poker.card.Suit.DIAMONDS;
import static com.greboreda.poker.card.Suit.HEARTS;
import static com.greboreda.poker.card.Suit.SPADES;
import static com.greboreda.poker.card.Value.ACE;
import static com.greboreda.poker.card.Value.EIGHT;
import static com.greboreda.poker.card.Value.FIVE;
import static com.greboreda.poker.card.Value.FOUR;
import static com.greboreda.poker.card.Value.JACK;
import static com.greboreda.poker.card.Value.KING;
import static com.greboreda.poker.card.Value.NINE;
import static com.greboreda.poker.card.Value.QUEEN;
import static com.greboreda.poker.card.Value.SEVEN;
import static com.greboreda.poker.card.Value.SIX;
import static com.greboreda.poker.card.Value.TEN;
import static com.greboreda.poker.card.Value.THREE;
import static com.greboreda.poker.card.Value.TWO;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class PokerParser {

	public static List<Pair<Hand,Hand>> parseFile(String path) throws IOException {
		return Files.lines(Paths.get(path))
				.map(PokerParser::parseLine)
				.collect(Collectors.toList());
	}

	private static Pair<Hand,Hand> parseLine(String line) {
		final List<String> hands = Arrays.asList(line.split(StringUtils.SPACE));
		if(hands.size() != 10) {
			throw new IllegalArgumentException("not valid line: " + line);
		}

		final Hand hand1 = buildHand(hands.subList(0, 5));
		final Hand hand2 = buildHand(hands.subList(5, 10));

		return new ImmutablePair<>(hand1, hand2);
	}

	private static Hand buildHand(List<String> cards) {
		final Iterator<Card> it1 = cards.stream()
				.map(CardParser::parseCard)
				.iterator();
		return new Hand(it1.next(), it1.next(), it1.next(), it1.next(), it1.next());
	}

	public static void main(String args[]) throws IOException {
		final List<Pair<Hand, Hand>> pairs = parseFile("src/main/resources/poker.txt");

		final Map<Comparision, Long> collect = pairs.stream()
				.map(p -> p.getLeft().compare(p.getRight()))
				.collect(groupingBy(identity(), counting()));

		final Long wins = collect.get(Comparision.WIN);

		System.out.print("wins " + wins + " of " + pairs.size());
	}

	public static class CardParser {

		private static final Map<Character,Suit> suits = new HashMap<>();
		private static final Map<Character,Value> values = new HashMap<>();
		private static final String NOT_VALID_SOURCE = "source is not valid hand: ";

		static {
			suits.put('C', CLUBS);
			suits.put('S', SPADES);
			suits.put('H', HEARTS);
			suits.put('D', DIAMONDS);

			values.put('A', ACE);
			values.put('2', TWO);
			values.put('3', THREE);
			values.put('4', FOUR);
			values.put('5', FIVE);
			values.put('6', SIX);
			values.put('7', SEVEN);
			values.put('8', EIGHT);
			values.put('9', NINE);
			values.put('T', TEN);
			values.put('J', JACK);
			values.put('Q', QUEEN);
			values.put('K', KING);
		}

		public static Card parseCard(String src) {
			Validate.notNull(src);
			final String cleanSource = src.trim();
			if(cleanSource.length() != 2) {
				throw new IllegalArgumentException(NOT_VALID_SOURCE + cleanSource);
			}
			final char valueChar = cleanSource.charAt(0);
			final char suitChar = cleanSource.charAt(1);

			if(!suits.containsKey(suitChar) || !values.containsKey(valueChar)) {
				throw new IllegalArgumentException(NOT_VALID_SOURCE + cleanSource);
			}
			final Value value = values.get(valueChar);
			final Suit suit = suits.get(suitChar);

			return new Card(value, suit);
		}
	}
}
