package com.greboreda.poker.hand.rank.fourofakind;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.Comparision;
import com.greboreda.poker.hand.rank.RankFactory;
import com.greboreda.poker.hand.rank.royalflush.RoyalFlush;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FourOfAKindTest {

	@Test
	public void when_comparingFourOfAKindWithRoyalFlush_then_resultIsLoose() {

		final FourOfAKind fourOfAKind = RankFactory.createFourOfAKind();
		final RoyalFlush royalFlush = RankFactory.createRoyalFlush();

		assertThat(fourOfAKind.compare(royalFlush), is(Comparision.LOOSE));
	}

	@Test
	public void when_comparingWithAnotherFourOfAKindIfHasBetterValue_then_resultIsWin() {

		final FourOfAKind aFourOfAKind = FourOfAKind.create()
				.of(Value.ACE)
				.withKicker(Value.KING)
				.build();

		final FourOfAKind anotherFourOfAKind = FourOfAKind.create()
				.of(Value.QUEEN)
				.withKicker(Value.KING)
				.build();

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(Comparision.WIN));
	}

	@Test
	public void when_comparingWithAnotherFourOfAKindIfHasWorseValue_then_resultIsLoose() {

		final FourOfAKind aFourOfAKind = FourOfAKind.create()
				.of(Value.TEN)
				.withKicker(Value.KING)
				.build();

		final FourOfAKind anotherFourOfAKind = FourOfAKind.create()
				.of(Value.QUEEN)
				.withKicker(Value.KING)
				.build();

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(Comparision.LOOSE));
	}

	@Test
	public void when_comparingWithAnotherFourOfAKindIfHasSameValueButBetterKicker_then_resultIsWin() {

		final FourOfAKind aFourOfAKind = FourOfAKind.create()
				.of(Value.ACE)
				.withKicker(Value.KING)
				.build();

		final FourOfAKind anotherFourOfAKind = FourOfAKind.create()
				.of(Value.ACE)
				.withKicker(Value.QUEEN)
				.build();

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(Comparision.WIN));
	}

	@Test
	public void when_comparingWithAnotherFourOfAKindIfHasSameValueButWorseKicker_then_resultIsLoose() {

		final FourOfAKind aFourOfAKind = FourOfAKind.create()
				.of(Value.ACE)
				.withKicker(Value.QUEEN)
				.build();

		final FourOfAKind anotherFourOfAKind = FourOfAKind.create()
				.of(Value.ACE)
				.withKicker(Value.KING)
				.build();

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(Comparision.LOOSE));
	}

	@Test
	public void when_comparingWithAnotherFourOfAKindIfHasSameValueAndKicker_then_resultIsTie() {

		final FourOfAKind aFourOfAKind = FourOfAKind.create()
				.of(Value.ACE)
				.withKicker(Value.KING)
				.build();

		final FourOfAKind anotherFourOfAKind = FourOfAKind.create()
				.of(Value.ACE)
				.withKicker(Value.KING)
				.build();

		assertThat(aFourOfAKind.compare(anotherFourOfAKind), is(Comparision.TIE));
	}

}