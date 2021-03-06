package ch.tutteli.atrium.domain.robstoll.lib.creating.iterable.contains.creators

import ch.tutteli.atrium.assertions.Assertion
import ch.tutteli.atrium.assertions.AssertionGroup
import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.domain.creating.iterable.contains.IterableContains
import ch.tutteli.atrium.domain.creating.iterable.contains.searchbehaviours.InAnyOrderOnlySearchBehaviour
import ch.tutteli.atrium.domain.creating.iterable.contains.searchbehaviours.InAnyOrderSearchBehaviour
import ch.tutteli.atrium.domain.creating.iterable.contains.searchbehaviours.InOrderOnlyGroupedSearchBehaviour
import ch.tutteli.atrium.domain.creating.iterable.contains.searchbehaviours.InOrderOnlySearchBehaviour
import ch.tutteli.atrium.domain.robstoll.lib.creating.iterable.contains.builders.NoOpCheckerOption

fun <E, T : Iterable<E>> _containsValuesInAnyOrder(
    checkerOption: IterableContains.CheckerOption<E, T, InAnyOrderSearchBehaviour>,
    expected: List<E>
): Assertion = createAssertionGroup(checkerOption, expected, ::InAnyOrderValuesAssertionCreator)

fun <E : Any, T : Iterable<E?>> _containsEntriesInAnyOrder(
    checkerOption: IterableContains.CheckerOption<E?, T, InAnyOrderSearchBehaviour>,
    assertionCreators: List<(Expect<E>.() -> Unit)?>
): Assertion = createAssertionGroup(checkerOption, assertionCreators, ::InAnyOrderEntriesAssertionCreator)


fun <E, T : Iterable<E>> _containsValuesInAnyOrderOnly(
    builder: IterableContains.Builder<E, T, InAnyOrderOnlySearchBehaviour>,
    expected: List<E>
): Assertion {
    val checkerBuilder = NoOpCheckerOption(builder)
    return createAssertionGroupWithoutChecker(checkerBuilder, expected, ::InAnyOrderOnlyValuesAssertionCreator)
}

fun <E : Any, T : Iterable<E?>> _containsEntriesInAnyOrderOnly(
    builder: IterableContains.Builder<E?, T, InAnyOrderOnlySearchBehaviour>,
    assertionCreators: List<(Expect<E>.() -> Unit)?>
): Assertion {
    val checkerBuilder = NoOpCheckerOption(builder)
    return createAssertionGroupWithoutChecker(
        checkerBuilder, assertionCreators, ::InAnyOrderOnlyEntriesAssertionCreator
    )
}


fun <E, T : Iterable<E>> _containsValuesInOrderOnly(
    builder: IterableContains.Builder<E, T, InOrderOnlySearchBehaviour>,
    expected: List<E>
): Assertion {
    val checkerBuilder = NoOpCheckerOption(builder)
    return createAssertionGroupWithoutChecker(checkerBuilder, expected, ::InOrderOnlyValuesAssertionCreator)
}

fun <E : Any, T : Iterable<E?>> _containsEntriesInOrderOnly(
    builder: IterableContains.Builder<E?, T, InOrderOnlySearchBehaviour>,
    assertionCreators: List<(Expect<E>.() -> Unit)?>
): Assertion {
    val checkerBuilder = NoOpCheckerOption(builder)
    return createAssertionGroupWithoutChecker(checkerBuilder, assertionCreators, ::InOrderOnlyEntriesAssertionCreator)
}

fun <E, T : Iterable<E>> _containsValuesInOrderOnlyGrouped(
    builder: IterableContains.Builder<E, T, InOrderOnlyGroupedSearchBehaviour>,
    groups: List<List<E>>
): Assertion {
    val checkerBuilder = NoOpCheckerOption(builder)
    return createAssertionGroupWithoutChecker(checkerBuilder, groups, ::InOrderOnlyGroupedValuesAssertionCreator)
}

fun <E : Any, T : Iterable<E?>> _containsEntriesInOrderOnlyGrouped(
    builder: IterableContains.Builder<E?, T, InOrderOnlyGroupedSearchBehaviour>,
    groups: List<List<(Expect<E>.() -> Unit)?>>
): Assertion {
    val checkerBuilder = NoOpCheckerOption(builder)
    return createAssertionGroupWithoutChecker(checkerBuilder, groups, ::InOrderOnlyGroupedEntriesAssertionCreator)
}


private fun <E, T : Iterable<E>, SC, S : IterableContains.SearchBehaviour> createAssertionGroupWithoutChecker(
    checkerOption: IterableContains.CheckerOption<E, T, S>,
    expected: List<SC>,
    factory: (S) -> IterableContains.Creator<T, SC>
): AssertionGroup {
    val creator = factory(checkerOption.containsBuilder.searchBehaviour)
    return creator.createAssertionGroup(checkerOption.containsBuilder.subjectProvider, expected)
}

private fun <E, T : Iterable<E>, SC, S : IterableContains.SearchBehaviour> createAssertionGroup(
    checkerOption: IterableContains.CheckerOption<E, T, S>,
    expected: List<SC>,
    factory: (S, List<IterableContains.Checker>) -> IterableContains.Creator<T, SC>
): AssertionGroup {
    val creator = factory(checkerOption.containsBuilder.searchBehaviour, checkerOption.checkers)
    return creator.createAssertionGroup(checkerOption.containsBuilder.subjectProvider, expected)
}
