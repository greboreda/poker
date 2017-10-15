package com.greboreda.poker.hand.rank;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Value;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface RankComparator<T extends Rank> {

	List<Pair<Value,Value>> initPairs();

	default Comparision compare() {
		final List<Pair<Value, Value>> pairs = initPairs();
		final CustomComparator customComparator = new CustomComparator(pairs);
		return customComparator.compare();
	}

	class CustomComparator {

		private final List<Pair<Value, Value>> valuePairs;

		private CustomComparator(List<Pair<Value,Value>> valuePairs) {
			Validate.notEmpty(valuePairs);
			this.valuePairs = valuePairs;
		}

		private Comparision compare() {
			for(Pair<Value,Value> pair : valuePairs) {
				final Comparision result = pair.getLeft().compare(pair.getRight());
				if(!result.isTie()) {
					return result;
				}
			}
			return Comparision.TIE;
		}
	}
}
