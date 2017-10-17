package com.greboreda.poker.hand.rank.straight;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.RankCalculator;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

import static com.greboreda.poker.card.Value.ACE;
import static com.greboreda.poker.card.Value.FIVE;
import static com.greboreda.poker.card.Value.FOUR;
import static com.greboreda.poker.card.Value.THREE;
import static com.greboreda.poker.card.Value.TWO;

public class StraightCalculator implements RankCalculator<Straight> {

	@Override
	public Optional<Straight> calculateRank(Hand hand) {
		Validate.notNull(hand);
		final boolean allCardsAreOfSameSuit = hand.getDistinctSuits().size() == 1;
		final Boolean areConsecutive = hand.cardsHaveConsecutiveValue();
		final boolean allCardsHaveDistinctValue = hand.allCardsHaveDistinctValue();

		if (allCardsAreOfSameSuit || !areConsecutive || !allCardsHaveDistinctValue) {
			return Optional.empty();
		}
		return Optional.of(Straight.create()
				.withHigh(retrieveStraightHigh(hand))
				.build());
	}

	private Value retrieveStraightHigh(Hand hand) {
		final Set<Value> values = hand.getCardsValues();
		if(!values.contains(ACE)) {
			return values.stream()
					.max(Comparator.comparingInt(Value::getWeight))
					.get();
		}
		if(values.containsAll(Arrays.asList(ACE, TWO, THREE, FOUR, FIVE))) {
			return FIVE;
		}
		return values.stream()
				.max(Comparator.comparingInt(Value::getWeight))
				.get();
	}
}
