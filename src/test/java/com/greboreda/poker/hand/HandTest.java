package com.greboreda.poker.hand;

import com.greboreda.poker.card.Card;
import com.greboreda.poker.card.Suit;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank.RankValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HandTest {

	@Test
	void testBuildHand() {

		final Card card1 = Card.create().withValue(Value.ACE).withSuit(Suit.HEARTS).build();
		final Card card2 = Card.create().withValue(Value.TEN).withSuit(Suit.HEARTS).build();
		final Card card3 = Card.create().withValue(Value.KING).withSuit(Suit.HEARTS).build();
		final Card card4 = Card.create().withValue(Value.QUEEN).withSuit(Suit.HEARTS).build();
		final Card card5 = Card.create().withValue(Value.JACK).withSuit(Suit.HEARTS).build();

		final Hand hand = new Hand(card1, card2, card3, card4, card5);
		assertAll("is valid hand",
				() -> assertThat(hand.getDistinctSuits(), hasItem(Suit.HEARTS)),
				() -> assertThat(hand.findValueRepeated(2), empty())
		);
		assertAll("hand rank",
				() -> assertThat(hand.getRank().getRankValue(), is(RankValue.ROYAL_FLUSH))
		);
	}

/*
	@ParameterizedTest
	@MethodSource("")
	void foo() {

	}

	private static Stream<Arguments> a() {
		return Stream.of(
				Arguments.of()
		);
	}
*/


	@Test
	void when_notAllCardsAreDifferentEachOther_then_throwError() {

		final Card card1 = Card.create().withValue(Value.ACE).withSuit(Suit.HEARTS).build();
		final Card card2 = Card.create().withValue(Value.TEN).withSuit(Suit.HEARTS).build();
		final Card card3 = Card.create().withValue(Value.KING).withSuit(Suit.HEARTS).build();
		final Card card4 = Card.create().withValue(Value.QUEEN).withSuit(Suit.HEARTS).build();
		final Card card5 = Card.create().withValue(Value.ACE).withSuit(Suit.HEARTS).build();

		final Throwable exception = assertThrows(IllegalStateException.class, () -> new Hand(card1, card2, card3, card4, card5));
		assertThat(exception.getMessage(), is("cards must be different each other"));
	}

}
