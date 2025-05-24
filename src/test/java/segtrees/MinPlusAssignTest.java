package segtrees;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class MinPlusAssignTest {
	@Test
	void minTest() {
		Long[] a = { 5L, 3L, 2L, 4L, 1L };
		SegmentTree<Long, Long> st = new SegmentTree<>(a, new MinCombiner(), new AssignUpdater());
		assertEquals(2L, st.query(0, 3));
		st.update(0, 5, 0L);
		assertEquals(0L, st.query(0, 5));
	}

	@Test
	void singleElement() {
		Long[] a = { 10L };
		SegmentTree<Long, Long> st = new SegmentTree<>(a, new MinCombiner(), new AssignUpdater());
		assertEquals(10L, st.query(0, 1));
		st.update(0, 1, 5L);
		assertEquals(5L, st.query(0, 1));
	}

	@Test
	void overlappingRanges() {
		Long[] a = { 9L, 8L, 7L, 6L, 5L };
		SegmentTree<Long, Long> st = new SegmentTree<>(a, new MinCombiner(), new AssignUpdater());
		st.update(0, 3, 2L);
		assertEquals(2L, st.query(0, 3));
		st.update(2, 5, 1L);
		assertEquals(1L, st.query(2, 5));
	}

	@Test
	void multipleAssignments() {
		Long[] a = new Long[100];
		Arrays.fill(a, 100L);
		SegmentTree<Long, Long> st = new SegmentTree<>(a, new MinCombiner(), new AssignUpdater());
		st.update(0, 100, 50L);
		st.update(50, 100, 25L);
		assertEquals(25L, st.query(0, 100));
	}

	@Test
	void noUpdateMin() {
		Long[] a = { 3L, 7L, 2L };
		SegmentTree<Long, Long> st = new SegmentTree<>(a, new MinCombiner(), new AssignUpdater());
		assertEquals(2L, st.query(0, 3));
	}
}