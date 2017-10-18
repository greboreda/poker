package com.greboreda.poker.card;

import com.greboreda.poker.ComparableEnum;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public enum Value implements ComparableEnum<Value> {

	ACE(13),
	KING(12),
	QUEEN(11),
	JACK(10),
	TEN(9),
	NINE(8),
	EIGHT(7),
	SEVEN(6),
	SIX(5),
	FIVE(4),
	FOUR(3),
	THREE(2),
	TWO(1);

	private Integer weight;

	@Override
	public Integer getWeight() {
		return weight;
	}

	Value(Integer weight) {
		this.weight = weight;
	}

	public static Boolean areDistinctAndNotConsecutive(List<Value> values) {
		return areDistinct(values) && !areConsecutive(values);
	}

	public static Boolean areDistinctAndConsecutive(List<Value> values) {
		return areDistinct(values) && areConsecutive(values);
	}

	public static Boolean areDistinct(List<Value> values) {
		return values.stream().distinct().count() == values.size();
	}

	public static Boolean areConsecutive(List<Value> values) {
		if(values.containsAll(EnumSet.of(ACE, TWO, THREE, FOUR, FIVE)))	{
			return true;
		}
		final List<Value> sorted = values.stream()
				.sorted(Comparator.comparingInt(Value::getWeight))
				.collect(toList());
		final Value min = sorted.stream().min(Comparator.comparingInt(Value::getWeight)).get();

		final int expectedHash = IntStream.range(min.getWeight(), min.getWeight() + values.size()).sum();
		final int hash = values.stream()
				.mapToInt(Value::getWeight)
				.sum();
		return hash == expectedHash;
	}

	public static Stream<Value> valueStream() {
		return Stream.of(Value.values());
	}

}
