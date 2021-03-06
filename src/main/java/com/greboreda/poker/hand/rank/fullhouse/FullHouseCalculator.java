package com.greboreda.poker.hand.rank.fullhouse;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.RankCalculator;
import org.apache.commons.lang3.Validate;

import java.util.Optional;
import java.util.Set;

public class FullHouseCalculator implements RankCalculator<FullHouse> {

	@Override
	public Optional<FullHouse> calculateRank(Hand hand) {
		Validate.notNull(hand);
		final Set<Value> repeatedThreeTimes = hand.findValuesRepeated(3);
		final Set<Value> repeatedTwoTimes = hand.findValuesRepeated(2);
		if(repeatedThreeTimes.size() != 1 || repeatedTwoTimes.size() != 1) {
			return Optional.empty();
		}
		return Optional.of(FullHouse.create()
				.of(repeatedThreeTimes.iterator().next())
				.over(repeatedTwoTimes.iterator().next())
				.build());
	}
}
