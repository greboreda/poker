package com.greboreda.poker.card;

import com.greboreda.poker.Comparision;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
