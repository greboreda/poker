package com.greboreda.poker.hand.rank.twopair;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;

public class TwoPair implements Rank {

	private final Value highPair;
	private final Value lowPair;
	private final Value kicker;

	private TwoPair(Value highPair, Value lowPair, Value kicker) {
		checkIsValid(highPair, lowPair, kicker);
		this.highPair = highPair;
		this.lowPair = lowPair;
		this.kicker = kicker;
	}

	private void checkIsValid(Value highPair, Value lowPair, Value kicker) {
		if(!highPair.wins(lowPair)) {
			throw new IllegalStateException("high pair must win low pair");
		}
		if(kicker.equals(highPair) || kicker.equals(lowPair)) {
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
		return RankValue.TWO_PAIRS;
	}

	@Override
	public Comparision compare(Rank another) {
		final Comparision rankComparision = this.getRankValue().compare(another.getRankValue());
		if(!rankComparision.isTie()) {
			return rankComparision;
		}
		final TwoPair anotherTwoPair = (TwoPair) another;
		final Comparision highPairComparision = this.getHighPair().compare(anotherTwoPair.getHighPair());
		if(!highPairComparision.isTie()) {
			return highPairComparision;
		}
		final Comparision lowPairComparision = this.getLowPair().compare(anotherTwoPair.getLowPair());
		if(!lowPairComparision.isTie()) {
			return lowPairComparision;
		}
		return this.getKicker().compare(anotherTwoPair.getKicker());
	}

	public static TwoPairBuilder create() {
		return new TwoPairBuilder();
	}

	public static class TwoPairBuilder {
		@FunctionalInterface
		public interface AddLowPair {
			AddKicker withLowPair(Value lowPair);
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
		public AddLowPair withHighPair(Value highPair) {
			return lowPair -> kicker -> () -> new TwoPair(highPair, lowPair, kicker);
		}
	}
}
