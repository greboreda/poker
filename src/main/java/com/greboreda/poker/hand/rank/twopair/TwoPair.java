package com.greboreda.poker.hand.rank.twopair;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class TwoPair implements Rank {

	private final Value highPair;
	private final Value lowPair;
	private final Value kicker;

	private TwoPair(Value pair1, Value pair2, Value kicker) {
		checkIsValid(pair1, pair2, kicker);

		final List<Value> sortedPairs = Stream.of(pair1, pair2)
				.sorted(Comparator.comparingInt(Value::getWeight))
				.collect(toList());

		this.highPair = sortedPairs.get(0);
		this.lowPair = sortedPairs.get(1);
		this.kicker = kicker;
	}

	private void checkIsValid(Value pair1, Value pair2, Value kicker) {
		Validate.noNullElements(Arrays.asList(pair1, pair2, kicker));
		if(pair1.equals(pair2)) {
			throw new IllegalStateException("pairs must be different each other");
		}
		if(kicker.equals(pair1) || kicker.equals(pair2)) {
			throw new IllegalStateException("kikcer must be different to highPair and lowPair");
		}
	}

	public Value getHighPair() {
		return highPair;
	}

	public Value getLowPair() {
		return lowPair;
	}

	public Value getKicker() {
		return kicker;
	}

	@Override
	public RankValue getRankValue() {
		return RankValue.TWO_PAIR;
	}

	public static TwoPairBuilder create() {
		return new TwoPairBuilder();
	}

	public static class TwoPairBuilder {
		@FunctionalInterface
		public interface AddAnotherPair {
			AddKicker withPair(Value lowPair);
		}
		@FunctionalInterface
		public interface AddKicker {
			Builder withKicker(Value kicker);
		}
		@FunctionalInterface
		public interface Builder {
			TwoPair build();
		}
		private TwoPairBuilder() {

		}
		public AddAnotherPair withPair(Value pair) {
			return pair2 -> kicker -> () -> new TwoPair(pair, pair2, kicker);
		}
	}
}
