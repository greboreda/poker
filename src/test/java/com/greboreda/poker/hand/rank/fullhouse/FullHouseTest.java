package com.greboreda.poker.hand.rank.fullhouse;

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

class FullHouseTest {

	@ParameterizedTest
	@MethodSource("fullHouseWins")
	void when_comparingWithAnotherRankIfAnotherRankIsWorse_then_resultIsWin(Rank rankAgainstWin) {
		final FullHouse fullHouse = createFullHouse();
		assertThat(fullHouse.compare(rankAgainstWin), is(Comparision.WIN));
	}

	private static Stream<Rank> fullHouseWins() {
		return Stream.of(
				createHighCard(),
				createOnePair(),
				createTwoPair(),
				createThreeOfAKind(),
				createStraight(),
				createFlush()
		);
	}

	@ParameterizedTest
	@MethodSource("fullHouseLooses")
	void when_comparingWithAnotherRankIfAnotherRankIsBetter_then_resultIsLoose(Rank rankAgainstLoose) {
		final FullHouse fullHouse = createFullHouse();
		assertThat(fullHouse.compare(rankAgainstLoose), is(Comparision.LOOSE));
	}
	private static Stream<Rank> fullHouseLooses() {
		return Stream.of(
				createRoyalFlush(),
				createStraightFlush(),
				createFourOfAKind()
		);
	}

	@Test
	void when_comparingWithAnotherFulHouseIfHasBetterValue_then_resultIsWin() {

		final FullHouse aFullHouse = FullHouse.create()
				.of(Value.ACE)
				.over(Value.KING)
				.build();

		final FullHouse anotherFullHouse = FullHouse.create()
				.of(Value.QUEEN)
				.over(Value.KING)
				.build();

		assertThat(aFullHouse.compare(anotherFullHouse), is(Comparision.WIN));
	}

	@Test
	void when_comparingWithAnotherFulHouseIfHasWorseValue_then_resultIsLoose() {

		final FullHouse aFullHouse = FullHouse.create()
				.of(Value.TEN)
				.over(Value.KING)
				.build();

		final FullHouse anotherFullHouse = FullHouse.create()
				.of(Value.QUEEN)
				.over(Value.KING)
				.build();

		assertThat(aFullHouse.compare(anotherFullHouse), is(Comparision.LOOSE));
	}

	@Test
	void when_comparingWithAnotherFulHouseIfHasSameValueAndBetterOver_then_resultIsWin() {

		final FullHouse aFullHouse = FullHouse.create()
				.of(Value.TEN)
				.over(Value.KING)
				.build();

		final FullHouse anotherFullHouse = FullHouse.create()
				.of(Value.TEN)
				.over(Value.TWO)
				.build();

		assertThat(aFullHouse.compare(anotherFullHouse), is(Comparision.WIN));
	}

	@Test
	void when_comparingWithAnotherFulHouseIfHasSameValueAndWorseOver_then_resultIsLoose() {

		final FullHouse aFullHouse = FullHouse.create()
				.of(Value.TEN)
				.over(Value.KING)
				.build();

		final FullHouse anotherFullHouse = FullHouse.create()
				.of(Value.TEN)
				.over(Value.ACE)
				.build();

		assertThat(aFullHouse.compare(anotherFullHouse), is(Comparision.LOOSE));
	}

	@Test
	void when_comparingWithAnotherFulHouseIfHasSameValueAndSameOver_then_resultIsTie() {

		final FullHouse aFullHouse = FullHouse.create()
				.of(Value.TEN)
				.over(Value.KING)
				.build();

		final FullHouse anotherFullHouse = FullHouse.create()
				.of(Value.TEN)
				.over(Value.KING)
				.build();

		assertThat(aFullHouse.compare(anotherFullHouse), is(Comparision.TIE));
	}


	@Test
	void when_creatingFullHouse_then_ofAndOverMustBeDifferent() {
		assertThrows(IllegalStateException.class, () -> FullHouse.create()
				.of(Value.ACE)
				.over(Value.ACE)
				.build());
	}

}