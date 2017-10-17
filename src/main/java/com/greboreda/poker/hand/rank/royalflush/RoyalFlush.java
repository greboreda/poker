package com.greboreda.poker.hand.rank.royalflush;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;

import java.util.EnumSet;
import java.util.Set;

public class RoyalFlush implements Rank {

	static final Set<Value> mandatoryRoyalFlushValues = EnumSet.of(
			Value.ACE,
			Value.KING,
			Value.QUEEN,
			Value.JACK,
			Value.TEN
	);

	@Override
	public RankValue getRankValue() {
		return RankValue.ROYAL_FLUSH;
	}

}
