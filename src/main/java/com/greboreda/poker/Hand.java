package com.greboreda.poker;

import com.greboreda.poker.Card.Suit;
import com.greboreda.poker.Card.Value;
import com.greboreda.poker.rank.HandRankCalculator;
import com.greboreda.poker.rank.Rank;
import org.apache.commons.lang3.Validate;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

public class Hand  {

	private final Set<Card> cards;
	public final Rank rank;

	public Hand(Card card1, Card card2, Card card3, Card card4, Card card5) {
		Validate.notNull(card1);
		Validate.notNull(card2);
		Validate.notNull(card3);
		Validate.notNull(card4);
		Validate.notNull(card5);
		cards = new HashSet<>();
		cards.add(card1);
		cards.add(card2);
		cards.add(card3);
		cards.add(card4);
		cards.add(card5);
		if (!allCardsAreDifferent()) {
			throw new IllegalStateException("cards must be different each other");
		}
		final HandRankCalculator handRankCalculator = new HandRankCalculator();
		rank = handRankCalculator.calculateHandRank(this);
	}

	public Set<Suit> getDistinctSuits() {

		return cards.stream()
				.map(c -> c.suit)
				.collect(toSet());
	}


	public Set<Value> getCardsValues() {
		return cards.stream()
				.map(c -> c.value)
				.collect(toSet());
	}

	public Set<Value> findValueRepeated(Integer times) {
		return cards.stream()
				.collect(groupingBy(h -> h.value, counting()))
				.entrySet()
				.stream()
				.filter(e -> e.getValue().equals(new Long(times)))
				.map(Entry::getKey)
				.collect(toSet());
	}

	private boolean allCardsAreDifferent() {
		return cards.size() == 5;
	}

	public static HandBuilder create() {
		return new HandBuilder();
	}

	public static class HandBuilder {
		@FunctionalInterface
		public interface AddSecondCard {
			AddThirdCard withSecondCard(Card card);
		}
		@FunctionalInterface
		public interface AddThirdCard {
			AddFourthCard withThirdCard(Card card);
		}
		@FunctionalInterface
		public interface AddFourthCard {
			AddFifthCard withFourthCard(Card card);
		}
		@FunctionalInterface
		public interface AddFifthCard {
			Builder withFifthCard(Card card);
		}
		@FunctionalInterface
		public interface Builder {
			Hand build();
		}
		private HandBuilder() {

		}
		public AddSecondCard withFirstCard(Card card) {
			return card2 -> card3 -> card4 -> card5 -> () -> new Hand(card, card2, card3, card4, card5);
		}
	}

}
