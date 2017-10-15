package com.greboreda.poker.hand.rank;

import com.greboreda.poker.hand.Hand;

import java.util.Optional;

public interface RankCalculator<T extends Rank> {
	Optional<T> calculateRank(Hand hand);
}
