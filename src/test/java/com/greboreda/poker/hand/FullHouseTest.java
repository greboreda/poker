package com.greboreda.poker.hand;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.Comparision.LOOSE;
import static com.greboreda.poker.Comparision.TIE;
import static com.greboreda.poker.Comparision.WIN;
import static com.greboreda.poker.card.CardBuilder.CardCreator.a;
import static com.greboreda.poker.hand.rank.Rank.RankValue.FULL_HOUSE;
import static com.greboreda.poker.hand.util.HandBuilder.hand;
import static com.greboreda.poker.hand.util.HandRepository.aFlush;
import static com.greboreda.poker.hand.util.HandRepository.aFourOfAKind;
import static com.greboreda.poker.hand.util.HandRepository.aFullHouse;
import static com.greboreda.poker.hand.util.HandRepository.aHighCard;
import static com.greboreda.poker.hand.util.HandRepository.aOnePair;
import static com.greboreda.poker.hand.util.HandRepository.aRoyalFlush;
import static com.greboreda.poker.hand.util.HandRepository.aStraight;
import static com.greboreda.poker.hand.util.HandRepository.aStraightFlush;
import static com.greboreda.poker.hand.util.HandRepository.aThreeOfAKind;
import static com.greboreda.poker.hand.util.HandRepository.aTwoPair;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

class FullHouseTest {

	@Test
	void should_have_full_house_if_has_one_value_repeated_three_times_and_another_value_two_times() {
		final Hand fullHouse = new Hand(
				a().EIGHT.of.DIAMONDS,
				a().EIGHT.of.CLUBS,
				a().EIGHT.of.SPADES,
				a().FIVE.of.HEARTS,
				a().FIVE.of.SPADES
		);
		assertThat(fullHouse.getRank(), is(FULL_HOUSE));
	}

	@ParameterizedTest
	@MethodSource("retrieveHandsWorseThanFullHouse")
	void should_win_if_compares_with_another_hand_with_worse_rank(Hand worseHand) {
		final Hand fullHouse = aFullHouse();
		assertThat(fullHouse.compare(worseHand), is(WIN));
	}

	private static Stream<Hand> retrieveHandsWorseThanFullHouse() {
		return Stream.of(
				aHighCard(),
				aOnePair(),
				aTwoPair(),
				aThreeOfAKind(),
				aStraight(),
				aFlush()
		);
	}

	@ParameterizedTest
	@MethodSource("retrieveHandsBetterThanFullHouse")
	void should_loose_if_compares_with_another_hand_with_better_rank(Hand betterHand) {
		final Hand fullHouse = aFullHouse();
		assertThat(fullHouse.compare(betterHand), is(LOOSE));
	}
	private static Stream<Hand> retrieveHandsBetterThanFullHouse() {
		return Stream.of(
				aRoyalFlush(),
				aStraightFlush(),
				aFourOfAKind()
		);
	}

	@Test
	void should_win_if_has_better_trips_than_another_full_house() {

		final Hand aFullHouse = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.DIAMONDS);

		final Hand anotherFullHouse = hand()
				.with(a().QUEEN.of.SPADES)
				.with(a().QUEEN.of.CLUBS)
				.with(a().QUEEN.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.DIAMONDS);

		assertAll(
				() -> assertThat(aFullHouse.getRank(), is(FULL_HOUSE)),
				() -> assertThat(anotherFullHouse.getRank(), is(FULL_HOUSE))
		);

		assertThat(aFullHouse.compare(anotherFullHouse), is(WIN));
	}

	@Test
	void should_loose_if_has_worse_trips_than_another_full_house() {

		final Hand aFullHouse = hand()
				.with(a().QUEEN.of.SPADES)
				.with(a().QUEEN.of.CLUBS)
				.with(a().QUEEN.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.DIAMONDS);

		final Hand anotherFullHouse = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.DIAMONDS);

		assertAll(
				() -> assertThat(aFullHouse.getRank(), is(FULL_HOUSE)),
				() -> assertThat(anotherFullHouse.getRank(), is(FULL_HOUSE))
		);

		assertThat(aFullHouse.compare(anotherFullHouse), is(LOOSE));
	}

	@Test
	void should_win_if_has_same_trips_but_better_pair_than_another_full_house() {

		final Hand aFullHouse = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.DIAMONDS);

		final Hand anotherFullHouse = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().QUEEN.of.SPADES)
				.with(a().QUEEN.of.DIAMONDS);

		assertAll(
				() -> assertThat(aFullHouse.getRank(), is(FULL_HOUSE)),
				() -> assertThat(anotherFullHouse.getRank(), is(FULL_HOUSE))
		);

		assertThat(aFullHouse.compare(anotherFullHouse), is(WIN));
	}

	@Test
	void should_loose_if_has_same_trips_but_worse_pair_than_another_full_house() {

		final Hand aFullHouse = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().QUEEN.of.SPADES)
				.with(a().QUEEN.of.DIAMONDS);

		final Hand anotherFullHouse = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.DIAMONDS);

		assertAll(
				() -> assertThat(aFullHouse.getRank(), is(FULL_HOUSE)),
				() -> assertThat(anotherFullHouse.getRank(), is(FULL_HOUSE))
		);

		assertThat(aFullHouse.compare(anotherFullHouse), is(LOOSE));
	}

	@Test
	void should_tie_if_has_same_trips_and_pair_than_another_full_house() {

		final Hand aFullHouse = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.DIAMONDS);

		final Hand anotherFullHouse = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.DIAMONDS);

		assertAll(
				() -> assertThat(aFullHouse.getRank(), is(FULL_HOUSE)),
				() -> assertThat(anotherFullHouse.getRank(), is(FULL_HOUSE))
		);

		assertThat(aFullHouse.compare(anotherFullHouse), is(TIE));
	}

}