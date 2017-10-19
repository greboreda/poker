package com.greboreda.poker.hand.rank.onepair;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class OnePair implements Rank {

	private final Value value;
	private final Value highKicker;
	private final Value secondKicker;
	private final Value thirdKicker;

	private OnePair(Value value, Value kicker1, Value kicker2, Value kicker3) {
		check(value, kicker1, kicker2, kicker3);
		this.value = value;

		final List<Value> sortedKickers = Stream.of(kicker1, kicker2, kicker3)
				.sorted(Comparator.comparingInt(Value::getWeight))
				.collect(toList());

		this.highKicker = sortedKickers.get(0);
		this.secondKicker = sortedKickers.get(1);
		this.thirdKicker = sortedKickers.get(2);
	}

	private void check(Value value, Value kicker1, Value kicker2, Value kicker3) {
		boolean areDistinctValues = Stream.of(value, kicker1, kicker2, kicker3)
				.distinct()
				.count() == 4;
		if(!areDistinctValues) {
			throw new IllegalStateException("value and kickers must be different");
		}
	}

	public Value getValue() {
		return value;
	}

	public Value getHighKicker() {
		return highKicker;
	}

	public Value getSecondKicker() {
		return secondKicker;
	}

	public Value getThirdKicker() {
		return thirdKicker;
	}

	@Override
	public RankValue getRankValue() {
		return RankValue.ONE_PAIR;
	}

	public static OnePairBuilder create() {
		return new OnePairBuilder();
	}

	public static class OnePairBuilder {
		@FunctionalInterface
		public interface AddAKicker {
			AddAnotherKicker withKicker(Value highKicker);
		}
		@FunctionalInterface
		public interface AddAnotherKicker {
			AddLastKicker withKicker(Value secondKicker);
		}
		@FunctionalInterface
		public interface AddLastKicker {
			Builder withKicker(Value thirdKicker);
		}
		@FunctionalInterface
		public interface Builder {
			OnePair build();
		}
		private OnePairBuilder() {

		}
		public AddAKicker of(Value of) {
			return kicker1 -> kicker2 -> kicker3 ->  () -> new OnePair(of, kicker1, kicker2, kicker3);
		}
	}
}
