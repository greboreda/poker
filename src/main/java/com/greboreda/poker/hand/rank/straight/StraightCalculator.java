package com.greboreda.poker.hand.rank.straight;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.RankCalculator;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.greboreda.poker.card.Value.*;

public class StraightCalculator implements RankCalculator<Straight> {

	@Override
	public Optional<Straight> calculateRank(Hand hand) {
		Validate.notNull(hand);
		final boolean notAllCardsAreOfSameSuit = hand.getDistinctSuits().size() != 1;
		final List<Value> values = new ArrayList<>(hand.getCardsValues());
		final boolean areDistinct = Value.areDistinct(values);
		final boolean areConsecutive = Value.areConsecutive(hand.getCardsValues());
		if(notAllCardsAreOfSameSuit && areDistinct && areConsecutive) {
			return Optional.of(Straight.create()
					.withHigh(retrieveStraightHigh(values))
					.build());
		}
		return Optional.empty();
	}

	private Value retrieveStraightHigh(List<Value> values) {
		if(values.containsAll(Arrays.asList(ACE, TWO, THREE, FOUR, FIVE))) {
			return FIVE;
		}
		return values.stream()
				.max(Comparator.comparingInt(Value::getWeight))
				.get();
	}
}
