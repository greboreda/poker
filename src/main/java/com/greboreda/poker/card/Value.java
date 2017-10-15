package com.greboreda.poker.card;

import com.greboreda.poker.ComparableEnum;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		return areDistinct(values) && !areConsecutive(new HashSet<>(values));
	}

	public static Boolean areDistinct(List<Value> values) {
		return values.stream().distinct().count() == values.size();
	}

	public static Boolean areConsecutive(Set<Value> values) {
		final List<Value> sorted = values.stream()
				.sorted(Comparator.comparingInt(Value::getWeight))
				.collect(toList());

		if(sorted.containsAll(EnumSet.of(ACE, TWO, THREE, FOUR, FIVE)))	{
			return true;
		}
		final Value max = sorted.stream().max(Comparator.comparingInt(Value::getWeight)).get();
		final Value min = sorted.stream().min(Comparator.comparingInt(Value::getWeight)).get();
		return max.getWeight() - min.getWeight() == values.size() - 1;
	}

}
