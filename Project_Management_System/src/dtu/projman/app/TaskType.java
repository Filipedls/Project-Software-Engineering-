package dtu.projman.app;


/*
 * @author Atakan Kaya
 */
public enum TaskType {
	MAIN {
		@Override
		public String toString() {
			return "Main Task";
		}
	}, HELPED{
		@Override
		public String toString() {
			return "Helped Task";
		}
	}
}
