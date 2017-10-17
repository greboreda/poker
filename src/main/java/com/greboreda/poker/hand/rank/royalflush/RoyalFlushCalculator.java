package com.greboreda.poker.hand.rank.royalflush;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.RankCalculator;
import org.apache.commons.lang3.Validate;

import java.util.EnumSet;
import java.util.Optional;

public class RoyalFlushCalculator implements RankCalculator<RoyalFlush> {

	public static final EnumSet<Value> mandatoryRoyalFlushValues = EnumSet.of(
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
