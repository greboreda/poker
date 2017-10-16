package com.greboreda.poker.hand;

import com.greboreda.poker.card.Card;
import com.greboreda.poker.card.Suit;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;
import com.greboreda.poker.hand.rank.RankCalculator;
import com.greboreda.poker.hand.rank.flush.FlushCalculator;
import com.greboreda.poker.hand.rank.fourofakind.FourOfAKindCalculator;
import com.greboreda.poker.hand.rank.fullhouse.FullHouseCalculator;
import com.greboreda.poker.hand.rank.royalflush.RoyalFlushCalculator;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

public class Hand {

	private static final List<RankCalculator> rankCalculators = new LinkedList<>();
	static {
		rankCalculators.add(new RoyalFlushCalculator());
		rankCalculators.add(new FourOfAKindCalculator());
		rankCalculators.add(new FullHouseCalculator());
		rankCalculators.add(new FlushCalculator());
	}

	private final Set<Card> cards;
	private final Rank rank;

	public Hand(Card card1, Card card2, Card card3, Card card4, Card card5) {
		final Card[] params = {card1, card2, card3, card4, card5};
		Validate.noNullElements(params);
		cards = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(params)));
		if (!allCardsAreDifferent()) {
			throw new IllegalStateException("cards must be different each other");
		}
		rank = calculateRank();
	}

	public Rank getRank() {
		return rank;
	}

	public Set<Suit> getDistinctSuits() {
		return cards.stream()
				.map(Card::getSuit)
				.collect(toSet());
	}

	public Set<Value> getCardsValues() {
		return cards.stream()
				.map(Card::getValue)
				.collect(toSet());
	}

	public Set<Value> findValueRepeated(Integer times) {
		return cards.stream()
				.collect(groupingBy(Card::getValue, counting()))
				.entrySet()
				.stream()
				.filter(e -> e.getValue().equals(new Long(times)))
				.map(Entry::getKey)
				.collect(toSet());
	}

	private boolean allCardsAreDifferent() {
		return cards.size() == 5;
	}

	private Rank calculateRank() {
		for(RankCalculator calculator : rankCalculators) {
			final Optional<Rank> rank = calculator.calculateRank(this);
			if(rank.isPresent()) {
				return rank.get();
			}
		}
		return null;
	}

}
