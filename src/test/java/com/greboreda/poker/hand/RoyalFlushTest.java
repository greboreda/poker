package com.greboreda.poker.hand;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.Comparision.TIE;
import static com.greboreda.poker.Comparision.WIN;
import static com.greboreda.poker.card.CardBuilder.CardCreator.a;
import static com.greboreda.poker.hand.rank.Rank.RankValue.ROYAL_FLUSH;
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

class RoyalFlushTest {

	@Test
	void should_have_royal_flush_if_has_ace_king_queen_jack_and_ten_of_same_suit() {
		final Hand royalFlush = new Hand(
				a().ACE.of.CLUBS,
				a().KING.of.CLUBS,
				a().QUEEN.of.CLUBS,
				a().JACK.of.CLUBS,
				a().TEN.of.CLUBS
		);
		assertThat(royalFlush.getRank(), is(ROYAL_FLUSH));
	}

	@Test
	void should_tie_if_compares_with_another_royal_flush() {
		final Hand aRoyalFlush = aRoyalFlush();
		final Hand anotherRoyalFlush = aRoyalFlush();
		assertThat(aRoyalFlush.compare(anotherRoyalFlush), is(TIE));
	}

	@ParameterizedTest
	@MethodSource("retrieveHandsWorseThanRoyalFlush")
	void should_win_if_compares_with_another_hand_that_is_not_royal_flush(Hand worseHand) {
		final Hand royalFlush = aRoyalFlush();
		assertThat(royalFlush.compare(worseHand), is(WIN));
	}

	private static Stream<Hand> retrieveHandsWorseThanRoyalFlush() {
		return Stream.of(
				aStraightFlush(),
				aFourOfAKind(),
				aFullHouse(),
				aFlush(),
				aStraight(),
				aThreeOfAKind(),
				aTwoPair(),
				aOnePair(),
				aHighCard()
		);
	}

}