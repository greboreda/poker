package com.greboreda.poker.hand.rank.flush;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.RankCalculator;
import org.apache.commons.lang3.Validate;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class FlushCalculator implements RankCalculator<Flush> {

	@Override
	public Optional<Flush> calculateRank(Hand hand) {
		Validate.notNull(hand);

		final boolean hasUniqueSuit = hand.getDistinctSuits().size() == 1;
		final boolean allCardsHaveDistinctValue = hand.allCardsHaveDistinctValue();
		final boolean areConsecutive = hand.cardsHaveConsecutiveValue();

		if (!hasUniqueSuit || !allCardsHaveDistinctValue || areConsecutive) {
			return Optional.empty();
		}
		final List<Value> sortedValues = hand.getCardsValues().stream()
				.sorted(Comparator.comparingInt(Value::getWeight).reversed())
				.collect(toList());

		return Optional.of(Flush.create()
				.withKicker(sortedValues.get(0))
				.withKicker(sortedValues.get(1))
				.withKicker(sortedValues.get(2))
				.withKicker(sortedValues.get(3))
				.withKicker(sortedValues.get(4))
				.build());
	}
}
