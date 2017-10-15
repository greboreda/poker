package com.greboreda.poker.hand.rank.flush;

import com.greboreda.poker.card.Card;
import com.greboreda.poker.card.Suit;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.HandFactory;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FlushCalculatorTest {

	@Test
	void when_handHasAllCardOfSameSuitAndValuesAreNotConsecutive_then_hasFlush() {

		final Suit diamonds = Suit.DIAMONDS;
		final Hand flushHand = HandFactory.createFlush(diamonds, Value.TEN, Value.TWO, Value.SEVEN, Value.THREE, Value.KING);

		final FlushCalculator flushCalculator = new FlushCalculator();

		final Optional<Flush> maybeFlush = flushCalculator.calculateRank(flushHand);

		assertTrue(maybeFlush.isPresent());
		final Flush flush = maybeFlush.get();
		assertAll("flush is valid",
				() -> assertThat(flush.getHighKicker(), Matchers.is(Value.KING)),
				() -> assertThat(flush.getSecondKicker(), Matchers.is(Value.TEN)),
				() -> assertThat(flush.getThirdKicker(), Matchers.is(Value.SEVEN)),
				() -> assertThat(flush.getFourthKicker(), Matchers.is(Value.THREE)),
				() -> assertThat(flush.getFifthKicker(), Matchers.is(Value.TWO))
		);

	}

}