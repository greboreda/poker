package com.greboreda.poker.hand.rank;

import com.greboreda.poker.hand.rank.flush.Flush;
import com.greboreda.poker.hand.rank.flush.FlushComparator;
import com.greboreda.poker.hand.rank.fourofakind.FourOfAKind;
import com.greboreda.poker.hand.rank.fourofakind.FourOfAKindComparator;
import com.greboreda.poker.hand.rank.fullhouse.FullHouse;
import com.greboreda.poker.hand.rank.fullhouse.FullHouseComparator;
import com.greboreda.poker.hand.rank.threeofakind.ThreeOfAKind;
import com.greboreda.poker.hand.rank.threeofakind.ThreeOfAKindComparator;
import com.greboreda.poker.hand.rank.twopair.TwoPair;
import com.greboreda.poker.hand.rank.twopair.TwoPairComparator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

class RankComparatorFactory {

	private static final Map<Class<? extends Rank>,Class<? extends RankComparator>> rankComparators = new HashMap<>();

	private static final Map<Class<?>,Constructor<? extends RankComparator>> constructors = new HashMap<>();
	static {
		rankComparators.put(FourOfAKind.class, FourOfAKindComparator.class);
		rankComparators.put(FullHouse.class, FullHouseComparator.class);
		rankComparators.put(Flush.class, FlushComparator.class);
		rankComparators.put(ThreeOfAKind.class, ThreeOfAKindComparator.class);
		rankComparators.put(TwoPair.class, TwoPairComparator.class);
		initConstructorsMap();
	}

	private static void initConstructorsMap() {
		for(Entry<Class<? extends Rank>,Class<? extends RankComparator>> entry : rankComparators.entrySet()) {
			final Class<? extends Rank> clazz = entry.getKey();
			final Class<? extends RankComparator> comparator = entry.getValue();
			Constructor<? extends RankComparator> constructor;
			try {
				constructor = comparator.getDeclaredConstructor(clazz, clazz);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
			constructors.put(clazz, constructor);
		}
	}

	static <R extends Rank> RankComparator create(R aRank, R anotherRank) {
		final Class<? extends Rank> clazz = aRank.getClass();
		if(!constructors.containsKey(clazz)) {
			throw new RuntimeException("Not found constructor for " + clazz.getCanonicalName());
		}
		final Constructor<? extends RankComparator> constructor = constructors.get(clazz);
		try {
			return constructor.newInstance(aRank, anotherRank);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

}
