package com.greboreda.poker.rank.royalflush;

import com.greboreda.poker.Card.Suit;
import com.greboreda.poker.Card.Value;
import com.greboreda.poker.Hand;
import com.greboreda.poker.rank.Rank.RankCalculator;
import org.apache.commons.lang3.Validate;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public class RoyalFlushCalculator implements RankCalculator<RoyalFlush> {

	private static final Set<Value> mandatoryRoyalFlushValues = EnumSet.of(
			Value.ACE,
			Value.KING,
			Value.QUEEN,
			Value.JACK,
			Value.TEN
	);

	@Override
	public Optional<RoyalFlush> calculateRank(Hand hand) {
		Validate.notNull(hand);
		final boolean containsMandatoryValues = hand.getCardsValues().containsAll(mandatoryRoyalFlushValues);
		final boolean allCardsHaveSameSuit = hand.getDistinctSuits().size() == 1;
		if(containsMandatoryValues && allCardsHaveSameSuit) {
			return Optional.of(new RoyalFlush());
		} else {
			return Optional.empty();
		}
	}



}
