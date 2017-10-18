package com.greboreda.poker.hand;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.util.HandRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.Comparision.LOOSE;
import static com.greboreda.poker.Comparision.TIE;
import static com.greboreda.poker.Comparision.WIN;
import static com.greboreda.poker.card.CardBuilder.CardCreator.a;
import static com.greboreda.poker.card.CardBuilder.aCard;
import static com.greboreda.poker.card.Suit.CLUBS;
import static com.greboreda.poker.card.Suit.DIAMONDS;
import static com.greboreda.poker.card.Suit.HEARTS;
import static com.greboreda.poker.card.Suit.SPADES;
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
import static com.greboreda.poker.hand.rank.Rank.RankValue.STRAIGHT;
import static com.greboreda.poker.hand.util.HandBuilder.hand;
import static com.greboreda.poker.hand.util.HandRepository.aFlush;
import static com.greboreda.poker.hand.util.HandRepository.aFourOfAKind;
import static com.greboreda.poker.hand.util.HandRepository.aFullHouse;
import static com.greboreda.poker.hand.util.HandRepository.aHighCard;
import static com.greboreda.poker.hand.util.HandRepository.aOnePair;
import static com.greboreda.poker.hand.util.HandRepository.aRoyalFlush;
import static com.greboreda.poker.hand.util.HandRepository.aStraightFlush;
import static com.greboreda.poker.hand.util.HandRepository.aThreeOfAKind;
import static com.greboreda.poker.hand.util.HandRepository.aTwoPair;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

class StraightTest {

	@ParameterizedTest
	@MethodSource("straights")
	void should_have_straight_if_all_values_are_consecutive_but_not_all_of_same_suit(Hand straightHand, Value high) {
			assertThat(straightHand.getRank(), is(STRAIGHT));
	}

	private static Stream<Arguments> straights() {
		return Stream.of(
				Arguments.of(aStraight(SIX, EIGHT, NINE, SEVEN, TEN), TEN),
				Arguments.of(aStraight(ACE, TWO, THREE, FOUR, FIVE), FIVE),
				Arguments.of(aStraight(ACE, KING, QUEEN, JACK, TEN), ACE)
		);
	}

	private static Hand aStraight(Value value1, Value value2, Value value3, Value value4, Value value5) {
		return hand()
				.with(aCard().withValue(value1).withSuit(DIAMONDS).build())
				.with(aCard().withValue(value2).withSuit(CLUBS).build())
				.with(aCard().withValue(value3).withSuit(HEARTS).build())
				.with(aCard().withValue(value4).withSuit(DIAMONDS).build())
				.with(aCard().withValue(value5).withSuit(SPADES).build());
	}

	@ParameterizedTest
	@MethodSource("retrieveHandsWorseThanStraight")
	void should_win_if_compares_with_another_hand_with_worse_rank(Hand worseHand) {
		final Hand straight = HandRepository.aStraight();
		assertThat(straight.compare(worseHand), is(WIN));
	}

	private static Stream<Hand> retrieveHandsWorseThanStraight() {
		return Stream.of(
				aHighCard(),
				aOnePair(),
				aTwoPair(),
				aThreeOfAKind()
		);
	}

	@ParameterizedTest
	@MethodSource("retrieveHandsBetterThanStraight")
	void should_loose_if_compares_with_another_hand_with_better_rank(Hand betterHand) {
		final Hand straight = HandRepository.aStraight();
		assertThat(straight.compare(betterHand), is(LOOSE));
	}

	private static Stream<Hand> retrieveHandsBetterThanStraight() {
		return Stream.of(
				aRoyalFlush(),
				aStraightFlush(),
				aFourOfAKind(),
				aFullHouse(),
				aFlush()
		);
	}

	@Test
	void should_win_if_has_better_high_than_other_straight() {

		final Hand aStraight = hand()
				.with(a().TEN.of.SPADES)
				.with(a().NINE.of.HEARTS)
				.with(a().EIGHT.of.SPADES)
				.with(a().SEVEN.of.SPADES)
				.with(a().SIX.of.SPADES);

		final Hand anotherStraight = hand()
				.with(a().NINE.of.SPADES)
				.with(a().EIGHT.of.HEARTS)
				.with(a().SEVEN.of.SPADES)
				.with(a().SIX.of.SPADES)
				.with(a().FIVE.of.SPADES);

		assertAll(
				() -> assertThat(aStraight.getRank(), is(STRAIGHT)),
				() -> assertThat(anotherStraight.getRank(), is(STRAIGHT))
		);

		assertThat(aStraight.compare(anotherStraight), is(WIN));
	}

	@Test
	void should_loose_if_has_worse_high_than_other_straight() {

		final Hand aStraight = hand()
				.with(a().NINE.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SEVEN.of.CLUBS)
				.with(a().SIX.of.SPADES)
				.with(a().FIVE.of.SPADES);

		final Hand anotherStraight = hand()
				.with(a().TEN.of.SPADES)
				.with(a().NINE.of.SPADES)
				.with(a().EIGHT.of.DIAMONDS)
				.with(a().SEVEN.of.SPADES)
				.with(a().SIX.of.SPADES);

		assertAll(
				() -> assertThat(aStraight.getRank(), is(STRAIGHT)),
				() -> assertThat(anotherStraight.getRank(), is(STRAIGHT))
		);

		assertThat(aStraight.compare(anotherStraight), is(LOOSE));
	}

	@Test
	void should_tie_if_has_same_high_than_other_straight() {

		final Hand aStraight = hand()
				.with(a().TEN.of.SPADES)
				.with(a().NINE.of.CLUBS)
				.with(a().EIGHT.of.SPADES)
				.with(a().SEVEN.of.SPADES)
				.with(a().SIX.of.SPADES);

		final Hand anotherStraight = hand()
				.with(a().TEN.of.SPADES)
				.with(a().NINE.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SEVEN.of.HEARTS)
				.with(a().SIX.of.SPADES);

		assertAll(
				() -> assertThat(aStraight.getRank(), is(STRAIGHT)),
				() -> assertThat(anotherStraight.getRank(), is(STRAIGHT))
		);

		assertThat(aStraight.compare(anotherStraight), is(TIE));
	}

}