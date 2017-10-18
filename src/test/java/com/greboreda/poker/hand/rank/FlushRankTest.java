package com.greboreda.poker.hand.rank;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.flush.Flush;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.greboreda.poker.card.Value.ACE;
import static com.greboreda.poker.card.Value.EIGHT;
import static com.greboreda.poker.card.Value.FIVE;
import static com.greboreda.poker.card.Value.FOUR;
import static com.greboreda.poker.card.Value.JACK;
import static com.greboreda.poker.card.Value.KING;
import static com.greboreda.poker.card.Value.NINE;
import static com.greboreda.poker.card.Value.QUEEN;
import static com.greboreda.poker.card.Value.SEVEN;
import static com.greboreda.poker.card.Value.SIX;
import static com.greboreda.poker.card.Value.TEN;
import static com.greboreda.poker.card.Value.THREE;
import static com.greboreda.poker.card.Value.TWO;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FlushRankTest {

	@ParameterizedTest
	@MethodSource("consecutives")
	void should_have_not_consectutive_values(Value kicker1, Value kicker2, Value kicker3, Value kicker4, Value kicker5) {
		assertThrows(IllegalStateException.class, () -> Flush.create()
				.with(kicker1)
				.with(kicker2)
				.with(kicker3)
				.with(kicker4)
				.with(kicker5)
				.build());
	}

	private static Stream<Arguments> consecutives() {
		return Stream.of(
				Arguments.of(TWO, THREE, FOUR, FIVE, SIX),
				Arguments.of(FIVE, SIX, SEVEN, NINE, EIGHT),
				Arguments.of(ACE, TEN, JACK, QUEEN, KING),
				Arguments.of(TEN, QUEEN, JACK, NINE, EIGHT),
				Arguments.of(FOUR, FIVE, THREE, SIX, TWO)
		);
	}

	@ParameterizedTest
	@MethodSource("repeated")
	void should_have_all_distinct_values(Value kicker1, Value kicker2, Value kicker3, Value kicker4, Value kicker5) {
		assertThrows(IllegalStateException.class, () -> Flush.create()
				.with(kicker1)
				.with(kicker2)
				.with(kicker3)
				.with(kicker4)
				.with(kicker5)
				.build());
	}

	private static Stream<Arguments> repeated() {
		return Stream.of(
				Arguments.of(TWO, TWO, FOUR, FIVE, SIX),
				Arguments.of(FIVE, SIX, SIX, SIX, EIGHT),
				Arguments.of(ACE, TEN, JACK, QUEEN, JACK),
				Arguments.of(EIGHT, QUEEN, JACK, NINE, EIGHT),
				Arguments.of(FOUR, FOUR, FOUR, FOUR, FOUR)
		);
	}

}
