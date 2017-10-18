package com.greboreda.poker.hand.rank.twopair;

import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.Rank;
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
import static com.greboreda.poker.card.Value.JACK;
import static com.greboreda.poker.card.Value.KING;
import static com.greboreda.poker.card.Value.QUEEN;
import static com.greboreda.poker.card.Value.SIX;
import static com.greboreda.poker.card.Value.TEN;
import static com.greboreda.poker.card.Value.THREE;
import static com.greboreda.poker.card.Value.TWO;
import static com.greboreda.poker.hand.rank.Rank.RankValue.TWO_PAIR;
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
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TwoPairTest {

	@Test
	void when_handHasTwoCardsRepeatedTwoTimes_then_hasTwoPairRank() {

		final Hand twoPairHand = new Hand(
				a().TEN.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().SIX.of.DIAMONDS,
				a().SIX.of.HEARTS,
				a().JACK.of.CLUBS
		);
		final Rank rank = twoPairHand.getRank();

		assertThat(rank.getRankValue(), is(TWO_PAIR));
		final TwoPair twoPair = (TwoPair) rank;
		assertAll("two pais is valid",
				() -> assertThat(twoPair.getHighPair(), is(TEN)),
				() -> assertThat(twoPair.getLowPair(), is(SIX)),
				() -> assertThat(twoPair.getKicker(), is(JACK))
		);
	}


	@ParameterizedTest
	@MethodSource("retrieveRanksWorseThanTwoPair")
	void when_comparingWithAnotherRankIfAnotherRankIsWorse_then_resultIsWin(Rank worseRank) {
		final TwoPair twoPair = createTwoPair();
		assertThat(twoPair.compare(worseRank), is(WIN));
	}

	private static Stream<Rank> retrieveRanksWorseThanTwoPair() {
		return Stream.of(createOnePair(), createHighCard());
	}

	@ParameterizedTest
	@MethodSource("retrieveRanksBetterThanTwoPair")
	void when_comparingWithAnotherRankIfAnotherRankIsBetter_then_resultIsLoose(Rank betterRank) {
		final TwoPair twoPair = createTwoPair();
		assertThat(twoPair.compare(betterRank), is(LOOSE));
	}
	private static Stream<Rank> retrieveRanksBetterThanTwoPair() {
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