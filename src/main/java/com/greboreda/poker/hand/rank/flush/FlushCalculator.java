package com.greboreda.poker.hand.rank.flush;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.Rank.RankCalculator;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class FlushCalculator implements RankCalculator<Flush> {

	@Override
	public Optional<Flush> calculateRank(Hand hand) {
		if(hand.getDistinctSuits().size() != 1) {
			return Optional.empty();
		}
		if(Value.areConsecutive(hand.getCardsValues())) {
			return Optional.empty();
		}
		final List<Value> sortedValues = hand.getCardsValues().stream()
				.sorted(Comparator.comparingInt(Value::getWeight).reversed())
				.collect(toList());
		return Optional.of(Flush.create()
				.withHighKicker(sortedValues.get(0))
				.withSecondKicker(sortedValues.get(1))
				.withThirdKicker(sortedValues.get(2))
				.withFourthKicker(sortedValues.get(3))
				.withFifthKicker(sortedValues.get(4))
				.build());
	}
}
