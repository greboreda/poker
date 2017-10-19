package com.greboreda.poker.hand.rank.highcard;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HighCard implements Rank {

	private final Value high;
	private final Value second;
	private final Value third;
	private final Value fourth;
	private final Value fifth;

	private HighCard(Value high, Value second, Value third, Value fourth, Value fifth) {
		checkAreDistinctAndNotConsecutive(high, second, third, fourth, fifth);
		final List<Value> sortedValues = Stream.of(high, second, third, fourth, fifth)
				.sorted(Comparator.comparingInt(Value::getWeight).reversed())
				.collect(Collectors.toList());
		this.high = sortedValues.get(0);
		this.second = sortedValues.get(1);
		this.third = sortedValues.get(2);
		this.fourth = sortedValues.get(3);
		this.fifth = sortedValues.get(4);
	}

	private void checkAreDistinctAndNotConsecutive(Value highKicker, Value secondKicker, Value thirdKicker, Value fourthKicker, Value fifthKicker) {
		final List<Value> values = Arrays.asList(highKicker, secondKicker, thirdKicker, fourthKicker, fifthKicker);
		if(!Value.areDistinctAndNotConsecutive(values)) {
			throw new IllegalStateException("all kickers must be different and not consecutive");
		}
	}

	public Value getHigh() {
		return high;
	}

	public Value getSecond() {
		return second;
	}

	public Value getThird() {
		return third;
	}

	public Value getFourth() {
		return fourth;
	}

	public Value getFifth() {
		return fifth;
	}

	@Override
	public RankValue getRankValue() {
		return RankValue.HIGH_CARD;
	}

	public static HighCardBuilder create() {
		return new HighCardBuilder();
	}

	public static class HighCardBuilder {
		@FunctionalInterface
		public interface AddSecond{
			AddThird with(Value value);
		}
		@FunctionalInterface
		public interface AddThird{
			AddFourth with(Value value);
		}
		@FunctionalInterface
		public interface AddFourth{
			AddFifth with(Value value);
		}
		@FunctionalInterface
		public interface AddFifth{
			Builder with(Value value);
		}
		@FunctionalInterface
		public interface Builder {
			HighCard build();
		}
		private HighCardBuilder() {

		}
		public AddSecond with(Value of) {
			return second -> third -> fourth -> fifth -> () -> new HighCard(of, second, third, fourth, fifth);
		}
	}
}
