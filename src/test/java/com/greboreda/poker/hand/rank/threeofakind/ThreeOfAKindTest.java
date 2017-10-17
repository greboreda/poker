package com.greboreda.poker.hand.rank.threeofakind;

import com.greboreda.poker.card.Card;
import com.greboreda.poker.card.Suit;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.Rank;
import com.greboreda.poker.hand.rank.Rank.RankValue;
import org.junit.jupiter.api.Test;

import static com.greboreda.poker.card.Value.JACK;
import static com.greboreda.poker.card.Value.SIX;
import static com.greboreda.poker.card.Value.TEN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

class ThreeOfAKindTest {

	@Test
	void when_handHasAValueRepeatedThreeTimesAndAnotherTwoDistinct_then_hasThreeOfAKind() {

		final Card trips1 = Card.create().withValue(TEN).withSuit(Suit.DIAMONDS).build();
		final Card trips2 = Card.create().withValue(TEN).withSuit(Suit.SPADES).build();
		final Card trips3 = Card.create().withValue(TEN).withSuit(Suit.CLUBS).build();
		final Card kicker1 = Card.create().withValue(SIX).withSuit(Suit.HEARTS).build();
		final Card kicker2 = Card.create().withValue(JACK).withSuit(Suit.CLUBS).build();
		final Hand threeOfAKindHand = new Hand(trips1, trips2, trips3, kicker1, kicker2);
		final Rank rank = threeOfAKindHand.getRank();

		assertThat(rank.getRankValue(), is(RankValue.THREE_OF_A_KIND));
		final ThreeOfAKind threeOfAKind = (ThreeOfAKind) rank;
		assertAll("two pais is valid",
				() -> assertThat(threeOfAKind.getValue(), is(TEN)),
				() -> assertThat(threeOfAKind.getHighKicker(), is(JACK)),
				() -> assertThat(threeOfAKind.getLowKicker(), is(SIX))
		);
	}

}