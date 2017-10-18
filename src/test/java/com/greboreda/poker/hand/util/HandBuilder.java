package com.greboreda.poker.hand.util;

import com.greboreda.poker.card.Card;
import com.greboreda.poker.hand.Hand;

public class HandBuilder {
	@FunctionalInterface
	public interface AddSecond {
		AddThird with(Card second);
	}
	@FunctionalInterface
	public interface AddThird {
		AddFourth with(Card third);
	}
	@FunctionalInterface
	public interface AddFourth {
		AddFifth with(Card fourth);
	}
	@FunctionalInterface
	public interface AddFifth {
		Hand with(Card fifth);
	}
	public AddSecond with(Card first) {
		return second -> third -> fourth -> fifth -> new Hand(first, second, third, fourth, fifth);
	}
	public static HandBuilder hand() {
		return new HandBuilder();
	}
}
