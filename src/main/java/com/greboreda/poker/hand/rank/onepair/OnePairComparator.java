package com.greboreda.poker.hand.rank.onepair;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.ComparisionPairs;
import com.greboreda.poker.hand.rank.RankComparator;

public class OnePairComparator implements RankComparator {

	private final OnePair aOnePair;
	private final OnePair anotherOnePair;

	public OnePairComparator(OnePair aOnePair, OnePair anotherOnePair) {
		this.aOnePair = aOnePair;
		this.anotherOnePair = anotherOnePair;
	}

	@Override
	public ComparisionPairs<Value> retrieveComparisionPairs() {
		return ComparisionPairs.forClass(Value.class)
				.withPair(aOnePair.getValue(), anotherOnePair.getValue())
				.withPair(aOnePair.getHighKicker(), anotherOnePair.getHighKicker())
				.withPair(aOnePair.getSecondKicker(), anotherOnePair.getSecondKicker())
				.withPair(aOnePair.getThirdKicker(), anotherOnePair.getThirdKicker())
				.build();
	}
}
