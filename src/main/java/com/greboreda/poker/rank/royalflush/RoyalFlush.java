package com.greboreda.poker.rank.royalflush;

import com.greboreda.poker.Card.Suit;
import com.greboreda.poker.MatchResult;
import com.greboreda.poker.rank.Rank;

public class RoyalFlush implements Rank {

	public final Suit suit;

	public RoyalFlush(Suit suit) {
		this.suit = suit;
	}

	@Override
	public RankValue getRankValue() {
		return RankValue.ROYAL_FLUSH;
	}

	@Override
	public MatchResult compare(Rank another) {
		if(RankValue.ROYAL_FLUSH.equals(another.getRankValue())) {
			return MatchResult.TIE;
		}
		return MatchResult.WIN;
	}
}
