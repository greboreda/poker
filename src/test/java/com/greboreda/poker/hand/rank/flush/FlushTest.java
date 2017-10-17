package com.greboreda.poker.hand.rank.flush;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Card;
import com.greboreda.poker.card.Suit;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.Rank;
import com.greboreda.poker.hand.rank.Rank.RankValue;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.card.Value.*;
import static com.greboreda.poker.hand.rank.RankRepository.createFlush;
import static com.greboreda.poker.hand.rank.RankRepository.createFourOfAKind;
import static com.greboreda.poker.hand.rank.RankRepository.createFullHouse;
import static com.greboreda.poker.hand.rank.RankRepository.createHighCard;
import static com.greboreda.poker.hand.rank.RankRepository.createOnePair;
import static com.greboreda.poker.hand.rank.RankRepository.createRoyalFlush;
import static com.greboreda.poker.hand.rank.RankRepository.createStraight;
import static com.greboreda.poker.hand.rank.RankRepository.createStraightFlush;
import static com.greboreda.poker.hand.rank.RankRepository.createThreeOfAKind;
import static com.greboreda.poker.hand.rank.RankRepository.createTwoPair;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FlushTest {

	@Test
	void when_handHasAllCardOfSameSuitAndValuesAreNotConsecutive_then_hasFlush() {

		final Card card1 = Card.create().withValue(Value.TEN).withSuit(Suit.DIAMONDS).build();
		final Card card2 = Card.create().withValue(Value.TWO).withSuit(Suit.DIAMONDS).build();
		final Card card3 = Card.create().withValue(Value.SEVEN).withSuit(Suit.DIAMONDS).build();
		final Card card4 = Card.create().withValue(Value.THREE).withSuit(Suit.DIAMONDS).build();
		final Card card5 = Card.create().withValue(Value.KING).withSuit(Suit.DIAMONDS).build();
		final Hand flushHand = new Hand(card1, card2, card3, card4, card5);
		final Rank rank = flushHand.getRank();

		assertThat(rank.getRankValue(), is(RankValue.FLUSH));
		final Flush flush = (Flush) rank;
		assertAll("flush is valid",
				() -> assertThat(flush.getHighKicker(), Matchers.is(Value.KING)),
				() -> assertThat(flush.getSecondKicker(), Matchers.is(Value.TEN)),
				() -> assertThat(flush.getThirdKicker(), Matchers.is(Value.SEVEN)),
				() -> assertThat(flush.getFourthKicker(), Matchers.is(Value.THREE)),
				() -> assertThat(flush.getFifthKicker(), Matchers.is(Value.TWO))
		);
	}


	@ParameterizedTest
	@MethodSource("consecutives")
	void when_creatingFlush_then_valuesCanNotBeConsecutive(Value kicker1, Value kicker2, Value kicker3, Value kicker4, Value kicker5) {
		assertThrows(IllegalStateException.class, () -> Flush.create()
				.withKicker(kicker1)
				.withKicker(kicker2)
				.withKicker(kicker3)
				.withKicker(kicker4)
				.withKicker(kicker5)
				.build());
	}

	private static Stream<Arguments> consecutives() {
		return Stream.of(
				Arguments.of(TWO, THREE, FOUR, FIVE, SIX),
				Arguments.of(FIVE, SIX, SEVEN, NINE, EIGHT),
				Arguments.of(ACE, TEN, JACK, QUEEN, KING),
				Arguments.of(TEN, QUEEN, JACK, NINE, EIGHT),
				Arguments.of(FOUR, FIVE, THREE, SIX, TWO)
		);
	}

	@ParameterizedTest
	@MethodSource("repeated")
	void when_creatingFlush_then_allValuesMustBeDistinctEachOther(Value kicker1, Value kicker2, Value kicker3, Value kicker4, Value kicker5) {
		assertThrows(IllegalStateException.class, () -> Flush.create()
				.withKicker(kicker1)
				.withKicker(kicker2)
				.withKicker(kicker3)
				.withKicker(kicker4)
				.withKicker(kicker5)
				.build());
	}

	private static Stream<Arguments> repeated() {
		return Stream.of(
				Arguments.of(TWO, TWO, FOUR, FIVE, SIX),
				Arguments.of(FIVE, SIX, SIX, SIX, EIGHT),
				Arguments.of(ACE, TEN, JACK, QUEEN, JACK),
				Arguments.of(EIGHT, QUEEN, JACK, NINE, EIGHT),
				Arguments.of(FOUR, FOUR, FOUR, FOUR, FOUR)
		);
	}


	@ParameterizedTest
	@MethodSource("retrieveRanksWorseThanFlush")
	void when_comparingWithAnotherRankIfAnotherRankIsWorse_then_resultIsWin(Rank worseRank) {
		final Flush flush = createFlush();
		assertThat(flush.compare(worseRank), is(Comparision.WIN));
	}

	private static Stream<Rank> retrieveRanksWorseThanFlush() {
		return Stream.of(
				createHighCard(),
				createOnePair(),
				createTwoPair(),
				createThreeOfAKind(),
				createStraight()
		);
	}

	@ParameterizedTest
	@MethodSource("retrieveRanksBetterThanFlush")
	void when_comparingWithAnotherRankIfAnotherRankIsBetter_then_resultIsLoose(Rank betterRank) {
		final Flush flush = createFlush();
		assertThat(flush.compare(betterRank), is(Comparision.LOOSE));
	}
	private static Stream<Rank> retrieveRanksBetterThanFlush() {
		return Stream.of(
				createRoyalFlush(),
				createStraightFlush(),
				createFourOfAKind(),
				createFullHouse()
		);
	}

	@Test
	void should_win_if_has_better_high_kicker_than_other_flush() {

		final Flush aFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(QUEEN)
				.withKicker(TEN)
				.withKicker(EIGHT)
				.withKicker(SIX)
				.build();

		final Flush anotherFlush = Flush.create()
				.withKicker(KING)
				.withKicker(QUEEN)
				.withKicker(TEN)
				.withKicker(EIGHT)
				.withKicker(SIX)
				.build();

		assertThat(aFlush.compare(anotherFlush), is(Comparision.WIN));
	}

	@Test
	void should_loose_if_has_worse_high_kicker_than_other_flush() {

		final Flush aFlush = Flush.create()
				.withKicker(QUEEN)
				.withKicker(JACK)
				.withKicker(TEN)
				.withKicker(EIGHT)
				.withKicker(SIX)
				.build();

		final Flush anotherFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(QUEEN)
				.withKicker(TEN)
				.withKicker(EIGHT)
				.withKicker(SIX)
				.build();

		assertThat(aFlush.compare(anotherFlush), is(Comparision.LOOSE));
	}

	@Test
	void should_win_if_has_same_high_kicker_and_better_second_kicker_than_other_flush() {

		final Flush aFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(QUEEN)
				.withKicker(TEN)
				.withKicker(EIGHT)
				.withKicker(SIX)
				.build();

		final Flush anotherFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(JACK)
				.withKicker(TEN)
				.withKicker(EIGHT)
				.withKicker(SIX)
				.build();

		assertThat(aFlush.compare(anotherFlush), is(Comparision.WIN));
	}

	@Test
	void should_loose_if_has_same_high_kicker_and_worse_second_kicker_than_other_flush() {

		final Flush aFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(QUEEN)
				.withKicker(TEN)
				.withKicker(EIGHT)
				.withKicker(SIX)
				.build();

		final Flush anotherFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(KING)
				.withKicker(TEN)
				.withKicker(EIGHT)
				.withKicker(SIX)
				.build();

		assertThat(aFlush.compare(anotherFlush), is(Comparision.LOOSE));
	}

	@Test
	void should_win_if_has_same_high_and_second_kickers_but_better_third_kicker_than_other_flush() {

		final Flush aFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(QUEEN)
				.withKicker(JACK)
				.withKicker(EIGHT)
				.withKicker(SIX)
				.build();

		final Flush anotherFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(QUEEN)
				.withKicker(TEN)
				.withKicker(EIGHT)
				.withKicker(SIX)
				.build();

		assertThat(aFlush.compare(anotherFlush), is(Comparision.WIN));

	}

	@Test
	void should_loose_if_has_same_high_and_second_kickers_but_worse_third_kicker_than_other_flush() {

		final Flush aFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(QUEEN)
				.withKicker(TEN)
				.withKicker(EIGHT)
				.withKicker(SIX)
				.build();

		final Flush anotherFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(QUEEN)
				.withKicker(JACK)
				.withKicker(EIGHT)
				.withKicker(SIX)
				.build();

		assertThat(aFlush.compare(anotherFlush), is(Comparision.LOOSE));

	}

	@Test
	void should_win_if_has_same_high_second_and_third_kickers_but_better_fourth_kicker_than_other_flush() {

		final Flush aFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(QUEEN)
				.withKicker(TEN)
				.withKicker(EIGHT)
				.withKicker(TWO)
				.build();

		final Flush anotherFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(QUEEN)
				.withKicker(TEN)
				.withKicker(SIX)
				.withKicker(TWO)
				.build();

		assertThat(aFlush.compare(anotherFlush), is(Comparision.WIN));

	}

	@Test
	void should_loose_if_has_same_high_second_and_third_kickers_but_worse_fourth_kicker_than_other_flush() {

		final Flush aFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(QUEEN)
				.withKicker(TEN)
				.withKicker(EIGHT)
				.withKicker(SIX)
				.build();

		final Flush anotherFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(KING)
				.withKicker(TEN)
				.withKicker(NINE)
				.withKicker(SIX)
				.build();

		assertThat(aFlush.compare(anotherFlush), is(Comparision.LOOSE));

	}

	@Test
	void should_win_if_has_same_high_second_third_and_fourth_kickers_but_better_fifth_kicker_than_other_flush() {

		final Flush aFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(QUEEN)
				.withKicker(TEN)
				.withKicker(EIGHT)
				.withKicker(SIX)
				.build();

		final Flush anotherFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(QUEEN)
				.withKicker(TEN)
				.withKicker(EIGHT)
				.withKicker(TWO)
				.build();

		assertThat(aFlush.compare(anotherFlush), is(Comparision.WIN));

	}

	@Test
	void should_loose_if_has_same_high_second_third_and_fourth_kickers_but_worse_fifth_kicker_than_other_flush() {

		final Flush aFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(QUEEN)
				.withKicker(TEN)
				.withKicker(EIGHT)
				.withKicker(TWO)
				.build();

		final Flush anotherFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(KING)
				.withKicker(TEN)
				.withKicker(EIGHT)
				.withKicker(SIX)
				.build();

		assertThat(aFlush.compare(anotherFlush), is(Comparision.LOOSE));

	}

	@Test
	void should_tie_if_all_kickers_are_same_than_other_flush() {

		final Flush aFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(QUEEN)
				.withKicker(TEN)
				.withKicker(EIGHT)
				.withKicker(SIX)
				.build();

		final Flush anotherFlush = Flush.create()
				.withKicker(ACE)
				.withKicker(QUEEN)
				.withKicker(TEN)
				.withKicker(EIGHT)
				.withKicker(SIX)
				.build();

		assertThat(aFlush.compare(anotherFlush), is(Comparision.TIE));

	}
}