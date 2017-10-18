package com.greboreda.poker.hand.rank.twopair;

import com.greboreda.poker.hand.Hand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.Comparision.LOOSE;
import static com.greboreda.poker.Comparision.TIE;
import static com.greboreda.poker.Comparision.WIN;
import static com.greboreda.poker.card.CardBuilder.CardCreator.a;
import static com.greboreda.poker.card.Value.ACE;
import static com.greboreda.poker.card.Value.FOUR;
import static com.greboreda.poker.card.Value.KING;
import static com.greboreda.poker.card.Value.QUEEN;
import static com.greboreda.poker.card.Value.TEN;
import static com.greboreda.poker.card.Value.THREE;
import static com.greboreda.poker.card.Value.TWO;
import static com.greboreda.poker.hand.rank.Rank.RankValue.TWO_PAIR;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

class TwoPairTest {

	@Test
	void when_handHasTwoCardsRepeatedTwoTimes_then_hasTwoPairRank() {

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
	void when_comparingWithAnotherTwoPairIfHasSameHighPairAndSameLowPairButWorseKicker_then_resultIsLoose() {

		final TwoPair aTwoPair = TwoPair.create()
				.withHighPair(FOUR)
				.withLowPair(THREE)
				.withKicker(TWO)
				.build();

		final TwoPair anotherTwoPair = TwoPair.create()
				.withHighPair(FOUR)
				.withLowPair(THREE)
				.withKicker(QUEEN)
				.build();

		assertThat(aTwoPair.compare(anotherTwoPair), is(LOOSE));
	}


	@Test
	void when_comparingWithAnotherTwoPairIfHasSameHighPairAndSameLowPairButBetterKicker_then_resultIsWin() {
		final TwoPair aTwoPair = TwoPair.create()
				.withHighPair(FOUR)
				.withLowPair(THREE)
				.withKicker(ACE)
				.build();

		final TwoPair anotherTwoPair = TwoPair.create()
				.withHighPair(FOUR)
				.withLowPair(THREE)
				.withKicker(QUEEN)
				.build();

		assertThat(aTwoPair.compare(anotherTwoPair), is(WIN));
	}


	@Test
	void when_comparingWithAnotherTwoPairIfHasSameHighPairButBetterLowPair_then_resultIsWin() {
		final TwoPair aTwoPair = TwoPair.create()
				.withHighPair(ACE)
				.withLowPair(KING)
				.withKicker(QUEEN)
				.build();

		final TwoPair anotherTwoPair = TwoPair.create()
				.withHighPair(ACE)
				.withLowPair(THREE)
				.withKicker(QUEEN)
				.build();

		assertThat(aTwoPair.compare(anotherTwoPair), is(WIN));
	}


	@Test
	void when_comparingWithAnotherTwoPairIfHasSameHighPairButWorseLowPair_then_resultIsLoose() {
		final TwoPair aTwoPair = TwoPair.create()
				.withHighPair(KING)
				.withLowPair(TWO)
				.withKicker(QUEEN)
				.build();

		final TwoPair anotherTwoPair = TwoPair.create()
				.withHighPair(KING)
				.withLowPair(THREE)
				.withKicker(QUEEN)
				.build();

		assertThat(aTwoPair.compare(anotherTwoPair), is(LOOSE));
	}


	@Test
	void when_comparingWithAnotherTwoPairIfHasWorseHighPair_then_resultIsLoose() {
		final TwoPair aTwoPair = TwoPair.create()
				.withHighPair(THREE)
				.withLowPair(TWO)
				.withKicker(QUEEN)
				.build();

		final TwoPair anotherTwoPair = TwoPair.create()
				.withHighPair(TEN)
				.withLowPair(TWO)
				.withKicker(QUEEN)
				.build();

		assertThat(aTwoPair.compare(anotherTwoPair), is(LOOSE));
	}

	@Test
	void when_comparingWithAnotherTwoPairIfHasBetterHighPair_then_resultIsWin() {
		final TwoPair aTwoPair = TwoPair.create()
				.withHighPair(ACE)
				.withLowPair(KING)
				.withKicker(QUEEN)
				.build();

		final TwoPair anotherTwoPair = TwoPair.create()
				.withHighPair(TEN)
				.withLowPair(THREE)
				.withKicker(QUEEN)
				.build();

		assertThat(aTwoPair.compare(anotherTwoPair), is(WIN));
	}

	@Test
	void when_comparingWithAnotherTwoPairIfHasSameHighPairAndLowPairAndKicker_then_resultIsTie() {
		final TwoPair aTwoPair = TwoPair.create()
				.withHighPair(ACE)
				.withLowPair(KING)
				.withKicker(QUEEN)
				.build();

		final TwoPair anotherTwoPair = TwoPair.create()
				.withHighPair(ACE)
				.withLowPair(KING)
				.withKicker(QUEEN)
				.build();

		assertThat(aTwoPair.compare(anotherTwoPair), is(TIE));
	}

	@Test
	void when_creatingTwoPair_then_highPairMustBeDifferentThanLowPair() {
		assertThrows(IllegalStateException.class, () -> TwoPair.create()
				.withHighPair(TWO)
				.withLowPair(TWO)
				.withKicker(ACE)
				.build());
	}

	@Test
	void when_creatingTwoPair_then_highPairMustBeBetterThanLowPair() {
		assertThrows(IllegalStateException.class, () -> TwoPair.create()
				.withHighPair(TWO)
				.withLowPair(THREE)
				.withKicker(ACE)
				.build());
	}

	@Test
	void when_creatingTwoPair_then_highPairMustBeDifferentThanKicker() {
		assertThrows(IllegalStateException.class, () -> TwoPair.create()
				.withHighPair(TWO)
				.withLowPair(THREE)
				.withKicker(TWO)
				.build());
	}

	@Test
	void when_creatingTwoPair_then_lowPairMustBeDifferentThanKicker() {
		assertThrows(IllegalStateException.class, () -> TwoPair.create()
				.withHighPair(ACE)
				.withLowPair(THREE)
				.withKicker(THREE)
				.build());
	}

}