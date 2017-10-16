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

}
