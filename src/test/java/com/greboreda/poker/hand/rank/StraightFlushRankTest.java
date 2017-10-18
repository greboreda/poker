package com.greboreda.poker.hand.rank;

import com.greboreda.poker.hand.rank.straightflush.StraightFlush;
import org.junit.jupiter.api.Test;

import static com.greboreda.poker.card.Value.ACE;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StraightFlushRankTest {

	@Test
	void should_not_have_and_ace_as_high_card() {
		assertThrows(IllegalArgumentException.class, () -> StraightFlush.create()
				.withHigh(ACE)
				.build()
		);
	}
}
