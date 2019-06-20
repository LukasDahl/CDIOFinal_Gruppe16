/*
Forfatter: Lukas
Ansvar: Klassen er oprettet som et interface med overordnede metoder for fejlhåndtering.
*/

package database.dal;

public interface IDALException {
	class DALException extends Exception {
		//Til Java serialisering...
		private static final long serialVersionUID = 7355418246336739229L;

		public DALException(String msg, Throwable e) {
			super(msg, e);
		}

		public DALException(String msg) {
			super(msg);
		}
	}
}
