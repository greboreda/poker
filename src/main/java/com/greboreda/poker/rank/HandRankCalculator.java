package com.greboreda.poker.rank;

import com.greboreda.poker.Hand;
import com.greboreda.poker.rank.Rank.RankCalculator;
import com.greboreda.poker.rank.fourofakind.FourOfAKindCalculator;
import com.greboreda.poker.rank.fullhouse.FullHouseCalculator;
import com.greboreda.poker.rank.royalflush.RoyalFlushCalculator;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class HandRankCalculator {

	private static final List<RankCalculator> calculators = new LinkedList<>();
	static {
		calculators.add(new RoyalFlushCalculator());
		calculators.add(new FourOfAKindCalculator());
		calculators.add(new FullHouseCalculator());
	}

	public Rank calculateHandRank(Hand hand) {
		for(RankCalculator calculator : calculators) {
			final Optional<Rank> rank = calculator.calculateRank(hand);
			if(rank.isPresent()) {
				return rank.get();
			}
		}
		return null;
	}

}
