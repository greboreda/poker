package com.greboreda.poker.hand.rank.fourofakind;

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

class FourOfAKindTest {

	@Test
	void when_handHasFourCardsOfSameValue_then_hasFourOfAKind() {

		final Card quad1 = Card.create().withValue(Value.ACE).withSuit(Suit.HEARTS).build();
		final Card quad2 = Card.create().withValue(Value.ACE).withSuit(Suit.CLUBS).build();
		final Card quad3 = Card.create().withValue(Value.ACE).withSuit(Suit.DIAMONDS).build();
		final Card quad4 = Card.create().withValue(Value.ACE).withSuit(Suit.SPADES).build();
		final Card kicker = Card.create().withValue(Value.KING).withSuit(Suit.DIAMONDS).build();
		final Hand fourOfAKindHand = new Hand(quad1, quad2, quad3, quad4, kicker);
		final Rank rank = fourOfAKindHand.getRank();
		
		assertThat(rank.getRankValue(), is(RankValue.FOUR_OF_A_KIND));
		final FourOfAKind fourOfAKind = (FourOfAKind) rank;
		assertAll("four of a kind is valid",
				() -> assertThat(fourOfAKind.getValue(), is(Value.ACE)),
				() -> assertThat(fourOfAKind.getKicker(), is(Value.KING))
		);
	}


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
	void when_comparingWithAnotherFourOfAKindIfHasSameValueAndBetterKicker_then_resultIsWin() {

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
	void when_comparingWithAnotherFourOfAKindIfHasSameValueAndWorseKicker_then_resultIsLoose() {

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
	void when_comparingWithAnotherFourOfAKindIfHasSameValueAndSameKicker_then_resultIsTie() {

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