package com.greboreda.poker.hand.rank.threeofakind;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

public class ThreeOfAKind implements Rank {

	private final Value value;
	private final Value highKicker;
	private final Value lowKicker;

	private ThreeOfAKind(Value value, Value highKicker, Value lowKicker) {
		checkAreDifferent(value, highKicker, lowKicker);
		this.value = value;
		this.highKicker = highKicker;
		this.lowKicker = lowKicker;
	}

	private void checkAreDifferent(Value value, Value highKicker, Value lowKicker) {
		final boolean allValuesAreDifferent = Stream.of(value, highKicker, lowKicker)
				.count() == 3;
		if(!allValuesAreDifferent) {
			throw new IllegalStateException("value, highKicker and lowKicker must be different");
		}
	}

	public Value getValue() {
		return value;
	}

	public Value getHighKicker() {
		return highKicker;
	}

	public Value getLowKicker() {
		return lowKicker;
	}

	@Override
	public RankValue getRankValue() {
		return null;
	}

	@Override
	public Comparision compare(Rank another) {
		return null;
	}

	public static ThreeOfAKindBuilder create() {
		return new ThreeOfAKindBuilder();
	}

	public static class ThreeOfAKindBuilder {
		@FunctionalInterface
		public interface AddHighKicker {
			AddLowKicker withHighKicker(Value highKicker);
		}
		@FunctionalInterface
		public interface AddLowKicker {
			Builder withLowKicker(Value lowKicker);
		}
		@FunctionalInterface
		public interface Builder {
			ThreeOfAKind build();
		}
		private ThreeOfAKindBuilder() {

		}
		public AddHighKicker of(Value of) {
			return highKicker -> lowKicker -> () -> new ThreeOfAKind(of, highKicker, lowKicker);
		}
	}
}
