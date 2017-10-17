package com.greboreda.poker.hand.rank.twopair;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.hand.rank.RankRepository.createFlush;
import static com.greboreda.poker.hand.rank.RankRepository.createFourOfAKind;
import static com.greboreda.poker.hand.rank.RankRepository.createFullHouse;
import static com.greboreda.poker.hand.rank.RankRepository.createHighCard;
import static com.greboreda.poker.hand.rank.RankRepository.createOnePair;
import static com.greboreda.poker.hand.rank.RankRepository.createRoyalFlush;
import static com.greboreda.poker.hand.rank.RankRepository.createStraight;
import static com.greboreda.poker.hand.rank.RankRepository.createThreeOfAKind;
import static com.greboreda.poker.hand.rank.RankRepository.createTwoPair;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TwoPairTest {

	@ParameterizedTest
	@MethodSource("twoPairWins")
	void when_comparingWithAnotherRankIfAnotherRankIsWorse_then_resultIsWin(Rank rankAgainstWin) {
		final TwoPair twoPair = createTwoPair();
		assertThat(twoPair.compare(rankAgainstWin), is(Comparision.WIN));
	}

	private static Stream<Rank> twoPairWins() {
		return Stream.of(createOnePair(), createHighCard());
	}

	@ParameterizedTest
	@MethodSource("twoPairLooses")
	void when_comparingWithAnotherRankIfAnotherRankIsBetter_then_resultIsLoose(Rank rankAgainstLoose) {
		final TwoPair twoPair = createTwoPair();
		assertThat(twoPair.compare(rankAgainstLoose), is(Comparision.LOOSE));
	}
	private static Stream<Rank> twoPairLooses() {
		return Stream.of(
				createRoyalFlush(),
				createFourOfAKind(),
				createRoyalFlush(),
				createFullHouse(),
				createFlush(),
				createStraight(),
				createThreeOfAKind());
	}

	@Test
	void when_comparingWithAnotherTwoPairIfHasSameHighPairAndSameLowPairButWorseKicker_then_resultIsLoose() {

		final TwoPair aTwoPair = TwoPair.create()
				.withHighPair(Value.FOUR)
				.withLowPair(Value.THREE)
				.withKicker(Value.TWO)
				.build();

		final TwoPair anotherTwoPair = TwoPair.create()
				.withHighPair(Value.FOUR)
				.withLowPair(Value.THREE)
				.withKicker(Value.QUEEN)
				.build();

		assertThat(aTwoPair.compare(anotherTwoPair), is(Comparision.LOOSE));
	}


	@Test
	void when_comparingWithAnotherTwoPairIfHasSameHighPairAndSameLowPairButBetterKicker_then_resultIsWin() {
		final TwoPair aTwoPair = TwoPair.create()
				.withHighPair(Value.FOUR)
				.withLowPair(Value.THREE)
				.withKicker(Value.ACE)
				.build();

		final TwoPair anotherTwoPair = TwoPair.create()
				.withHighPair(Value.FOUR)
				.withLowPair(Value.THREE)
				.withKicker(Value.QUEEN)
				.build();

		assertThat(aTwoPair.compare(anotherTwoPair), is(Comparision.WIN));
	}


	@Test
	void when_comparingWithAnotherTwoPairIfHasSameHighPairButBetterLowPair_then_resultIsWin() {
		final TwoPair aTwoPair = TwoPair.create()
				.withHighPair(Value.ACE)
				.withLowPair(Value.KING)
				.withKicker(Value.QUEEN)
				.build();

		final TwoPair anotherTwoPair = TwoPair.create()
				.withHighPair(Value.ACE)
				.withLowPair(Value.THREE)
				.withKicker(Value.QUEEN)
				.build();

		assertThat(aTwoPair.compare(anotherTwoPair), is(Comparision.WIN));
	}


	@Test
	void when_comparingWithAnotherTwoPairIfHasSameHighPairButWorseLowPair_then_resultIsLoose() {
		final TwoPair aTwoPair = TwoPair.create()
				.withHighPair(Value.KING)
				.withLowPair(Value.TWO)
				.withKicker(Value.QUEEN)
				.build();

		final TwoPair anotherTwoPair = TwoPair.create()
				.withHighPair(Value.KING)
				.withLowPair(Value.THREE)
				.withKicker(Value.QUEEN)
				.build();

		assertThat(aTwoPair.compare(anotherTwoPair), is(Comparision.LOOSE));
	}


	@Test
	void when_comparingWithAnotherTwoPairIfHasWorseHighPair_then_resultIsLoose() {
		final TwoPair aTwoPair = TwoPair.create()
				.withHighPair(Value.THREE)
				.withLowPair(Value.TWO)
				.withKicker(Value.QUEEN)
				.build();

		final TwoPair anotherTwoPair = TwoPair.create()
				.withHighPair(Value.TEN)
				.withLowPair(Value.TWO)
				.withKicker(Value.QUEEN)
				.build();

		assertThat(aTwoPair.compare(anotherTwoPair), is(Comparision.LOOSE));
	}

	@Test
	void when_comparingWithAnotherTwoPairIfHasBetterHighPair_then_resultIsWin() {
		final TwoPair aTwoPair = TwoPair.create()
				.withHighPair(Value.ACE)
				.withLowPair(Value.KING)
				.withKicker(Value.QUEEN)
				.build();

		final TwoPair anotherTwoPair = TwoPair.create()
				.withHighPair(Value.TEN)
				.withLowPair(Value.THREE)
				.withKicker(Value.QUEEN)
				.build();

		assertThat(aTwoPair.compare(anotherTwoPair), is(Comparision.WIN));
	}

	@Test
	void when_comparingWithAnotherTwoPairIfHasSameHighPairAndLowPairAndKicker_then_resultIsTie() {
		final TwoPair aTwoPair = TwoPair.create()
				.withHighPair(Value.ACE)
				.withLowPair(Value.KING)
				.withKicker(Value.QUEEN)
				.build();

		final TwoPair anotherTwoPair = TwoPair.create()
				.withHighPair(Value.ACE)
				.withLowPair(Value.KING)
				.withKicker(Value.QUEEN)
				.build();

		assertThat(aTwoPair.compare(anotherTwoPair), is(Comparision.TIE));
	}

	@Test
	void when_creatingTwoPair_then_highPairMustBeDifferentThanLowPair() {
		assertThrows(IllegalStateException.class, () -> TwoPair.create()
				.withHighPair(Value.TWO)
				.withLowPair(Value.TWO)
				.withKicker(Value.ACE)
				.build());
	}

	@Test
	void when_creatingTwoPair_then_highPairMustBeBetterThanLowPair() {
		assertThrows(IllegalStateException.class, () -> TwoPair.create()
				.withHighPair(Value.TWO)
				.withLowPair(Value.THREE)
				.withKicker(Value.ACE)
				.build());
	}

	@Test
	void when_creatingTwoPair_then_highPairMustBeDifferentThanKicker() {
		assertThrows(IllegalStateException.class, () -> TwoPair.create()
				.withHighPair(Value.TWO)
				.withLowPair(Value.THREE)
				.withKicker(Value.TWO)
				.build());
	}

	@Test
	void when_creatingTwoPair_then_lowPairMustBeDifferentThanKicker() {
		assertThrows(IllegalStateException.class, () -> TwoPair.create()
				.withHighPair(Value.ACE)
				.withLowPair(Value.THREE)
				.withKicker(Value.THREE)
				.build());
	}

}