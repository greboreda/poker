package com.greboreda.poker.hand.rank.fullhouse;

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
import static com.greboreda.poker.card.Value.EIGHT;
import static com.greboreda.poker.card.Value.FIVE;
import static com.greboreda.poker.card.Value.KING;
import static com.greboreda.poker.card.Value.QUEEN;
import static com.greboreda.poker.card.Value.TEN;
import static com.greboreda.poker.card.Value.TWO;
import static com.greboreda.poker.hand.rank.Rank.RankValue.FULL_HOUSE;
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

class FullHouseTest {

	@Test
	void when_handHasThreeCardOfSameValueAndTwoOfAnotherSameValue_then_hasFullHouse() {

		final Hand fullHouseHand = new Hand(
				a().EIGHT.of.DIAMONDS,
				a().EIGHT.of.CLUBS,
				a().EIGHT.of.SPADES,
				a().FIVE.of.HEARTS,
				a().FIVE.of.SPADES
		);
		final Rank rank = fullHouseHand.getRank();

		assertThat(rank.getRankValue(), is(FULL_HOUSE));
		final FullHouse fullHouse = (FullHouse) rank;
		assertAll("full house is valid",
				() -> assertThat(fullHouse.getValue(), is(EIGHT)),
				() -> assertThat(fullHouse.getOver(), is(FIVE))
		);
	}


	@ParameterizedTest
	@MethodSource("retrieveRanksWorseThanFullHouse")
	void when_comparingWithAnotherRankIfAnotherRankIsWorse_then_resultIsWin(Rank worseRank) {
		final FullHouse fullHouse = createFullHouse();
		assertThat(fullHouse.compare(worseRank), is(WIN));
	}

	private static Stream<Rank> retrieveRanksWorseThanFullHouse() {
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
	@MethodSource("retrieveRanksBetterThanFullHouse")
	void when_comparingWithAnotherRankIfAnotherRankIsBetter_then_resultIsLoose(Rank betterRank) {
		final FullHouse fullHouse = createFullHouse();
		assertThat(fullHouse.compare(betterRank), is(LOOSE));
	}
	private static Stream<Rank> retrieveRanksBetterThanFullHouse() {
		return Stream.of(
				createRoyalFlush(),
				createStraightFlush(),
				createFourOfAKind()
		);
	}

	@Test
	void when_comparingWithAnotherFullHouseIfHasBetterValue_then_resultIsWin() {

		final FullHouse aFullHouse = FullHouse.create()
				.of(ACE)
				.over(KING)
				.build();

		final FullHouse anotherFullHouse = FullHouse.create()
				.of(QUEEN)
				.over(KING)
				.build();

		assertThat(aFullHouse.compare(anotherFullHouse), is(WIN));
	}

	@Test
	void when_comparingWithAnotherFullHouseIfHasWorseValue_then_resultIsLoose() {

		final FullHouse aFullHouse = FullHouse.create()
				.of(TEN)
				.over(KING)
				.build();

		final FullHouse anotherFullHouse = FullHouse.create()
				.of(QUEEN)
				.over(KING)
				.build();

		assertThat(aFullHouse.compare(anotherFullHouse), is(LOOSE));
	}

	@Test
	void when_comparingWithAnotherFullHouseIfHasSameValueAndBetterOver_then_resultIsWin() {

		final FullHouse aFullHouse = FullHouse.create()
				.of(TEN)
				.over(KING)
				.build();

		final FullHouse anotherFullHouse = FullHouse.create()
				.of(TEN)
				.over(TWO)
				.build();

		assertThat(aFullHouse.compare(anotherFullHouse), is(WIN));
	}

	@Test
	void when_comparingWithAnotherFullHouseIfHasSameValueAndWorseOver_then_resultIsLoose() {

		final FullHouse aFullHouse = FullHouse.create()
				.of(TEN)
				.over(KING)
				.build();

		final FullHouse anotherFullHouse = FullHouse.create()
				.of(TEN)
				.over(ACE)
				.build();

		assertThat(aFullHouse.compare(anotherFullHouse), is(LOOSE));
	}

	@Test
	void when_comparingWithAnotherFullHouseIfHasSameValueAndSameOver_then_resultIsTie() {

		final FullHouse aFullHouse = FullHouse.create()
				.of(TEN)
				.over(KING)
				.build();

		final FullHouse anotherFullHouse = FullHouse.create()
				.of(TEN)
				.over(KING)
				.build();

		assertThat(aFullHouse.compare(anotherFullHouse), is(TIE));
	}


	@Test
	void when_creatingFullHouse_then_ofAndOverMustBeDifferent() {
		assertThrows(IllegalStateException.class, () -> FullHouse.create()
				.of(ACE)
				.over(ACE)
				.build());
	}

}