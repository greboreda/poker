package com.greboreda.poker.hand.rank.royalflush;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.hand.rank.RankFactory;
import com.greboreda.poker.hand.rank.fourofakind.FourOfAKind;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class RoyalFlushTest {

	@Test
	void when_comparingRoyalFlushWithAnotherRoyalFlush_then_matchResultIsTie() {

		final RoyalFlush aRoyalFlush = new RoyalFlush();
		final RoyalFlush anotherRoyalFlush = new RoyalFlush();

		assertThat(aRoyalFlush.compare(anotherRoyalFlush), is(Comparision.TIE));
	}

	@Test
	void when_comparingRoyalFlushWithAnotherNotRoyalFlush_then_matchResultIsWin() {

		final RoyalFlush aRoyalFlush = new RoyalFlush();
		final FourOfAKind fourOfAKind = RankFactory.createFourOfAKind();

		assertThat(aRoyalFlush.compare(fourOfAKind), is(Comparision.WIN));
	}

}