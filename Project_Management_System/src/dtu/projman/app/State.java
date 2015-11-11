package dtu.projman.app;


/*
 * @author Atakan Kaya
 */
public enum State {
	NOTSTARTED {
		@Override
		public String toString() {
			return "Not Started";
		}
	}, 
	ONGOING {
		@Override
		public String toString() {
			return "Ongoing";
		}
	}, 
	PAUSED {
		@Override
		public String toString() {
			return "Paused";
		}
	}, 
	FINISHED {
		@Override
		public String toString() {
			return "Finished";
		}
	}
	
}
