package com.greboreda.poker.hand.rank.twopair;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.ComparisionPairs;
import com.greboreda.poker.hand.rank.RankComparator;

public class TwoPairComparator implements RankComparator {

	private final TwoPair aTwoPair;
	private final TwoPair anotherTwoPair;

	public TwoPairComparator(TwoPair aTwoPair, TwoPair anotherTwoPair) {
		this.aTwoPair = aTwoPair;
		this.anotherTwoPair = anotherTwoPair;
	}

	@Override
	public ComparisionPairs<Value> retrieveComparisionPairs() {
		return ComparisionPairs.forClass(Value.class)
				.withPair(aTwoPair.getHighPair(), anotherTwoPair.getHighPair())
				.withPair(aTwoPair.getLowPair(), anotherTwoPair.getLowPair())
				.withPair(aTwoPair.getKicker(), anotherTwoPair.getKicker())
				.build();
	}
}
