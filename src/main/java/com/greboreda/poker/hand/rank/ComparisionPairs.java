package com.greboreda.poker.hand.rank;

import com.greboreda.poker.ComparableEnum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class ComparisionPairs<T extends ComparableEnum> {

	private Integer order = 0;
	private List<ComparisionPair<T>> pairs = new ArrayList<>();

	private ComparisionPairs() {
	}

	private void add(T left, T right) {
		pairs.add(new ComparisionPair<>(order, left, right));
		order += 1;
	}

	public Stream<ComparisionPair<T>> stream() {
		return pairs.stream()
				.sorted(Comparator.comparingInt(ComparisionPair::getOrder));
	}

	public static <T extends ComparableEnum> ComparisionPairsBuilder<T> forClass(Class<T> clazz) {
		return new ComparisionPairsBuilder<>();
	}

	static class ComparisionPair<T extends ComparableEnum> {
		private final T left;
		private final T right;
		private final Integer order;
		ComparisionPair(Integer order, T left, T right) {
			this.order = order;
			this.left = left;
			this.right = right;
		}
		T getLeft() {
			return left;
		}
		T getRight() {
			return right;
		}
		Integer getOrder() {
			return order;
		}
	}

	public static class ComparisionPairsBuilder<T extends ComparableEnum> {
		private final ComparisionPairs<T> pairs = new ComparisionPairs<>();
		private ComparisionPairsBuilder() {
		}
		public ComparisionPairsBuilder<T> withPair(T e1, T e2) {
			pairs.add(e1, e2);
			return this;
		}
		public ComparisionPairs<T> build() {
			return pairs;
		}
	}
}
