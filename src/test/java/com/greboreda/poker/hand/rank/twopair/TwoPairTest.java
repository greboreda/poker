package com.greboreda.poker.hand.rank.twopair;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TwoPairTest {

	@Test
	void when_comparingTwoPairWithAnotherRankIfAnotherRankIsWorse_then_resultIsWin() {

		//final List<Rank> worseThanTwoPair = Arrays.asList()

	}


	@Test
	void when_comparingTwoPairWithAnotherRankIfAnotherRankIsBetter_then_resultIsLoose() {

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

		assertThat(aTwoPair.compare(anotherTwoPair), Matchers.is(Comparision.LOOSE));
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

		assertThat(aTwoPair.compare(anotherTwoPair), Matchers.is(Comparision.WIN));
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

		assertThat(aTwoPair.compare(anotherTwoPair), Matchers.is(Comparision.WIN));
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

		assertThat(aTwoPair.compare(anotherTwoPair), Matchers.is(Comparision.LOOSE));
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

		assertThat(aTwoPair.compare(anotherTwoPair), Matchers.is(Comparision.LOOSE));
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

		assertThat(aTwoPair.compare(anotherTwoPair), Matchers.is(Comparision.WIN));
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

		assertThat(aTwoPair.compare(anotherTwoPair), Matchers.is(Comparision.TIE));
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