package com.greboreda.poker.hand.rank.onepair;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.RankCalculator;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class OnePairCalculator implements RankCalculator<OnePair> {
	@Override
	public Optional<OnePair> calculateRank(Hand hand) {
		final Set<Value> valuesRepeatedTwoTimes = hand.findValuesRepeated(2);
		final boolean hasAValueTwoTimes = valuesRepeatedTwoTimes.size() == 1;
		final boolean hasTrips = hand.findValuesRepeated(3).size() != 0;
		final boolean allCardsAreOfSameSuit = hand.getDistinctSuits().size() == 1;
		final boolean areConsecutive = hand.cardsHaveConsecutiveValue();
		if (!hasAValueTwoTimes || hasTrips || allCardsAreOfSameSuit || areConsecutive) {
			return Optional.empty();
		} else {
			final Value of = valuesRepeatedTwoTimes.iterator().next();
			final Iterator<Value> iterator = hand.getCardsValues().stream()
					.filter(v -> !v.equals(of))
					.sorted(Comparator.comparingInt(Value::getWeight).reversed())
					.iterator();

			return Optional.of(OnePair.create()
					.of(of)
					.withHighKiker(iterator.next())
					.withSecondKicker(iterator.next())
					.withThirdKicker(iterator.next())
					.build());
		}
	}
}
