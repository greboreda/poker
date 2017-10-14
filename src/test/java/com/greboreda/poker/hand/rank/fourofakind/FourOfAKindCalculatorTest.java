package com.greboreda.poker.hand.rank.fourofakind;

import com.greboreda.poker.card.Suit;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.HandFactory;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Optional;

public class FourOfAKindCalculatorTest {

	@Test
	public void when_handHasFourCardsOfSameValue_then_hasFourOfAKind() {

		final Hand fourOfAKindHand = HandFactory.createFourOfAKind(Value.ACE, Suit.DIAMONDS, Value.KING);

		final FourOfAKindCalculator fourOfAKindCalculator = new FourOfAKindCalculator();
		final Optional<FourOfAKind> maybeFourOfAKind = fourOfAKindCalculator.calculateRank(fourOfAKindHand);

		assertThat(maybeFourOfAKind.isPresent(), is(true));
		final FourOfAKind fourOfAKind = maybeFourOfAKind.get();
		assertThat(fourOfAKind.getValue(), is(Value.ACE));
		assertThat(fourOfAKind.getKicker(), is(Value.KING));
	}

}
