package com.greboreda.poker.hand.rank;

import com.greboreda.poker.hand.rank.flush.Flush;
import com.greboreda.poker.hand.rank.flush.FlushComparator;
import com.greboreda.poker.hand.rank.fourofakind.FourOfAKind;
import com.greboreda.poker.hand.rank.fourofakind.FourOfAKindComparator;
import com.greboreda.poker.hand.rank.fullhouse.FullHouse;
import com.greboreda.poker.hand.rank.fullhouse.FullHouseComparator;
import com.greboreda.poker.hand.rank.royalflush.RoyalFlush;
import com.greboreda.poker.hand.rank.royalflush.RoyalFlushComparator;
import com.greboreda.poker.hand.rank.straightflush.StraightFlush;
import com.greboreda.poker.hand.rank.straightflush.StraightFlushComparator;
import com.greboreda.poker.hand.rank.threeofakind.ThreeOfAKind;
import com.greboreda.poker.hand.rank.threeofakind.ThreeOfAKindComparator;
import com.greboreda.poker.hand.rank.twopair.TwoPair;
import com.greboreda.poker.hand.rank.twopair.TwoPairComparator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

class RankComparatorFactory {

	private static Map<Class<? extends Rank>, BiFunction<Rank,Rank,RankComparator>> map = new HashMap<>();
	static {
		map.put(RoyalFlush.class, (r1, r2) -> new RoyalFlushComparator());
		map.put(StraightFlush.class, (r1, r2) -> new StraightFlushComparator((StraightFlush) r1, (StraightFlush) r2));
		map.put(FourOfAKind.class, (r1, r2) -> new FourOfAKindComparator((FourOfAKind) r1, (FourOfAKind) r2));
		map.put(FullHouse.class, (r1, r2) -> new FullHouseComparator((FullHouse) r1, (FullHouse) r2));
		map.put(Flush.class, (r1, r2) -> new FlushComparator((Flush) r1, (Flush) r2));
		map.put(ThreeOfAKind.class, (r1, r2) -> new ThreeOfAKindComparator((ThreeOfAKind) r1, (ThreeOfAKind) r2));
		map.put(TwoPair.class, (r1, r2) -> new TwoPairComparator((TwoPair) r1, (TwoPair) r2));
	}

	static <R extends Rank> RankComparator create(Class<? extends Rank> clazz, R aRank, R anotherRank) {
		final boolean aRankIsOfValidType = aRank.getClass().equals(clazz);
		final boolean anotherRankIsOfValidType = anotherRank.getClass().equals(clazz);
		if(!aRankIsOfValidType || !anotherRankIsOfValidType) {
			throw new IllegalArgumentException("ranks must be instance of clazz");
		}
		if(!map.containsKey(clazz)) {
			throw new RuntimeException("Not found constructor for " + clazz.getCanonicalName());
		}
		return map.get(clazz).apply(aRank, anotherRank);
	}

}
