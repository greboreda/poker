package com.greboreda.poker.hand.rank;

import com.greboreda.poker.hand.Hand;

import java.util.Optional;

public interface RankCalculator<R extends Rank> {
	Optional<R> calculateRank(Hand hand);
}
