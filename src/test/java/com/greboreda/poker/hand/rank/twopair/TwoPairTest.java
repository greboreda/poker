package com.greboreda.poker.hand.rank.twopair;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Card;
import com.greboreda.poker.card.Suit;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.Rank;
import com.greboreda.poker.hand.rank.Rank.RankValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.card.Value.ACE;
import static com.greboreda.poker.card.Value.JACK;
import static com.greboreda.poker.card.Value.SIX;
import static com.greboreda.poker.card.Value.TEN;
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

		final Card highPair1 = Card.create().withValue(TEN).withSuit(Suit.DIAMONDS).build();
		final Card highPair2 = Card.create().withValue(TEN).withSuit(Suit.SPADES).build();
		final Card lowPair1 = Card.create().withValue(SIX).withSuit(Suit.DIAMONDS).build();
		final Card lowPair2 = Card.create().withValue(SIX).withSuit(Suit.HEARTS).build();
		final Card kickerVal = Card.create().withValue(JACK).withSuit(Suit.CLUBS).build();
		final Hand twoPairHand = new Hand(highPair1, highPair2, lowPair1, lowPair2, kickerVal);
		final Rank rank = twoPairHand.getRank();

		assertThat(rank.getRankValue(), is(RankValue.TWO_PAIR));
		final TwoPair twoPair = (TwoPair) rank;
		assertAll("two pais is valid",
				() -> assertThat(twoPair.getHighPair(), is(TEN)),
				() -> assertThat(twoPair.getLowPair(), is(SIX)),
				() -> assertThat(twoPair.getKicker(), is(JACK))
		);
	}


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
				.withKicker(ACE)
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
				.withHighPair(ACE)
				.withLowPair(Value.KING)
				.withKicker(Value.QUEEN)
				.build();

		final TwoPair anotherTwoPair = TwoPair.create()
				.withHighPair(ACE)
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
				.withHighPair(ACE)
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
				.withHighPair(ACE)
				.withLowPair(Value.KING)
				.withKicker(Value.QUEEN)
				.build();

		final TwoPair anotherTwoPair = TwoPair.create()
				.withHighPair(ACE)
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
				.withKicker(ACE)
				.build());
	}

	@Test
	void when_creatingTwoPair_then_highPairMustBeBetterThanLowPair() {
		assertThrows(IllegalStateException.class, () -> TwoPair.create()
				.withHighPair(Value.TWO)
				.withLowPair(Value.THREE)
				.withKicker(ACE)
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
				.withHighPair(ACE)
				.withLowPair(Value.THREE)
				.withKicker(Value.THREE)
				.build());
	}

}