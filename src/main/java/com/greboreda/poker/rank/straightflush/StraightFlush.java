package com.greboreda.poker.rank.straightflush;

import com.greboreda.poker.Card.Value;
import com.greboreda.poker.MatchResult;
import com.greboreda.poker.rank.Rank;

public class StraightFlush implements Rank {

	private Value highValue;

	@Override
	public RankValue getRankValue() {
		return RankValue.STRAIGHT_FLUSH;
	}

	@Override
	public MatchResult compare(Rank another) {
		return null;
	}
}
