package com.greboreda.poker.hand.rank;

import com.greboreda.poker.hand.rank.threeofakind.ThreeOfAKind;
import org.junit.jupiter.api.Test;

import static com.greboreda.poker.card.Value.ACE;
import static com.greboreda.poker.card.Value.KING;
import static com.greboreda.poker.card.Value.QUEEN;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ThreeOfAKindRankTest {


	@Test
	void should_have_trips_distinct_to_high_kicker() {
		assertThrows(IllegalStateException.class, () -> ThreeOfAKind.create()
				.of(KING)
				.withKicker(KING)
				.withKicker(ACE)
				.build());
	}

	@Test
	void should_have_trips_distinct_to_low_kicker() {
		assertThrows(IllegalStateException.class, () -> ThreeOfAKind.create()
				.of(KING)
				.withKicker(QUEEN)
				.withKicker(KING)
				.build());
	}

	@Test
	void should_have_distinct_high_kicker_and_low_kicker() {
		assertThrows(IllegalStateException.class, () -> ThreeOfAKind.create()
				.of(KING)
				.withKicker(ACE)
				.withKicker(ACE)
				.build());
	}

}
