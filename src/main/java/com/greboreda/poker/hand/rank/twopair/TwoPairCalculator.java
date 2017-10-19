package com.greboreda.poker.hand.rank.twopair;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.RankCalculator;
import org.apache.commons.lang3.Validate;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class TwoPairCalculator implements RankCalculator<TwoPair> {

	@Override
	public Optional<TwoPair> calculateRank(Hand hand) {
		Validate.notNull(hand);
		final Set<Value> valuesRepeatedTwoTimes = hand.findValuesRepeated(2);
		final boolean hasTwoValuesRepeatedTwoTimes = valuesRepeatedTwoTimes.size() == 2;
		if(!hasTwoValuesRepeatedTwoTimes) {
			return Optional.empty();
		}
		final Iterator<Value> iterator = valuesRepeatedTwoTimes.iterator();
		final Value pair1 = iterator.next();
		final Value pair2 = iterator.next();
		final Value kicker = hand.getCardsValues().stream()
				.filter(v -> !v.equals(pair1))
				.filter(v -> !v.equals(pair2))
				.findFirst()
				.get();

		return Optional.of(TwoPair.create()
				.withPair(pair1)
				.withPair(pair2)
				.withKicker(kicker)
				.build());
	}
}
