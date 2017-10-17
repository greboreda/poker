package com.greboreda.poker.hand.rank.straight;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Card;
import com.greboreda.poker.card.Suit;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.Rank;
import com.greboreda.poker.hand.rank.Rank.RankValue;
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
import static com.greboreda.poker.hand.rank.RankRepository.createRoyalFlush;
import static com.greboreda.poker.hand.rank.RankRepository.createStraight;
import static com.greboreda.poker.hand.rank.RankRepository.createStraightFlush;
import static com.greboreda.poker.hand.rank.RankRepository.createThreeOfAKind;
import static com.greboreda.poker.hand.rank.RankRepository.createTwoPair;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

class StraightTest {

	@ParameterizedTest
	@MethodSource("straights")
	void when_handHasAllConsecutiveValuesOfSameSuitButTheyAreNotAceKingQueenJackAndTen_then_hasStraight(Hand straightHand, Value high) {

		final Rank rank = straightHand.getRank();

		assertThat(rank.getRankValue(), is(RankValue.STRAIGHT));
		final Straight straight = (Straight) rank;
		assertAll("striaght is valid",
				() -> assertThat(straight.getHigh(), is(high))
		);
	}

	private static Stream<Arguments> straights() {
		return Stream.of(
				Arguments.of(createStraightHand(SIX, EIGHT, NINE, SEVEN, TEN), TEN),
				Arguments.of(createStraightHand(ACE, TWO, THREE, FOUR, FIVE), FIVE),
				Arguments.of(createStraightHand(ACE, KING, QUEEN, JACK, TEN), ACE)
		);
	}

	private static Hand createStraightHand(Value value1, Value value2, Value value3, Value value4, Value value5) {
		final Card card1 = Card.create().withValue(value1).withSuit(Suit.DIAMONDS).build();
		final Card card2 = Card.create().withValue(value2).withSuit(Suit.SPADES).build();
		final Card card3 = Card.create().withValue(value3).withSuit(Suit.DIAMONDS).build();
		final Card card4 = Card.create().withValue(value4).withSuit(Suit.CLUBS).build();
		final Card card5 = Card.create().withValue(value5).withSuit(Suit.HEARTS).build();
		return new Hand(card1, card2, card3, card4, card5);
	}

	@ParameterizedTest
	@MethodSource("retrieveRanksWorseThanStraight")
	void when_comparingWithAnotherRankIfAnotherRankIsWorse_then_resultIsWin(Rank worseRank) {
		final Straight straight = createStraight();
		assertThat(straight.compare(worseRank), is(Comparision.WIN));
	}

	private static Stream<Rank> retrieveRanksWorseThanStraight() {
		return Stream.of(
				createHighCard(),
				createOnePair(),
				createTwoPair(),
				createThreeOfAKind()
		);
	}

	@ParameterizedTest
	@MethodSource("retrieveRanksBetterThanStraight")
	void when_comparingWithAnotherRankIfAnotherRankIsBetter_then_resultIsLoose(Rank betterRank) {
		final Straight straight = createStraight();
		assertThat(straight.compare(betterRank), is(Comparision.LOOSE));
	}

	private static Stream<Rank> retrieveRanksBetterThanStraight() {
		return Stream.of(
				createRoyalFlush(),
				createStraightFlush(),
				createFourOfAKind(),
				createFullHouse(),
				createFlush()
		);
	}


	@Test
	void when_comparingWithAnotherStraightIfHasBetterHigh_then_resultIsWin() {

		final Straight aStraight = Straight.create()
				.withHigh(KING)
				.build();

		final Straight anotherStraight = Straight.create()
				.withHigh(QUEEN)
				.build();

		assertThat(aStraight.compare(anotherStraight), is(Comparision.WIN));
	}

	@Test
	void when_comparingWithAnotherStraightIfHasWorseHigh_then_resultIsLoose() {
		final Straight aStraight = Straight.create()
				.withHigh(JACK)
				.build();

		final Straight anotherStraight = Straight.create()
				.withHigh(QUEEN)
				.build();

		assertThat(aStraight.compare(anotherStraight), is(Comparision.LOOSE));
	}

	@Test
	void when_comparingWithAnotherStraightIfHasSameHigh_then_resultIsTie() {
		final Straight aStraight = Straight.create()
				.withHigh(KING)
				.build();

		final Straight anotherStraight = Straight.create()
				.withHigh(KING)
				.build();

		assertThat(aStraight.compare(anotherStraight), is(Comparision.TIE));
	}

}