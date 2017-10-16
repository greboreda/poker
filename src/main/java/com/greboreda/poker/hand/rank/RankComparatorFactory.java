package com.greboreda.poker.hand.rank;

import com.greboreda.poker.hand.rank.flush.Flush;
import com.greboreda.poker.hand.rank.flush.FlushComparator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

class RankComparatorFactory {

	private static final Map<Class<?>,Constructor<? extends RankComparator>> constructors = new HashMap<>();
	static {
		try {
			constructors.put(Flush.class, FlushComparator.class.getDeclaredConstructor(Flush.class, Flush.class));
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
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
