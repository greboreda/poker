package com.greboreda.poker.hand;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.Comparision.LOOSE;
import static com.greboreda.poker.Comparision.TIE;
import static com.greboreda.poker.Comparision.WIN;
import static com.greboreda.poker.card.CardBuilder.CardCreator.a;
import static com.greboreda.poker.hand.rank.Rank.RankValue.FLUSH;
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

class FlushTest {

	@Test
	void should_have_flush_if_has_all_cards_of_same_suit_and_not_consecutive() {
		final Hand flush = new Hand(
				a().TEN.of.DIAMONDS,
				a().TWO.of.DIAMONDS,
				a().SEVEN.of.DIAMONDS,
				a().THREE.of.DIAMONDS,
				a().KING.of.DIAMONDS);
		assertThat(flush.getRank(), is(FLUSH));
	}

	@ParameterizedTest
	@MethodSource("retrieveHandsWorseThanFlush")
	void should_win_if_compares_with_another_hand_with_worse_rank(Hand worseHand) {
		final Hand flush = aFlush();
		assertThat(flush.compare(worseHand), is(WIN));
	}

	private static Stream<Hand> retrieveHandsWorseThanFlush() {
		return Stream.of(
				aHighCard(),
				aOnePair(),
				aTwoPair(),
				aThreeOfAKind(),
				aStraight()
		);
	}

	@ParameterizedTest
	@MethodSource("retrieveHandsBetterThanFlush")
	void should_loose_if_compares_with_another_hand_with_better_rank(Hand betterHand) {
		final Hand flush = aFlush();
		assertThat(flush.compare(betterHand), is(LOOSE));
	}
	private static Stream<Hand> retrieveHandsBetterThanFlush() {
		return Stream.of(
				aRoyalFlush(),
				aStraightFlush(),
				aFourOfAKind(),
				aFullHouse()
		);
	}

	@Test
	void should_win_if_has_better_high_kicker_than_other_flush() {
		
		final Hand aFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().QUEEN.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SIX.of.SPADES);

		final Hand anotherFlush = hand()
				.with(a().KING.of.SPADES)
				.with(a().QUEEN.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SIX.of.SPADES);

		assertAll(
				() -> assertThat(aFlush.getRank(), is(FLUSH)),
				() -> assertThat(anotherFlush.getRank(), is(FLUSH))
		);

		assertThat(aFlush.compare(anotherFlush), is(WIN));
	}

	@Test
	void should_loose_if_has_worse_high_kicker_than_other_flush() {

		final Hand aFlush = hand()
				.with(a().QUEEN.of.SPADES)
				.with(a().JACK.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SIX.of.SPADES);

		final Hand anotherFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().QUEEN.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SIX.of.SPADES);

		assertAll(
				() -> assertThat(aFlush.getRank(), is(FLUSH)),
				() -> assertThat(anotherFlush.getRank(), is(FLUSH))
		);

		assertThat(aFlush.compare(anotherFlush), is(LOOSE));
	}

	@Test
	void should_win_if_has_same_high_kicker_and_better_second_kicker_than_other_flush() {

		final Hand aFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().QUEEN.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SIX.of.SPADES);

		final Hand anotherFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().JACK.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SIX.of.SPADES);

		assertAll(
				() -> assertThat(aFlush.getRank(), is(FLUSH)),
				() -> assertThat(anotherFlush.getRank(), is(FLUSH))
		);

		assertThat(aFlush.compare(anotherFlush), is(WIN));
	}

	@Test
	void should_loose_if_has_same_high_kicker_and_worse_second_kicker_than_other_flush() {

		final Hand aFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().QUEEN.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SIX.of.SPADES);

		final Hand anotherFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().KING.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SIX.of.SPADES);

		assertAll(
				() -> assertThat(aFlush.getRank(), is(FLUSH)),
				() -> assertThat(anotherFlush.getRank(), is(FLUSH))
		);

		assertThat(aFlush.compare(anotherFlush), is(LOOSE));
	}

	@Test
	void should_win_if_has_same_high_and_second_kickers_but_better_third_kicker_than_other_flush() {

		final Hand aFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().QUEEN.of.SPADES)
				.with(a().JACK.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SIX.of.SPADES);

		final Hand anotherFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().QUEEN.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SIX.of.SPADES);

		assertAll(
				() -> assertThat(aFlush.getRank(), is(FLUSH)),
				() -> assertThat(anotherFlush.getRank(), is(FLUSH))
		);

		assertThat(aFlush.compare(anotherFlush), is(WIN));
	}

	@Test
	void should_loose_if_has_same_high_and_second_kickers_but_worse_third_kicker_than_other_flush() {

		final Hand aFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().QUEEN.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SIX.of.SPADES);

		final Hand anotherFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().QUEEN.of.SPADES)
				.with(a().JACK.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SIX.of.SPADES);

		assertAll(
				() -> assertThat(aFlush.getRank(), is(FLUSH)),
				() -> assertThat(anotherFlush.getRank(), is(FLUSH))
		);

		assertThat(aFlush.compare(anotherFlush), is(LOOSE));
	}

	@Test
	void should_win_if_has_same_high_second_and_third_kickers_but_better_fourth_kicker_than_other_flush() {

		final Hand aFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().QUEEN.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().TWO.of.SPADES);

		final Hand anotherFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().QUEEN.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().SIX.of.SPADES)
				.with(a().TWO.of.SPADES);

		assertAll(
				() -> assertThat(aFlush.getRank(), is(FLUSH)),
				() -> assertThat(anotherFlush.getRank(), is(FLUSH))
		);

		assertThat(aFlush.compare(anotherFlush), is(WIN));
	}

	@Test
	void should_loose_if_has_same_high_second_and_third_kickers_but_worse_fourth_kicker_than_other_flush() {

		final Hand aFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().QUEEN.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SIX.of.SPADES);

		final Hand anotherFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().KING.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().NINE.of.SPADES)
				.with(a().SIX.of.SPADES);

		assertAll(
				() -> assertThat(aFlush.getRank(), is(FLUSH)),
				() -> assertThat(anotherFlush.getRank(), is(FLUSH))
		);

		assertThat(aFlush.compare(anotherFlush), is(LOOSE));
	}

	@Test
	void should_win_if_has_same_high_second_third_and_fourth_kickers_but_better_fifth_kicker_than_other_flush() {

		final Hand aFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().QUEEN.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SIX.of.SPADES);

		final Hand anotherFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().QUEEN.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().TWO.of.SPADES);

		assertAll(
				() -> assertThat(aFlush.getRank(), is(FLUSH)),
				() -> assertThat(anotherFlush.getRank(), is(FLUSH))
		);

		assertThat(aFlush.compare(anotherFlush), is(WIN));
	}

	@Test
	void should_loose_if_has_same_high_second_third_and_fourth_kickers_but_worse_fifth_kicker_than_other_flush() {

		final Hand aFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().QUEEN.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().TWO.of.SPADES);

		final Hand anotherFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().KING.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SIX.of.SPADES);

		assertAll(
				() -> assertThat(aFlush.getRank(), is(FLUSH)),
				() -> assertThat(anotherFlush.getRank(), is(FLUSH))
		);

		assertThat(aFlush.compare(anotherFlush), is(LOOSE));
	}

	@Test
	void should_tie_if_all_kickers_are_same_than_other_flush() {

		final Hand aFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().QUEEN.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SIX.of.SPADES);

		final Hand anotherFlush = hand()
				.with(a().ACE.of.SPADES)
				.with(a().QUEEN.of.SPADES)
				.with(a().TEN.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SIX.of.SPADES);

		assertAll(
				() -> assertThat(aFlush.getRank(), is(FLUSH)),
				() -> assertThat(anotherFlush.getRank(), is(FLUSH))
		);

		assertThat(aFlush.compare(anotherFlush), is(TIE));
	}
}