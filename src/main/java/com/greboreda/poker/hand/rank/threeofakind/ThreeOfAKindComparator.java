package com.greboreda.poker.hand.rank.threeofakind;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.ComparisionPairs;
import com.greboreda.poker.hand.rank.RankComparator;

public class ThreeOfAKindComparator implements RankComparator {

	private final ThreeOfAKind aThreeOfAKind;
	private final ThreeOfAKind anotherThreeOfAKind;

	public ThreeOfAKindComparator(ThreeOfAKind aThreeOfAKind, ThreeOfAKind anotherThreeOfAKind) {
		this.aThreeOfAKind = aThreeOfAKind;
		this.anotherThreeOfAKind = anotherThreeOfAKind;
	}

	@Override
	public ComparisionPairs<Value> retrieveComparisionPairs() {
		return ComparisionPairs.forClass(Value.class)
				.withPair(aThreeOfAKind.getValue(), anotherThreeOfAKind.getValue())
				.withPair(aThreeOfAKind.getHighKicker(), anotherThreeOfAKind.getHighKicker())
				.withPair(aThreeOfAKind.getLowKicker(), anotherThreeOfAKind.getLowKicker())
				.build();
	}
}
