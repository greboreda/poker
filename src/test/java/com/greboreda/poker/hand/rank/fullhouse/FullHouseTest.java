package com.greboreda.poker.hand.rank.fullhouse;

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
import static org.junit.jupiter.api.Assertions.assertTrue;

class FullHouseTest {

	@Test
	void when_handHasThreeCardOfSameValueAndTwoOfAnotherSameValue_then_hasFullHouse() {

		final Card trio1 = Card.create().withValue(Value.EIGHT).withSuit(Suit.DIAMONDS).build();
		final Card trio2 = Card.create().withValue(Value.EIGHT).withSuit(Suit.CLUBS).build();
		final Card trio3 = Card.create().withValue(Value.EIGHT).withSuit(Suit.SPADES).build();
		final Card pair1 = Card.create().withValue(Value.FIVE).withSuit(Suit.HEARTS).build();
		final Card pair2 = Card.create().withValue(Value.FIVE).withSuit(Suit.SPADES).build();
		final Hand fullHouseHand = new Hand(trio1, trio2, trio3, pair1, pair2);
		final Rank rank = fullHouseHand.getRank();

		assertThat(rank.getRankValue(), is(RankValue.FULL_HOUSE));
		final FullHouse fullHouse = (FullHouse) rank;
		assertAll("full house is valid",
				() -> assertThat(fullHouse.getValue(), is(Value.EIGHT)),
				() -> assertThat(fullHouse.getOver(), is(Value.FIVE))
		);
	}


	@ParameterizedTest
	@MethodSource("retrieveRanksWorseThanFullHouse")
	void when_comparingWithAnotherRankIfAnotherRankIsWorse_then_resultIsWin(Rank worseRank) {
		final FullHouse fullHouse = createFullHouse();
		assertThat(fullHouse.compare(worseRank), is(Comparision.WIN));
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
		assertThat(fullHouse.compare(betterRank), is(Comparision.LOOSE));
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
	void when_comparingWithAnotherFullHouseIfHasWorseValue_then_resultIsLoose() {

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
	void when_comparingWithAnotherFullHouseIfHasSameValueAndBetterOver_then_resultIsWin() {

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
	void when_comparingWithAnotherFullHouseIfHasSameValueAndWorseOver_then_resultIsLoose() {

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
	void when_comparingWithAnotherFullHouseIfHasSameValueAndSameOver_then_resultIsTie() {

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