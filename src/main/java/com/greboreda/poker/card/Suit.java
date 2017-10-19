package com.greboreda.poker.card;

import java.util.HashMap;
import java.util.Map;

public enum Suit {
	HEARTS,
	SPADES,
	CLUBS,
	DIAMONDS;

	private static final Map<Suit,String> reprs = new HashMap<>();
	static {
		reprs.put(HEARTS, "♥");
		reprs.put(CLUBS, "♣");
		reprs.put(SPADES, "♠");
		reprs.put(DIAMONDS, "♦");
	}

	public String toString() {
		return reprs.get(this);
	}

}
