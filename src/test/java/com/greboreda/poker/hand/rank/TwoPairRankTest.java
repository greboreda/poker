package com.greboreda.poker.hand.rank;

import com.greboreda.poker.hand.rank.twopair.TwoPair;
import org.junit.jupiter.api.Test;

import static com.greboreda.poker.card.Value.ACE;
import static com.greboreda.poker.card.Value.THREE;
import static com.greboreda.poker.card.Value.TWO;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TwoPairRankTest {
	@Test
	void souhld_be_distinct_high_pair_than_low_pair() {
		assertThrows(IllegalStateException.class, () -> TwoPair.create()
				.withHighPair(TWO)
				.withLowPair(TWO)
				.withKicker(ACE)
				.build());
	}

	@Test
	void should_have_better_high_pair_than_low_pair() {
		assertThrows(IllegalStateException.class, () -> TwoPair.create()
				.withHighPair(TWO)
				.withLowPair(THREE)
				.withKicker(ACE)
				.build());
	}

	@Test
	void should_have_distinct_high_pair_than_kicker() {
		assertThrows(IllegalStateException.class, () -> TwoPair.create()
				.withHighPair(TWO)
				.withLowPair(THREE)
				.withKicker(TWO)
				.build());
	}

	@Test
	void should_have_distinct_low_pair_than_kicker() {
		assertThrows(IllegalStateException.class, () -> TwoPair.create()
				.withHighPair(ACE)
				.withLowPair(THREE)
				.withKicker(THREE)
				.build());
	}

}
