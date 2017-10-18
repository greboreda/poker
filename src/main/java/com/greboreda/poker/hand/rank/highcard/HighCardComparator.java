package com.greboreda.poker.hand.rank.highcard;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.ComparisionPairs;
import com.greboreda.poker.hand.rank.RankComparator;

public class HighCardComparator implements RankComparator {

	private final HighCard aHighCard;
	private final HighCard anotherHighCard;

	public HighCardComparator(HighCard aHighCard, HighCard anotherHighCard) {
		this.aHighCard = aHighCard;
		this.anotherHighCard = anotherHighCard;
	}

	@Override
	public ComparisionPairs<Value> retrieveComparisionPairs() {
		return ComparisionPairs.forClass(Value.class)
				.withPair(aHighCard.getHigh(), anotherHighCard.getHigh())
				.withPair(aHighCard.getSecond(), anotherHighCard.getSecond())
				.withPair(aHighCard.getThird(), anotherHighCard.getThird())
				.withPair(aHighCard.getFourth(), anotherHighCard.getFourth())
				.withPair(aHighCard.getFifth(), anotherHighCard.getFifth())
				.build();
	}
}
