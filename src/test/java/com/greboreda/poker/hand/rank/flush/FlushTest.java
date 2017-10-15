package com.greboreda.poker.hand.rank.flush;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.card.Value.*;
import static com.greboreda.poker.hand.rank.RankFactory.createFlush;
import static com.greboreda.poker.hand.rank.RankFactory.createFourOfAKind;
import static com.greboreda.poker.hand.rank.RankFactory.createFullHouse;
import static com.greboreda.poker.hand.rank.RankFactory.createHighCard;
import static com.greboreda.poker.hand.rank.RankFactory.createOnePair;
import static com.greboreda.poker.hand.rank.RankFactory.createRoyalFlush;
import static com.greboreda.poker.hand.rank.RankFactory.createStraight;
import static com.greboreda.poker.hand.rank.RankFactory.createStraightFlush;
import static com.greboreda.poker.hand.rank.RankFactory.createThreeOfAKind;
import static com.greboreda.poker.hand.rank.RankFactory.createTwoPair;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FlushTest {

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
	@MethodSource("flushWins")
	void when_comparingWithAnotherRankIfAnotherRankIsWorse_then_resultIsWin(Rank rankAgainstWin) {
		final Flush flush = createFlush();
		assertThat(flush.compare(rankAgainstWin), is(Comparision.WIN));
	}

	private static Stream<Rank> flushWins() {
		return Stream.of(
				createHighCard(),
				createOnePair(),
				createTwoPair(),
				createThreeOfAKind(),
				createStraight()
		);
	}

	@ParameterizedTest
	@MethodSource("flushLooses")
	void when_comparingWithAnotherRankIfAnotherRankIsBetter_then_resultIsLoose(Rank rankAgainstLoose) {
		final Flush flush = createFlush();
		assertThat(flush.compare(rankAgainstLoose), is(Comparision.LOOSE));
	}
	private static Stream<Rank> flushLooses() {
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