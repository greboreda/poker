package com.greboreda.poker.hand.rank.straightflush;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.ComparisionPairs;
import com.greboreda.poker.hand.rank.RankComparator;

public class StraightFlushComparator implements RankComparator {

	private final StraightFlush aStraightFlush;
	private final StraightFlush anotherStraightFlush;

	public StraightFlushComparator(StraightFlush aStraightFlush, StraightFlush anotherStraightFlush) {
		this.aStraightFlush = aStraightFlush;
		this.anotherStraightFlush = anotherStraightFlush;
	}

	@Override
	public ComparisionPairs<Value> retrieveComparisionPairs() {
		return ComparisionPairs.forClass(Value.class)
				.withPair(aStraightFlush.getHigh(), anotherStraightFlush.getHigh())
				.build();
	}
}
