package com.greboreda.poker;

import org.apache.commons.lang3.Validate;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Card {

	private final Value value;
	private final Suit suit;

	private Card(Value value, Suit suit) {
		Validate.notNull(value);
		Validate.notNull(suit);
		this.value = value;
		this.suit = suit;
	}

	public Value getValue() {
		return value;
	}

	public Suit getSuit() {
		return suit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Card card = (Card) o;

		if (value != card.value) return false;
		return suit == card.suit;
	}

	@Override
	public int hashCode() {
		int result = value != null ? value.hashCode() : 0;
		result = 31 * result + (suit != null ? suit.hashCode() : 0);
		return result;
	}

	public static CardBuilder create() {
		return new CardBuilder();
	}

	public static class CardBuilder {
		@FunctionalInterface
		public interface AddSuit {
			Builder withSuit(Suit suit);
		}
		@FunctionalInterface
		public interface Builder {
			Card build();
		}
		private CardBuilder() {

		}
		public AddSuit withValue(Value value) {
			return suit -> () -> new Card(value, suit);
		}
	}

	public enum Suit {
		HEARTS,
		SPADES,
		CLUBS,
		DIAMONDS
	}

	public enum Value {

		ACE,
		KING,
		QUEEN,
		JACK,
		TEN,
		NINE,
		EIGHT,
		SEVEN,
		SIX,
		FIVE,
		FOUR,
		THREE,
		TWO;

		private static final Map<Value, Set<Value>> betters = new HashMap<>();
		static {
			betters.put(ACE, EnumSet.of(KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO));
			betters.put(KING, EnumSet.of(QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO));
			betters.put(QUEEN, EnumSet.of(JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO));
			betters.put(JACK, EnumSet.of(TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO));
			betters.put(TEN, EnumSet.of(NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO));
			betters.put(NINE, EnumSet.of(EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO));
			betters.put(EIGHT, EnumSet.of(SEVEN, SIX, FIVE, FOUR, THREE, TWO));
			betters.put(SEVEN, EnumSet.of(SIX, FIVE, FOUR, THREE, TWO));
			betters.put(SIX, EnumSet.of(FIVE, FOUR, THREE, TWO));
			betters.put(FIVE, EnumSet.of(FOUR, THREE, TWO));
			betters.put(FOUR, EnumSet.of(THREE, TWO));
			betters.put(THREE, EnumSet.of(TWO));
			betters.put(TWO, Collections.emptySet());
		}

		public Comparision compare(Value value) {
			if(this.equals(value)) {
				return Comparision.TIE;
			} else if (betters.get(this).contains(value)) {
				return Comparision.WIN;
			} else {
				return Comparision.LOOSE;
			}
		}

	}
}
