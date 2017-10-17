package com.greboreda.poker.hand.rank.straightflush;


import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Card;
import com.greboreda.poker.card.Suit;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.Rank;
import com.greboreda.poker.hand.rank.Rank.RankValue;
import com.greboreda.poker.hand.rank.RankRepository;
import com.greboreda.poker.hand.rank.royalflush.RoyalFlush;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.card.Value.ACE;
import static com.greboreda.poker.card.Value.EIGHT;
import static com.greboreda.poker.card.Value.FIVE;
import static com.greboreda.poker.card.Value.FOUR;
import static com.greboreda.poker.card.Value.JACK;
import static com.greboreda.poker.card.Value.KING;
import static com.greboreda.poker.card.Value.NINE;
import static com.greboreda.poker.card.Value.QUEEN;
import static com.greboreda.poker.card.Value.SEVEN;
import static com.greboreda.poker.card.Value.SIX;
import static com.greboreda.poker.card.Value.TEN;
import static com.greboreda.poker.card.Value.THREE;
import static com.greboreda.poker.card.Value.TWO;
import static com.greboreda.poker.hand.rank.RankRepository.createFlush;
import static com.greboreda.poker.hand.rank.RankRepository.createFourOfAKind;
import static com.greboreda.poker.hand.rank.RankRepository.createFullHouse;
import static com.greboreda.poker.hand.rank.RankRepository.createHighCard;
import static com.greboreda.poker.hand.rank.RankRepository.createOnePair;
import static com.greboreda.poker.hand.rank.RankRepository.createStraight;
import static com.greboreda.poker.hand.rank.RankRepository.createStraightFlush;
import static com.greboreda.poker.hand.rank.RankRepository.createThreeOfAKind;
import static com.greboreda.poker.hand.rank.RankRepository.createTwoPair;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StraightFlushTest {

	@ParameterizedTest
	@MethodSource("straightFlushes")
	void when_handHasAllConsecutiveValuesOfSameSuitButTheyAreNotAceKingQueenJackAndTen_then_hasStraightFlush(Hand straightFlushHand, Value high) {

		final Rank rank = straightFlushHand.getRank();

		assertThat(rank.getRankValue(), is(RankValue.STRAIGHT_FLUSH));
		final StraightFlush straightFlush = (StraightFlush) rank;
		assertAll("striaght flush is valid",
				() -> assertThat(straightFlush.getHigh(), is(high))
		);
	}

	private static Stream<Arguments> straightFlushes() {
		return Stream.of(
				Arguments.of(createStraightFlushHand(Suit.CLUBS, SIX, EIGHT, NINE, SEVEN, TEN), TEN),
				Arguments.of(createStraightFlushHand(Suit.CLUBS, ACE, TWO, THREE, FOUR, FIVE), FIVE)
		);
	}

	private static Hand createStraightFlushHand(Suit suit, Value value1, Value value2, Value value3, Value value4, Value value5) {
		final Card card1 = Card.create().withValue(value1).withSuit(suit).build();
		final Card card2 = Card.create().withValue(value2).withSuit(suit).build();
		final Card card3 = Card.create().withValue(value3).withSuit(suit).build();
		final Card card4 = Card.create().withValue(value4).withSuit(suit).build();
		final Card card5 = Card.create().withValue(value5).withSuit(suit).build();
		return new Hand(card1, card2, card3, card4, card5);
	}

	@ParameterizedTest
	@MethodSource("straightFlushWins")
	void when_comparingWithAnotherRankIfAnotherRankIsWorse_then_resultIsWin(Rank rankAgainstWin) {
		final StraightFlush straightFlush = createStraightFlush();
		assertThat(straightFlush.compare(rankAgainstWin), is(Comparision.WIN));
	}

	private static Stream<Rank> straightFlushWins() {
		return Stream.of(
				createHighCard(),
				createOnePair(),
				createTwoPair(),
				createThreeOfAKind(),
				createStraight(),
				createFlush(),
				createFullHouse(),
				createFourOfAKind()
		);
	}

	@Test
	void when_comparingWithRoyalFlush_then_resultIsLoose() {
		final StraightFlush straightFlush = createStraightFlush();
		final RoyalFlush royalFlush = RankRepository.createRoyalFlush();
		assertThat(straightFlush.compare(royalFlush), is(Comparision.LOOSE));
	}

	@Test
	void when_comparingWithAnotherStraightFlushIfHasBetterHigh_then_resultIsWin() {

		final StraightFlush aStraightFlush = StraightFlush.create()
				.withHigh(KING)
				.build();

		final StraightFlush anotherStraightFlush = StraightFlush.create()
				.withHigh(QUEEN)
				.build();

		assertThat(aStraightFlush.compare(anotherStraightFlush), is(Comparision.WIN));
	}

	@Test
	void when_comparingWithAnotherStraightFlushIfHasWorseHigh_then_resultIsLoose() {
		final StraightFlush aStraightFlush = StraightFlush.create()
				.withHigh(JACK)
				.build();

		final StraightFlush anotherStraightFlush = StraightFlush.create()
				.withHigh(QUEEN)
				.build();

		assertThat(aStraightFlush.compare(anotherStraightFlush), is(Comparision.LOOSE));
	}

	@Test
	void when_comparingWithAnotherStraightFlushIfHasSameHigh_then_resultIsTie() {
		final StraightFlush aStraightFlush = StraightFlush.create()
				.withHigh(KING)
				.build();

		final StraightFlush anotherStraightFlush = StraightFlush.create()
				.withHigh(KING)
				.build();

		assertThat(aStraightFlush.compare(anotherStraightFlush), is(Comparision.TIE));
	}

	@Test
	void when_creatingAStraightFlush_then_highCanNotBeAce() {
		assertThrows(IllegalArgumentException.class, () -> StraightFlush.create()
				.withHigh(ACE)
				.build()
		);
	}

}