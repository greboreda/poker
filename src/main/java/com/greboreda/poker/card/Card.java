package com.greboreda.poker.card;

import org.apache.commons.lang3.Validate;

public class Card {

	private final Value value;
	private final Suit suit;

	public Card(Value value, Suit suit) {
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

	public String toString() {
		return CardStringifier.getRepresentation(value, suit);
	}

}
