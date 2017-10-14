package com.greboreda.poker.rank.fullhouse;

import com.greboreda.poker.Card.Value;
import com.greboreda.poker.Hand;
import com.greboreda.poker.rank.Rank.RankValue;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.greboreda.poker.HandFactory.createFullHouse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FullHouseCalculatorTest {

	@Test
	public void when_handHasThreeCardOfSameValueAndTwoOfAnotherSameValue_then_hasFullHouse() {

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
