package com.greboreda.poker.hand.rank.royalflush;

import com.greboreda.poker.hand.rank.Rank;

public class RoyalFlush implements Rank {

	@Override
	public RankValue getRankValue() {
		return RankValue.ROYAL_FLUSH;
	}

}
