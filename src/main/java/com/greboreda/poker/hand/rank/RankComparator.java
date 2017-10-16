package com.greboreda.poker.hand.rank;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Value;

public interface RankComparator {

	ComparisionPairs<Value> retrieveComparisionPairs();

	default Comparision compare() {
		return retrieveComparisionPairs().stream()
				.map(p -> p.getLeft().compare(p.getRight()))
				.filter(c -> !c.isTie())
				.findFirst()
				.orElse(Comparision.TIE);
	}

	static <R extends Rank> RankComparatorBuilder<R> forRank(Class<R> clazz) {
		return new RankComparatorBuilder<>();
	}

	class RankComparatorBuilder<R extends Rank>  {
		@FunctionalInterface
		public interface AddAnotherRank<R extends Rank> {
			Comparator<R> withAnotherRank(R anotherRank);
		}
		@FunctionalInterface
		public interface Comparator<R extends Rank> {
			Comparision compare();
		}
		public AddAnotherRank<R> withRank(R aRank) {
			return anotherRank -> () -> RankComparatorFactory.create(aRank, anotherRank).compare();
		}
	}


}
