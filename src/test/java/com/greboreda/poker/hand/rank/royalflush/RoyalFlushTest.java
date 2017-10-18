package com.greboreda.poker.hand.rank.royalflush;

import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.hand.rank.Rank;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.Comparision.TIE;
import static com.greboreda.poker.Comparision.WIN;
import static com.greboreda.poker.card.CardBuilder.CardCreator.a;
import static com.greboreda.poker.hand.rank.Rank.RankValue.ROYAL_FLUSH;
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

		final Hand royalFlushHand = new Hand(
				a().ACE.of.CLUBS,
				a().KING.of.CLUBS,
				a().QUEEN.of.CLUBS,
				a().JACK.of.CLUBS,
				a().TEN.of.CLUBS
		);
		final Rank rank = royalFlushHand.getRank();

		assertThat(rank.getRankValue(), is(ROYAL_FLUSH));
		final RoyalFlush royalFlush = (RoyalFlush) rank;
	}


	@Test
	void when_comparingRoyalFlushWithAnotherRoyalFlush_then_matchResultIsTie() {
		final RoyalFlush aRoyalFlush = new RoyalFlush();
		final RoyalFlush anotherRoyalFlush = new RoyalFlush();
		assertThat(aRoyalFlush.compare(anotherRoyalFlush), is(TIE));
	}

	@ParameterizedTest
	@MethodSource("retrieveRanksWorseThanRoyalFlush")
	void when_comparingRoyalFlushWithAnotherNotRoyalFlush_then_matchResultIsWin(Rank worseRank) {
		final RoyalFlush royalFlush = new RoyalFlush();
		assertThat(royalFlush.compare(worseRank), is(WIN));
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