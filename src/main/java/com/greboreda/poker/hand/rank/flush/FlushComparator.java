package com.greboreda.poker.hand.rank.flush;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.ComparisionPairs;
import com.greboreda.poker.hand.rank.RankComparator;

class FlushComparator implements RankComparator {

	private final Flush aFlush;
	private final Flush anotherFlush;

	FlushComparator(Flush aFlush, Flush anotherFlush) {
		this.aFlush = aFlush;
		this.anotherFlush = anotherFlush;
	}

	@Override
	public ComparisionPairs<Value> retrieveComparisionPairs() {
		return ComparisionPairs.forClass(Value.class)
				.withPair(aFlush.getHighKicker(), anotherFlush.getHighKicker())
				.withPair(aFlush.getSecondKicker(), anotherFlush.getSecondKicker())
				.withPair(aFlush.getThirdKicker(), anotherFlush.getThirdKicker())
				.withPair(aFlush.getFourthKicker(), anotherFlush.getFourthKicker())
				.withPair(aFlush.getFifthKicker(), anotherFlush.getFifthKicker())
				.build();
	}
	
}
