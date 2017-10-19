package com.greboreda.poker.hand.rank.threeofakind;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.RankCalculator;
import org.apache.commons.lang3.Validate;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class ThreeOfAKindCalculator implements RankCalculator<ThreeOfAKind> {
	@Override
	public Optional<ThreeOfAKind> calculateRank(Hand hand) {
		Validate.notNull(hand);

		final Set<Value> valuesRepeatedThreeTimes = hand.findValuesRepeated(3);
		final boolean hasAValueRepeatedThreeTimes = valuesRepeatedThreeTimes.size() == 1;
		if (!hasAValueRepeatedThreeTimes) {
			return Optional.empty();
		}

		final Value trips = valuesRepeatedThreeTimes.iterator().next();
		final Iterator<Value> sortedIterator = hand.getCardsValues().stream()
				.filter(v -> !v.equals(trips))
				.sorted(Comparator.comparingInt(Value::getWeight).reversed())
				.iterator();

		return Optional.of(ThreeOfAKind.create()
				.of(trips)
				.withKicker(sortedIterator.next())
				.withKicker(sortedIterator.next())
				.build());
	}
}
