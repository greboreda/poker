package com.greboreda.poker.hand.rank.fourofakind;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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

class FourOfAKindTest {


	@ParameterizedTest
	@MethodSource("fourOfAKindWins")
	void when_comparingWithAnotherRankIfAnotherRankIsWorse_then_resultIsWin(Rank rankAgainstWin) {
		final FourOfAKind fourOfAKind = createFourOfAKind();
		assertThat(fourOfAKind.compare(rankAgainstWin), is(Comparision.WIN));
	}

	private static Stream<Rank> fourOfAKindWins() {
		return Stream.of(
				createHighCard(),
				createOnePair(),
				createTwoPair(),
				createThreeOfAKind(),
				createStraight(),
				createFlush(),
				createFullHouse()
		);
	}

	@ParameterizedTest
	@MethodSource("fourOfAKindLooses")
	void when_comparingWithAnotherRankIfAnotherRankIsBetter_then_resultIsLoose(Rank rankAgainstLoose) {
		final FourOfAKind fourOfAKind = createFourOfAKind();
		assertThat(fourOfAKind.compare(rankAgainstLoose), is(Comparision.LOOSE));
	}
	private static Stream<Rank> fourOfAKindLooses() {
		return Stream.of(createRoyalFlush(), createStraightFlush());
	}

	@Test
	void when_comparingWithAnotherFourOfAKindIfHasBetterValue_then_resultIsWin() {

		final FourOfAKind aFourOfAKind = FourOfAKind.create()
				.of(Value.ACE)
				.withKicker(Value.KING)
				.build();

		final FourOfAKind anotherFourOfAKind = FourOfAKind.create()
				.of(Value.QUEEN)
				.withKicker(Value.KING)
				.build();

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(Comparision.WIN));
	}

	@Test
	void when_comparingWithAnotherFourOfAKindIfHasWorseValue_then_resultIsLoose() {

		final FourOfAKind aFourOfAKind = FourOfAKind.create()
				.of(Value.TEN)
				.withKicker(Value.KING)
				.build();

		final FourOfAKind anotherFourOfAKind = FourOfAKind.create()
				.of(Value.QUEEN)
				.withKicker(Value.KING)
				.build();

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(Comparision.LOOSE));
	}

	@Test
	void when_comparingWithAnotherFourOfAKindIfHasSameValueButBetterKicker_then_resultIsWin() {

		final FourOfAKind aFourOfAKind = FourOfAKind.create()
				.of(Value.ACE)
				.withKicker(Value.KING)
				.build();

		final FourOfAKind anotherFourOfAKind = FourOfAKind.create()
				.of(Value.ACE)
				.withKicker(Value.QUEEN)
				.build();

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(Comparision.WIN));
	}

	@Test
	void when_comparingWithAnotherFourOfAKindIfHasSameValueButWorseKicker_then_resultIsLoose() {

		final FourOfAKind aFourOfAKind = FourOfAKind.create()
				.of(Value.ACE)
				.withKicker(Value.QUEEN)
				.build();

		final FourOfAKind anotherFourOfAKind = FourOfAKind.create()
				.of(Value.ACE)
				.withKicker(Value.KING)
				.build();

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(Comparision.LOOSE));
	}

	@Test
	void when_comparingWithAnotherFourOfAKindIfHasSameValueAndKicker_then_resultIsTie() {

		final FourOfAKind aFourOfAKind = FourOfAKind.create()
				.of(Value.ACE)
				.withKicker(Value.KING)
				.build();

		final FourOfAKind anotherFourOfAKind = FourOfAKind.create()
				.of(Value.ACE)
				.withKicker(Value.KING)
				.build();

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(Comparision.TIE));
	}

	@Test
	void when_creatingFourOfAKind_then_valueAndKickerMustBeDifferent() {
		assertThrows(IllegalStateException.class, () -> FourOfAKind.create()
				.of(Value.ACE)
				.withKicker(Value.ACE)
				.build());
	}

}