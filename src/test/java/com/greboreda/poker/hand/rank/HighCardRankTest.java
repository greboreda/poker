package com.greboreda.poker.hand.rank;

import com.greboreda.poker.hand.rank.highcard.HighCard;
import org.junit.jupiter.api.Test;

import static com.greboreda.poker.card.Value.ACE;
import static com.greboreda.poker.card.Value.EIGHT;
import static com.greboreda.poker.card.Value.JACK;
import static com.greboreda.poker.card.Value.NINE;
import static com.greboreda.poker.card.Value.SEVEN;
import static com.greboreda.poker.card.Value.SIX;
import static com.greboreda.poker.card.Value.TEN;
import static com.greboreda.poker.card.Value.TWO;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HighCardRankTest {

	@Test
	void should_have_all_values_distinct() {
		assertThrows(IllegalStateException.class, () -> HighCard.create()
				.with(ACE)
				.with(ACE)
				.with(TEN)
				.with(SIX)
				.with(TWO)
				.build());
	}

	@Test
	void should_have_not_consecutive_values() {
		assertThrows(IllegalStateException.class, () -> HighCard.create()
				.with(JACK)
				.with(NINE)
				.with(TEN)
				.with(EIGHT)
				.with(SEVEN)
				.build());
	}

}
