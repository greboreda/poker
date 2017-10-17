package com.greboreda.poker.card;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
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

	@ParameterizedTest(name = "run consecutive #{index} with {arguments}")
	@MethodSource("consecutivesProvider")
	void test_consecutive(List<Value> values) {
		assertTrue(Value.areConsecutive(values));
	}

	@ParameterizedTest(name = "run not consecutive #{index} with {arguments}")
	@MethodSource("notConsecutivesProvider")
	void test_notConsecutive(List<Value> values) {
		assertFalse(Value.areConsecutive(values));
	}

	static Stream<List<Value>> consecutivesProvider() {
		return Stream.of(
				new ArrayList<>(EnumSet.of(ACE, THREE, TWO, FOUR, FIVE)),
				new ArrayList<>(EnumSet.of(TWO, THREE, FOUR, FIVE, SIX)),
				new ArrayList<>(EnumSet.of(QUEEN, ACE, TEN, KING, JACK)),
				new ArrayList<>(EnumSet.of(TWO, TWO))
		);
	}

	static Stream<List<Value>> notConsecutivesProvider() {
		return Stream.of(
				new ArrayList<>(EnumSet.of(ACE, THREE, TWO, FOUR, SIX)),
				new ArrayList<>(EnumSet.of(KING, QUEEN, ACE, TWO, THREE)),
				new ArrayList<>(EnumSet.of(TWO, FOUR, SIX, EIGHT, TEN))
		);
	}

}