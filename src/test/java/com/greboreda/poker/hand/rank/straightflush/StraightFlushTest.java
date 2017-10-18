package com.greboreda.poker.hand.rank.straightflush;


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

import static com.greboreda.poker.Comparision.LOOSE;
import static com.greboreda.poker.Comparision.TIE;
import static com.greboreda.poker.Comparision.WIN;
import static com.greboreda.poker.card.Suit.CLUBS;
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
				Arguments.of(createStraightFlushHand(CLUBS, SIX, EIGHT, NINE, SEVEN, TEN), TEN),
				Arguments.of(createStraightFlushHand(CLUBS, ACE, TWO, THREE, FOUR, FIVE), FIVE)
		);
	}

	private static Hand createStraightFlushHand(Suit suit, Value value1, Value value2, Value value3, Value value4, Value value5) {
		final Card card1 = new Card(value1, suit);
		final Card card2 = new Card(value2, suit);
		final Card card3 = new Card(value3, suit);
		final Card card4 = new Card(value4, suit);
		final Card card5 = new Card(value5, suit);
		return new Hand(card1, card2, card3, card4, card5);
	}

	@ParameterizedTest
	@MethodSource("retrieveRanksWorseThanStraightFlush")
	void when_comparingWithAnotherRankIfAnotherRankIsWorse_then_resultIsWin(Rank worseRank) {
		final StraightFlush straightFlush = createStraightFlush();
		assertThat(straightFlush.compare(worseRank), is(WIN));
	}

	private static Stream<Rank> retrieveRanksWorseThanStraightFlush() {
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
		assertThat(straightFlush.compare(royalFlush), is(LOOSE));
	}

	@Test
	void when_comparingWithAnotherStraightFlushIfHasBetterHigh_then_resultIsWin() {

		final StraightFlush aStraightFlush = StraightFlush.create()
				.withHigh(KING)
				.build();

		final StraightFlush anotherStraightFlush = StraightFlush.create()
				.withHigh(QUEEN)
				.build();

		assertThat(aStraightFlush.compare(anotherStraightFlush), is(WIN));
	}

	@Test
	void when_comparingWithAnotherStraightFlushIfHasWorseHigh_then_resultIsLoose() {
		final StraightFlush aStraightFlush = StraightFlush.create()
				.withHigh(JACK)
				.build();

		final StraightFlush anotherStraightFlush = StraightFlush.create()
				.withHigh(QUEEN)
				.build();

		assertThat(aStraightFlush.compare(anotherStraightFlush), is(LOOSE));
	}

	@Test
	void when_comparingWithAnotherStraightFlushIfHasSameHigh_then_resultIsTie() {
		final StraightFlush aStraightFlush = StraightFlush.create()
				.withHigh(KING)
				.build();

		final StraightFlush anotherStraightFlush = StraightFlush.create()
				.withHigh(KING)
				.build();

		assertThat(aStraightFlush.compare(anotherStraightFlush), is(TIE));
	}

	@Test
	void when_creatingAStraightFlush_then_highCanNotBeAce() {
		assertThrows(IllegalArgumentException.class, () -> StraightFlush.create()
				.withHigh(ACE)
				.build()
		);
	}

}