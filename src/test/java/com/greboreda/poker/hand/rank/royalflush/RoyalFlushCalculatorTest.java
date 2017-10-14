package com.greboreda.poker.hand.rank.royalflush;


import com.greboreda.poker.card.Suit;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.HandFactory;
import com.greboreda.poker.hand.rank.Rank.RankValue;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoyalFlushCalculatorTest {

	@Test
	void whenHandHasAceKingQueenJackAndTenOfSameSuit_then_hasRoyalFlush() {

		final Suit aSuit = Suit.CLUBS;
		final Hand royalFlushHand = HandFactory.createRoyalFlush(aSuit);

		final RoyalFlushCalculator royalFlushCalculator = new RoyalFlushCalculator();
		Optional<RoyalFlush> maybeRoyalFlush = royalFlushCalculator.calculateRank(royalFlushHand);

		assertTrue(maybeRoyalFlush.isPresent());
		final RoyalFlush royalFlush = maybeRoyalFlush.get();
		assertThat(royalFlush.getRankValue(), is(RankValue.ROYAL_FLUSH));
	}

}
