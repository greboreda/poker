package com.greboreda.poker.card;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Stream;

import static com.greboreda.poker.card.Value.ACE;
import static com.greboreda.poker.card.Value.EIGHT;
import static com.greboreda.poker.card.Value.FIVE;
import static com.greboreda.poker.card.Value.FOUR;
import static com.greboreda.poker.card.Value.JACK;
import static com.greboreda.poker.card.Value.KING;
import static com.greboreda.poker.card.Value.QUEEN;
import static com.greboreda.poker.card.Value.SIX;
import static com.greboreda.poker.card.Value.TEN;
import static com.greboreda.poker.card.Value.THREE;
import static com.greboreda.poker.card.Value.TWO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ValueTest {

	@ParameterizedTest(name = "run consecutive #{index} with [{arguments}]")
	@MethodSource("consecutivesProvider")
	void test_consecutive(Set<Value> values) {
		assertTrue(Value.areConsecutive(values));
	}

	@ParameterizedTest(name = "run not consecutive #{index} with [{arguments}]")
	@MethodSource("notConsecutivesProvider")
	void test_notConsecutive(Set<Value> values) {
		assertFalse(Value.areConsecutive(values));
	}

	static Stream<Set<Value>> consecutivesProvider() {
		return Stream.of(
				EnumSet.of(ACE, THREE, TWO, FOUR, FIVE),
				EnumSet.of(TWO, THREE, FOUR, FIVE, SIX),
				EnumSet.of(QUEEN, ACE, TEN, KING, JACK)
		);
	}

	static Stream<Set<Value>> notConsecutivesProvider() {
		return Stream.of(
				EnumSet.of(ACE, THREE, TWO, FOUR, SIX),
				EnumSet.of(KING, QUEEN, ACE, TWO, THREE),
				EnumSet.of(TWO, FOUR, SIX, EIGHT, TEN)
		);
	}

}