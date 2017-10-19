package com.greboreda.poker.hand;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.Comparision.LOOSE;
import static com.greboreda.poker.Comparision.TIE;
import static com.greboreda.poker.Comparision.WIN;
import static com.greboreda.poker.card.CardBuilder.CardCreator.a;
import static com.greboreda.poker.hand.rank.Rank.RankValue.TWO_PAIR;
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

class TwoPairTest {

	@Test
	void should_have_two_pair_if_has_two_values_repeated() {

		final Hand twoPair = new Hand(
				a().TEN.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().SIX.of.DIAMONDS,
				a().SIX.of.HEARTS,
				a().JACK.of.CLUBS);

		assertThat(twoPair.getRank(), is(TWO_PAIR));
	}

	@ParameterizedTest
	@MethodSource("retrieveHandsWorseThanTwoPair")
	void should_win_if_compares_with_another_hand_with_worse_rank(Hand worseHand) {
		final Hand twoPair = aTwoPair();
		assertThat(twoPair.compare(worseHand), is(WIN));
	}

	private static Stream<Hand> retrieveHandsWorseThanTwoPair() {
		return Stream.of(aOnePair(), aHighCard());
	}

	@ParameterizedTest
	@MethodSource("retrieveHandsBetterThanTwoPair")
	void should_loose_if_compares_with_another_hand_with_better_rank(Hand betterHand) {
		final Hand twoPair = aTwoPair();
		assertThat(twoPair.compare(betterHand), is(LOOSE));
	}
	private static Stream<Hand> retrieveHandsBetterThanTwoPair() {
		return Stream.of(
				aRoyalFlush(),
				aFourOfAKind(),
				aRoyalFlush(),
				aFullHouse(),
				aFlush(),
				aStraight(),
				aThreeOfAKind());
	}

	@Test
	void should_win_if_has_better_high_pair_than_another_two_pair() {

		final Hand aTwoPair = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().QUEEN.of.HEARTS);

		final Hand anotherTwoPair = hand()
				.with(a().TWO.of.SPADES)
				.with(a().TWO.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().QUEEN.of.HEARTS);

		assertAll(
				() -> assertThat(aTwoPair.getRank(), is(TWO_PAIR)),
				() -> assertThat(anotherTwoPair.getRank(), is(TWO_PAIR))
		);

		assertThat(aTwoPair.compare(anotherTwoPair), is(WIN));
	}

	@Test
	void should_loose_if_has_worse_high_pair_than_another_two_pair() {

		final Hand aTwoPair = hand()
				.with(a().TWO.of.SPADES)
				.with(a().TWO.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().QUEEN.of.HEARTS);

		final Hand anotherTwoPair = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().QUEEN.of.HEARTS);

		assertAll(
				() -> assertThat(aTwoPair.getRank(), is(TWO_PAIR)),
				() -> assertThat(anotherTwoPair.getRank(), is(TWO_PAIR))
		);

		assertThat(aTwoPair.compare(anotherTwoPair), is(LOOSE));
	}

	@Test
	void should_win_if_has_same_high_pair_but_better_low_pair_than_other_two_pair() {

		final Hand aTwoPair = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().QUEEN.of.HEARTS);

		final Hand anotherTwoPair = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().TEN.of.SPADES)
				.with(a().TEN.of.CLUBS)
				.with(a().QUEEN.of.HEARTS);

		assertAll(
				() -> assertThat(aTwoPair.getRank(), is(TWO_PAIR)),
				() -> assertThat(anotherTwoPair.getRank(), is(TWO_PAIR))
		);

		assertThat(aTwoPair.compare(anotherTwoPair), is(WIN));
	}

	@Test
	void should_loose_if_has_same_high_pair_but_worse_low_pair_than_other_two_pair() {

		final Hand aTwoPair = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().TWO.of.SPADES)
				.with(a().TWO.of.CLUBS)
				.with(a().QUEEN.of.HEARTS);

		final Hand anotherTwoPair = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().QUEEN.of.HEARTS);

		assertAll(
				() -> assertThat(aTwoPair.getRank(), is(TWO_PAIR)),
				() -> assertThat(anotherTwoPair.getRank(), is(TWO_PAIR))
		);

		assertThat(aTwoPair.compare(anotherTwoPair), is(LOOSE));
	}

	@Test
	void should_win_if_has_same_high_pair_and_low_pair_but_better_kicker_than_other_two_pair() {

		final Hand aTwoPair = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().QUEEN.of.HEARTS);

		final Hand anotherTwoPair = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().JACK.of.HEARTS);

		assertAll(
				() -> assertThat(aTwoPair.getRank(), is(TWO_PAIR)),
				() -> assertThat(anotherTwoPair.getRank(), is(TWO_PAIR))
		);

		assertThat(aTwoPair.compare(anotherTwoPair), is(WIN));
	}

	@Test
	void should_loose_if_has_same_high_pair_and_low_pair_but_worse_kicker_than_other_two_pair() {

		final Hand aTwoPair = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().TEN.of.HEARTS);

		final Hand anotherTwoPair = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().QUEEN.of.HEARTS);

		assertAll(
				() -> assertThat(aTwoPair.getRank(), is(TWO_PAIR)),
				() -> assertThat(anotherTwoPair.getRank(), is(TWO_PAIR))
		);

		assertThat(aTwoPair.compare(anotherTwoPair), is(LOOSE));
	}

	@Test
	void should_tie_if_has_same_high_card_low_pair_and_kicker_than_other_two_pair() {

		final Hand aTwoPair = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().QUEEN.of.HEARTS);

		final Hand anotherTwoPair = hand()
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().QUEEN.of.HEARTS);

		assertAll(
				() -> assertThat(aTwoPair.getRank(), is(TWO_PAIR)),
				() -> assertThat(anotherTwoPair.getRank(), is(TWO_PAIR))
		);

		assertThat(aTwoPair.compare(anotherTwoPair), is(TIE));
	}


}