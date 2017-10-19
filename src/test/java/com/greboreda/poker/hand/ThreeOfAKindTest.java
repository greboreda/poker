package com.greboreda.poker.hand;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.Comparision.LOOSE;
import static com.greboreda.poker.Comparision.TIE;
import static com.greboreda.poker.Comparision.WIN;
import static com.greboreda.poker.card.CardBuilder.CardCreator.a;
import static com.greboreda.poker.hand.rank.Rank.RankValue.THREE_OF_A_KIND;
import static com.greboreda.poker.hand.util.HandBuilder.hand;
import static com.greboreda.poker.hand.util.HandRepository.aFlush;
import static com.greboreda.poker.hand.util.HandRepository.aFourOfAKind;
import static com.greboreda.poker.hand.util.HandRepository.aFullHouse;
import static com.greboreda.poker.hand.util.HandRepository.aHighCard;
import static com.greboreda.poker.hand.util.HandRepository.aOnePair;
import static com.greboreda.poker.hand.util.HandRepository.aRoyalFlush;
import static com.greboreda.poker.hand.util.HandRepository.aStraight;
import static com.greboreda.poker.hand.util.HandRepository.aThreeOfAKind;
import static com.greboreda.poker.hand.util.HandRepository.aTwoPair;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

class ThreeOfAKindTest {

	@Test
	void should_have_three_of_a_kind_if_has_a_value_repeated_three_times_and_other_two_distinct() {
		final Hand threeOfAKind = new Hand(
				a().TEN.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().TEN.of.CLUBS,
				a().SIX.of.HEARTS,
				a().JACK.of.CLUBS);
		assertThat(threeOfAKind.getRank(), is(THREE_OF_A_KIND));
	}

	@ParameterizedTest
	@MethodSource("retrieveHandsWorseThanThreeOfAPair")
	void should_win_if_compares_with_another_hand_with_worse_rank(Hand worseHand) {
		final Hand threeOfAKind = aThreeOfAKind();
		assertThat(threeOfAKind.compare(worseHand), is(WIN));
	}

	private static Stream<Hand> retrieveHandsWorseThanThreeOfAPair() {
		return Stream.of(
				aTwoPair(),
				aOnePair(),
				aHighCard());
	}

	@ParameterizedTest
	@MethodSource("retrieveHandsBetterThanThreeOfAKind")
	void should_loose_if_compares_with_another_hand_with_better_rank(Hand betterHand) {
		final Hand threeOfAKind = aThreeOfAKind();
		assertThat(threeOfAKind.compare(betterHand), is(LOOSE));
	}
	private static Stream<Hand> retrieveHandsBetterThanThreeOfAKind() {
		return Stream.of(
				aRoyalFlush(),
				aFourOfAKind(),
				aRoyalFlush(),
				aFullHouse(),
				aFlush(),
				aStraight());
	}


	@Test
	void should_win_if_has_better_trips_than_other_three_of_a_kind() {

		final Hand aThreeOfAKind = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().ACE.of.CLUBS)
				.with(a().KING.of.SPADES)
				.with(a().QUEEN.of.SPADES);

		final Hand anotherThreeOfAKind = hand()
				.with(a().TWO.of.SPADES)
				.with(a().TWO.of.HEARTS)
				.with(a().TWO.of.CLUBS)
				.with(a().KING.of.SPADES)
				.with(a().QUEEN.of.SPADES);

		assertAll(
				() -> assertThat(aThreeOfAKind.getRank(), is(THREE_OF_A_KIND)),
				() -> assertThat(anotherThreeOfAKind.getRank(), is(THREE_OF_A_KIND))
		);

		assertThat(aThreeOfAKind.compare(anotherThreeOfAKind), is(WIN));
	}

	@Test
	void should_loose_if_has_worse_trips_than_other_three_of_a_kind() {

		final Hand aThreeOfAKind = hand()
				.with(a().TWO.of.SPADES)
				.with(a().TWO.of.HEARTS)
				.with(a().TWO.of.CLUBS)
				.with(a().KING.of.SPADES)
				.with(a().QUEEN.of.SPADES);

		final Hand anotherThreeOfAKind = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().ACE.of.CLUBS)
				.with(a().KING.of.SPADES)
				.with(a().QUEEN.of.SPADES);

		assertAll(
				() -> assertThat(aThreeOfAKind.getRank(), is(THREE_OF_A_KIND)),
				() -> assertThat(anotherThreeOfAKind.getRank(), is(THREE_OF_A_KIND))
		);

		assertThat(aThreeOfAKind.compare(anotherThreeOfAKind), is(LOOSE));
	}

	@Test
	void should_win_if_has_same_trips_but_better_high_kicker_than_other_three_of_a_kind() {

		final Hand aThreeOfAKind = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().ACE.of.CLUBS)
				.with(a().KING.of.SPADES)
				.with(a().QUEEN.of.SPADES);

		final Hand anotherThreeOfAKind = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().ACE.of.CLUBS)
				.with(a().QUEEN.of.SPADES)
				.with(a().TEN.of.SPADES);

		assertAll(
				() -> assertThat(aThreeOfAKind.getRank(), is(THREE_OF_A_KIND)),
				() -> assertThat(anotherThreeOfAKind.getRank(), is(THREE_OF_A_KIND))
		);

		assertThat(aThreeOfAKind.compare(anotherThreeOfAKind), is(WIN));
	}

	@Test
	void should_loose_if_has_same_trips_but_worse_high_kicker_than_other_three_of_a_kind() {

		final Hand aThreeOfAKind = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().ACE.of.CLUBS)
				.with(a().QUEEN.of.SPADES)
				.with(a().NINE.of.SPADES);

		final Hand anotherThreeOfAKind = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().ACE.of.CLUBS)
				.with(a().KING.of.SPADES)
				.with(a().QUEEN.of.SPADES);

		assertAll(
				() -> assertThat(aThreeOfAKind.getRank(), is(THREE_OF_A_KIND)),
				() -> assertThat(anotherThreeOfAKind.getRank(), is(THREE_OF_A_KIND))
		);

		assertThat(aThreeOfAKind.compare(anotherThreeOfAKind), is(LOOSE));
	}

	@Test
	void should_win_if_has_same_trips_and_high_kicker_but_better_low_kicker_than_other_three_of_a_kind() {

		final Hand aThreeOfAKind = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().ACE.of.CLUBS)
				.with(a().KING.of.SPADES)
				.with(a().QUEEN.of.SPADES);

		final Hand anotherThreeOfAKind = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().ACE.of.CLUBS)
				.with(a().KING.of.SPADES)
				.with(a().TEN.of.SPADES);

		assertAll(
				() -> assertThat(aThreeOfAKind.getRank(), is(THREE_OF_A_KIND)),
				() -> assertThat(anotherThreeOfAKind.getRank(), is(THREE_OF_A_KIND))
		);

		assertThat(aThreeOfAKind.compare(anotherThreeOfAKind), is(WIN));
	}

	@Test
	void should_loose_if_has_same_trips_and_high_kicker_but_worse_low_kicker_than_other_three_of_a_kind() {

		final Hand aThreeOfAKind = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().ACE.of.CLUBS)
				.with(a().KING.of.SPADES)
				.with(a().TEN.of.SPADES);

		final Hand anotherThreeOfAKind = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().ACE.of.CLUBS)
				.with(a().KING.of.SPADES)
				.with(a().QUEEN.of.SPADES);

		assertAll(
				() -> assertThat(aThreeOfAKind.getRank(), is(THREE_OF_A_KIND)),
				() -> assertThat(anotherThreeOfAKind.getRank(), is(THREE_OF_A_KIND))
		);

		assertThat(aThreeOfAKind.compare(anotherThreeOfAKind), is(LOOSE));
	}

	@Test
	void should_tie_if_has_same_trips_and_high_kicker_and_low_kicker_than_other_three_of_a_kind() {

		final Hand aThreeOfAKind = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().ACE.of.CLUBS)
				.with(a().KING.of.SPADES)
				.with(a().QUEEN.of.SPADES);

		final Hand anotherThreeOfAKind = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().ACE.of.CLUBS)
				.with(a().KING.of.SPADES)
				.with(a().QUEEN.of.SPADES);

		assertAll(
				() -> assertThat(aThreeOfAKind.getRank(), is(THREE_OF_A_KIND)),
				() -> assertThat(anotherThreeOfAKind.getRank(), is(THREE_OF_A_KIND))
		);

		assertThat(aThreeOfAKind.compare(anotherThreeOfAKind), is(TIE));
	}

}