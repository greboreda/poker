package com.greboreda.poker.hand.rank.threeofakind;

import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.Rank;
import com.greboreda.poker.hand.rank.twopair.TwoPair;
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
import static com.greboreda.poker.hand.rank.Rank.RankValue.THREE_OF_A_KIND;
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

class ThreeOfAKindTest {

	@Test
	void when_handHasAValueRepeatedThreeTimesAndAnotherTwoDistinct_then_hasThreeOfAKind() {

		final Hand threeOfAKindHand = new Hand(
				a().TEN.of.DIAMONDS,
				a().TEN.of.SPADES,
				a().TEN.of.CLUBS,
				a().SIX.of.HEARTS,
				a().JACK.of.CLUBS
		);
		final Rank rank = threeOfAKindHand.getRank();

		assertThat(rank.getRankValue(), is(THREE_OF_A_KIND));
		final ThreeOfAKind threeOfAKind = (ThreeOfAKind) rank;
		assertAll("two pais is valid",
				() -> assertThat(threeOfAKind.getValue(), is(TEN)),
				() -> assertThat(threeOfAKind.getHighKicker(), is(JACK)),
				() -> assertThat(threeOfAKind.getLowKicker(), is(SIX))
		);
	}

	@ParameterizedTest
	@MethodSource("retrieveRanksWorseThanThreeOfAPair")
	void when_comparingWithAnotherRankIfAnotherRankIsWorse_then_resultIsWin(Rank worseRank) {
		final ThreeOfAKind threeOfAKind = createThreeOfAKind();
		assertThat(threeOfAKind.compare(worseRank), is(WIN));
	}

	private static Stream<Rank> retrieveRanksWorseThanThreeOfAPair() {
		return Stream.of(
				createTwoPair(),
				createOnePair(),
				createHighCard());
	}

	@ParameterizedTest
	@MethodSource("retrieveRanksBetterThanThreeOfAKind")
	void when_comparingWithAnotherRankIfAnotherRankIsBetter_then_resultIsLoose(Rank betterRank) {
		final ThreeOfAKind threeOfAKind = createThreeOfAKind();
		assertThat(threeOfAKind.compare(betterRank), is(LOOSE));
	}
	private static Stream<Rank> retrieveRanksBetterThanThreeOfAKind() {
		return Stream.of(
				createRoyalFlush(),
				createFourOfAKind(),
				createRoyalFlush(),
				createFullHouse(),
				createFlush(),
				createStraight());
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
	void when_creatingAThreeOfAKind_then_highKickerMustBeBetterThanLowKicker() {
		assertThrows(IllegalStateException.class, () -> ThreeOfAKind.create()
				.of(KING)
				.withHighKicker(TWO)
				.withLowKicker(ACE)
				.build());
	}

	@Test
	void when_creatingAThreePair_then_tripsMustBeDifferentOfHighKicker() {
		assertThrows(IllegalStateException.class, () -> ThreeOfAKind.create()
				.of(KING)
				.withHighKicker(KING)
				.withLowKicker(ACE)
				.build());
	}

	@Test
	void when_creatingAThreePair_then_tripsMustBeDifferentOfLowKicker() {
		assertThrows(IllegalStateException.class, () -> ThreeOfAKind.create()
				.of(KING)
				.withHighKicker(QUEEN)
				.withLowKicker(KING)
				.build());
	}

	@Test
	void when_creatingAThreePair_then_highKickerMustBeDifferentOfLowKicker() {
		assertThrows(IllegalStateException.class, () -> ThreeOfAKind.create()
				.of(KING)
				.withHighKicker(ACE)
				.withLowKicker(ACE)
				.build());
	}

	@Test
	void should_win_if_have_better_trips_than_another_three_of_a_kind() {

		final ThreeOfAKind aThreeOfAKind = ThreeOfAKind.create()
				.of(ACE)
				.withHighKicker(KING)
				.withLowKicker(TEN)
				.build();

		final ThreeOfAKind anotherThreeOfAKind = ThreeOfAKind.create()
				.of(QUEEN)
				.withHighKicker(KING)
				.withLowKicker(TEN)
				.build();

		assertThat(aThreeOfAKind.compare(anotherThreeOfAKind), is(WIN));
	}

	@Test
	void should_loose_if_have_worse_trips_than_another_three_of_a_kind() {

		final ThreeOfAKind aThreeOfAKind = ThreeOfAKind.create()
				.of(QUEEN)
				.withHighKicker(KING)
				.withLowKicker(TEN)
				.build();

		final ThreeOfAKind anotherThreeOfAKind = ThreeOfAKind.create()
				.of(ACE)
				.withHighKicker(KING)
				.withLowKicker(TEN)
				.build();

		assertThat(aThreeOfAKind.compare(anotherThreeOfAKind), is(LOOSE));
	}

	@Test
	void should_win_if_have_same_trips_but_better_high_kicker_than_another_three_of_a_kind() {

		final ThreeOfAKind aThreeOfAKind = ThreeOfAKind.create()
				.of(ACE)
				.withHighKicker(KING)
				.withLowKicker(TEN)
				.build();

		final ThreeOfAKind anotherThreeOfAKind = ThreeOfAKind.create()
				.of(ACE)
				.withHighKicker(JACK)
				.withLowKicker(TEN)
				.build();

		assertThat(aThreeOfAKind.compare(anotherThreeOfAKind), is(WIN));
	}

	@Test
	void should_loose_if_have_same_trips_but_worse_high_kicker_than_another_three_of_a_kind() {

		final ThreeOfAKind aThreeOfAKind = ThreeOfAKind.create()
				.of(ACE)
				.withHighKicker(JACK)
				.withLowKicker(TEN)
				.build();

		final ThreeOfAKind anotherThreeOfAKind = ThreeOfAKind.create()
				.of(ACE)
				.withHighKicker(KING)
				.withLowKicker(TEN)
				.build();

		assertThat(aThreeOfAKind.compare(anotherThreeOfAKind), is(LOOSE));
	}

	@Test
	void should_win_if_have_same_trips_and_high_kicker_but_better_low_kicker_than_another_three_of_a_kind() {

		final ThreeOfAKind aThreeOfAKind = ThreeOfAKind.create()
				.of(ACE)
				.withHighKicker(KING)
				.withLowKicker(QUEEN)
				.build();

		final ThreeOfAKind anotherThreeOfAKind = ThreeOfAKind.create()
				.of(ACE)
				.withHighKicker(KING)
				.withLowKicker(TEN)
				.build();

		assertThat(aThreeOfAKind.compare(anotherThreeOfAKind), is(WIN));

	}

	@Test
	void should_loose_if_have_same_trips_and_high_kicker_but_worse_low_kicker_than_another_three_of_a_kind() {

		final ThreeOfAKind aThreeOfAKind = ThreeOfAKind.create()
				.of(ACE)
				.withHighKicker(KING)
				.withLowKicker(TWO)
				.build();

		final ThreeOfAKind anotherThreeOfAKind = ThreeOfAKind.create()
				.of(ACE)
				.withHighKicker(KING)
				.withLowKicker(TEN)
				.build();

		assertThat(aThreeOfAKind.compare(anotherThreeOfAKind), is(LOOSE));
	}

	@Test
	void should_tie_if_have_same_trips_and_high_kicker_and_low_kicker_than_another_three_of_a_kind() {

		final ThreeOfAKind aThreeOfAKind = ThreeOfAKind.create()
				.of(ACE)
				.withHighKicker(KING)
				.withLowKicker(TEN)
				.build();

		final ThreeOfAKind anotherThreeOfAKind = ThreeOfAKind.create()
				.of(ACE)
				.withHighKicker(KING)
				.withLowKicker(TEN)
				.build();

		assertThat(aThreeOfAKind.compare(anotherThreeOfAKind), is(TIE));
	}

}