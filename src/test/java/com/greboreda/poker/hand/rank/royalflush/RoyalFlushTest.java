package com.greboreda.poker.hand.rank.royalflush;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.hand.rank.Rank;
import com.greboreda.poker.hand.rank.RankFactory;
import com.greboreda.poker.hand.rank.fourofakind.FourOfAKind;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.hand.rank.RankFactory.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class RoyalFlushTest {

	@Test
	void when_comparingRoyalFlushWithAnotherRoyalFlush_then_matchResultIsTie() {
		final RoyalFlush aRoyalFlush = new RoyalFlush();
		final RoyalFlush anotherRoyalFlush = new RoyalFlush();
		assertThat(aRoyalFlush.compare(anotherRoyalFlush), is(Comparision.TIE));
	}

	@ParameterizedTest
	@MethodSource("royalFlushWinnings")
	void when_comparingRoyalFlushWithAnotherNotRoyalFlush_then_matchResultIsWin(Rank rankAgainstWin) {
		final RoyalFlush royalFlush = new RoyalFlush();
		assertThat(royalFlush.compare(rankAgainstWin), is(Comparision.WIN));
	}

	private static Stream<Rank> royalFlushWinnings() {
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