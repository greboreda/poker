package com.greboreda.poker.hand.rank.straight;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.ComparisionPairs;
import com.greboreda.poker.hand.rank.RankComparator;

public class StraightComparator implements RankComparator {

	private final Straight aStraight;
	private final Straight anotherStraight;

	public StraightComparator(Straight aStraight, Straight anotherStraight) {
		this.aStraight = aStraight;
		this.anotherStraight = anotherStraight;
	}

	@Override
	public ComparisionPairs<Value> retrieveComparisionPairs() {
		return ComparisionPairs.forClass(Value.class)
				.withPair(aStraight.getHigh(), anotherStraight.getHigh())
				.build();
	}
}
