package com.greboreda.poker.hand.rank.twopair;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.RankCalculator;
import org.apache.commons.lang3.Validate;

import java.util.Comparator;
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
		final Value highPair = valuesRepeatedTwoTimes.stream()
				.max(Comparator.comparingInt(Value::getWeight))
				.get();
		final Value lowPair = valuesRepeatedTwoTimes.stream()
				.min(Comparator.comparingInt(Value::getWeight))
				.get();
		final Value kicker = hand.getCardsValues().stream()
				.filter(v -> !valuesRepeatedTwoTimes.contains(v))
				.findFirst()
				.get();

		return Optional.of(TwoPair.create()
				.withHighPair(highPair)
				.withLowPair(lowPair)
				.withKicker(kicker)
				.build());
	}
}
