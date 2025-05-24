package segtrees;

import java.util.Random;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StressRandomTest {
	@Test
	void compareWithNaiveRandomLength() {
		Random rnd = new Random(12345);
		int n = rnd.nextInt(1_000);
		int ops = 10_000;
		Long[] a = new Long[n];
		Arrays.fill(a, rnd.nextLong());
		SegmentTree<Long, Long> st = new SegmentTree<>(a, new SumCombiner(), new AddUpdater());
		NaiveArray<Long, Long> na = new NaiveArray<>(a, new SumCombiner(), new AddUpdater());
		for (int i = 0; i < ops; i++) {
			if (rnd.nextBoolean()) {
				int l = rnd.nextInt(n);
				int r = rnd.nextInt(n - l) + l + 1;
				Long update = rnd.nextLong(1000);
				st.update(l, r, update);
				na.update(l, r, update);
				assertEquals(na.query(l, r), st.query(l, r));
			} else {
				int l = rnd.nextInt(n);
				int r = rnd.nextInt(n - l) + l + 1;
				assertEquals(na.query(l, r), st.query(l, r));
			}
		}
	}

	@Test
	void compareWithNaiveBigArray() {
		Random rnd = new Random(12345);
		int n = 10_000;
		int ops = 100_000;
		Long[] a = new Long[n];
		Arrays.fill(a, rnd.nextLong());
		SegmentTree<Long, Long> st = new SegmentTree<>(a, new SumCombiner(), new AddUpdater());
		NaiveArray<Long, Long> na = new NaiveArray<>(a, new SumCombiner(), new AddUpdater());
		for (int i = 0; i < ops; i++) {
			if (rnd.nextBoolean()) {
				int l = rnd.nextInt(n);
				int r = rnd.nextInt(n - l) + l + 1;
				Long update = rnd.nextLong(1000);
				st.update(l, r, update);
				na.update(l, r, update);
				assertEquals(na.query(l, r), st.query(l, r));
			} else {
				int l = rnd.nextInt(n);
				int r = rnd.nextInt(n - l) + l + 1;
				assertEquals(na.query(l, r), st.query(l, r));
			}
		}
	}
}
