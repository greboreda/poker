package com.greboreda.poker.hand.rank.threeofakind;

import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.Rank;
import org.junit.jupiter.api.Test;

import static com.greboreda.poker.card.CardBuilder.CardCreator.a;
import static com.greboreda.poker.card.Value.JACK;
import static com.greboreda.poker.card.Value.SIX;
import static com.greboreda.poker.card.Value.TEN;
import static com.greboreda.poker.hand.rank.Rank.RankValue.THREE_OF_A_KIND;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

class ThreeOfAKindTest {

	@Test
	void when_handHasAValueRepeatedThreeTimesAndAnotherTwoDistinct_then_hasThreeOfAKind() {

		final Hand threeOfAKindHand = new Hand(
				a().TEN.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().TEN.of.CLUBS,
				a().SIX.of.HEARTS,
				a().JACK.of.CLUBS
		);
		final Rank rank = threeOfAKindHand.getRank();

		assertThat(rank.getRankValue(), is(THREE_OF_A_KIND));
		final ThreeOfAKind threeOfAKind = (ThreeOfAKind) rank;
		assertAll("two pais is valid",
				() -> assertThat(threeOfAKind.getValue(), is(TEN)),
				() -> assertThat(threeOfAKind.getHighKicker(), is(JACK)),
				() -> assertThat(threeOfAKind.getLowKicker(), is(SIX))
		);
	}

}