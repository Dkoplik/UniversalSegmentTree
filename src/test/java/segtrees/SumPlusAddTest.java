package segtrees;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class SumPlusAddTest {
	@Test
	void smallTest() {
		Long[] a = { 1L, 2L, 3L, 4L, 5L };
		SegmentTree<Long, Long> st = new SegmentTree<>(a, new SumCombiner(), new AddUpdater());
		assertEquals(9L, st.query(1, 4));
		st.update(0, 5, 3L);
		assertEquals(15L, st.query(0, 3));
	}

	@Test
	void fullRangeUpdate() {
		Long[] a = { 10L, 20L };
		SegmentTree<Long, Long> st = new SegmentTree<>(a, new SumCombiner(), new AddUpdater());
		assertEquals(30L, st.query(0, 2));
		st.update(0, 2, 5L);
		assertEquals(40L, st.query(0, 2));
	}

	@Test
	void edgeCases() {
		Long[] a = { 5L };
		SegmentTree<Long, Long> st = new SegmentTree<>(a, new SumCombiner(), new AddUpdater());
		assertEquals(5L, st.query(0, 1));
		st.update(0, 1, 10L);
		assertEquals(15L, st.query(0, 1));
	}

	@Test
	void multipleUpdates() {
		Long[] a = new Long[1000];
		Arrays.fill(a, 0L);
		SegmentTree<Long, Long> st = new SegmentTree<>(a, new SumCombiner(), new AddUpdater());
		st.update(0, 1000, 1L);
		st.update(500, 1000, 2L);
		assertEquals(1000L + 500 * 2, st.query(0, 1000));
	}

	@Test
	void noUpdate() {
		Long[] a = { 3L, 7L };
		SegmentTree<Long, Long> st = new SegmentTree<>(a, new SumCombiner(), new AddUpdater());
		assertEquals(10L, st.query(0, 2));
	}
}