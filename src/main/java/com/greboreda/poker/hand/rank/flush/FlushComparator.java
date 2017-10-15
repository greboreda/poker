package com.greboreda.poker.hand.rank.flush;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.RankComparator;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

class FlushComparator implements RankComparator<Flush> {

	private final Flush aFlush;
	private final Flush anotherFlush;

	private FlushComparator(Flush aFlush, Flush anotherFlush) {
		this.aFlush = aFlush;
		this.anotherFlush = anotherFlush;
	}

	static FlushComparatorBuilder create() {
		return new FlushComparatorBuilder();
	}

	@Override
	public List<Pair<Value, Value>> initPairs() {
		final List<Pair<Value,Value>> pairs = new ArrayList<>();
		pairs.add(new ImmutablePair<>(aFlush.getHighKicker(), anotherFlush.getHighKicker()));
		pairs.add(new ImmutablePair<>(aFlush.getSecondKicker(), anotherFlush.getSecondKicker()));
		pairs.add(new ImmutablePair<>(aFlush.getThirdKicker(), anotherFlush.getThirdKicker()));
		pairs.add(new ImmutablePair<>(aFlush.getFourthKicker(), anotherFlush.getFourthKicker()));
		pairs.add(new ImmutablePair<>(aFlush.getFifthKicker(), anotherFlush.getFifthKicker()));
		return pairs;
	}

	static class FlushComparatorBuilder {
		@FunctionalInterface
		interface AddAnotherFlush {
			Builder andForAnother(Flush flush);
		}
		@FunctionalInterface
		interface Builder {
			FlushComparator build();
		}
		private FlushComparatorBuilder() {

		}
		AddAnotherFlush forFlush(Flush flush) {
			return another -> () -> new FlushComparator(flush, another);
		}
	}

}
