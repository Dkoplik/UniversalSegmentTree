package segtrees;

public class AssignUpdater implements Updater<Long, Long> {
	@Override
	public Long apply(Long current, Long update, int len) {
		return update;
	}

	@Override
	public Long compose(Long existing, Long update) {
		return update;
	}

	@Override
	public Long neutral() {
		return null;
	}
}
