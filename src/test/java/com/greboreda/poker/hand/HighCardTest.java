package com.greboreda.poker.hand;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.Comparision.LOOSE;
import static com.greboreda.poker.Comparision.TIE;
import static com.greboreda.poker.Comparision.WIN;
import static com.greboreda.poker.card.CardBuilder.CardCreator.a;
import static com.greboreda.poker.hand.rank.Rank.RankValue.HIGH_CARD;
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

class HighCardTest {

	@Test
	void should_have_high_pair_if_has_not_any_repeated_value_and_has_not_consecutive_values() {

		final Hand highCard = new Hand(
				a().TEN.of.DIAMONDS,
				a().EIGHT.of.SPADES,
				a().SIX.of.DIAMONDS,
				a().SEVEN.of.HEARTS,
				a().JACK.of.CLUBS);

		assertThat(highCard.getRank(), is(HIGH_CARD));
	}

	@ParameterizedTest
	@MethodSource("retrieveHandsBetterThanHighCard")
	void should_loose_if_compares_with_another_hand_that_is_not_high_card(Hand betterHand) {
		final Hand highCard = aHighCard();
		assertThat(highCard.compare(betterHand), is(LOOSE));
	}
	private static Stream<Hand> retrieveHandsBetterThanHighCard() {
		return Stream.of(
				aRoyalFlush(),
				aFourOfAKind(),
				aRoyalFlush(),
				aFullHouse(),
				aFlush(),
				aStraight(),
				aThreeOfAKind(),
				aTwoPair(),
				aOnePair());
	}

	@Test
	void should_win_if_has_better_high_than_other_high_card() {

		final Hand aHighCardHand = new Hand(
				a().ACE.of.DIAMONDS,
				a().EIGHT.of.SPADES,
				a().SIX.of.DIAMONDS,
				a().SEVEN.of.HEARTS,
				a().JACK.of.CLUBS);

		final Hand anotherHighCardHand = new Hand(
				a().TEN.of.DIAMONDS,
				a().EIGHT.of.SPADES,
				a().SIX.of.DIAMONDS,
				a().SEVEN.of.HEARTS,
				a().JACK.of.CLUBS);

		assertAll(
				() -> assertThat(aHighCardHand.getRank(), is(HIGH_CARD)),
				() -> assertThat(anotherHighCardHand.getRank(), is(HIGH_CARD))
		);

		assertThat(aHighCardHand.compare(anotherHighCardHand), is(WIN));
	}

	@Test
	void should_loose_if_has_worse_high_than_other_high_card() {

		final Hand aHighCardHand = new Hand(
				a().TEN.of.DIAMONDS,
				a().EIGHT.of.SPADES,
				a().SIX.of.DIAMONDS,
				a().SEVEN.of.HEARTS,
				a().JACK.of.CLUBS);

		final Hand anotherHighCardHand = new Hand(
				a().ACE.of.DIAMONDS,
				a().EIGHT.of.SPADES,
				a().SIX.of.DIAMONDS,
				a().SEVEN.of.HEARTS,
				a().JACK.of.CLUBS);

		assertAll(
				() -> assertThat(aHighCardHand.getRank(), is(HIGH_CARD)),
				() -> assertThat(anotherHighCardHand.getRank(), is(HIGH_CARD))
		);

		assertThat(aHighCardHand.compare(anotherHighCardHand), is(LOOSE));
	}

	@Test
	void should_win_if_has_same_high_but_better_second_than_other_high_card() {

		final Hand aHighCardHand = new Hand(
				a().ACE.of.CLUBS,
				a().QUEEN.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().EIGHT.of.DIAMONDS,
				a().SIX.of.HEARTS);

		final Hand anotherHighCardHand = new Hand(
				a().ACE.of.CLUBS,
				a().JACK.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().EIGHT.of.DIAMONDS,
				a().SIX.of.HEARTS);

		assertAll(
				() -> assertThat(aHighCardHand.getRank(), is(HIGH_CARD)),
				() -> assertThat(anotherHighCardHand.getRank(), is(HIGH_CARD))
		);

		assertThat(aHighCardHand.compare(anotherHighCardHand), is(WIN));
	}

	@Test
	void should_loose_if_has_same_high_but_worse_second_than_other_high_card() {

		final Hand aHighCardHand = new Hand(
				a().ACE.of.CLUBS,
				a().JACK.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().EIGHT.of.DIAMONDS,
				a().SIX.of.HEARTS);

		final Hand anotherHighCardHand = new Hand(
				a().ACE.of.CLUBS,
				a().QUEEN.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().EIGHT.of.DIAMONDS,
				a().SIX.of.HEARTS);

		assertAll(
				() -> assertThat(aHighCardHand.getRank(), is(HIGH_CARD)),
				() -> assertThat(anotherHighCardHand.getRank(), is(HIGH_CARD))
		);

		assertThat(aHighCardHand.compare(anotherHighCardHand), is(LOOSE));
	}

	@Test
	void should_win_if_has_same_high_and_second_but_better_third_than_other_high_card() {

		final Hand aHighCardHand = new Hand(
				a().ACE.of.CLUBS,
				a().QUEEN.of.DIAMONDS,
				a().JACK.of.SPADES,
				a().EIGHT.of.DIAMONDS,
				a().SIX.of.HEARTS);

		final Hand anotherHighCardHand = new Hand(
				a().ACE.of.CLUBS,
				a().QUEEN.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().EIGHT.of.DIAMONDS,
				a().SIX.of.HEARTS);

		assertAll(
				() -> assertThat(aHighCardHand.getRank(), is(HIGH_CARD)),
				() -> assertThat(anotherHighCardHand.getRank(), is(HIGH_CARD))
		);

		assertThat(aHighCardHand.compare(anotherHighCardHand), is(WIN));
	}

	@Test
	void should_loose_if_has_same_high_and_second_but_worse_third_than_other_high_card() {

		final Hand aHighCardHand = new Hand(
				a().ACE.of.CLUBS,
				a().QUEEN.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().EIGHT.of.DIAMONDS,
				a().SIX.of.HEARTS);

		final Hand anotherHighCardHand = new Hand(
				a().ACE.of.CLUBS,
				a().QUEEN.of.DIAMONDS,
				a().JACK.of.SPADES,
				a().EIGHT.of.DIAMONDS,
				a().SIX.of.HEARTS);

		assertAll(
				() -> assertThat(aHighCardHand.getRank(), is(HIGH_CARD)),
				() -> assertThat(anotherHighCardHand.getRank(), is(HIGH_CARD))
		);

		assertThat(aHighCardHand.compare(anotherHighCardHand), is(LOOSE));
	}

	@Test
	void should_win_if_has_same_high_second_and_third_but_better_fourth_than_other_high_card() {

		final Hand aHighCardHand = new Hand(
				a().ACE.of.CLUBS,
				a().QUEEN.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().NINE.of.DIAMONDS,
				a().SIX.of.HEARTS);

		final Hand anotherHighCardHand = new Hand(
				a().ACE.of.CLUBS,
				a().QUEEN.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().EIGHT.of.DIAMONDS,
				a().SIX.of.HEARTS);

		assertAll(
				() -> assertThat(aHighCardHand.getRank(), is(HIGH_CARD)),
				() -> assertThat(anotherHighCardHand.getRank(), is(HIGH_CARD))
		);

		assertThat(aHighCardHand.compare(anotherHighCardHand), is(WIN));
	}

	@Test
	void should_loose_if_has_same_high_second_and_third_but_worse_fourth_than_other_high_card() {

		final Hand aHighCardHand = new Hand(
				a().ACE.of.CLUBS,
				a().QUEEN.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().EIGHT.of.DIAMONDS,
				a().SIX.of.HEARTS);

		final Hand anotherHighCardHand = new Hand(
				a().ACE.of.CLUBS,
				a().QUEEN.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().NINE.of.DIAMONDS,
				a().SIX.of.HEARTS);

		assertAll(
				() -> assertThat(aHighCardHand.getRank(), is(HIGH_CARD)),
				() -> assertThat(anotherHighCardHand.getRank(), is(HIGH_CARD))
		);

		assertThat(aHighCardHand.compare(anotherHighCardHand), is(LOOSE));
	}

	@Test
	void should_win_if_has_same_high_second_third_and_fourth_but_better_fifth_than_other_high_card() {

		final Hand aHighCardHand = new Hand(
				a().ACE.of.CLUBS,
				a().QUEEN.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().EIGHT.of.DIAMONDS,
				a().SEVEN.of.HEARTS);

		final Hand anotherHighCardHand = new Hand(
				a().ACE.of.CLUBS,
				a().QUEEN.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().EIGHT.of.DIAMONDS,
				a().SIX.of.HEARTS);

		assertAll(
				() -> assertThat(aHighCardHand.getRank(), is(HIGH_CARD)),
				() -> assertThat(anotherHighCardHand.getRank(), is(HIGH_CARD))
		);

		assertThat(aHighCardHand.compare(anotherHighCardHand), is(WIN));
	}

	@Test
	void should_loose_if_has_same_high_second_third_and_fourth_but_worse_fifth_than_other_high_card() {

		final Hand aHighCardHand = new Hand(
				a().ACE.of.CLUBS,
				a().QUEEN.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().EIGHT.of.DIAMONDS,
				a().SIX.of.HEARTS);

		final Hand anotherHighCardHand = new Hand(
				a().ACE.of.CLUBS,
				a().QUEEN.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().EIGHT.of.DIAMONDS,
				a().SEVEN.of.HEARTS);

		assertAll(
				() -> assertThat(aHighCardHand.getRank(), is(HIGH_CARD)),
				() -> assertThat(anotherHighCardHand.getRank(), is(HIGH_CARD))
		);

		assertThat(aHighCardHand.compare(anotherHighCardHand), is(LOOSE));
	}

	@Test
	void should_tie_if_has_same_values_than_other_high_card() {

		final Hand aHighCardHand = new Hand(
				a().TEN.of.DIAMONDS,
				a().EIGHT.of.SPADES,
				a().SIX.of.DIAMONDS,
				a().SEVEN.of.HEARTS,
				a().JACK.of.CLUBS);

		final Hand anotherHighCardHand = new Hand(
				a().TEN.of.DIAMONDS,
				a().EIGHT.of.SPADES,
				a().SIX.of.DIAMONDS,
				a().SEVEN.of.HEARTS,
				a().JACK.of.CLUBS);

		assertAll(
				() -> assertThat(aHighCardHand.getRank(), is(HIGH_CARD)),
				() -> assertThat(anotherHighCardHand.getRank(), is(HIGH_CARD))
		);

		assertThat(aHighCardHand.compare(anotherHighCardHand), is(TIE));
	}

}
