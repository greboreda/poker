package com.greboreda.poker.hand.rank;

import com.greboreda.poker.hand.rank.twopair.TwoPair;
import org.junit.jupiter.api.Test;

import static com.greboreda.poker.card.Value.ACE;
import static com.greboreda.poker.card.Value.THREE;
import static com.greboreda.poker.card.Value.TWO;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TwoPairRankTest {
	@Test
	void should_have_distinct_high_pair_than_low_pair() {
		assertThrows(IllegalStateException.class, () -> TwoPair.create()
				.withPair(TWO)
				.withPair(TWO)
				.withKicker(ACE)
				.build());
	}

	@Test
	void should_have_distinct_high_pair_than_kicker() {
		assertThrows(IllegalStateException.class, () -> TwoPair.create()
				.withPair(TWO)
				.withPair(THREE)
				.withKicker(TWO)
				.build());
	}

	@Test
	void should_have_distinct_low_pair_than_kicker() {
		assertThrows(IllegalStateException.class, () -> TwoPair.create()
				.withPair(ACE)
				.withPair(THREE)
				.withKicker(THREE)
				.build());
	}

}
