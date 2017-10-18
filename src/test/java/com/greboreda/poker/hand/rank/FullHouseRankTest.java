package com.greboreda.poker.hand.rank;

import com.greboreda.poker.hand.rank.fullhouse.FullHouse;
import org.junit.jupiter.api.Test;

import static com.greboreda.poker.card.Value.ACE;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FullHouseRankTest {

	@Test
	void should_have_distinct_trips_and_pair_values() {
		assertThrows(IllegalStateException.class, () -> FullHouse.create()
				.of(ACE)
				.over(ACE)
				.build());
	}

}
