package com.greboreda.poker.hand.rank.royalflush;

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
import static com.greboreda.poker.hand.rank.RankRepository.createStraight;
import static com.greboreda.poker.hand.rank.RankRepository.createStraightFlush;
import static com.greboreda.poker.hand.rank.RankRepository.createThreeOfAKind;
import static com.greboreda.poker.hand.rank.RankRepository.createTwoPair;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class RoyalFlushTest {

	@Test
	void whenHandHasAceKingQueenJackAndTenOfSameSuit_then_hasRoyalFlush() {

		final Card ace = Card.create().withValue(Value.ACE).withSuit(Suit.CLUBS).build();
		final Card king = Card.create().withValue(Value.KING).withSuit(Suit.CLUBS).build();
		final Card queen = Card.create().withValue(Value.QUEEN).withSuit(Suit.CLUBS).build();
		final Card jack = Card.create().withValue(Value.JACK).withSuit(Suit.CLUBS).build();
		final Card ten = Card.create().withValue(Value.TEN).withSuit(Suit.CLUBS).build();
		final Hand royalFlushHand = new Hand(ace, king, queen, jack, ten);
		final Rank rank = royalFlushHand.getRank();

		assertThat(rank.getRankValue(), is(RankValue.ROYAL_FLUSH));
		final RoyalFlush royalFlush = (RoyalFlush) rank;
	}


	@Test
	void when_comparingRoyalFlushWithAnotherRoyalFlush_then_matchResultIsTie() {
		final RoyalFlush aRoyalFlush = new RoyalFlush();
		final RoyalFlush anotherRoyalFlush = new RoyalFlush();
		assertThat(aRoyalFlush.compare(anotherRoyalFlush), is(Comparision.TIE));
	}

	@ParameterizedTest
	@MethodSource("retrieveRanksWorseThanRoyalFlush")
	void when_comparingRoyalFlushWithAnotherNotRoyalFlush_then_matchResultIsWin(Rank worseRank) {
		final RoyalFlush royalFlush = new RoyalFlush();
		assertThat(royalFlush.compare(worseRank), is(Comparision.WIN));
	}

	private static Stream<Rank> retrieveRanksWorseThanRoyalFlush() {
		return Stream.of(
				createStraightFlush(),
				createFourOfAKind(),
				createFullHouse(),
				createFlush(),
				createStraight(),
				createThreeOfAKind(),
				createTwoPair(),
				createOnePair(),
				createHighCard()
		);
	}

}