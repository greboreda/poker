package com.greboreda.poker.hand.rank.fourofakind;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.ComparisionPairs;
import com.greboreda.poker.hand.rank.RankComparator;

public class FourOfAKindComparator implements RankComparator {

	private final FourOfAKind aFourOfAKind;
	private final FourOfAKind anotherFourOfAKind;

	public FourOfAKindComparator(FourOfAKind aFourOfAKind, FourOfAKind anotherFourOfAKind) {
		this.aFourOfAKind = aFourOfAKind;
		this.anotherFourOfAKind = anotherFourOfAKind;
	}

	@Override
	public ComparisionPairs<Value> retrieveComparisionPairs() {
		return ComparisionPairs.forClass(Value.class)
				.withPair(aFourOfAKind.getValue(), anotherFourOfAKind.getValue())
				.withPair(aFourOfAKind.getKicker(), anotherFourOfAKind.getKicker())
				.build();
	}
}
