package com.greboreda.poker.hand.rank.fullhouse;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.ComparisionPairs;
import com.greboreda.poker.hand.rank.RankComparator;

public class FullHouseComparator implements RankComparator {

	private final FullHouse aFullHouse;
	private final FullHouse anotherFullHouse;

	public FullHouseComparator(FullHouse aFullHouse, FullHouse anotherFullHouse) {
		this.aFullHouse = aFullHouse;
		this.anotherFullHouse = anotherFullHouse;
	}

	@Override
	public ComparisionPairs<Value> retrieveComparisionPairs() {
		return ComparisionPairs.forClass(Value.class)
				.withPair(aFullHouse.getTrips(), anotherFullHouse.getTrips())
				.withPair(aFullHouse.getOver(), anotherFullHouse.getOver())
				.build();
	}
}
