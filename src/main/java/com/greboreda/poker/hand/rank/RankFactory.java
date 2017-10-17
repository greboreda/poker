package com.greboreda.poker.hand.rank;

import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.flush.FlushCalculator;
import com.greboreda.poker.hand.rank.fourofakind.FourOfAKindCalculator;
import com.greboreda.poker.hand.rank.fullhouse.FullHouseCalculator;
import com.greboreda.poker.hand.rank.royalflush.RoyalFlushCalculator;
import com.greboreda.poker.hand.rank.straight.StraightCalculator;
import com.greboreda.poker.hand.rank.straightflush.StraightFlushCalculator;
import com.greboreda.poker.hand.rank.threeofakind.ThreeOfAKindCalculator;
import com.greboreda.poker.hand.rank.twopair.TwoPairCalculator;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class RankFactory {

	private static final Set<RankCalculator<? extends Rank>> rankCalculators = new LinkedHashSet<>();
	static {
		rankCalculators.add(new RoyalFlushCalculator());
		rankCalculators.add(new StraightFlushCalculator());
		rankCalculators.add(new FourOfAKindCalculator());
		rankCalculators.add(new FullHouseCalculator());
		rankCalculators.add(new FlushCalculator());
		rankCalculators.add(new StraightCalculator());
		rankCalculators.add(new ThreeOfAKindCalculator());
		rankCalculators.add(new TwoPairCalculator());
	}

	public static Rank retrieveRank(Hand hand) {
		return rankCalculators.stream()
				.map(c -> c.calculateRank(hand))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.findFirst()
				.orElseThrow(IllegalStateException::new);
	}


}
