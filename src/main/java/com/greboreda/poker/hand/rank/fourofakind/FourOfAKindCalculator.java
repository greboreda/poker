package com.greboreda.poker.hand.rank.fourofakind;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.Rank.RankCalculator;

import java.util.Optional;
import java.util.Set;

public class FourOfAKindCalculator implements RankCalculator<FourOfAKind> {

	@Override
	public Optional<FourOfAKind> calculateRank(Hand hand) {
		final Set<Value> repeatedFourTimes = hand.findValueRepeated(4);
		final Set<Value> onlyOneTime = hand.findValueRepeated(1);
		if(repeatedFourTimes.size() != 1 || onlyOneTime.size() != 1) {
			return Optional.empty();
		}
		final Value value = repeatedFourTimes.iterator().next();
		final Value of = onlyOneTime.iterator().next();

		return Optional.of(FourOfAKind.create()
				.of(value)
				.withKicker(of)
				.build());
	}

}
