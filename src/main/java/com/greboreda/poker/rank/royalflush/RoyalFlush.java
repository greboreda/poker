package com.greboreda.poker.rank.royalflush;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.rank.Rank;

public class RoyalFlush implements Rank {

	@Override
	public RankValue getRankValue() {
		return RankValue.ROYAL_FLUSH;
	}

	@Override
	public Comparision compare(Rank another) {
		if(RankValue.ROYAL_FLUSH.equals(another.getRankValue())) {
			return Comparision.TIE;
		}
		return Comparision.WIN;
	}
}
