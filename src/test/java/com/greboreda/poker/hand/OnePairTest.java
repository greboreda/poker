package com.greboreda.poker.hand;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.Comparision.LOOSE;
import static com.greboreda.poker.Comparision.TIE;
import static com.greboreda.poker.Comparision.WIN;
import static com.greboreda.poker.card.CardBuilder.CardCreator.a;
import static com.greboreda.poker.hand.rank.Rank.RankValue.ONE_PAIR;
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

class OnePairTest {

	@Test
	void should_have_one_pair_if_has_one_values_repeated() {

		final Hand twoPair = new Hand(
				a().TEN.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().SIX.of.DIAMONDS,
				a().SEVEN.of.HEARTS,
				a().JACK.of.CLUBS);

		assertThat(twoPair.getRank(), is(ONE_PAIR));
	}

	@Test
	void should_win_versus_high_card() {
		final Hand aOnePair = aOnePair();
		final Hand aHighCard = aHighCard();
		assertThat(aOnePair.compare(aHighCard), is(WIN));
	}


	@ParameterizedTest
	@MethodSource("retrieveHandsBetterThanOnePair")
	void should_loose_if_compares_with_another_hand_with_better_rank(Hand betterHand) {
		final Hand onePair = aOnePair();
		assertThat(onePair.compare(betterHand), is(LOOSE));
	}
	private static Stream<Hand> retrieveHandsBetterThanOnePair() {
		return Stream.of(
				aRoyalFlush(),
				aFourOfAKind(),
				aRoyalFlush(),
				aFullHouse(),
				aFlush(),
				aStraight(),
				aThreeOfAKind(),
				aTwoPair());
	}

	@Test
	void should_win_if_has_better_pair_than_other_one_pair() {

		final Hand aOnePair = hand()
				.with(a().ACE.of.DIAMONDS)
				.with(a().ACE.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().SEVEN.of.SPADES)
				.with(a().TWO.of.HEARTS);

		final Hand anotherOnePair = hand()
				.with(a().QUEEN.of.CLUBS)
				.with(a().QUEEN.of.HEARTS)
				.with(a().KING.of.HEARTS)
				.with(a().SEVEN.of.CLUBS)
				.with(a().TWO.of.DIAMONDS);

		assertAll(
				() -> assertThat(aOnePair.getRank(), is(ONE_PAIR)),
				() -> assertThat(anotherOnePair.getRank(), is(ONE_PAIR))
		);

		assertThat(aOnePair.compare(anotherOnePair), is(WIN));
	}

	@Test
	void should_loose_if_has_worse_pair_than_other_one_pair() {

		final Hand aOnePair = hand()
				.with(a().QUEEN.of.DIAMONDS)
				.with(a().QUEEN.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().SEVEN.of.SPADES)
				.with(a().TWO.of.HEARTS);

		final Hand anotherOnePair = hand()
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.HEARTS)
				.with(a().SEVEN.of.CLUBS)
				.with(a().TWO.of.DIAMONDS);

		assertAll(
				() -> assertThat(aOnePair.getRank(), is(ONE_PAIR)),
				() -> assertThat(anotherOnePair.getRank(), is(ONE_PAIR))
		);

		assertThat(aOnePair.compare(anotherOnePair), is(LOOSE));
	}

	@Test
	void should_win_if_has_same_pair_but_better_high_kicker_than_other_one_pair() {

		final Hand aOnePair = hand()
				.with(a().ACE.of.DIAMONDS)
				.with(a().ACE.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().SEVEN.of.SPADES)
				.with(a().TWO.of.HEARTS);

		final Hand anotherOnePair = hand()
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().JACK.of.HEARTS)
				.with(a().SEVEN.of.CLUBS)
				.with(a().TWO.of.DIAMONDS);

		assertAll(
				() -> assertThat(aOnePair.getRank(), is(ONE_PAIR)),
				() -> assertThat(anotherOnePair.getRank(), is(ONE_PAIR))
		);

		assertThat(aOnePair.compare(anotherOnePair), is(WIN));
	}

	@Test
	void should_loose_if_has_same_pair_but_worse_high_kicker_than_other_one_pair() {

		final Hand aOnePair = hand()
				.with(a().ACE.of.DIAMONDS)
				.with(a().ACE.of.SPADES)
				.with(a().JACK.of.CLUBS)
				.with(a().SEVEN.of.SPADES)
				.with(a().TWO.of.HEARTS);

		final Hand anotherOnePair = hand()
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.HEARTS)
				.with(a().SEVEN.of.CLUBS)
				.with(a().TWO.of.DIAMONDS);

		assertAll(
				() -> assertThat(aOnePair.getRank(), is(ONE_PAIR)),
				() -> assertThat(anotherOnePair.getRank(), is(ONE_PAIR))
		);

		assertThat(aOnePair.compare(anotherOnePair), is(LOOSE));
	}

	@Test
	void should_win_if_has_same_pair_and_high_kicker_but_better_second_kicker_than_other_one_pair() {

		final Hand aOnePair = hand()
				.with(a().ACE.of.DIAMONDS)
				.with(a().ACE.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().JACK.of.SPADES)
				.with(a().TWO.of.HEARTS);

		final Hand anotherOnePair = hand()
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.HEARTS)
				.with(a().SEVEN.of.CLUBS)
				.with(a().TWO.of.DIAMONDS);

		assertAll(
				() -> assertThat(aOnePair.getRank(), is(ONE_PAIR)),
				() -> assertThat(anotherOnePair.getRank(), is(ONE_PAIR))
		);

		assertThat(aOnePair.compare(anotherOnePair), is(WIN));
	}

	@Test
	void should_loose_if_has_same_pair_and_high_kicker_but_worse_second_kicker_than_other_one_pair() {

		final Hand aOnePair = hand()
				.with(a().ACE.of.DIAMONDS)
				.with(a().ACE.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().SEVEN.of.SPADES)
				.with(a().TWO.of.HEARTS);

		final Hand anotherOnePair = hand()
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.HEARTS)
				.with(a().JACK.of.CLUBS)
				.with(a().TWO.of.DIAMONDS);

		assertAll(
				() -> assertThat(aOnePair.getRank(), is(ONE_PAIR)),
				() -> assertThat(anotherOnePair.getRank(), is(ONE_PAIR))
		);

		assertThat(aOnePair.compare(anotherOnePair), is(LOOSE));
	}

	@Test
	void should_win_if_has_same_pair_high_kicker_and_second_kicker_but_better_third_kicker_than_other_one_pair() {

		final Hand aOnePair = hand()
				.with(a().ACE.of.DIAMONDS)
				.with(a().ACE.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().SEVEN.of.SPADES)
				.with(a().THREE.of.HEARTS);

		final Hand anotherOnePair = hand()
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.HEARTS)
				.with(a().SEVEN.of.CLUBS)
				.with(a().TWO.of.DIAMONDS);

		assertAll(
				() -> assertThat(aOnePair.getRank(), is(ONE_PAIR)),
				() -> assertThat(anotherOnePair.getRank(), is(ONE_PAIR))
		);

		assertThat(aOnePair.compare(anotherOnePair), is(WIN));
	}

	@Test
	void should_loose_if_has_same_pair_high_kicker_and_second_kicker_but_worse_third_kicker_than_other_one_pair() {

		final Hand aOnePair = hand()
				.with(a().ACE.of.DIAMONDS)
				.with(a().ACE.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().SEVEN.of.SPADES)
				.with(a().TWO.of.HEARTS);

		final Hand anotherOnePair = hand()
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.HEARTS)
				.with(a().SEVEN.of.CLUBS)
				.with(a().THREE.of.DIAMONDS);

		assertAll(
				() -> assertThat(aOnePair.getRank(), is(ONE_PAIR)),
				() -> assertThat(anotherOnePair.getRank(), is(ONE_PAIR))
		);

		assertThat(aOnePair.compare(anotherOnePair), is(LOOSE));
	}

	@Test
	void should_tie_if_has_same_pair_high_kicker_second_kicker_and_third_kicker_than_other_one_pair() {

		final Hand aOnePair = hand()
				.with(a().ACE.of.DIAMONDS)
				.with(a().ACE.of.SPADES)
				.with(a().KING.of.CLUBS)
				.with(a().SEVEN.of.SPADES)
				.with(a().TWO.of.HEARTS);

		final Hand anotherOnePair = hand()
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.HEARTS)
				.with(a().SEVEN.of.CLUBS)
				.with(a().TWO.of.DIAMONDS);

		assertAll(
				() -> assertThat(aOnePair.getRank(), is(ONE_PAIR)),
				() -> assertThat(anotherOnePair.getRank(), is(ONE_PAIR))
		);

		assertThat(aOnePair.compare(anotherOnePair), is(TIE));
	}


}
