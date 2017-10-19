package com.greboreda.poker.hand.rank.threeofakind;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ThreeOfAKind implements Rank {

	private final Value value;
	private final Value highKicker;
	private final Value lowKicker;

	private ThreeOfAKind(Value value, Value kicker1, Value kicker2) {
		checkAreDifferent(value, kicker1, kicker2);
		final List<Value> sortedKickers = Stream.of(kicker1, kicker2)
				.sorted(Comparator.comparingInt(Value::getWeight))
				.collect(Collectors.toList());
		this.value = value;
		this.highKicker = sortedKickers.get(0);
		this.lowKicker = sortedKickers.get(1);
	}

	private void checkAreDifferent(Value value, Value highKicker, Value lowKicker) {
		final boolean allValuesAreDifferent = Stream.of(value, highKicker, lowKicker)
				.distinct()
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
		return RankValue.THREE_OF_A_KIND;
	}

	public static ThreeOfAKindBuilder create() {
		return new ThreeOfAKindBuilder();
	}

	public static class ThreeOfAKindBuilder {
		@FunctionalInterface
		public interface AddAKicker {
			AddAnotherKicker withKicker(Value highKicker);
		}
		@FunctionalInterface
		public interface AddAnotherKicker {
			Builder withKicker(Value lowKicker);
		}
		@FunctionalInterface
		public interface Builder {
			ThreeOfAKind build();
		}
		private ThreeOfAKindBuilder() {

		}
		public AddAKicker of(Value of) {
			return kicker1 -> kicker2 -> () -> new ThreeOfAKind(of, kicker1, kicker2);
		}
	}
}
