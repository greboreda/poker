package com.greboreda.poker.hand.rank.highcard;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.RankCalculator;

import java.util.Iterator;
import java.util.Optional;

public class HighCardCalculator implements RankCalculator<HighCard> {
	@Override
	public Optional<HighCard> calculateRank(Hand hand) {
		final boolean allValuesAreDifferent = hand.allCardsHaveDistinctValue();
		final boolean areConsecutive = hand.cardsHaveConsecutiveValue();
		final boolean allCardsAreOfSameSuit = hand.getDistinctSuits().size() == 1;
		if (!allValuesAreDifferent || areConsecutive || allCardsAreOfSameSuit) {
			return Optional.empty();
		}
		final Iterator<Value> iterator = hand.getCardsValues().stream().iterator();
		return Optional.of(HighCard.create()
				.with(iterator.next())
				.with(iterator.next())
				.with(iterator.next())
				.with(iterator.next())
				.with(iterator.next())
				.build());
	}
}
