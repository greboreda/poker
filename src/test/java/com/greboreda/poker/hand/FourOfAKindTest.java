package com.greboreda.poker.hand;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.Comparision.LOOSE;
import static com.greboreda.poker.Comparision.TIE;
import static com.greboreda.poker.Comparision.WIN;
import static com.greboreda.poker.card.CardBuilder.CardCreator.a;
import static com.greboreda.poker.hand.rank.Rank.RankValue.FOUR_OF_A_KIND;
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

class FourOfAKindTest {

	@Test
	void should_have_four_of_a_kind_if_has_four_cards_of_same_value() {
		final Hand fourOfAKind = new Hand(
				a().ACE.of.HEARTS,
				a().ACE.of.CLUBS,
				a().ACE.of.DIAMONDS,
				a().ACE.of.SPADES,
				a().KING.of.DIAMONDS
		);
		assertThat(fourOfAKind.getRank(), is(FOUR_OF_A_KIND));
	}

	@ParameterizedTest
	@MethodSource("retrieveHandsWorseThanFourOfAKind")
	void should_win_if_compares_with_another_hand_with_worse_rank(Hand worseHand) {
		final Hand fourOfAKind = aFourOfAKind();
		assertThat(fourOfAKind.compare(worseHand), is(WIN));
	}

	private static Stream<Hand> retrieveHandsWorseThanFourOfAKind() {
		return Stream.of(
				aHighCard(),
				aOnePair(),
				aTwoPair(),
				aThreeOfAKind(),
				aStraight(),
				aFlush(),
				aFullHouse()
		);
	}

	@ParameterizedTest
	@MethodSource("retrieveHandsBetterThanFourOfAKind")
	void should_loose_if_compares_with_another_hand_with_better_rank(Hand betterHand) {
		final Hand fourOfAKind = aFourOfAKind();
		assertThat(fourOfAKind.compare(betterHand), is(LOOSE));
	}
	private static Stream<Hand> retrieveHandsBetterThanFourOfAKind() {
		return Stream.of(aRoyalFlush(), aStraightFlush());
	}

	@Test
	void should_win_if_has_better_quads_than_other_four_of_a_kind() {
		final Hand aFourOfAKind = hand()
				.with(a().ACE.of.DIAMONDS)
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.DIAMONDS);

		final Hand anotherFourOfAKind = hand()
				.with(a().QUEEN.of.DIAMONDS)
				.with(a().QUEEN.of.SPADES)
				.with(a().QUEEN.of.CLUBS)
				.with(a().QUEEN.of.HEARTS)
				.with(a().TWO.of.DIAMONDS);

		assertAll(
				() -> assertThat(aFourOfAKind.getRank(), is(FOUR_OF_A_KIND)),
				() -> assertThat(anotherFourOfAKind.getRank(), is(FOUR_OF_A_KIND))
		);

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(WIN));
	}

	@Test
	void should_loose_if_has_worse_quads_than_other_four_of_a_kind() {

		final Hand aFourOfAKind = hand()
				.with(a().QUEEN.of.DIAMONDS)
				.with(a().QUEEN.of.SPADES)
				.with(a().QUEEN.of.CLUBS)
				.with(a().QUEEN.of.HEARTS)
				.with(a().TWO.of.DIAMONDS);

		final Hand anotherFourOfAKind = hand()
				.with(a().ACE.of.DIAMONDS)
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.DIAMONDS);

		assertAll(
				() -> assertThat(aFourOfAKind.getRank(), is(FOUR_OF_A_KIND)),
				() -> assertThat(anotherFourOfAKind.getRank(), is(FOUR_OF_A_KIND))
		);

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(LOOSE));
	}

	@Test
	void should_win_if_has_same_quads_but_better_kicker_than_other_four_of_a_kind() {

		final Hand aFourOfAKind = hand()
				.with(a().QUEEN.of.DIAMONDS)
				.with(a().QUEEN.of.SPADES)
				.with(a().QUEEN.of.CLUBS)
				.with(a().QUEEN.of.HEARTS)
				.with(a().JACK.of.DIAMONDS);

		final Hand anotherFourOfAKind = hand()
				.with(a().QUEEN.of.DIAMONDS)
				.with(a().QUEEN.of.SPADES)
				.with(a().QUEEN.of.CLUBS)
				.with(a().QUEEN.of.HEARTS)
				.with(a().TWO.of.DIAMONDS);

		assertAll(
				() -> assertThat(aFourOfAKind.getRank(), is(FOUR_OF_A_KIND)),
				() -> assertThat(anotherFourOfAKind.getRank(), is(FOUR_OF_A_KIND))
		);

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(WIN));
	}

	@Test
	void should_loose_if_has_same_quads_but_worse_kicker_than_other_four_of_a_kind() {

		final Hand aFourOfAKind = hand()
				.with(a().QUEEN.of.DIAMONDS)
				.with(a().QUEEN.of.SPADES)
				.with(a().QUEEN.of.CLUBS)
				.with(a().QUEEN.of.HEARTS)
				.with(a().TWO.of.DIAMONDS);

		final Hand anotherFourOfAKind = hand()
				.with(a().QUEEN.of.DIAMONDS)
				.with(a().QUEEN.of.SPADES)
				.with(a().QUEEN.of.CLUBS)
				.with(a().QUEEN.of.HEARTS)
				.with(a().JACK.of.DIAMONDS);

		assertAll(
				() -> assertThat(aFourOfAKind.getRank(), is(FOUR_OF_A_KIND)),
				() -> assertThat(anotherFourOfAKind.getRank(), is(FOUR_OF_A_KIND))
		);

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(LOOSE));
	}

	@Test
	void should_tie_if_has_same_quads_and_kicker_than_other_four_of_a_kind() {

		final Hand aFourOfAKind = hand()
				.with(a().QUEEN.of.DIAMONDS)
				.with(a().QUEEN.of.SPADES)
				.with(a().QUEEN.of.CLUBS)
				.with(a().QUEEN.of.HEARTS)
				.with(a().TWO.of.DIAMONDS);

		final Hand anotherFourOfAKind = hand()
				.with(a().QUEEN.of.DIAMONDS)
				.with(a().QUEEN.of.SPADES)
				.with(a().QUEEN.of.CLUBS)
				.with(a().QUEEN.of.HEARTS)
				.with(a().TWO.of.DIAMONDS);

		assertAll(
				() -> assertThat(aFourOfAKind.getRank(), is(FOUR_OF_A_KIND)),
				() -> assertThat(anotherFourOfAKind.getRank(), is(FOUR_OF_A_KIND))
		);

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(TIE));
	}

}