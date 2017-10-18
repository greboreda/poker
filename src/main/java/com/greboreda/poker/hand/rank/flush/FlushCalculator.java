package com.greboreda.poker.hand.rank.flush;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.RankCalculator;
import org.apache.commons.lang3.Validate;

import java.util.Iterator;
import java.util.Optional;

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
		final Iterator<Value> iterator = hand.getCardsValues().stream()
				.iterator();

		return Optional.of(Flush.create()
				.with(iterator.next())
				.with(iterator.next())
				.with(iterator.next())
				.with(iterator.next())
				.with(iterator.next())
				.build());
	}
}
