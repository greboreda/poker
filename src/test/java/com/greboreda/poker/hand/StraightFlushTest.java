package com.greboreda.poker.hand;


import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank.RankValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.Comparision.LOOSE;
import static com.greboreda.poker.Comparision.TIE;
import static com.greboreda.poker.Comparision.WIN;
import static com.greboreda.poker.card.CardBuilder.CardCreator.a;
import static com.greboreda.poker.card.CardBuilder.aCard;
import static com.greboreda.poker.card.Suit.DIAMONDS;
import static com.greboreda.poker.card.Value.ACE;
import static com.greboreda.poker.card.Value.EIGHT;
import static com.greboreda.poker.card.Value.FIVE;
import static com.greboreda.poker.card.Value.FOUR;
import static com.greboreda.poker.card.Value.NINE;
import static com.greboreda.poker.card.Value.SEVEN;
import static com.greboreda.poker.card.Value.SIX;
import static com.greboreda.poker.card.Value.TEN;
import static com.greboreda.poker.card.Value.THREE;
import static com.greboreda.poker.card.Value.TWO;
import static com.greboreda.poker.hand.rank.Rank.RankValue.STRAIGHT_FLUSH;
import static com.greboreda.poker.hand.util.HandBuilder.hand;
import static com.greboreda.poker.hand.util.HandRepository.aFlush;
import static com.greboreda.poker.hand.util.HandRepository.aFourOfAKind;
import static com.greboreda.poker.hand.util.HandRepository.aFullHouse;
import static com.greboreda.poker.hand.util.HandRepository.aHighCard;
import static com.greboreda.poker.hand.util.HandRepository.aOnePair;
import static com.greboreda.poker.hand.util.HandRepository.aRoyalFlush;
import static com.greboreda.poker.hand.util.HandRepository.aStraight;
import static com.greboreda.poker.hand.util.HandRepository.aStraightFlush;
import static com.greboreda.poker.hand.util.HandRepository.aThreeOfAKind;
import static com.greboreda.poker.hand.util.HandRepository.aTwoPair;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

class StraightFlushTest {

	@ParameterizedTest
	@MethodSource("straightFlushes")
	void should_have_straight_flush_if_have_all_cards_of_same_suit_and_are_consecutive_but_not_ace_king_queen_jack_ten(Hand straightFlushHand, Value high) {
		assertThat(straightFlushHand.getRank(), is(RankValue.STRAIGHT_FLUSH));
	}

	private static Stream<Arguments> straightFlushes() {
		return Stream.of(
				Arguments.of(aStraightFlushWith(SIX, EIGHT, NINE, SEVEN, TEN), TEN),
				Arguments.of(aStraightFlushWith(ACE, TWO, THREE, FOUR, FIVE), FIVE)
		);
	}

	private static Hand aStraightFlushWith(Value value1, Value value2, Value value3, Value value4, Value value5) {
		return hand()
				.with(aCard().withValue(value1).withSuit(DIAMONDS).build())
				.with(aCard().withValue(value2).withSuit(DIAMONDS).build())
				.with(aCard().withValue(value3).withSuit(DIAMONDS).build())
				.with(aCard().withValue(value4).withSuit(DIAMONDS).build())
				.with(aCard().withValue(value5).withSuit(DIAMONDS).build());
	}

	@ParameterizedTest
	@MethodSource("retrieveHandsWorseThanStraightFlush")
	void should_win_if_compares_with_another_hand_with_worse_rank(Hand worseHand) {
		final Hand straightFlush = aStraightFlush();
		assertThat(straightFlush.compare(worseHand), is(WIN));
	}

	private static Stream<Hand> retrieveHandsWorseThanStraightFlush() {
		return Stream.of(
				aHighCard(),
				aOnePair(),
				aTwoPair(),
				aThreeOfAKind(),
				aStraight(),
				aFlush(),
				aFullHouse(),
				aFourOfAKind()
		);
	}

	@Test
	void should_loose_versus_royal_flush() {
		final Hand straightFlush = aStraightFlush();
		final Hand royalFlush = aRoyalFlush();
		assertThat(straightFlush.compare(royalFlush), is(LOOSE));
	}

	@Test
	void should_win_if_has_better_high_card_than_another_straight_flush() {

		final Hand aStraightFlush = hand()
				.with(a().TEN.of.SPADES)
				.with(a().NINE.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SEVEN.of.SPADES)
				.with(a().SIX.of.SPADES);

		final Hand anotherStraightFlush = hand()
				.with(a().NINE.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SEVEN.of.SPADES)
				.with(a().SIX.of.SPADES)
				.with(a().FIVE.of.SPADES);

		assertAll(
				() -> assertThat(aStraightFlush.getRank(), is(STRAIGHT_FLUSH)),
				() -> assertThat(anotherStraightFlush.getRank(), is(STRAIGHT_FLUSH))
		);

		assertThat(aStraightFlush.compare(anotherStraightFlush), is(WIN));
	}

	@Test
	void should_loose_if_has_worse_high_card_than_another_straight_flush() {

		final Hand aStraightFlush = hand()
				.with(a().NINE.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SEVEN.of.SPADES)
				.with(a().SIX.of.SPADES)
				.with(a().FIVE.of.SPADES);

		final Hand anotherStraightFlush = hand()
				.with(a().TEN.of.SPADES)
				.with(a().NINE.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SEVEN.of.SPADES)
				.with(a().SIX.of.SPADES);

		assertAll(
				() -> assertThat(aStraightFlush.getRank(), is(STRAIGHT_FLUSH)),
				() -> assertThat(anotherStraightFlush.getRank(), is(STRAIGHT_FLUSH))
		);

		assertThat(aStraightFlush.compare(anotherStraightFlush), is(LOOSE));
	}

	@Test
	void should_tie_if_has_same_high_card_than_another_straight_flush() {

		final Hand aStraightFlush = hand()
				.with(a().TEN.of.SPADES)
				.with(a().NINE.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SEVEN.of.SPADES)
				.with(a().SIX.of.SPADES);

		final Hand anotherStraightFlush = hand()
				.with(a().TEN.of.SPADES)
				.with(a().NINE.of.SPADES)
				.with(a().EIGHT.of.SPADES)
				.with(a().SEVEN.of.SPADES)
				.with(a().SIX.of.SPADES);

		assertAll(
				() -> assertThat(aStraightFlush.getRank(), is(STRAIGHT_FLUSH)),
				() -> assertThat(anotherStraightFlush.getRank(), is(STRAIGHT_FLUSH))
		);

		assertThat(aStraightFlush.compare(anotherStraightFlush), is(TIE));
	}

}