package com.greboreda.poker.hand;

import org.junit.jupiter.api.Test;

import static com.greboreda.poker.card.CardBuilder.CardCreator.a;
import static com.greboreda.poker.card.Suit.HEARTS;
import static com.greboreda.poker.hand.rank.Rank.RankValue.ROYAL_FLUSH;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HandTest {

	@Test
	void testBuildHand() {

		final Hand hand = new Hand(
				a().ACE.of.HEARTS,
				a().TEN.of.HEARTS,
				a().KING.of.HEARTS,
				a().QUEEN.of.HEARTS,
				a().JACK.of.HEARTS
		);
		assertAll("is valid hand",
				() -> assertThat(hand.getDistinctSuits(), hasItem(HEARTS)),
				() -> assertThat(hand.findValuesRepeated(2), empty())
		);
		assertAll("hand rank",
				() -> assertThat(hand.getRank(), is(ROYAL_FLUSH))
		);
	}

	@Test
	void when_notAllCardsAreDifferentEachOther_then_throwError() {

		final Throwable exception = assertThrows(IllegalStateException.class,
				() -> new Hand(
						a().ACE.of.HEARTS,
						a().TEN.of.HEARTS,
						a().KING.of.HEARTS,
						a().QUEEN.of.HEARTS,
						a().ACE.of.HEARTS
				)
		);
		assertThat(exception.getMessage(), is("cards must be different each other"));
	}

}
