package com.greboreda.poker.rank.royalflush;


import com.greboreda.poker.card.Suit;
import com.greboreda.poker.Hand;
import com.greboreda.poker.HandFactory;
import com.greboreda.poker.rank.Rank.RankValue;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RoyalFlushCalculatorTest {

	@Test
	public void whenHandHasAceKingQueenJackAndTenOfSameSuit_then_hasRoyalFlush() {

		final Suit aSuit = Suit.CLUBS;
		final Hand royalFlushHand = HandFactory.createRoyalFlush(aSuit);

		final RoyalFlushCalculator royalFlushCalculator = new RoyalFlushCalculator();
		Optional<RoyalFlush> maybeRoyalFlush = royalFlushCalculator.calculateRank(royalFlushHand);

		assertThat(maybeRoyalFlush.isPresent(), is(true));
		final RoyalFlush royalFlush = maybeRoyalFlush.get();
		assertThat(royalFlush.getRankValue(), is(RankValue.ROYAL_FLUSH));
	}

}
