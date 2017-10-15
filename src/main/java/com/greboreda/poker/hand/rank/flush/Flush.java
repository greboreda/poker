package com.greboreda.poker.hand.rank.flush;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


public class Flush implements Rank {

	private final Value highKicker;
	private final Value secondKicker;
	private final Value thirdKicker;
	private final Value fourthKicker;
	private final Value fifthKicker;

	private Flush(Value kicker1, Value kicker2, Value kicker3, Value kicker4, Value kicker5) {
		checkAreDistinctAndNotConsecutive(kicker1, kicker2, kicker3, kicker4, kicker5);

		final List<Value> orderedKickers = Stream.of(kicker1, kicker2, kicker3, kicker4, kicker5)
				.sorted(Comparator.comparingInt(Value::getWeight).reversed())
				.collect(toList());

		this.highKicker = orderedKickers.get(0);
		this.secondKicker = orderedKickers.get(1);
		this.thirdKicker = orderedKickers.get(2);
		this.fourthKicker = orderedKickers.get(3);
		this.fifthKicker = orderedKickers.get(4);
	}

	private void checkAreDistinctAndNotConsecutive(Value highKicker, Value secondKicker, Value thirdKicker, Value fourthKicker, Value fifthKicker) {
		final List<Value> values = Arrays.asList(highKicker, secondKicker, thirdKicker, fourthKicker, fifthKicker);
		if(!Value.areDistinctAndNotConsecutive(values)) {
			throw new IllegalStateException("all kickers must be different and not consecutive");
		}
	}

	public Value getHighKicker() {
		return highKicker;
	}

	public Value getSecondKicker() {
		return secondKicker;
	}

	public Value getThirdKicker() {
		return thirdKicker;
	}

	public Value getFourthKicker() {
		return fourthKicker;
	}

	public Value getFifthKicker() {
		return fifthKicker;
	}

	@Override
	public RankValue getRankValue() {
		return RankValue.FLUSH;
	}

	@Override
	public Comparision compare(Rank another) {
		final Comparision rankComparision = this.getRankValue().compare(another.getRankValue());
		if(!rankComparision.isTie()) {
			return rankComparision;
		}
		final Flush anotherFlush = (Flush) another;
		return FlushComparator.create()
				.forFlush(this)
				.andForAnother(anotherFlush)
				.build()
				.compare();
	}


	public static StraightFlushBuilder create() {
		return new StraightFlushBuilder();
	}

	public static class StraightFlushBuilder {
		@FunctionalInterface
		public interface AddSecondKicker {
			AddThirdKicker withKicker(Value value);
		}
		@FunctionalInterface
		public interface AddThirdKicker {
			AddFourthKicker withKicker(Value value);
		}
		@FunctionalInterface
		public interface AddFourthKicker {
			AddFifthKicker withKicker(Value value);
		}
		@FunctionalInterface
		public interface AddFifthKicker {
			Builder withKicker(Value value);
		}
		@FunctionalInterface
		public interface Builder {
			Flush build();
		}
		private StraightFlushBuilder() {

		}
		public AddSecondKicker withKicker(Value first) {
			return second -> third -> fourth -> fifth -> () -> new Flush(first, second, third, fourth, fifth);
		}
	}

}
