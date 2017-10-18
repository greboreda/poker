package com.greboreda.poker.hand.rank.fourofakind;

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
import static com.greboreda.poker.card.Value.KING;
import static com.greboreda.poker.card.Value.QUEEN;
import static com.greboreda.poker.card.Value.TEN;
import static com.greboreda.poker.hand.rank.Rank.RankValue.FOUR_OF_A_KIND;
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

class FourOfAKindTest {

	@Test
	void when_handHasFourCardsOfSameValue_then_hasFourOfAKind() {

		final Hand fourOfAKindHand = new Hand(
				a().ACE.of.HEARTS,
				a().ACE.of.CLUBS,
				a().ACE.of.DIAMONDS,
				a().ACE.of.SPADES,
				a().KING.of.DIAMONDS
		);
		final Rank rank = fourOfAKindHand.getRank();
		
		assertThat(rank.getRankValue(), is(FOUR_OF_A_KIND));
		final FourOfAKind fourOfAKind = (FourOfAKind) rank;
		assertAll("four of a kind is valid",
				() -> assertThat(fourOfAKind.getValue(), is(ACE)),
				() -> assertThat(fourOfAKind.getKicker(), is(KING))
		);
	}


	@ParameterizedTest
	@MethodSource("retrieveRanksWorseThanFourOfAKind")
	void when_comparingWithAnotherRankIfAnotherRankIsWorse_then_resultIsWin(Rank worseRank) {
		final FourOfAKind fourOfAKind = createFourOfAKind();
		assertThat(fourOfAKind.compare(worseRank), is(WIN));
	}

	private static Stream<Rank> retrieveRanksWorseThanFourOfAKind() {
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
	@MethodSource("retrieveRanksBetterThanFourOfAKind")
	void when_comparingWithAnotherRankIfAnotherRankIsBetter_then_resultIsLoose(Rank betterRank) {
		final FourOfAKind fourOfAKind = createFourOfAKind();
		assertThat(fourOfAKind.compare(betterRank), is(LOOSE));
	}
	private static Stream<Rank> retrieveRanksBetterThanFourOfAKind() {
		return Stream.of(createRoyalFlush(), createStraightFlush());
	}

	@Test
	void when_comparingWithAnotherFourOfAKindIfHasBetterValue_then_resultIsWin() {

		final FourOfAKind aFourOfAKind = FourOfAKind.create()
				.of(ACE)
				.withKicker(KING)
				.build();

		final FourOfAKind anotherFourOfAKind = FourOfAKind.create()
				.of(QUEEN)
				.withKicker(KING)
				.build();

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(WIN));
	}

	@Test
	void when_comparingWithAnotherFourOfAKindIfHasWorseValue_then_resultIsLoose() {

		final FourOfAKind aFourOfAKind = FourOfAKind.create()
				.of(TEN)
				.withKicker(KING)
				.build();

		final FourOfAKind anotherFourOfAKind = FourOfAKind.create()
				.of(QUEEN)
				.withKicker(KING)
				.build();

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(LOOSE));
	}

	@Test
	void when_comparingWithAnotherFourOfAKindIfHasSameValueAndBetterKicker_then_resultIsWin() {

		final FourOfAKind aFourOfAKind = FourOfAKind.create()
				.of(ACE)
				.withKicker(KING)
				.build();

		final FourOfAKind anotherFourOfAKind = FourOfAKind.create()
				.of(ACE)
				.withKicker(QUEEN)
				.build();

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(WIN));
	}

	@Test
	void when_comparingWithAnotherFourOfAKindIfHasSameValueAndWorseKicker_then_resultIsLoose() {

		final FourOfAKind aFourOfAKind = FourOfAKind.create()
				.of(ACE)
				.withKicker(QUEEN)
				.build();

		final FourOfAKind anotherFourOfAKind = FourOfAKind.create()
				.of(ACE)
				.withKicker(KING)
				.build();

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(LOOSE));
	}

	@Test
	void when_comparingWithAnotherFourOfAKindIfHasSameValueAndSameKicker_then_resultIsTie() {

		final FourOfAKind aFourOfAKind = FourOfAKind.create()
				.of(ACE)
				.withKicker(KING)
				.build();

		final FourOfAKind anotherFourOfAKind = FourOfAKind.create()
				.of(ACE)
				.withKicker(KING)
				.build();

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(TIE));
	}

	@Test
	void when_creatingFourOfAKind_then_valueAndKickerMustBeDifferent() {
		assertThrows(IllegalStateException.class, () -> FourOfAKind.create()
				.of(ACE)
				.withKicker(ACE)
				.build());
	}

}