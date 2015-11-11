package dtu.projman.app;

/*
 * @author Atakan Kaya
 */
public enum ProjectType {
	INTERNAL {
		@Override
		public String toString() {
			return "Internal";
		}
	},
	EXTERNAL {
		@Override
		public String toString() {
			return "External";
		}
	}
}
