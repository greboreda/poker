package com.greboreda.poker.hand.rank.straightflush;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.RankCalculator;
import com.greboreda.poker.hand.rank.royalflush.RoyalFlushCalculator;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class StraightFlushCalculator implements RankCalculator<StraightFlush> {

	@Override
	public Optional<StraightFlush> calculateRank(Hand hand) {
		Validate.notNull(hand);
		final boolean allCardsAreOfSameSuit = hand.getDistinctSuits().size() == 1;
		final List<Value> values = new ArrayList<>(hand.getCardsValues());
		final boolean areConsecutive = Value.areDistinctAndConsecutive(values);
		final boolean containsAllRoyalFlushValues = values.containsAll(RoyalFlushCalculator.mandatoryRoyalFlushValues);
		if(allCardsAreOfSameSuit && areConsecutive && !containsAllRoyalFlushValues) {
			final Value high = values.stream()
					.filter(v -> !Value.ACE.equals(v))
					.max(Comparator.comparingInt(Value::getWeight))
					.get();
			return Optional.of(StraightFlush.create()
					.withHigh(high)
					.build());
		}
		return Optional.empty();
	}

}
