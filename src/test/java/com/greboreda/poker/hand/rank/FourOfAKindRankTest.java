package com.greboreda.poker.hand.rank;

import com.greboreda.poker.hand.rank.fourofakind.FourOfAKind;
import org.junit.jupiter.api.Test;

import static com.greboreda.poker.card.Value.ACE;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FourOfAKindRankTest {

	@Test
	void should_have_distinct_quads_and_kicker_values() {
		assertThrows(IllegalStateException.class, () -> FourOfAKind.create()
				.of(ACE)
				.withKicker(ACE)
				.build());
	}


}
