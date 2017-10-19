package com.greboreda.poker.hand;

import org.junit.jupiter.api.Test;

import static com.greboreda.poker.card.CardBuilder.CardCreator.a;
import static com.greboreda.poker.card.Suit.HEARTS;
import static com.greboreda.poker.hand.rank.Rank.RankValue.ROYAL_FLUSH;
import static com.greboreda.poker.hand.util.HandBuilder.hand;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HandTest {

	@Test
	void testBuildHand() {

		final Hand hand = hand()
				.with(a().ACE.of.HEARTS)
				.with(a().TEN.of.HEARTS)
				.with(a().KING.of.HEARTS)
				.with(a().QUEEN.of.HEARTS)
				.with(a().JACK.of.HEARTS);

		assertAll("is valid hand",
				() -> assertThat(hand.getDistinctSuits(), hasItem(HEARTS)),
				() -> assertThat(hand.findValuesRepeated(2), empty())
		);
		assertAll("hand rank",
				() -> assertThat(hand.getRank(), is(ROYAL_FLUSH))
		);
	}

	@Test
	void should_all_cards_be_distinct() {

		final Throwable exception = assertThrows(IllegalStateException.class,
				() -> hand()
						.with(a().ACE.of.HEARTS)
						.with(a().TEN.of.HEARTS)
						.with(a().KING.of.HEARTS)
						.with(a().QUEEN.of.HEARTS)
						.with(a().ACE.of.HEARTS)
		);
		assertThat(exception.getMessage(), is("cards must be different each other"));
	}

	@Test
	void test_print_hand() {
		final Hand hand = hand()
				.with(a().EIGHT.of.HEARTS)
				.with(a().JACK.of.DIAMONDS)
				.with(a().TEN.of.SPADES)
				.with(a().QUEEN.of.HEARTS)
				.with(a().ACE.of.SPADES);

		assertThat(hand.toString(), is("🂡 🂽 🃋 🂪 🂸"));
	}

}
