package com.greboreda.poker.hand.rank;

import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.Comparision;

import java.util.Optional;

public interface Rank {

	RankValue getRankValue();

	Comparision compare(Rank another);

	enum RankValue {
		ROYAL_FLUSH,
		STRAIGHT_FLUSH,
		FOUR_OF_A_KIND,
		FULL_HOUSE,
		FLUSH,
		STRAIGHT,
		THREE_OF_A_KIND,
		TWO_PAIRS,
		ONE_PAIR,
		HIGH_CARD
	}

	interface RankCalculator<T extends Rank> {
		Optional<T> calculateRank(Hand hand);
	}

}