package com.greboreda.poker.hand.rank.fullhouse;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.Rank.RankValue;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.greboreda.poker.hand.HandFactory.createFullHouse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FullHouseCalculatorTest {

	@Test
	void when_handHasThreeCardOfSameValueAndTwoOfAnotherSameValue_then_hasFullHouse() {

		final Hand fullHouseHand = createFullHouse(Value.EIGHT, Value.FIVE);

		final FullHouseCalculator fullHouseCalculator = new FullHouseCalculator();
		final Optional<FullHouse> maybeFullHouse = fullHouseCalculator.calculateRank(fullHouseHand);

		assertTrue(maybeFullHouse.isPresent());
		final FullHouse fullHouse = maybeFullHouse.get();
		assertThat(fullHouse.getRankValue(), is(RankValue.FULL_HOUSE));
		assertThat(fullHouse.getValue(), is(Value.EIGHT));
		assertThat(fullHouse.getOver(), is(Value.FIVE));

	}
}
